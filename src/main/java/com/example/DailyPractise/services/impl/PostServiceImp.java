package com.example.DailyPractise.services.impl;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.repositories.PostRepository;
import com.example.DailyPractise.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public PostDto createPost(PostDto postDto) {
        PostEntity postEntity = modelMapper.map(postDto,PostEntity.class);
        PostEntity savedPost = postRepository.save(postEntity);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public PostDto getPostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id : "+id+" does not exists"));
        return modelMapper.map(postEntity,PostDto.class);
    }

    @Override
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public List<PostDto> getAllPosts() {
        List<PostEntity> getAllPosts = postRepository.findAll();
        return getAllPosts.stream().map(postEntity -> modelMapper.map(postEntity,PostDto.class)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @PreAuthorize("hasAuthority('POST_DELETE')")
    public void deletePostById(Long id) {
            PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post with id : "+id+ "does not exists"));
            postRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('POST_UPDATE')")
    public PostDto updatePost(Long id, Map<String, Object> data) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post with id : "+id+" does not exists"));

        data.forEach((key , value) -> {
            Field toBeSavedFields = ReflectionUtils.findRequiredField(PostEntity.class,key);
            toBeSavedFields.setAccessible(true);
            ReflectionUtils.setField(toBeSavedFields,postEntity,value);
        });


        PostEntity updatdEntity = postRepository.save(postEntity);

        return modelMapper.map(updatdEntity,PostDto.class);

    }
}
