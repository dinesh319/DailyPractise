package com.example.DailyPractise.services.imp;

import com.example.DailyPractise.TestContainerConfig;
import com.example.DailyPractise.customexceptions.ResourceNotFoundException;
import com.example.DailyPractise.dtos.PostDto;
import com.example.DailyPractise.entities.PostEntity;
import com.example.DailyPractise.repositories.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Import(TestContainerConfig.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostServiceImpTest {

    @Mock
    private PostRepository postRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private  PostServiceImp postServiceImp;

    private PostEntity post;
    private PostDto postDto;

    @BeforeEach
    void  setUp(){

        post = PostEntity.builder()
                .message("this is test post")
                .description("this is description for test post")
                .build();

        postDto = modelMapper.map(post,PostDto.class);
    }

    @Test
    void testCreatePost(){
        // assign
            when(postRepository.save(any(PostEntity.class))).thenReturn(post); // stubbing

        // act
            PostDto savedPost = postServiceImp.createPost(postDto);

        //assert
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getMessage()).isEqualTo(postDto.getMessage());
        assertThat(savedPost.getDescription()).isEqualTo(postDto.getDescription());

        verify(postRepository,atMostOnce()).save(any(PostEntity.class));

    }


    @Test
    void testGetAllPosts(){  // positive flow when atleast one post exists
        // assign
            when(postRepository.findAll()).thenReturn(List.of(post));

        // act
        List<PostDto> posts  = postServiceImp.getAllPosts();

        // assert
        assertThat(posts).isNotNull();
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0).getMessage()).isEqualTo(postDto.getMessage());

        verify(postRepository,atMostOnce()).findAll();
    }


    @Test
    void testGetPostById_whenIdIsPresent_returnPostDto(){  // when post with id exists

        // assign
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        // act
        PostDto fetchedPost = postServiceImp.getPostById(1L);

        // assert
        assertThat(fetchedPost).isNotNull();
        assertThat(fetchedPost.getMessage()).isEqualTo(postDto.getMessage());
        assertThat(fetchedPost.getDescription()).isEqualTo(postDto.getDescription());

        verify(postRepository,atMostOnce()).findById(1L);

    }
    
    @Test
    void testGetPostById_whenIdIsNotPresent_throwError(){
        // assign
        when(postRepository.findById(2L)).thenReturn(Optional.empty());
        
        // act and assert
        assertThatThrownBy(() -> postServiceImp.getPostById(2L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("post with id : "+2L+" does not exists");

    }


    @Test
    void testDeletePostById_whenIdIsPresent_returnNull(){
        // assign
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        doNothing().when(postRepository).deleteById(1L);
        // act
        postServiceImp.deletePostById(1L);

        // assert
        verify(postRepository,times(1)).findById(1L);
        verify(postRepository,times(1)).deleteById(1L);
    }


    @Test
    void testDeletePostById_whenIdIsNotPresent_throwError(){
        // assign
        when(postRepository.findById(2L)).thenReturn(Optional.empty());
        // act and assert

        assertThatThrownBy(()->         postServiceImp.deletePostById(2L)
                ).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("post with id : "+2L+" does not exists");

        verify(postRepository,never()).deleteById(anyLong());
    }


    @Test
    void testUpdatePostById_whenIdIsPresent_returnPost(){

        PostEntity updatedPost = PostEntity.builder()
                .message("updated post")
                .description("updated post description for testing")
                .build();

        Map<String, Object> postDtoUpdated = new HashMap<>();
        postDtoUpdated.put("message", "updated post");
        postDtoUpdated.put("description", "updated post description for testing");



        // assign
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(PostEntity.class))).thenReturn(post);
        // act
        PostDto savedPost = postServiceImp.updatePost(1L , postDtoUpdated);
        // assert
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getMessage()).isEqualTo(updatedPost.getMessage());
        assertThat(savedPost.getDescription()).isEqualTo(updatedPost.getDescription());

        verify(postRepository,times(1)).findById(1L);
        verify(postRepository,times(1)).save(any(PostEntity.class));

    }

    @Test
    void testUpdatePostById_whenIdIsNotPresent_throwError(){

        // assign

        PostEntity updatedPost = PostEntity.builder()
                .message("updated post")
                .description("updated post description for testing")
                .build();

        Map<String, Object> postDtoUpdated = new HashMap<>();
        postDtoUpdated.put("message", "updated post");
        postDtoUpdated.put("description", "updated post description for testing");

        when(postRepository.findById(2L)).thenReturn(Optional.empty());

        // act and assert
        assertThatThrownBy(()-> postServiceImp.updatePost(2L,postDtoUpdated))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("post with id : "+2L+" does not exists");

        verify(postRepository,never()).save(any(PostEntity.class));
    }

}