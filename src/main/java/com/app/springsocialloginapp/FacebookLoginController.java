package com.app.springsocialloginapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacebookLoginController {
	

	private final String app_id = "{YOUR_APP_ID}";	
	
	private final String app_secret ="{YOUR_APP_SECRET}";	
	
	
	private FacebookConnectionFactory factory = new FacebookConnectionFactory(app_id ,app_secret);
	
	 @GetMapping(value = "/facebookLogin")
	    public String facebookLogin() {
		 System.out.println("inside fb login");
	        OAuth2Operations operations= factory.getOAuthOperations();
	 	    OAuth2Parameters params= new OAuth2Parameters();
	        params.setRedirectUri("http://localhost:9004/doLogin");
	        params.setScope("email, public_profile");
	 	    String authUrl = operations.buildAuthenticateUrl(params);
	        System.out.println("Generated url is= " + authUrl);
	        return "redirect:" + authUrl;
	    }
	 
	 @GetMapping(value = "/doLogin")
	    public String doLogin(@RequestParam("code") String code,ModelMap map) {
		 
		 System.out.println("code............. "  +code);
	        OAuth2Operations operations= factory.getOAuthOperations();	 
	        // create access token.
	        AccessGrant accessToken= operations.exchangeForAccess(code, "http://localhost:9004/doLogin", null);
	        System.out.println("accessToken......... " +accessToken);
	        Connection<Facebook> connection= factory.createConnection(accessToken);
	 	     Facebook facebook= connection.getApi();
	        // Fetching the details from the facebook.
	        String[] fields = { "id", "name", "email", "about", "birthday"};
	       User userProfile= facebook.fetchObject("me", User.class, fields);	 
	        System.out.println("userProfile.......... "  +userProfile);
	        System.out.println("email........... "  +userProfile.getEmail());
	        System.out.println("name........... "  +userProfile.getName());
	        map.put("name", userProfile.getName());
	        
	        // register this user data in your web application
	        return "dashboard";
	    }
	 

}
