package com.tap.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jakarta.servlet.ServletContext;

/**
 * Loads Google OAuth client settings from WEB-INF/google-oauth.properties.
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
        Properties props = new Properties();
        try (InputStream in = context.getResourceAsStream("/WEB-INF/google-oauth.properties")) {
            if (in == null) {
                throw new IOException("Missing WEB-INF/google-oauth.properties");
            }
            props.load(in);
        }

        String clientId = props.getProperty("google.clientId", "").trim();
        String clientSecret = props.getProperty("google.clientSecret", "").trim();
        String redirectUri = props.getProperty("google.redirectUri", "").trim();

        return new GoogleOAuthConfig(clientId, clientSecret, redirectUri);
    }

    public boolean isConfigured() {
        return !clientId.isEmpty() && !clientSecret.isEmpty() && !redirectUri.isEmpty();
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
