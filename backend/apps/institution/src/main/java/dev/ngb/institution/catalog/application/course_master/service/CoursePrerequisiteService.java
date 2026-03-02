package dev.ngb.institution.catalog.application.course_master.service;

import dev.ngb.domain.ApplicationService;
import dev.ngb.domain.catalog.model.CoursePrerequisite;
import dev.ngb.domain.catalog.repository.CoursePrerequisiteRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CoursePrerequisiteService implements ApplicationService {

    private final CoursePrerequisiteRepository coursePrerequisiteRepository;

    public void replacePrerequisites(String courseCode, List<String> newPrerequisiteCodes) {
        if (newPrerequisiteCodes == null) {
            newPrerequisiteCodes = List.of();
        }

        Set<String> descendantCodes = coursePrerequisiteRepository.findByAncestorCode(courseCode).stream()
                .map(CoursePrerequisite::getDescendantCode)
                .collect(Collectors.toSet());

        Set<String> toRebuild = new LinkedHashSet<>();
        toRebuild.add(courseCode);
        toRebuild.addAll(descendantCodes);

        List<CoursePrerequisite> existingEntries = coursePrerequisiteRepository.findByDescendantCodeIn(toRebuild);

        Map<String, List<String>> directPrereqsMap = existingEntries.stream()
                .filter(cp -> cp.getDepth() == 1)
                .collect(Collectors.groupingBy(
                        CoursePrerequisite::getDescendantCode,
                        Collectors.mapping(CoursePrerequisite::getAncestorCode, Collectors.toList())
                ));
        for (String code : toRebuild) {
            directPrereqsMap.putIfAbsent(code, List.of());
        }
        directPrereqsMap.put(courseCode, newPrerequisiteCodes);

        Set<String> externalPrereqs = directPrereqsMap.values().stream()
                .flatMap(Collection::stream)
                .filter(p -> !toRebuild.contains(p))
                .collect(Collectors.toSet());

        Map<String, Map<String, Integer>> externalAncestorsCache = loadExternalAncestors(externalPrereqs);

        if (!existingEntries.isEmpty()) {
            coursePrerequisiteRepository.deleteAll(existingEntries);
        }

        Map<String, Map<String, Integer>> closureCache = new HashMap<>();
        for (String code : toRebuild) {
            resolveAncestors(code, directPrereqsMap, closureCache, toRebuild, externalAncestorsCache);
        }

        List<CoursePrerequisite> newEntries = new ArrayList<>();
        for (var entry : closureCache.entrySet()) {
            String descendant = entry.getKey();
            for (var ancestorEntry : entry.getValue().entrySet()) {
                newEntries.add(CoursePrerequisite.create(
                        ancestorEntry.getKey(), descendant, ancestorEntry.getValue()
                ));
            }
        }

        if (!newEntries.isEmpty()) {
            coursePrerequisiteRepository.saveAll(newEntries);
        }
    }

    private Map<String, Map<String, Integer>> loadExternalAncestors(Set<String> externalPrereqs) {
        if (externalPrereqs.isEmpty()) {
            return Map.of();
        }
        Map<String, Map<String, Integer>> cache = new HashMap<>();
        for (CoursePrerequisite cp : coursePrerequisiteRepository.findByDescendantCodeIn(externalPrereqs)) {
            cache.computeIfAbsent(cp.getDescendantCode(), k -> new HashMap<>())
                    .merge(cp.getAncestorCode(), cp.getDepth(), Math::min);
        }
        return cache;
    }

    private Map<String, Integer> resolveAncestors(
            String code,
            Map<String, List<String>> directPrereqsMap,
            Map<String, Map<String, Integer>> closureCache,
            Set<String> rebuildCodes,
            Map<String, Map<String, Integer>> externalAncestorsCache
    ) {
        if (closureCache.containsKey(code)) {
            return closureCache.get(code);
        }

        Map<String, Integer> ancestors = new HashMap<>();
        List<String> directPrereqs = directPrereqsMap.getOrDefault(code, List.of());

        for (String prereq : directPrereqs) {
            ancestors.merge(prereq, 1, Math::min);

            Map<String, Integer> prereqAncestors;
            if (rebuildCodes.contains(prereq)) {
                prereqAncestors = resolveAncestors(prereq, directPrereqsMap, closureCache, rebuildCodes, externalAncestorsCache);
            } else {
                prereqAncestors = externalAncestorsCache.getOrDefault(prereq, Map.of());
            }

            for (var entry : prereqAncestors.entrySet()) {
                ancestors.merge(entry.getKey(), entry.getValue() + 1, Math::min);
            }
        }

        closureCache.put(code, ancestors);
        return ancestors;
    }
}
