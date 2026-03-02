package dev.ngb.institution;

import dev.ngb.application.UseCaseService;
import dev.ngb.constant.PackageConstants;
import dev.ngb.domain.ApplicationService;
import dev.ngb.domain.Repository;
import dev.ngb.infrastructure.AdapterService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        basePackages = PackageConstants.BASE_PACKAGE,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        UseCaseService.class,
                        AdapterService.class,
                        ApplicationService.class,
                        Repository.class
                }
        )
)
public class InstitutionApplication {
}
