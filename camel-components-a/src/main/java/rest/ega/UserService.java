package rest.ega;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service class to handle user operations
 */
public class UserService {
    
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public UserService() {
        // Initialize with sample data
        users.put(1L, new User(1L, "John Doe", "john@example.com"));
        users.put(2L, new User(2L, "Jane Smith", "jane@example.com"));
        idGenerator.set(3L);
    }
    
    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }
    
    public User getUserById(Long id) {
        return users.get(id);
    }
    
    public User createUser(User user) {
        if (user != null && user.getName() != null && user.getEmail() != null) {
            Long id = idGenerator.getAndIncrement();
            user.setId(id);
            users.put(id, user);
            return user;
        }
        return null;
    }
    
    public User updateUser(Long id, User updatedUser) {
        if (users.containsKey(id) && updatedUser != null) {
            updatedUser.setId(id);
            users.put(id, updatedUser);
            return updatedUser;
        }
        return null;
    }
    
    public boolean deleteUser(Long id) {
        return users.remove(id) != null;
    }
    
    public boolean userExists(Long id) {
        return users.containsKey(id);
    }
}