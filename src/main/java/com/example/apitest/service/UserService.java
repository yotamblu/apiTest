package com.example.apitest.service;

import com.example.apitest.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Random;

@Service
public class UserService {
    private final DatabaseReference databaseReference;
    private final Random random;

    public UserService() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("users");
        this.random = new Random();
    }

    public User createRandomUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername("user" + random.nextInt(10000));
        user.setEmail("user" + random.nextInt(10000) + "@example.com");
        user.setFirstName("FirstName" + random.nextInt(1000));
        user.setLastName("LastName" + random.nextInt(1000));
        user.setAge(random.nextInt(80) + 18); // Random age between 18 and 98

        // Save to Firebase
        databaseReference.child(user.getId()).setValueAsync(user);
        
        return user;
    }
} 