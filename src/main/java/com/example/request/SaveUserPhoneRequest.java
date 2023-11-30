package com.example.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SaveUserPhoneRequest {

    @NotNull(message = "number required")
    private String number;
    @NotNull(message = "cityCode required")
    private String cityCode;
    @NotNull(message = "countryCode required")
    private String countryCode;
}
