package com.example.DailyPractise.controllers;

import com.example.DailyPractise.advices.ApiResponse;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
//    @Secured("ROLE_ADMIN")
//    @Secured("POST_CREATE")
    @PreAuthorize("hasAuthority('POST_CREATE')")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postData){
        PostDto createdPost = postService.createPost(postData);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping
//    @Secured({"ROLE_ADMIN","ROLE_USER"})
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")  this is also fine

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping(path = "/{postId}")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long id){
        PostDto post = postService.getPostById(id);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @DeleteMapping(path = "/{postId}")
//    @Secured("ROLE_ADMIN")
    @Secured("POST_DELETE")
    public ResponseEntity<ApiResponse<String>> deletePostById(@PathVariable(name = "postId") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>(new ApiResponse<>("post with id : "+id+" deleted successfully"),HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/{postId}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<PostDto> updatePost(@RequestBody Map<String , Object> data,
                                              @PathVariable(name = "postId") Long id){
        PostDto updatedPost = postService.updatePost(data,id);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
}
