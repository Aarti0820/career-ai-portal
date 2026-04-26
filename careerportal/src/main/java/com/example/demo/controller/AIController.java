package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/ai")
@CrossOrigin
public class AIController {

    @Value("${openrouter.api.key}")
    private String apiKey;

    @GetMapping("/suggest")
    public String chat(@RequestParam String skill) {

        String url = "https://openrouter.ai/api/v1/chat/completions";

        RestTemplate rt = new RestTemplate();

        // 🔥 SMART SYSTEM PROMPT (ChatGPT style)
        Map<String,String> system = new HashMap<>();
        system.put("role","system");
        system.put("content",
        		"You are a friendly chatbot.\n" +

        		"Rules:\n" +
        		"- Reply ONLY to user's current message\n" +
        		"- Do NOT include examples in your answer\n" +
        		"- Do NOT mention 'User' or 'AI'\n" +
        		"- Keep replies short (1 line usually)\n" +
        		"- Be natural and human-like 😊\n" +
        		"- Do NOT continue old topics unless asked\n" +
        		"- Stay relevant to the question only\n"
        		);

        // USER MESSAGE
        Map<String,String> user = new HashMap<>();
        user.put("role","user");
        user.put("content", skill);

        List<Map<String,String>> messages = new ArrayList<>();
        messages.add(system);
        messages.add(user);

        // REQUEST BODY
        Map<String,Object> body = new HashMap<>();
        body.put("model","openai/gpt-3.5-turbo");
        body.put("messages",messages);
        body.put("temperature", 0.5);
        body.put("max_tokens", 60);       // 🔥 medium length

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer " + apiKey);
        headers.set("Referer","http://localhost:8080");
        headers.set("X-Title","AI Career Portal");
        headers.setContentType(MediaType.APPLICATION_JSON); 

        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body, headers);

        try {

            ResponseEntity<Map> response =
                    rt.postForEntity(url, entity, Map.class);

            Map choice = (Map)((List)response.getBody().get("choices")).get(0);
            Map msg = (Map)choice.get("message");

            return msg.get("content").toString();

        } catch(Exception e){
            e.printStackTrace();
            System.out.println("KEY = " + apiKey);
            return "AI error 😅";
        }
    }
}