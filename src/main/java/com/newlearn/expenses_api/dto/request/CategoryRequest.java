package com.newlearn.expenses_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100,message = "no. of characters cannot exceed 100")
    private String name;

    @Size(max=200,message = "Description cannot exceed 200 characters")
    private String description;
}
