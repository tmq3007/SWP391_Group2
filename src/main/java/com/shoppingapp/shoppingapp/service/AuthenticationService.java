package com.shoppingapp.shoppingapp.service;

import com.nimbusds.jose.JOSEException;
import com.shoppingapp.shoppingapp.dto.request.AuthenticationRequest;
import com.shoppingapp.shoppingapp.dto.request.IntrospectRequest;
import com.shoppingapp.shoppingapp.dto.request.LogoutRequest;
import com.shoppingapp.shoppingapp.dto.request.RefreshRequest;
import com.shoppingapp.shoppingapp.dto.response.AuthenticationResponse;
import com.shoppingapp.shoppingapp.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse isAuthenticated(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
