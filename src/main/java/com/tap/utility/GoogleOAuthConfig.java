package com.tap.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jakarta.servlet.ServletContext;

/**
 * Loads Google OAuth configuration.
 *
 * Production (Render):
 * Reads GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET,
 * and GOOGLE_REDIRECT_URI from environment variables.
 *
 * Local development:
 * Falls back to WEB-INF/google-oauth.properties.
 */
public final class GoogleOAuthConfig {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    private GoogleOAuthConfig(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public static GoogleOAuthConfig load(ServletContext context) throws IOException {

        // First try environment variables (Render / Production)
        String clientId = System.getenv("GOOGLE_CLIENT_ID");
        String clientSecret = System.getenv("GOOGLE_CLIENT_SECRET");
        String redirectUri = System.getenv("GOOGLE_REDIRECT_URI");

        // If environment variables are not available,
        // use local google-oauth.properties
        if (clientId == null || clientId.isBlank()
                || clientSecret == null || clientSecret.isBlank()
                || redirectUri == null || redirectUri.isBlank()) {

            Properties props = new Properties();

            try (InputStream in =
                    context.getResourceAsStream("/WEB-INF/google-oauth.properties")) {

                if (in == null) {
                    throw new IOException(
                        "Google OAuth configuration not found. "
                        + "Set GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, "
                        + "GOOGLE_REDIRECT_URI environment variables."
                    );
                }

                props.load(in);
            }

            clientId = props.getProperty("google.clientId", "").trim();
            clientSecret = props.getProperty("google.clientSecret", "").trim();
            redirectUri = props.getProperty("google.redirectUri", "").trim();
        }

        return new GoogleOAuthConfig(
                clientId.trim(),
                clientSecret.trim(),
                redirectUri.trim()
        );
    }

    public boolean isConfigured() {
        return !clientId.isEmpty()
                && !clientSecret.isEmpty()
                && !redirectUri.isEmpty();
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}