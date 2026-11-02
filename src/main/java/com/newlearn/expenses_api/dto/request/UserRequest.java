package com.newlearn.expenses_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 30,message = "Name cannot exceed length 30")
    private String name;

    @NotBlank(message = "Email required!")
    @Email(message = "Please provide a valid email")
    @Size(max = 100,message = "Email cannot exceed 100 characters")
    private String email;

    @NotBlank(message = "Password required!")
    @Size(min = 8,max = 20,message = "Password must be of at least 8 character and at most of 20 characters")
    private String password;
}
