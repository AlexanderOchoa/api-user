package com.example.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SaveUserResponse {

    private Long idUser;
    private String createdDate;
    private String modificationDate;
    private String lastLogin;
    private String token;
    private boolean actived;
}
