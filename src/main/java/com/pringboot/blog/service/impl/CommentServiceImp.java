package com.pringboot.blog.service.impl;

import com.pringboot.blog.exception.BlogAppApiException;
import com.pringboot.blog.exception.ResourceNotFoundException;
import com.pringboot.blog.model.Comment;
import com.pringboot.blog.model.Post;
import com.pringboot.blog.payload.CommentDto;
import com.pringboot.blog.repository.CommentRepository;
import com.pringboot.blog.repository.PostRepository;
import com.pringboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;


    public CommentServiceImp(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
       Comment comment = mapToEntity(commentDto);

      // retrieve post by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //set post to Comment entity
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);


        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        //retrieve comment by post id
        List<Comment> comments = commentRepository.findByPostId(postId);

        //convert list of comment entity to list comment Dto`s
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());


    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        // retrieve post by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));

        if (!(comment.getPost().getId().equals(post.getId()))){
            throw new BlogAppApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentRequest) {
        // retrieve post by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));

        if (!(comment.getPost().getId().equals(post.getId()))){
            throw new BlogAppApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);

    }

    @Override
    public void deleteComment(long postId, long commentId) {
        // retrieve post by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));

        if (!(comment.getPost().getId().equals(post.getId()))){
            throw new BlogAppApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }


    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();

        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();

        comment.setId(commentDto.getId());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        return comment;


    }



}
