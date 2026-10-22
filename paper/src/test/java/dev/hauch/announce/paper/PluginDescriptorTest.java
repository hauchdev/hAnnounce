package dev.hauch.announce.paper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.junit.jupiter.api.Test;

/**
 * Validates the plugin descriptor ({@code plugin.yml}) so that descriptor errors fail the
 * build instead of the server startup.
 */
class PluginDescriptorTest {

    private static Properties descriptor() {
        try (InputStream in = PluginDescriptorTest.class.getResourceAsStream("/plugin.yml")) {
            assertNotNull(in, "plugin.yml must be present in the paper module resources");
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read plugin.yml from resources", e);
        }
    }

    @Test
    void descriptorDeclaresPluginIdentity() {
        Properties properties = descriptor();
        assertEquals("hAnnounce", properties.getProperty("name"));
        assertEquals("dev.hauch.announce.paper.HAnnouncePlugin", properties.getProperty("main"));
        assertNotNull(properties.getProperty("api-version"), "api-version must be declared");
        assertFalse(properties.getProperty("main", "").isBlank(), "main class must not be blank");
    }

    @Test
    void versionPlaceholderIsExpandedByBuild() {
        Properties properties = descriptor();
        String version = properties.getProperty("version", "");
        assertFalse(version.contains("${version}"), "version placeholder must be expanded during processResources");
        assertFalse(version.isBlank(), "plugin version must not be empty");
    }
}
