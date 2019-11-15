package com.ngu.Model;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {


    private static final long serialVersionUID = 1L;

    private int id;
    private String fullName;
    private String fname;
    private String lname;
    private String email;
    private String username;

    private User user;

    private Role role;
    private long phone;

}
