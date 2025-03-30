package com.example.DailyPractise.services.imp;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.repositories.PostRepository;
import com.example.DailyPractise.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;


    @Override
    public PostDto createPost(PostDto postData) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PostEntity postEntity = modelMapper.map(postData,PostEntity.class);
        postEntity.setAuthor(user);
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
        PostEntity postEntity = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id : "+id+" does not exists"));
        return modelMapper.map(postEntity,PostDto.class);
    }

    @Override
    public void deletePostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id : "+id+" does not exists"));
        postRepository.delete(postEntity);
    }

    @Override
    public PostDto updatePost(Map<String, Object> data , Long id) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PostEntity postEntity = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id : "+id+" does not exists"));
        data.forEach((key, value)->{
            Field toBeUpdatedField = ReflectionUtils.findRequiredField(PostEntity.class,key);
            toBeUpdatedField.setAccessible(true);
            ReflectionUtils.setField(toBeUpdatedField,postEntity,value);
        });

        postEntity.setAuthor(user);

        PostEntity updatedPost = postRepository.save(postEntity);
        return modelMapper.map(updatedPost,PostDto.class);
    }
}
