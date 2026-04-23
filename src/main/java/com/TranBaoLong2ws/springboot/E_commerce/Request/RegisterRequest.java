package com.TranBaoLong2ws.springboot.E_commerce.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {

    @NotEmpty(message = "First name is mandatory")
    @Size(min = 3, max = 30, message = "First name must be at least 3 characters long")
    private String fullname;

    @NotEmpty(message = "Password is mandatory")
    @Size(min = 5, max = 30, message = "Password must be at least 5 characters long")
    private String password;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "address is mandatory")
    @Size(min = 5, max = 30)
    private String address;

    @NotEmpty(message = "Phone is mandatory")
    @Size(min = 5, max = 30, message = "Password must be at least 5 characters long")
    private String phone;


    public RegisterRequest(String fullName, String password, String email, String address, String phone) {
        this.fullname = fullName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public RegisterRequest() {}


}
