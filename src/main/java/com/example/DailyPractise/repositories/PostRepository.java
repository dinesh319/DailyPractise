package com.example.DailyPractise.repositories;

import com.example.DailyPractise.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {


    PostEntity findByMessage(String message);

    PostEntity findByDescription(String description);
}
