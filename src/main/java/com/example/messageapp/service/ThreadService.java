package com.example.messageapp.service;

import com.example.messageapp.model.CommentDto;
import com.example.messageapp.model.ThreadDto;
import com.example.messageapp.repository.CommentRepository;
import com.example.messageapp.repository.ThreadRepository;
import com.example.messageapp.repository.model.Comment;
import com.example.messageapp.repository.model.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ThreadService {

    private static final Logger LOG = LoggerFactory.getLogger(ThreadService.class);

    private final ThreadRepository threadRepository;

    @Autowired
    public ThreadService(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    public void upvoteThread(Long id) {
        threadRepository.upvoteThread(id);
    }

    public void downvoteThread(Long id) {
        threadRepository.downvoteThread(id);
    }

    public boolean createNewThread(ThreadDto threadDto) {
        Thread thread = new Thread(threadDto.getTitle(), threadDto.getContent());
        try {
            threadRepository.save(thread);
            return true;
        } catch (PersistenceException e) {
            LOG.error("Persistence Error Saving New Thread " + e);
        }
        return false;
    }

    public boolean updateOrCreateThread(ThreadDto threadDto, Long id) {
        Thread thread = new Thread(threadDto.getTitle(), threadDto.getContent());
        thread.setId(id);
        try {
            threadRepository.save(thread);
            return true;
        } catch (PersistenceException e) {
            LOG.error("Persistence Error Saving New Thread " + e);
            return false;
        }
    }

    @Transactional
    public boolean addCommentToThread(CommentDto commentDto, Long id) {
        Thread thread = threadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Thread id:" + id));
        thread.getComments().add(new Comment(commentDto.getContent()));
        try {
            threadRepository.save(thread);
            return true;
        } catch (PersistenceException e) {
            LOG.error("Persistence Error Saving New Comment " + e);
            return false;
        }
    }

    @Transactional
    public List<Comment> getCommentForThread(Long id) {
        return threadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Thread id:" + id))
                .getComments();
    }

    public void deleteThread(Long id) {
        threadRepository.deleteById(id);
    }

    public ThreadRepository getThreadRepository() {
        return threadRepository;
    }
}
