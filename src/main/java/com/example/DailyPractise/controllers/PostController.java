package com.example.DailyPractise.controllers;

import com.example.DailyPractise.Dtos.PostDto;
import com.example.DailyPractise.advice.ApiResponse;
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
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto){
        PostDto savedPost = postService.createPost(postDto);
        return new ResponseEntity<>(savedPost , HttpStatus.CREATED);
    }


    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long id){
        PostDto postDto = postService.getPostById(id);
        return  new ResponseEntity<>(postDto , HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(){
        List<PostDto> posts = postService.getPosts();
        return new ResponseEntity<>(posts , HttpStatus.OK);
    }

    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<ApiResponse<?>> deletePostbyId(@PathVariable(name = "postId") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>(new ApiResponse<>("post with id "+id+" deleted successfully"), HttpStatus.NO_CONTENT);
    }

}
