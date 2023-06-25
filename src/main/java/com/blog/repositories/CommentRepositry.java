package com.blog.repositories;

import com.blog.entities.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositry extends JpaRepository<Comment ,Long> {

  List <Comment> findByPostId(Long postId);//this method built a query to find comment based on postId & fetch comment. store into list


}
