package com.desabisc.guide.camel.processeg.egc;

/**
 * A simple Message Translator implemented as a Bean
 * This class provides methods to transform messages
 */
public class MessageTranslator {
    
    /**
     * Transforms a string message to uppercase
     * This method is used by Camel as a translator
     * 
     * @param message The message to transform
     * @return The transformed message
     */
    public String toUpperCase(String message) {
        return message != null ? message.toUpperCase() : null;
    }
    
    /**
     * Transforms a string message to lowercase
     * 
     * @param message The message to transform
     * @return The transformed message
     */
    public String toLowerCase(String message) {
        return message != null ? message.toLowerCase() : null;
    }
    
    /**
     * Reverses a string message
     * 
     * @param message The message to transform
     * @return The reversed message
     */
    public String reverse(String message) {
        if (message == null) {
            return null;
        }
        return new StringBuilder(message).reverse().toString();
    }
}