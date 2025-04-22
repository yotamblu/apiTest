package com.example.helloapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HelloController {
    
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    
    @GetMapping("/hello")
    public String hello() {
        logger.info("Received request to /hello endpoint");
        return "hello world";
    }

    @GetMapping("/user")
    public String user(@RequestParam String name, @RequestParam int age) {
        logger.info("Received request to /user endpoint with name: {} and age: {}", name, age);
        return String.format("Hello %s, you are %d years old", name, age);
    }

    @GetMapping(value = "/pizza", produces = "text/html")
    public String giveTheGuySomePizza() {
        return "<!DOCTYPE html>" +
               "<html>" +
               "<head>" +
               "<style>" +
               "body { background-color: #f5f5f5; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }" +
               ".pizza { font-family: 'Courier New', monospace; font-size: 20px; line-height: 1.2; text-align: center; color: #333; }" +
               ".title { font-family: Arial, sans-serif; font-size: 24px; color: #d35400; margin-bottom: 20px; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='pizza'>" +
               "<div class='title'>🍕 Fresh Hot Pizza! 🍕</div>" +
               "<pre>" +
               "       _..--\"\"--._\n" +
               "     .'    o  o    '.\n" +
               "    /     o  o  o    \\\n" +
               "   :     o  o  o  o  :\n" +
               "   |    o  o  o  o   |\n" +
               "   :    o  o  o  o   :\n" +
               "    \\   o  o  o     /\n" +
               "     '.  o  o     .'\n" +
               "       `-.....-'" +
               "</pre>" +
               "</div>" +
               "</body>" +
               "</html>";
    }



    
    

} 