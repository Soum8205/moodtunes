package com.nsoumanjan.moodtunes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")

public class MoodController {
    private final SpotifyService spotifyService;

    public MoodController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }
    
    @GetMapping("/songs")
    public ResponseEntity<String> getSongsByMood(@RequestParam String mood) {
        String songs = spotifyService.getSongsByMood(mood);
        return ResponseEntity.ok(songs);
    }
    
    @GetMapping("/search")
    public ResponseEntity<String> searchSongs(@RequestParam String query) {
        String songs = spotifyService.searchSongs(query);
        return ResponseEntity.ok(songs);
    }
}

