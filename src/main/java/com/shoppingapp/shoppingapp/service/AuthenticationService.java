package com.shoppingapp.shoppingapp.service;

import com.nimbusds.jose.JOSEException;
import com.shoppingapp.shoppingapp.dto.request.AuthenticationRequest;
import com.shoppingapp.shoppingapp.dto.request.IntrospectRequest;
import com.shoppingapp.shoppingapp.dto.response.AuthenticationResponse;
import com.shoppingapp.shoppingapp.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse isAuthenticated(AuthenticationRequest request);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
