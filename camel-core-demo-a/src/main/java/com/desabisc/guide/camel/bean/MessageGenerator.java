package com.desabisc.guide.camel.bean;

// The bean class that our route will use
  public class MessageGenerator {

    public String generateMessage() {
      return "Hello from the Bean component! Time: " + System.currentTimeMillis();
    }

    public String processMessage(String input) {
      return "PROCESSED: " + input.toUpperCase();
    }
  }