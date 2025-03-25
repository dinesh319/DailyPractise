package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.PostDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface PostService {

    PostDto createPost(@Valid PostDto postDto);

    PostDto getPostById(Long id);

    List<PostDto> getAllPosts();

    PostDto updatePost(Long id, Map<String, Object> data);

    void deletePostById(Long id);

}
