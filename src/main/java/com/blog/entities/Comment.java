package com.blog.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)//fatch type lazy load those entity into required current running loading memory
    @JoinColumn(name = "post_id", nullable = false)//these help to join comment table to post by columnTable/forging key post_id
    private Post post;//Post is parent table.The Comment entity has a many-to-one relationship with the Post entity through the post field
    //post not set because post is one
    //joinColumn create in comment class

}
