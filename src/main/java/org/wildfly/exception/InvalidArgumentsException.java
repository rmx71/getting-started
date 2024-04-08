package org.wildfly.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class InvalidArgumentsException extends WebApplicationException {
    public InvalidArgumentsException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(message)
                .build());
    }
}
