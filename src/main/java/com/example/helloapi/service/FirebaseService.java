package com.example.helloapi.service;

import com.example.helloapi.model.DataItem;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class FirebaseService {

    private final FirebaseDatabase firebaseDatabase;

    public FirebaseService(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public CompletableFuture<List<DataItem>> getAllItems() {
        CompletableFuture<List<DataItem>> future = new CompletableFuture<>();
        DatabaseReference ref = firebaseDatabase.getReference("items");
        
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DataItem> items = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataItem item = snapshot.getValue(DataItem.class);
                    items.add(item);
                }
                future.complete(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }

    public CompletableFuture<DataItem> getItem(String id) {
        CompletableFuture<DataItem> future = new CompletableFuture<>();
        DatabaseReference ref = firebaseDatabase.getReference("items").child(id);
        
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataItem item = dataSnapshot.getValue(DataItem.class);
                future.complete(item);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }

    public CompletableFuture<Void> saveItem(DataItem item) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference ref = firebaseDatabase.getReference("items").child(item.getId());
        
        ref.setValueAsync(item).addListener(() -> {
            future.complete(null);
        }, Runnable::run);

        return future;
    }
} 