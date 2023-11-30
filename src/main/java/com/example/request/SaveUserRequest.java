package com.example.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class SaveUserRequest {

    @NotNull(message = "name required")
    private String name;
    @NotNull(message = "email required")
    private String email;
    @NotNull(message = "password required")
    private String password;
    @NotNull(message = "phones required")
    private List<SaveUserPhoneRequest> phones;
}
