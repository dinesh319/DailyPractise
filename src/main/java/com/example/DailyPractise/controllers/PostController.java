package com.example.DailyPractise.controllers;

import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createNewPost(@RequestBody @Valid PostDto postDto){
        PostDto createdPost = postService.createPost(postDto);
        return new ResponseEntity<>(createdPost , HttpStatus.CREATED);
    }

    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long postId){
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto , HttpStatus.OK);
    }

    @PutMapping(path = "/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "postId") Long postId,
                                              @RequestBody @Valid PostDto postDto){

        PostDto updatedPost = postService.updatePost(postDto , postId);
        return new ResponseEntity<>(updatedPost , HttpStatus.OK);

    }

}
