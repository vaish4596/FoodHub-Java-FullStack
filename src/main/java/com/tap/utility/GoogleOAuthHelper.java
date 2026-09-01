package com.tap.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Minimal Google OAuth 2.0 helper using only JDK APIs (no extra JSON libraries).
 */
public final class GoogleOAuthHelper {

    private GoogleOAuthHelper() {
    }

    public static String buildAuthorizationUrl(GoogleOAuthConfig config) {
        return "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + urlEncode(config.getClientId())
                + "&redirect_uri=" + urlEncode(config.getRedirectUri())
                + "&response_type=code"
                + "&scope=" + urlEncode("openid email profile")
                + "&access_type=online"
                + "&prompt=select_account";
    }

    public static Map<String, String> exchangeCodeForToken(GoogleOAuthConfig config, String code)
            throws Exception {

        String body = "code=" + urlEncode(code)
                + "&client_id=" + urlEncode(config.getClientId())
                + "&client_secret=" + urlEncode(config.getClientSecret())
                + "&redirect_uri=" + urlEncode(config.getRedirectUri())
                + "&grant_type=authorization_code";

        String json = httpPost("https://oauth2.googleapis.com/token", body, "application/x-www-form-urlencoded");
        Map<String, String> map = parseSimpleJson(json);

        if (!map.containsKey("access_token")) {
            throw new IllegalStateException("Google token response did not include access_token");
        }
        return map;
    }

    public static Map<String, String> fetchUserInfo(String accessToken) throws Exception {
        String json = httpGet("https://www.googleapis.com/oauth2/v2/userinfo", accessToken);
        return parseSimpleJson(json);
    }

    private static String httpPost(String urlString, String body, String contentType) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", contentType);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.getBytes(StandardCharsets.UTF_8));
        }

        return readResponse(conn);
    }

    private static String httpGet(String urlString, String accessToken) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        return readResponse(conn);
    }

    private static String readResponse(HttpURLConnection conn) throws Exception {
        int status = conn.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                status >= 400 ? conn.getErrorStream() : conn.getInputStream(),
                StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        if (status >= 400) {
            throw new IllegalStateException("Google API error (" + status + "): " + sb);
        }
        return sb.toString();
    }

    /** Parses flat string JSON objects like {"email":"a@b.com","name":"A"}. */
    static Map<String, String> parseSimpleJson(String json) {
        Map<String, String> result = new HashMap<>();
        if (json == null || json.isBlank()) {
            return result;
        }

        String trimmed = json.trim();
        if (trimmed.startsWith("{")) {
            trimmed = trimmed.substring(1);
        }
        if (trimmed.endsWith("}")) {
            trimmed = trimmed.substring(0, trimmed.length() - 1);
        }

        boolean inString = false;
        boolean escape = false;
        StringBuilder token = new StringBuilder();
        java.util.List<String> parts = new java.util.ArrayList<>();

        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if (escape) {
                token.append(c);
                escape = false;
                continue;
            }
            if (c == '\\') {
                escape = true;
                continue;
            }
            if (c == '"') {
                inString = !inString;
                token.append(c);
                continue;
            }
            if (c == ',' && !inString) {
                parts.add(token.toString().trim());
                token.setLength(0);
                continue;
            }
            token.append(c);
        }
        if (token.length() > 0) {
            parts.add(token.toString().trim());
        }

        for (String part : parts) {
            int colon = part.indexOf(':');
            if (colon <= 0) {
                continue;
            }
            String key = stripQuotes(part.substring(0, colon).trim());
            String value = stripQuotes(part.substring(colon + 1).trim());
            result.put(key, value);
        }
        return result;
    }

    private static String stripQuotes(String value) {
        if (value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    private static String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
