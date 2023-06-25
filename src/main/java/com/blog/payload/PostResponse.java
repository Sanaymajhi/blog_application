package com.blog.payload;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class PostResponse {
   private List<PostDto> content;//if put entity instead dto . data may be expose to end User.for safety we put dto
    private  int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
}
