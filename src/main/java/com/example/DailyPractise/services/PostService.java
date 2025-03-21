package com.example.DailyPractise.services;

import com.example.DailyPractise.dtos.PostDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PostService {


    PostDto createPost(@Valid PostDto postDto);

    List<PostDto> getAllPost();

    PostDto getPostById(Long postId);
}
