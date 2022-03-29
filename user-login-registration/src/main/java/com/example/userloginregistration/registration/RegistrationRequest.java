package com.example.userloginregistration.registration;

public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password) {
}
