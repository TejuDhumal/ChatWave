package com.axis.chatwave.entity.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {


    private String email;

    private String password;

    private long mobile_no;

    private String username;

    private String firstName;

    private String lastName;

    private String verifyStatus;

    private String bio;

    private String profilePhoto;


}
