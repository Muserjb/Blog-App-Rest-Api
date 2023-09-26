package com.pringboot.blog.service;

import com.pringboot.blog.payload.PostDto;
import com.pringboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
     PostDto createPost(PostDto postDto);
     PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
     PostDto getPostById(Long id);


     PostDto updatePostById(PostDto postDto, long id);

     void deletePostById(long id);
}
