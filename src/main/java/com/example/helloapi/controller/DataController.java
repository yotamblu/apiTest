package com.example.helloapi.controller;

import com.example.helloapi.model.DataItem;
import com.example.helloapi.service.FirebaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final FirebaseService firebaseService;

    public DataController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @GetMapping
    public ResponseEntity<List<DataItem>> getAllItems() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(firebaseService.getAllItems().get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataItem> getItem(@PathVariable String id) throws ExecutionException, InterruptedException {
        DataItem item = firebaseService.getItem(id).get();
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Void> saveItem(@RequestBody DataItem item) throws ExecutionException, InterruptedException {
        firebaseService.saveItem(item).get();
        return ResponseEntity.ok().build();
    }
} 