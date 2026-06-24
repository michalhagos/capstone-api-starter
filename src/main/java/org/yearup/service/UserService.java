package org.yearup.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.yearup.models.User;
import org.yearup.repository.UserRepository;

import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    // Returns all registered users typically used for admin purposes
    public List<User> getAll()
    {
        return userRepository.findAll();
    }
    // Looks up a user by their numeric id, or returns null if not found
    public User getUserById(int userId)
    {
        return userRepository.findById(userId).orElse(null);
    }
    // Looks up a user by their username
    public User getByUserName(String username)
    {
        return userRepository.findByUsername(username);
    }
    // Convenience method that returns just the userId for a given username.
    public int getIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? user.getId() : -1;
    }
    // Checks whether a username is already taken — used during registration to prevent duplicate accounts
    public boolean exists(String username)
    {
        return userRepository.existsByUsername(username);
    }
    // Registers a new user.
    public User create(User user)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
