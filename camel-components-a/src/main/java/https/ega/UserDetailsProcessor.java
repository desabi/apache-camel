package https.ega;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

// User Details Processor - Extracts and logs user details
class UserDetailsProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        JsonNode users = (JsonNode) exchange.getIn().getHeader("users");

        if (users != null && users.isArray()) {
            System.out.println("\n📋 USER DETAILS:");
            System.out.println("================");

            // Using Java 21 features - Enhanced pattern matching and records
            for (JsonNode user : users) {
                var userRecord = new User(
                        user.get("id").asLong(),
                        user.get("name").asText(),
                        user.get("email").asText(),
                        user.get("gender").asText(),
                        user.get("status").asText()
                );

                // Using Java 21 switch expressions and pattern matching
                String statusEmoji = switch (userRecord.status().toLowerCase()) {
                    case "active" -> "🟢";
                    case "inactive" -> "🔴";
                    default -> "⚪";
                };

                String genderEmoji = switch (userRecord.gender().toLowerCase()) {
                    case "male" -> "👨";
                    case "female" -> "👩";
                    default -> "👤";
                };

                System.out.printf("""
                                %s ID: %d
                                   Name: %s
                                   Email: %s
                                   Gender: %s %s
                                   Status: %s %s
                                ---
                                """,
                        genderEmoji, userRecord.id(), userRecord.name(),
                        userRecord.email(), userRecord.gender(), genderEmoji,
                        userRecord.status(), statusEmoji);
            }

            // Java 21 - Using collections with enhanced operations
            var activeUsers = users.findValues("status").stream()
                    .mapToLong(status -> "active".equalsIgnoreCase(status.asText()) ? 1 : 0)
                    .sum();

            System.out.println("📊 STATISTICS:");
            System.out.println("Total Users: " + users.size());
            System.out.println("Active Users: " + activeUsers);
            System.out.println("Inactive Users: " + (users.size() - activeUsers));
        }
    }
}