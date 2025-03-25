package com.example.DailyPractise.services.implementations;

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
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        PostEntity post = modelMapper.map(postDto,PostEntity.class);
        PostEntity savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto getPostById(Long id) {
        PostEntity post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id : "+id+" does not exists"));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        return postEntities.stream().map(postEntity -> modelMapper.map(postEntity,PostDto.class)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public PostDto updatePost(Long id, Map<String, Object> data) {

        PostEntity post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id : "+id+" does not exists"));

        data.forEach((key,value)->{
            Field toBeUpdatedField = ReflectionUtils.findRequiredField(PostEntity.class,key);
            toBeUpdatedField.setAccessible(true);

            ReflectionUtils.setField(toBeUpdatedField,post,value);

        });

        PostEntity postEntity = postRepository.save(post);
        return modelMapper.map(postEntity,PostDto.class);
    }

    @Override
    public void deletePostById(Long id) {

        PostEntity post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post with id : "+id+" does not exists"));
        postRepository.deleteById(id);
    }
}
