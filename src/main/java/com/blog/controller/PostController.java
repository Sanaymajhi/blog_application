package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.CommentService;
import com.blog.service.PostService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;//create construct base injection for create bean(autowired)
    private CommentService commentService;

    public PostController(PostService postService,CommentService commentService) {

        this.postService = postService;
        this.commentService = commentService;

    }

    //http://localhost:8080/api/posts
    //backend of my application
    //Create the post

    @PreAuthorize("hasRole('ADMIN')")//this method access only admin login. by help of preAuthorize
    @PostMapping //json object come to here and save to dto through postservice
    public ResponseEntity<?>createPost(@Valid @RequestBody PostDto postDto,
                                             BindingResult result){//requestbody take the data from json and put dto.@valid help provide validation
        //bindingResult help if any error occour that error return to postman help of below code
        //? mark put in generic<> return type is anything.may be DTO or hasError

        if(result.hasErrors()){//check is their any error.
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }

        PostDto dto = postService.createPost(postDto);//controller layer takes dto called service layer.and dto display postman
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//show status code

    }
    //read  the all  data and pagination

    //http://localhost:8080/api/posts?pageNo=1&pageSize=5&sortBy=title&sortDir=desc or asc
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir


    ){//pageNo aNd page SIZE . REQUIRED = FALSE MEANS NOT MANDATORY TO GIVE URL
       PostResponse postResponse = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
       return postResponse;

    }

    //http//localhost:8080/api/posts/{id} this url the pathparameter.if there is question mark in url then it query parameter
    // get the recorder by Id
    @GetMapping("/{id}")
    //responseEntity is return type of getPostByid
     public ResponseEntity<PostDto> getPostById(@PathVariable("id")Long id){//@pathVariable use read the Pathparameter
         PostDto dto = postService.getPostById(id);//all the recorder are display Dto
        return new ResponseEntity<>(dto,HttpStatus.OK);//STATUS CODE 200 ,WHEN GET RECORDER

     }

     //Create UpdatePost Controller

    //http//localhost:8080/api/posts/{id}
    @PreAuthorize("hasRole('ADMIN')")//this method access only admin login. by help of preAuthorize
    @PutMapping("/{id}")
     public ResponseEntity<PostDto>updatePost(
             @RequestBody PostDto postDto,
             @PathVariable("id") Long id
    ){//@RequestBody annotation consists Josn object and through postman joson object go post dto. and @pathvariable for get id from url
     PostDto dto= postService.updatePost(postDto,id);//update entity go to postservice
        return new ResponseEntity<>(dto,HttpStatus.OK);

     }

     //Delete the post

    //http//localhost:8080/api/posts/{id}

    @PreAuthorize("hasRole('ADMIN')")//this method access only admin login. by help of preAuthorize
    @DeleteMapping("/{id}")

     public ResponseEntity<String> deletePost(@PathVariable("id")Long id){

        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted",HttpStatus.OK);//After post deleted in dto .status show in postman


    }



}

