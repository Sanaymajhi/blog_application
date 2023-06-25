package com.blog.service.impl;

import com.blog.BlogApplication;
import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exception.BlogAPIException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repositories.CommentRepositry;
import com.blog.repositories.PostRepository;
import com.blog.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;
    private CommentRepositry commentRepositry;

    private ModelMapper mapper;//model mapper. it external libery

    public CommentServiceImpl(PostRepository postRepository, CommentRepositry commentRepositry,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.commentRepositry = commentRepositry;
        this.mapper=mapper;
    }

    // Save Comment for specific post
    @Override
    public CommentDto saveComment(Long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found by id:"+id)
        );//check post is existe or not
        //after check the post exists or not. take commentDto method give to comment for save
        //below code response returning happening
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);//what ever comment coming save the post.
        Comment newComment = commentRepositry.save(comment);//after comment save .it convert CommentDto
        return  mapToDto(newComment);//take newcomment call the dto



    }
     //get comment for post
    @Override
    public List<CommentDto> getCommentByPostId(Long postId) {

        List<Comment> comments = commentRepositry.findByPostId(postId);//retrieve comment Entities by postId

        return   comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());//convert comment etities to coment dto



    }

    //get the all comment for specific post

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found by id:"+id)
        );//check post exist or not for id

       Comment comment= commentRepositry.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment not found with id: "+commentId)
        );//check where comment exist or not through commentId

        if(!comment.getPost().getId().equals(post.getId())){//check comment id is match with postid for paticular id
            throw new BlogAPIException( "Comment does not belong to post");
        }//check specific comment id is existed for specific post ,if not gave comment not found error

        return mapToDto(comment);
    }

    //update feature for comment
    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
     Post post  = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post not found with id:"+postId)

        );//check is post is existed by PostId or not

      Comment comment = commentRepositry.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("comment not found with id:"+id)

        );//check is commentId is existed for specific  PostId or not . or we also say  comment.getPost().getPostId()

        if (!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException("comment does not belong to post");
        }// this method check whether comment is match with post or not (! not equal sign).if not give exceptin.if match go to update
       //after comment match with post then update the comment

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepositry.save(comment);
        return mapToDto(updatedComment);//this help us convert entity to dto

    }

    //delete the comment
    @Override
    public void deleteComment(long postId, long id) {

        Post post  = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post not found with id:"+postId)

        );//check is post is existed by PostId or not

        Comment comment = commentRepositry.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("comment not found with id:"+id)

        );//check is commentId is existed for specific  PostId or not . or we also say  comment.getPost().getPostId()

        if (!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException("comment does not belong to post");
        }// this method check whether comment is match with post or not if not give exceptin.if match go to delete

        commentRepositry.deleteById(id);//this help call db through repositry

    }

    //convert dto to entity
    Comment mapToEntity(CommentDto commentDto){

        Comment comment  = mapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
//        comment.setName(commentDto.getName());

        return comment;

    }

    //After comment save . it converts Dto
    CommentDto mapToDto(Comment comment){

        CommentDto dto = mapper.map(comment,CommentDto.class);
//        CommentDto dto = new CommentDto();
//        dto.setId(comment.getId());
//        dto.setBody(comment.getBody());
//        dto.setEmail(comment.getEmail());
//        dto.setBody(comment.getBody());
//        dto.setName(comment.getName());

        return dto;

    }

}
