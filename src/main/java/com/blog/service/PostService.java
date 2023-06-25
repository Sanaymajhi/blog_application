package com.blog.service;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);//it is method. PostDto object from payload package
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);


}
