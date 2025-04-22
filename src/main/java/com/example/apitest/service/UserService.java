package com.example.apitest.service;

import com.example.apitest.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

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

        try {
            // Save to Firebase and wait for completion
            CompletableFuture<Void> future = new CompletableFuture<>();
            databaseReference.child(user.getId()).setValue(user, (error, ref) -> {
                if (error != null) {
                    future.completeExceptionally(error.toException());
                } else {
                    future.complete(null);
                }
            });
            
            future.get(); // Wait for the operation to complete
        } catch (Exception e) {
            // Log the error but don't fail the request
            System.err.println("Error saving user to Firebase: " + e.getMessage());
        }
        
        return user;
    }
} 