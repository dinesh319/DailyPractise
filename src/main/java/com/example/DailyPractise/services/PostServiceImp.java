package com.example.DailyPractise.services;

import com.example.DailyPractise.Dtos.PostDto;
import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService{

    private final ModelMapper modelMapper;

    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        PostEntity postEntity = modelMapper.map(postDto,PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity),PostDto.class);
    }

    @Override
    public PostDto getPostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id "+id+" does not exists"));
        return modelMapper.map(postEntity,PostDto.class);
    }

    @Override
    public List<PostDto> getPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream().map(postEntity -> modelMapper.map(postEntity,PostDto.class)).toList();
    }

    @Override
    public void deletePostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id "+id+" does not exists"));
        postRepository.delete(postEntity);
    }
}
