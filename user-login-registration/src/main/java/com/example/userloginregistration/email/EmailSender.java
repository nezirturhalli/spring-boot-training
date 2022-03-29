package com.example.userloginregistration.email;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {
    CompletableFuture<String> send(String to, String email);
}
