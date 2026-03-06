package com.nsoumanjan.moodtunes;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    
}
