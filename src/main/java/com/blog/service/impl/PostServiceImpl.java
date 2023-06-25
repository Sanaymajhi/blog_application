package com.blog.service.impl;

import com.blog.entities.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.PostRepository;
import com.blog.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
      //below 3 line before use modelmapper
    //private PostRepository postRepository;//construct base injection to create bean like autowired annotation

   // public PostServiceImpl(PostRepository postRepository) {

       // this.postRepository = postRepository;

    //this modelmapper convert dto to entity or entity to dto

    private PostRepository postRepository;
    private ModelMapper mapper;//mapper method convert dto to entity viceversa
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }



    @Override
    public PostDto createPost(PostDto postDto) {//what dto comes this method ,that dto go to entity method(45).
        //then dto Entity convert to post entity(47 to 50)
        //below method convert one dto to one entity
        Post post = mapToEntity(postDto);//it is reusable the entity method and call  entity to dto

        Post savePost = postRepository.save(post);//NOW save the data on entity from dto.
      //above method convert one entity object to one dto object
        PostDto dto = mapToDto(savePost);
        return dto;



    }

    //this method convert all the entity object to all dto object
    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
      //use ternary operator use for if else condition
        Sort sorts = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create pageable instance(object)
        PageRequest pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));//this the method overloading and page-request is built in classs
        //SortBy string convert into object by sort.by method. Sort is class

        Page<Post> content = postRepository.findAll(pageable);//fetch the all data from db repo.findall.do find all assgn local

        List<Post> posts = content.getContent();//convert page to list by applying getContent

        List<PostDto> dtos = posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);//give all the postContent. if it not declared complete details not show in postman
        postResponse.setPageNo(content.getNumber());//getNumber is built in method to show current page no
        postResponse.setPageSize(content.getSize());//it give size of the page automatically
        postResponse.setTotalPages(content.getTotalPages());//show total pages
        postResponse.setTotalElements(content.getTotalElements());
        postResponse.setLast(content.isLast());//show is last page or not. return boolean

        return postResponse;


    }//list of entity convert to dto
//get the recorder post
    @Override
    public PostDto getPostById(Long id) {//read the value by id
      Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("post not found by id: "+id)
        );//new is object of lambda.and create res.not.fou.excep.
        PostDto postDto = mapToDto(post);//convert post to dto.

        return postDto;
    }
//update the recorder post
    @Override
    public PostDto updatePost(PostDto postDto, Long id) {//if recorder is found ,recorder save post
       Post post= postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("post not found with id: "+id)
        );
       //update the recorder
       post.setTitle(postDto.getTitle());
       post.setContent(postDto.getContent());
       post.setDescription(post.getContent());
        Post updatePost = postRepository.save(post);//save the post in updatepost

        return   mapToDto(updatePost);//convert entity to dto .because service layer can not return entity

    }
//Delete the post
    @Override
    public void deletePost(Long id) {
        postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("post not found with id: "+id)

        );

        postRepository.deleteById(id);

    }

    //method convert dto object into entity
    //method will help to call dto to entity
    Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto,Post.class);//CONVERT DTO  TO ENTITY
//        Post post = new Post();//convert dto to entity to give entity.dto object not go DB so convert dto to entity
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return  post;//return entity
    }
    //convert savePost to dto
    //now convert entity to dto to give controller

    PostDto mapToDto(Post post){//entity to dto

        PostDto dto = mapper.map(post,PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;//return dto
    }



}
