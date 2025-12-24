package com.sector.webhook.controller;

import com.sector.webhook.service.MessageStore;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class WebhookController {

    private static final String VERIFY_TOKEN = "sector7_verify";
    private final MessageStore store;

    public WebhookController(MessageStore store) {
        this.store = store;
    }

    @GetMapping("/webhook")
    public ResponseEntity<String> verify(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge) {

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> receive(@RequestBody String payload) {
        store.add(payload);
        return ResponseEntity.ok().build();
    }
}
