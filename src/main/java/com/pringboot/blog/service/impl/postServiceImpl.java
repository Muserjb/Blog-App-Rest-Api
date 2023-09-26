package com.pringboot.blog.service.impl;

import com.pringboot.blog.exception.ResourceNotFoundException;
import com.pringboot.blog.model.Post;
import com.pringboot.blog.payload.PostDto;
import com.pringboot.blog.payload.PostResponse;
import com.pringboot.blog.repository.PostRepository;
import com.pringboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class postServiceImpl implements PostService{
    private PostRepository postRepository;
    private ModelMapper mapper;

    public postServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto){
        //convert Dto into Entity
        Post post = mapToEntity(postDto);
        //save to database
        Post newPost = postRepository.save(post);
        //convert entity into Dto
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //let create a pageable instance
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        //get contents for page object
        List<Post> lostOfObject = posts.getContent();

        // return posts.stream().map(postServiceImpl::mapToDto).collect(Collectors.toList());
        List<PostDto> content = lostOfObject.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContents(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;


    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post with", "id", id));
    return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        //get post from a database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post with", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post with", "id", id));
        postRepository.delete(post);
    }


    private  PostDto mapToDto(Post newPost) {
        PostDto postDto = mapper.map(newPost, PostDto.class);
//        PostDto postpostDto = new PostDto();
//        postpostDto.setId(newPost.getId());
//        postpostDto.setTitle(newPost.getTitle());
//        postpostDto.setDescription(newPost.getDescription());
//        postpostDto.setContent(newPost.getContent());

        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        //using model mapper
        Post post = mapper.map(postDto, Post.class);

//        Post post = new Post();
//        post.setId(postDto.getId());
//        post.setDescription(postDto.getDescription());
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
        return  post;
    }


}

