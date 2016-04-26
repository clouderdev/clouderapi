package com.clouder.clouderapi.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.service.ResponseService;
import com.clouder.clouderapi.service.UserService;

@Path("user")
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserApi {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private UserService userService;

    /**
     *
     * @param json
     *            - JSON string of type {@code User}<br>
     *            <b>Example JSON string: </b><br>
     *
     *            <pre>
     * {
     *     "emailId": "ssshukla1993@gmail.com",
     *     "firstName": "Shriroop",
     *     "lastName": "Joshi",
     *     "username": "shrinivas93",
     *     "password": "P@ssw0rd",
     *     "clouds": [
     *         {
     *             "accessToken": "accessToken",
     *             "refreshToken": "refreshToken",
     *             "cloudType": "GOOGLEDRIVE"
     *         },
     *         {
     *             "dropBoxAccessToken": "dropBoxAccessToken",
     *             "dropboxRefreshToken": "dropBoxRefreshToken",
     *             "cloudType": "DROPBOX"
     *         }
     *     ]
     * }
     * </pre>
     *
     * @return
     */
    @POST
    public Response saveUser(String json) {
        userService.saveUser(json);
        return responseService.getSuccessResponse("User added", Status.CREATED.getStatusCode());
    }

    @GET
    public Response getUsers() {
        List<User> users = userService.getUsers();
        return responseService.getSuccessResponse(users, "List of all users", Status.OK.getStatusCode());
    }

}
