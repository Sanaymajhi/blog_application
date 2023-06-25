package com.blog.entities;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//lombok help reduce the code line i,e getter and setters
@Data
//built constructor automatically
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts",
uniqueConstraints={@UniqueConstraint(columnNames ={"title"})}//title became unique
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @Column(name ="title",nullable = false)
   private String title;
    @Column(name="description",nullable = false)
   private String description;
   @Column(name="content",nullable = false)
   private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)//this method mapped with comment table bye oneToMany
    private Set<Comment> comments = new HashSet<>();//comment is many so store data set
//Here, the Post entity has a one-to-many relationship with the Comment entity through the comments field.
// The mappedBy attribute indicates that the relationship is mapped by the post field in the Comment entity.
// The cascade attribute specifies that any changes to a Post object should be cascaded to its associated Comment objects, and the fetch attribute sets the fetch type to lazy loading.


}
