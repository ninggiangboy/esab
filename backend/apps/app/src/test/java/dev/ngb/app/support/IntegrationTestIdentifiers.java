package dev.ngb.app.support;

import java.util.UUID;

/**
 * Stable, unique values for integration tests (emails, etc.).
 */
public final class IntegrationTestIdentifiers {

    private IntegrationTestIdentifiers() {
    }

    public static String uniqueIntegrationEmail() {
        return "it-" + UUID.randomUUID() + "@integration.test";
    }
}
