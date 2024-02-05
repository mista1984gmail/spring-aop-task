package com.example.springaoptask.security.service;


import com.example.springaoptask.security.dao.request.SignUpRequest;
import com.example.springaoptask.security.dao.request.SigninRequest;
import com.example.springaoptask.security.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
