package com.blog.service;

import com.blog.payload.CommentDto;
import java.util.List;

public interface CommentService {
    CommentDto saveComment(Long postId , CommentDto commentDto);//this is an incomplete method
    List<CommentDto> getCommentByPostId(Long postId);//this method return list Of Dto. and this method give particular comment  for particular post
    CommentDto getCommentById(long postId,long commentId);//give all the comment for all the post

    CommentDto updateComment(long postId, long id, CommentDto commentDto);

    void deleteComment(long postId, long id);


}
