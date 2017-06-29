package ora.chat.application.security;

import ora.chat.application.services.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.core.Authentication;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class JWTAuthenticationFilter extends GenericFilterBean {


    private TokenHelper tokenHelper;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {


        tokenHelper = WebApplicationContextUtils
                .getWebApplicationContext(request.getServletContext()).getBean(TokenHelper.class);

        String authToken = tokenHelper.getToken((HttpServletRequest)request);


        if(tokenHelper.isInvalidToken(authToken)){
            SecurityContextHolder.getContext()
                    .setAuthentication(null);
        }else{
            Authentication authentication = tokenHelper.getAuthentication(authToken);
            SecurityContextHolder.getContext()
                .setAuthentication(authentication);
            HttpServletResponse res = (HttpServletResponse) response;

            res.addHeader("Authorization", "Bearer" + " " + authToken);
            res.setContentType("application/json; charset=UTF-8");
        }
        filterChain.doFilter(request,response);
    }
}