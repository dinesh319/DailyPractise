package com.example.DailyPractise.services.imp;

import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.repositories.PostRepository;
import com.example.DailyPractise.services.PostService;
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
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;
    private  final ModelMapper modelMapper;


    @Override
    public PostDto createPost(PostDto postData) {
        PostEntity postEntity = modelMapper.map(postData,PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity),PostDto.class);
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
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post with id : "+id+" does not exists"));
        return modelMapper.map(postEntity,PostDto.class);
    }

    @Override
    public PostDto updatePost(Long id, Map<String, Object> data) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post with id : "+id+" does not exists"));
        data.forEach((key,value) -> {
            Field toBeUpdatedFields = ReflectionUtils.findRequiredField(PostEntity.class,key);
            toBeUpdatedFields.setAccessible(true);
            ReflectionUtils.setField(toBeUpdatedFields,postEntity,value);
        });

        PostEntity updatedPost = postRepository.save(postEntity);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post with id : "+id+" does not exists"));
        postRepository.deleteById(id);
    }
}
