package com.example.DailyPractise.services;

import com.example.DailyPractise.Dtos.PostDto;
import jakarta.validation.Valid;

import java.util.List;


public interface PostService {


    public PostDto createPost(@Valid PostDto postDto);

    public PostDto getPostById(Long id) ;

    public List<PostDto> getPosts() ;

    public void deletePostById(Long id);

}
