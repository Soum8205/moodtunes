package com.nsoumanjan.moodtunes;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private String getAccessToken() throws Exception {
        String credentials = clientId + ":" + clientSecret;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://accounts.spotify.com/api/token"))
            .header("Authorization", "Basic " + encoded)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        String token = body.split("\"access_token\":\"")[1].split("\"")[0];
        return token;
    }

    public String getSongsByMood(String mood) {
        try {
            String token = getAccessToken();

            String query = switch (mood.toLowerCase()) {
                case "happy" -> "happy upbeat";
                case "sad" -> "sad emotional";
                case "energetic" -> "energetic workout";
                case "calm" -> "calm relaxing";
                case "angry" -> "aggressive intense";
                default -> "popular hits";
            };

            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.spotify.com/v1/search?q=" + 
                    encodedQuery + "&type=track&limit=5"))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, 
                HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    public String searchSongs(String query) {
        try {
            String token = getAccessToken();

            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.spotify.com/v1/search?q=" +
                    encodedQuery + "&type=track&limit=5"))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
            HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }
}

