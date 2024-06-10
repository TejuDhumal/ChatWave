package com.axis.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "group_tbl")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @NotBlank
    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_admin_user_id")
    private int groupAdminUserId;
    private String profileImage;
    private String group_desc;
    private Date created_at;
    private Date update_at;
    private String created_by;
    private String updated_by;

}
