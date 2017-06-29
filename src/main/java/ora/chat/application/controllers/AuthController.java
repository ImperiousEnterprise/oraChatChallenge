package ora.chat.application.controllers;

import ora.chat.application.models.AccountCredentials;
import ora.chat.application.services.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by zstaff on 2017/06/26.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenHelper tokenHelper;

    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";


    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {

        String authToken = tokenHelper.getToken( request );
        if (authToken != null && tokenHelper.canTokenBeRefreshed(authToken)) {
            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken);
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + refreshedToken);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.accepted().body(authToken);
        }
    }
}
