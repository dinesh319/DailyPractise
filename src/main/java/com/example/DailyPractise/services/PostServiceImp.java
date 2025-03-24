package com.example.DailyPractise.services;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImp  implements PostService{

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;


    @Override
    public PostDto createPost(PostDto postDto) {
        PostEntity postEntity = modelMapper.map(postDto,PostEntity.class);
        PostEntity savedPost = postRepository.save(postEntity);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities
                .stream().map(postEntity -> modelMapper.map(postEntity,PostDto.class))
                .collect(Collectors.toUnmodifiableList());

    }

    @Override
    public PostDto getPostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id: "+id+" doesnot exists"));
        return modelMapper.map(postEntity,PostDto.class);
    }

    @Override
    public void deletePostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id: "+id+" doesnot exists"));
        postRepository.delete(postEntity);
    }

    @Override
    public PostDto updatePost(Long id, Map<String, Object> postData) {

        PostEntity postEntity = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id: "+id+" does not exists"));
        postData.forEach((key,value) -> {
            Field toBeUpdatedField = ReflectionUtils.findRequiredField(PostEntity.class , key);
            toBeUpdatedField.setAccessible(true);
            ReflectionUtils.setField(toBeUpdatedField,postEntity,value);

        });

        PostEntity savedPost = postRepository.save(postEntity);
        return modelMapper.map(savedPost,PostDto.class);
    }
}
