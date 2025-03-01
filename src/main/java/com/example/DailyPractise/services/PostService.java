package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.PostDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PostService {
    PostDto createPosts(@Valid PostDto post);

    List<PostDto> getAllPosts();

    PostDto getPostById(Long postId);
}
