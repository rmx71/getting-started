package org.wildfly.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class ValidationException extends WebApplicationException {

    public ValidationException(List<String> messages) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(messages)
                .build());
    }
}
