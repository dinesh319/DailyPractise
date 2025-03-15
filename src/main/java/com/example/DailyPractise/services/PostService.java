package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.PostDto;
import jakarta.validation.Valid;

public interface PostService {
    PostDto createPost(@Valid PostDto postDto);

    PostDto getPostById(Long postId);

    PostDto updatePost(@Valid PostDto postDto, Long postId);
}
