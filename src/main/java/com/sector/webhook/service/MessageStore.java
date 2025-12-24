package com.sector.webhook.service;

import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MessageStore {
    private final List<String> messages = new CopyOnWriteArrayList<>();

    public void add(String msg) {
        messages.add(Instant.now() + " | " + msg);
    }

    public List<String> all() {
        return messages;
    }
}
