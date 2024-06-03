package com.axis.chatwave.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {


    @NotNull
	@NotBlank
	@Email
    private String email;

	@NotNull
	@NotBlank
    private String password;

    
    @NotNull
    private long mobile_no;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;


    private String bio;

    private String profilePhoto;


}
