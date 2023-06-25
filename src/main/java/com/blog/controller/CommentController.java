package com.blog.controller;


import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")//calling this url
public class CommentController {


    //call Comment service layer
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }//construct based injector for create bean

    //Creat Comment and  Save Post

    //http://localhost:8080/api/posts/1/comments after post is post id and after comment is comment id. this url use for oneToMany Mapping

    @PostMapping("/posts/{postId}/comments")//via this url post id and joson contain give to commentDto .and pathvariable and requestbody help copy joson containt put commentdto
    public ResponseEntity<CommentDto>createComment(
          @PathVariable("postId")long postId,
          @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.saveComment(postId, commentDto);//return dto to controller jayer
        return new ResponseEntity<>(dto, HttpStatus.CREATED); //CREATE WORD USE FOR CREATE COMMENT
    }

    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")// through this url get all comment by postId
    // and return List Of commentDto . pathVariable help to fetch the value by postId
   public List<CommentDto> getCommentByPostId(@PathVariable("postId")long  postId){

        List<CommentDto> commentDto = commentService.getCommentByPostId(postId);//controller layer called service layer and return list of comment dto
        return commentDto;
    }

    //get the comment

    //http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{commentId}")

    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId , @PathVariable("commentId") long commentId){

        CommentDto dto = commentService.getCommentById(postId , commentId);
        return new ResponseEntity<>(dto ,HttpStatus.OK);

    }

    //update comment
    //http://localhost:8080/api/posts/1/comments/1


    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id,
            @RequestBody CommentDto commentDto
    ){//in this url supply postId,commentId and Json object(requestbody)

        CommentDto dto= commentService.updateComment(postId,id,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);//THIS HELP RETURN TO POSTMAN
    }

    //delete the comment

    //http://localhost:8080/api/posts/1/comments/1

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id
    ){

        commentService.deleteComment(postId,id);

        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
    }
}
