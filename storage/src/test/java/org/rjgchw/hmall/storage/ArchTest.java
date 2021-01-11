package org.rjgchw.hmall.storage;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.rjgchw.hmall.storage");

        noClasses()
            .that()
                .resideInAnyPackage("org.rjgchw.hmall.storage.service..")
            .or()
                .resideInAnyPackage("org.rjgchw.hmall.storage.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..org.rjgchw.hmall.storage.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
