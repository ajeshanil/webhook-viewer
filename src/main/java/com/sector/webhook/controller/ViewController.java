package com.sector.webhook.controller;

import com.sector.webhook.service.MessageStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    private final MessageStore store;

    public ViewController(MessageStore store) {
        this.store = store;
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        model.addAttribute("messages", store.all());
        return "messages";
    }
}
