package org.mehmood.blogapplicationbackendproject.payLoads;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private CategoryDto category;
    private UserDto user;
    private LocalDateTime postCreatedDate;
    private Set<CommentDto> comments=new HashSet<>();

}
