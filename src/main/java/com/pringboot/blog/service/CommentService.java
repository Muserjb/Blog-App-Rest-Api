package com.pringboot.blog.service;

import com.pringboot.blog.model.Comment;
import com.pringboot.blog.model.Post;
import com.pringboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long id, CommentDto commentDto);
    List<CommentDto> getCommentByPostId(long postId);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto commentRequest);

    void deleteComment(long postId, long commentId);
}
