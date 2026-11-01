package com.apextech.productservice.service;

import com.apextech.productservice.model.response.SessionData;
import com.apextech.productservice.model.response.SessionResponse;

public interface SessionService {

    SessionResponse create(SessionData data);

    boolean verify(String sessionId);

    boolean consume(String sessionId);

    SessionData getSession(String sessionId);

}