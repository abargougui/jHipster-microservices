package fr.magnolia.dsi.signaturemanager;

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
            .importPackages("fr.magnolia.dsi.signaturemanager");

        noClasses()
            .that()
                .resideInAnyPackage("fr.magnolia.dsi.signaturemanager.service..")
            .or()
                .resideInAnyPackage("fr.magnolia.dsi.signaturemanager.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..fr.magnolia.dsi.signaturemanager.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
