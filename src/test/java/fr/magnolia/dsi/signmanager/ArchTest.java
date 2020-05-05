package fr.magnolia.dsi.signmanager;

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
            .importPackages("fr.magnolia.dsi.signmanager");

        noClasses()
            .that()
                .resideInAnyPackage("fr.magnolia.dsi.signmanager.service..")
            .or()
                .resideInAnyPackage("fr.magnolia.dsi.signmanager.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..fr.magnolia.dsi.signmanager.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
