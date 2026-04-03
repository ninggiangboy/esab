<script setup lang="ts">
import DocToc from '@/docs/components/DocToc.vue'
import MdxRenderer from '@/docs/components/MdxRenderer.vue'
import DocsLayout from '@/docs/layouts/DocsLayout.vue'
import { loadDocSource } from '@/docs/lib/loadDocSource'
import { renderDoc, type ParsedDoc } from '@/docs/mdx/renderDoc'
import { computed, ref, watch } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const slug = computed(() => {
  const section = route.params.section as string
  const s = route.params.slug as string
  return `${section}/${s}`
})

const parsed = ref<ParsedDoc | null>(null)
const notFound = ref(false)
const loading = ref(true)

async function load() {
  loading.value = true
  notFound.value = false
  const raw = await loadDocSource(slug.value)
  if (!raw) {
    parsed.value = null
    notFound.value = true
    loading.value = false
    return
  }
  parsed.value = await renderDoc(raw)
  loading.value = false
}

watch(slug, load, { immediate: true })
</script>

<template>
  <DocsLayout>
    <template #toc>
      <DocToc v-if="parsed?.toc?.length" :toc="parsed.toc" />
    </template>

    <div class="docs-article px-5 py-8 lg:px-10 lg:py-10 min-h-[60vh]">
      <div v-if="loading" class="text-muted-foreground text-sm">Loading…</div>
      <div v-else-if="notFound" class="text-destructive text-sm">Document not found.</div>
      <template v-else-if="parsed">
        <h1 class="text-[40px] font-semibold tracking-tight text-foreground scroll-mt-24">
          {{ parsed.frontmatter.title ?? 'Untitled' }}
        </h1>
        <p v-if="parsed.frontmatter.description" class="text-muted-foreground mt-2 text-[15px] leading-relaxed max-w-3xl">
          {{ parsed.frontmatter.description }}
        </p>
        <div class="mt-8 w-full min-w-0">
          <MdxRenderer :segments="parsed.segments" />
        </div>
      </template>
    </div>
  </DocsLayout>
</template>
