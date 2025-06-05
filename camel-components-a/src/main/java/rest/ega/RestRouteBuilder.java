package rest.ega;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import static org.apache.camel.model.rest.RestParamType.path;

import java.util.List;

public class RestRouteBuilder extends RouteBuilder {

    private final UserService userService;

    // Constructor injection
    public RestRouteBuilder(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void configure() throws Exception {
        // Configure REST DSL
        restConfiguration()
                .component("undertow")
                .host("localhost")
                .port(8080)
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .corsHeaderProperty("Access-Control-Allow-Origin", "*")
                .corsHeaderProperty("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
                .corsHeaderProperty("Access-Control-Allow-Headers", "Content-Type");


        // Define REST endpoints
        rest("/api/users")
                .description("User management REST service")
                .consumes("application/json")
                .produces("application/json")

                // GET /api/users - Get all users
                .get()
                .description("Get all users")
                .to("direct:getUsers")

                // GET /api/users/{id} - Get user by ID
                .get("/{id}")
                .description("Get user by ID")
                .param().name("id").type(path).description("User ID").endParam()
                .to("direct:getUserById")

                // POST /api/users - Create new user
                .post()
                .description("Create new user")
                .type(User.class)
                .to("direct:createUser")

                // PUT /api/users/{id} - Update user
                .put("/{id}")
                .description("Update user")
                .param().name("id").type(path).description("User ID").endParam()
                .type(User.class)
                .to("direct:updateUser")

                // DELETE /api/users/{id} - Delete user
                .delete("/{id}")
                .description("Delete user")
                .param().name("id").type(path).description("User ID").endParam()
                .to("direct:deleteUser");

        // Route implementations using UserService

        // Get all users
        from("direct:getUsers")
                .log("Getting all users")
                .process(exchange -> {
                    List<User> userList = userService.getAllUsers();
                    exchange.getIn().setBody(userList);
                })
                .setHeader("Content-Type", constant("application/json"));

        // Get user by ID
        from("direct:getUserById")
                .log("Getting user by ID: ${header.id}")
                .process(exchange -> {
                    Long id = Long.valueOf(exchange.getIn().getHeader("id", String.class));
                    User user = userService.getUserById(id);

                    if (user != null) {
                        exchange.getIn().setBody(user);
                        exchange.getIn().setHeader("HTTP_RESPONSE_CODE", 200);
                    } else {
                        exchange.getIn().setBody(new ErrorResponse("User not found"));
                        exchange.getIn().setHeader("HTTP_RESPONSE_CODE", 404);
                    }
                });

        // Create new user
        from("direct:createUser")
                .log("Creating new user")
                .process(exchange -> {
                    User newUser = exchange.getIn().getBody(User.class);
                    User createdUser = userService.createUser(newUser);

                    if (createdUser != null) {
                        exchange.getIn().setBody(createdUser);
                        exchange.getIn().setHeader("HTTP_RESPONSE_CODE", 201);
                    } else {
                        exchange.getIn().setBody(new ErrorResponse("Invalid user data"));
                        exchange.getIn().setHeader("HTTP_RESPONSE_CODE", 400);
                    }
                });

        // Update user
        from("direct:updateUser")
                .log("Updating user with ID: ${header.id}")
                .process(exchange -> {
                    Long id = Long.valueOf(exchange.getIn().getHeader("id", String.class));
                    User updatedUserData = exchange.getIn().getBody(User.class);
                    User updatedUser = userService.updateUser(id, updatedUserData);

                    if (updatedUser != null) {
                        exchange.getIn().setBody(updatedUser);
                        exchange.getIn().setHeader("HTTP_RESPONSE_CODE", 200);
                    } else {
                        exchange.getIn().setBody(new ErrorResponse("User not found or invalid data"));
                        exchange.getIn().setHeader("HTTP_RESPONSE_CODE", 404);
                    }
                });

        // Delete user
        from("direct:deleteUser")
                .log("Deleting user with ID: ${header.id}")
                .process(exchange -> {
                    Long id = Long.valueOf(exchange.getIn().getHeader("id", String.class));
                    boolean deleted = userService.deleteUser(id);

                    if (deleted) {
                        exchange.getIn().setBody(new SuccessResponse("User deleted successfully"));
                        exchange.getIn().setHeader("HTTP_RESPONSE_CODE", 200);
                    } else {
                        exchange.getIn().setBody(new ErrorResponse("User not found"));
                        exchange.getIn().setHeader("HTTP_RESPONSE_CODE", 404);
                    }
                });
    }
}
