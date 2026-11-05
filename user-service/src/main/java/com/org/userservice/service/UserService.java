package com.org.userservice.service;
import org.springframework.stereotype.Service;
import com.org.userservice.model.User;
import com.org.userservice.repository.UserRepository;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean existsById(Long userId) {
        return repository.existsById(userId);
    }
}
