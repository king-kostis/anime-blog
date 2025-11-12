package com.blogwebapp.webapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmailDto{

    @Email(message = "Invalid emailAddress format")
    @NotBlank(message="Email is mandatory")
    private String emailAddress;

    @NotBlank(message="Subject is mandatory")
    @Size(max=30, message="Subject must be at most 30 characters long")
    private String subject;

    @NotBlank(message = "Email message is mandatory")
    private String message;

    public EmailDto() {}

    public EmailDto(String emailAddress, String subject, String message){
        this.emailAddress = emailAddress;
        this.subject = subject;
        this.message = message;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return "Email from: " + emailAddress +
                "\nMessage: " + message;
    }
}
