package com.example.DailyPractise.controllers;

import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostControllers {

        private final PostService postService;

        @PostMapping
        public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto){
            PostDto createdPost = postService.createPost(postDto);
            return new ResponseEntity<>(createdPost, HttpStatus.OK);
        }

        @GetMapping
        public ResponseEntity<List<PostDto>> getAllPosts(){
            List<PostDto> posts = postService.getAllPost();
            return new ResponseEntity<>(posts , HttpStatus.OK);
        }

        @GetMapping(path = "/{postId}")
        public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long postId){
            PostDto postDto = postService.getPostById(postId);
            return new ResponseEntity<>(postDto , HttpStatus.OK);
        }
}
