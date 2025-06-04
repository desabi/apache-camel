package https.ega;

// Java 21 Record for User data
record User(long id, String name, String email, String gender, String status) {

    // Compact constructor with validation using Java 21 features
    public User {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    // Using Java 21 string templates and pattern matching
    public String getFormattedInfo() {
        return switch (status.toLowerCase()) {
            case "active" -> """
                    🟢 ACTIVE USER
                    Name: %s
                    Email: %s
                    """.formatted(name, email);
            case "inactive" -> """
                    🔴 INACTIVE USER
                    Name: %s
                    Email: %s
                    """.formatted(name, email);
            default -> "Unknown status for user: " + name;
        };
    }
}