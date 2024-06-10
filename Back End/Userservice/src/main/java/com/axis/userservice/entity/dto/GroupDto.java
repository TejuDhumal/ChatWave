package com.axis.userservice.entity.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GroupDto {

    private String groupName;
    private String desc;
    private String profileImage;

}
