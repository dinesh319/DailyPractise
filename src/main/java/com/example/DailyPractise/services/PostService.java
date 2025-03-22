package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.PostDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PostService {

    PostDto createPost(@Valid PostDto postDto);

    PostDto getPostById(Long postId);

    List<PostDto> getAllPosts();

    void deletePostById(Long postId);
}
