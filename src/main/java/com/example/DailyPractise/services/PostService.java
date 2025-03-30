package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.PostDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface PostService {

    PostDto createPost(@Valid PostDto postData);

    List<PostDto> getAllPosts();

    PostDto getPostById(Long id);

    void deletePostById(Long id);

    PostDto updatePost(Map<String, Object> data , Long id);
}
