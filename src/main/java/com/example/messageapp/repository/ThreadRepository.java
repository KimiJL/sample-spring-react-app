package com.example.messageapp.repository;

import com.example.messageapp.repository.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
    @Modifying
    @Query(value = "UPDATE threads SET vote = vote + 1 WHERE id = ?1", nativeQuery = true)
    void upvoteThread(Long id);

    @Modifying
    @Query(value = "UPDATE threads SET vote = vote - 1 WHERE id = ?1", nativeQuery = true)
    void downvoteThread(Long id);
}
