package com.example.messageapp.controller;

import com.example.messageapp.model.CommentDto;
import com.example.messageapp.model.ThreadDto;
import com.example.messageapp.repository.model.Comment;
import com.example.messageapp.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ThreadController {

    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/thread/{id}/up")
    public ResponseEntity<Long> upvoteThread(@PathVariable Long id) {
        threadService.upvoteThread(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/thread/{id}/down")
    public ResponseEntity<Long> downvoteThread(@PathVariable Long id) {
        threadService.downvoteThread(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/thread/new")
    public ResponseEntity<Boolean> createNewThread(@Valid @RequestBody ThreadDto threadDto) {
        boolean success = threadService.createNewThread(threadDto);
        if (success) {
            return new ResponseEntity<>(success, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(success, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @DeleteMapping("/thread/{id}")
    public void deleteThread(@PathVariable Long id) {
        threadService.deleteThread(id);
    }

    @PutMapping("/thread/{id}")
    public void updateThread(@Valid @RequestBody ThreadDto threadDto, @PathVariable Long id) {
        threadService.updateOrCreateThread(threadDto, id);
    }

    @PostMapping("/thread/{id}/comment")
    public ResponseEntity<Boolean> addComment(@Valid @RequestBody CommentDto commentDto, @PathVariable Long id) {
        boolean success = threadService.addCommentToThread(commentDto, id);
        if (success) {
            return new ResponseEntity<>(success, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(success, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/thread/{id}/comment")
    public ResponseEntity<List<Comment>> getComment(@PathVariable Long id) {
        return new ResponseEntity<>(threadService.getCommentForThread(id), HttpStatus.OK);
    }
}
