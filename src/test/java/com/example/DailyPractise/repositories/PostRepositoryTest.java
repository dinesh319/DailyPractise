package com.example.DailyPractise.repositories;

import com.example.DailyPractise.TestContainerConfig;
import com.example.DailyPractise.entities.PostEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestContainerConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private PostEntity post;

    @BeforeEach
    void setUp(){
        post = PostEntity.builder()
                .message("this is test message")
                .description("this is description for test messsgae")
                .build();
    }


    @Test
    void testFindByMessage_ifMessageExists_thenReturnPostEntity(){
        // arrange
        postRepository.save(post);
        String message = "this is test message";

        // act
        PostEntity fetchedPost = postRepository.findByMessage(message);

        // assert

        Assertions.assertThat(fetchedPost).isNotNull();
        Assertions.assertThat(fetchedPost.getMessage()).isEqualTo(message);
    }


    @Test
    void testFindByMessage_ifMessageDoesnotExists_thenReturnNull(){

        // arrange
        String message = "message that does not exists";

        // act
        PostEntity fetchedPost = postRepository.findByMessage(message);

        // assert
        Assertions.assertThat(fetchedPost).isNull();
    }


    @Test
    void testByDescription_ifDescriptionExists_thenReturnPostEntity(){

        // arrange
        postRepository.save(post);

        //act
        PostEntity fetchedPost = postRepository.findByDescription(post.getDescription());

        // assert
        Assertions.assertThat(fetchedPost).isNotNull();
        Assertions.assertThat(fetchedPost.getDescription()).isEqualTo(post.getDescription());
    }


    @Test
    void testByDescription_ifDescriptionDoesNotExists_thenReturnNull(){

        // arrange
        String description = "descritption that does not exists";

        // act
        PostEntity fetchedPost = postRepository.findByDescription(description);

        // assert
        Assertions.assertThat(fetchedPost).isNull();
    }


}