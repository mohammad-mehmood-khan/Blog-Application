package org.mehmood.blogapplicationbackendproject.payLoads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @NotBlank(message = "Category title cannot be blank")
    @Size(min = 3, max = 100, message = "Category title must be between 3 and 100 characters")
    private String categoryTitle;
    @NotBlank(message = "Category description cannot be blank")
    @Size(min = 10, max = 500, message = "Category description must be between 10 and 500 characters")
    private String categoryDescription;
}
