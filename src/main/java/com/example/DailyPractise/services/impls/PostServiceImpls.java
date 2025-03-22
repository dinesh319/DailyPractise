package com.example.DailyPractise.services.impls;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.repositories.PostRepository;
import com.example.DailyPractise.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpls  implements PostService {

    private final ModelMapper modelMapper;

    private final PostRepository postRepository;


    @Override
    public PostDto createPost(PostDto postDto) {
        PostEntity postEntity = modelMapper.map(postDto,PostEntity.class);
        PostEntity savedPost = postRepository.save(postEntity);
        return modelMapper.map(savedPost , PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postId) {
        PostEntity postEntity= postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post with id : "+postId+" does not exists"));
        return modelMapper.map(postEntity,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream().map(postEntity -> modelMapper.map(postEntity,PostDto.class)).toList();
    }

    @Override
    public void deletePostById(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post with id : "+postId+" does not exists"));
        postRepository.delete(postEntity);
    }
}
