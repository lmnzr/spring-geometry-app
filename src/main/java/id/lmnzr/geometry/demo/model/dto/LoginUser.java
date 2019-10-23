package id.lmnzr.geometry.demo.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginUser {
    @NotNull
    private String email;

    @NotNull
    private String password;

    private String name = "anonymous";
}
