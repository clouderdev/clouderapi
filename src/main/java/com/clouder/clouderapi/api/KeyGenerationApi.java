package com.clouder.clouderapi.api;

import java.security.spec.InvalidKeySpecException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.dto.PublicKeyDTO;
import com.clouder.clouderapi.exception.ClouderException;
import com.clouder.clouderapi.service.KeyGenerationService;
import com.clouder.clouderapi.service.ResponseService;

@Path("public/key")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KeyGenerationApi {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private KeyGenerationService keyGenerationService;

    @GET
    public Response getPublicKey(@QueryParam("username") String username) {
        PublicKeyDTO publicKeyDTO;
        try {
            publicKeyDTO = keyGenerationService.getPublicKey(username);
        } catch (InvalidKeySpecException e) {
            throw new ClouderException(e.getMessage(), e);
        }
        return responseService.getSuccessResponse(publicKeyDTO, "Public key for username '" + username + "'",
                Status.OK.getStatusCode());
    }

}
