package com.example.DailyPractise.controllers;

import com.example.DailyPractise.advices.ApiResponse;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postData){
        PostDto savedPost = postService.createPost(postData);
        return new ResponseEntity<>(savedPost , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> postDtos = postService.getAllPosts();
        return new ResponseEntity<>(postDtos , HttpStatus.OK);
    }

    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long id){
        PostDto postDto = postService.getPostById(id);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @PatchMapping(path = "/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "postId") Long id,
                                              @RequestBody Map<String , Object> data){
        PostDto postDto = postService.updatePost(id,data);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<ApiResponse<String>> deletePostById(@PathVariable(name = "postId") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>(new ApiResponse<>("post with id : "+id+" deleted successfully"),HttpStatus.NO_CONTENT);
    }
}
