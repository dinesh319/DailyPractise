package com.example.DailyPractise.services;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.repositories.PostRepository;
import com.example.DailyPractise.services.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;


    @Override
    public PostDto createPosts(PostDto post) {
        PostEntity postEntity = modelMapper.map(post,PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity),PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream().map(postEntity -> modelMapper.map(postEntity,PostDto.class)).toList();
    }

    @Override
    public PostDto getPostById(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post with id : "+postId +" doesnt exists"));
        return modelMapper.map(postEntity,PostDto.class);
    }
}
