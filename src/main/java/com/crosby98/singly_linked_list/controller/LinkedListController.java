package com.crosby98.singly_linked_list.controller;

import com.crosby98.singly_linked_list.collections.SynchronizedSinglyLinkedList;
import com.crosby98.singly_linked_list.dto.InsertionNodeRequest;
import com.crosby98.singly_linked_list.dto.ObjectNodeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public/list")
@Slf4j
public class LinkedListController {
    private final SynchronizedSinglyLinkedList<Object> list = new SynchronizedSinglyLinkedList<>();

    @GetMapping("/size")
    public ResponseEntity<Integer> getList() {
        return ResponseEntity.ok(list.size());
    }

    @PostMapping("/push")
    public void push(@RequestBody ObjectNodeRequest request) {
        log.info("Pushing object {} to list.", request.getData());
        list.push(request.getData());
    }

    @DeleteMapping("/pop")
    public Object pop() {
        log.info("Popping element");
        return list.pop();
    }

    @PostMapping("/insertAfter")
    public void insertAfter(@RequestBody InsertionNodeRequest request) {
        log.info("Inserting element {} after {}.", request.getData(), request.getAfter());
        list.insertAfter(request.getData(), request.getAfter());
    }
}
