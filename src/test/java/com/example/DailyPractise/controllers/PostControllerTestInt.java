package com.example.DailyPractise.controllers;

import com.example.DailyPractise.TestContainerConfig;

import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestContainerConfig.class)
@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTestInt {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PostRepository postRepository;

    private PostEntity post;

    private PostDto postDto;


    @BeforeEach
    void setUp(){
        post = PostEntity.builder()
                .message("this is a post")
                .description("this is a post description for integration testing")
                .build();

        postDto = PostDto.builder()
                .message("this is a post")
                .description("this is a post description for integration testing")
                .build();

        postRepository.deleteAll();  // not to effect other
    }


    @Test
    void createPost(){
        webTestClient.post()
                .uri("/posts")
                .bodyValue(postDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.message").isEqualTo("this is a post")
                .jsonPath("$.data.description").isEqualTo("this is a post description for integration testing");
    }

    @Test
    void  getAllPosts(){
       PostEntity savedSinglePost =  postRepository.save(post);

       webTestClient.get()
               .uri("/posts")
               .exchange()
               .expectStatus().isOk()
               .expectBody()
               .jsonPath("$.data.length()").isEqualTo(1)
               .jsonPath("$.data[0].message").isEqualTo("this is a post");

    }


    @Test
    void  getPostById_whenIdExists(){
        PostEntity savedPost = postRepository.save(post);

        webTestClient.get()
                .uri("/posts/{id}",savedPost.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.message").isEqualTo("this is a post")
                .jsonPath("$.data.description").isEqualTo("this is a post description for integration testing");
    }

    @Test
    void getPostById_whenIdDoesnotExists(){

        webTestClient.get()
                .uri("/posts/123456")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.apiError.message").isEqualTo("post with id : "+123456+" does not exists");
    }


    @Test
    void deletePostById_whenIdExists(){
        PostEntity savedPost = postRepository.save(post);

        webTestClient.delete()
                .uri("/posts/{id}",1L)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deletePostById_whenIdDoesNotExists(){
        webTestClient.delete()
                .uri("/posts/123456")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.apiError.message").isEqualTo("post with id : "+123456+" does not exists");
    }

}