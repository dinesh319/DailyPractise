package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.PostDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface PostService {

    PostDto createPost(@Valid PostDto postData);

    PostDto getPostById(Long id);

    List<PostDto> getAllPosts();

    void deletePostById(Long id);

    PostDto updatePost(Map<String, Object> data, Long id);
}
