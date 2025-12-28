package com.sector.webhook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sector.webhook.service.MessageStore;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/webhook")
    public ResponseEntity<String> verify(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge) {

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            System.out.println("Subscribing to hub  - " +  mode + " - "+ challenge);
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> receive(@RequestBody String payload) {
        System.out.println("Payload : \n" +  payload);
        try {
            Object jsonObject = objectMapper.readValue(payload, Object.class);
            payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            System.out.println("Error converting object to json");
        }
        store.add(payload);
        return ResponseEntity.ok().build();
    }
}
