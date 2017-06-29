package ora.chat.application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ora.chat.application.services.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MR.F on 6/29/2017.
 */
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TokenHelper tokenHelper;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        String authToken = tokenHelper.getToken(request);
        if(authToken != null){
            tokenHelper.invalidateToken(authToken);
        }
    }
}
