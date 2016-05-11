package com.clouder.clouderapi.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.clouder.clouderapi.exception.ClouderException;
import com.clouder.clouderapi.pojo.Cloud;
import com.clouder.clouderapi.service.CloudService;

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
        System.setProperty("http.proxyHost", "ngproxy.persistent.co.in");
        System.setProperty("http.proxyPort", "8080");
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        String ss = response.getEntity().toString();
        System.out.println(ss);
        return null;
    }

    @Override
    public URI authenticateCloud(HttpServletRequest servletRequest, String username) {
        String uri = "https://login.live.com/oauth20_authorize.srf?"
                + "client_id=" + clientID + "&"
                + "scope=" + scope + "&" 
                + "response_type=code&"
                + "redirect_uri=" + redirectUri;
        uri = uri.replace(" ", "%20");
        System.out.println(uri);
        try {
//            return new URI(URLEncoder.encode(uri, StandardCharsets.UTF_8.name()));
            return new URI(uri);
        } catch (URISyntaxException /*| UnsupportedEncodingException*/ e) {
            throw new ClouderException("Invalid URI for OneDrive", e);
        }
    }

}
