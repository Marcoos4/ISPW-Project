package it.ispw.unilife.boundary;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GithubAuthBoundary {

    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";

    private static final String REDIRECT_URI = "http://localhost/callback";
    private static final String AUTH_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID
            + "&redirect_uri=" + REDIRECT_URI + "&scope=user:email";

    private String capturedCode = null;

    public String executeLoginAndFetchProfile() throws Exception {
        String code = getAuthorizationCode();
        if (code == null) return null;

        String accessToken = exchangeCodeForToken(code);
        if (accessToken == null) return null;

        return fetchUserProfile(accessToken);
    }

    private String getAuthorizationCode() {
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();

        CookieHandler.setDefault(new CookieManager());
        engine.getHistory();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Login GitHub");
        stage.setScene(new Scene(webView, 600, 700));

        engine.locationProperty().addListener((obs, oldUrl, newUrl) -> {
            if (newUrl != null) {
                if (newUrl.contains("code=")) {
                    String[] parts = newUrl.split("code=");
                    if (parts.length > 1) {
                        this.capturedCode = parts[1].split("&")[0];
                        stage.close();
                    }
                }
            }
        });

        engine.load(AUTH_URL);
        stage.showAndWait();

        return this.capturedCode;
    }

    private String exchangeCodeForToken(String code) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String params = "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + code;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://github.com/login/oauth/access_token"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(params))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return extractJsonValue(response.body(), "access_token");
    }

    private String fetchUserProfile(String token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/user"))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private String extractJsonValue(String json, String key) {
        Matcher matcher = Pattern.compile("\"" + key + "\":\\s*\"([^\"]+)\"").matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }
}