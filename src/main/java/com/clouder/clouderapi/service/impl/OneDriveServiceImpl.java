package com.clouder.clouderapi.service.impl;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.document.User;
import com.clouder.clouderapi.dto.AccessTokenDTO;
import com.clouder.clouderapi.exception.ClouderException;
import com.clouder.clouderapi.pojo.Cloud;
import com.clouder.clouderapi.pojo.OneDrive;
import com.clouder.clouderapi.service.CloudService;
import com.clouder.clouderapi.service.UserService;
import com.clouder.clouderapi.util.JsonUtility;
import com.dropbox.core.v2.files.Metadata;

@Service("onedrive")
public class OneDriveServiceImpl implements CloudService {

    @Value("${onedrive.clientID}")
    private String clientID;

    @Value("${onedrive.clientSecret}")
    private String clientSecret;

    @Value("${onedrive.redirectUri}")
    private String redirectUri;

    @Value("${onedrive.scope}")
    private String scope;

    @Autowired
    JsonUtility jsonUtility;

    @Autowired
    UserService userService;

    @Override
    public Cloud addCloud(HttpServletRequest servletRequest, String username) {
        System.out.println("OneDrive" + username);
        String code = servletRequest.getParameter("code");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://login.live.com").path("oauth20_token.srf");
        Form form = new Form();
        form.param("client_id", clientID);
        form.param("client_secret", clientSecret);
        form.param("redirect_uri", redirectUri);
        form.param("code", code);
        form.param("grant_type", "authorization_code");
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.form(form));
        String jsonResponse = response.readEntity(String.class);
        AccessTokenDTO accessTokenDTO = jsonUtility.toObject(jsonResponse, AccessTokenDTO.class);
        User user = userService.findByUsername(username);
        String accessToken = accessTokenDTO.getAccessToken();
        String refreshToken = accessTokenDTO.getRefreshToken();
        Cloud onedrive = new OneDrive(null, accessToken, refreshToken);
        user.addCloud(onedrive);
        userService.saveUser(user);
        return onedrive;
    }

    @Override
    public URI authenticateCloud(HttpServletRequest servletRequest, String username) {
        String uri = "https://login.live.com/oauth20_authorize.srf?" + "client_id=" + clientID + "&" + "scope=" + scope
                + "&" + "response_type=code&" + "redirect_uri=" + redirectUri;
        uri = uri.replace(" ", "%20");
        System.out.println(uri);
        try {
            // return new URI(URLEncoder.encode(uri,
            // StandardCharsets.UTF_8.name()));
            return new URI(uri);
        } catch (URISyntaxException /* | UnsupportedEncodingException */e) {
            throw new ClouderException("Invalid URI for OneDrive", e);
        }
    }

    @Override
    public List<Metadata> listFiles(String username, String cloudId, String parentDir) {
        return null;
    }

    @Override
    public Metadata deleteFile(String username, String cloudId, String filePath) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InputStream downloadFile(String username, String cloudId, String filePath) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void uploadFile(String username, String cloudId, String filePath, InputStream inputStream,
            FormDataContentDisposition fileDetails) {
        // TODO Auto-generated method stub
    }

}
