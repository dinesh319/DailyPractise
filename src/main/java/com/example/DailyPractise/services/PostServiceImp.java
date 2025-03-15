package com.example.DailyPractise.services;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostServiceImp(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public PostDto createPost(PostDto postDto) {
        PostEntity postEntity = modelMapper.map(postDto , PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity) , PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postId) {
        PostEntity postEntity =
                postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post with id: "+ postId+" does not exists"));

        return modelMapper.map(postEntity , PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        PostEntity updatedPost = modelMapper.map(postDto , PostEntity.class);
        updatedPost.setId(postId);

        PostEntity oldPost = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post with id: "+postId+" does not exists"));

        return modelMapper.map(postRepository.save(updatedPost),PostDto.class);
    }
}
