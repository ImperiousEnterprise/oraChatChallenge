package ora.chat.application.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import ora.chat.application.models.Authority;
import ora.chat.application.models.wrapper.OutputResults;
import ora.chat.application.models.Users;
import ora.chat.application.services.TokenHelper;
import ora.chat.application.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ora.chat.application.controllers.AuthController.HEADER_STRING;
import static ora.chat.application.controllers.AuthController.TOKEN_PREFIX;


@RestController
public class UsersController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersService usersService;

    @Autowired
    private TokenHelper tokenHelper;

    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = {MediaType.ALL_VALUE})
    @ResponseBody
    public OutputResults createUser(@Validated({Users.ValidationStepOne.class,Users.ValidationStepTwo.class}) @RequestBody Users users
            ,HttpServletResponse res) throws IOException{

        String pass = users.getPassword();
        users.setPassword(passwordEncoder.encode(pass));

        //Authories for new users need to be set
        List<Authority> grantedAuths = new ArrayList<>();
        Authority newAuthority = new Authority();
        newAuthority.setName("ROLE_USER");
        grantedAuths.add(newAuthority);
        users.setAuthorities(grantedAuths);

        Users u = usersService.saveUsers(users);

        //After saving successfully to the DB token is generated and returned to header
        String token = tokenHelper.generateToken(users.getEmail()) ;

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);


        res.setContentType("application/json");

        OutputResults output = new OutputResults(u.getId(), u.getName(),u.getEmail());
        res.getWriter().write( new ObjectMapper().writeValueAsString(output));

        Authentication authentication = tokenHelper.getAuthentication(token);
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

       return output;

    }

    @GetMapping("/users/current")
    public OutputResults currentuser() {
        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Users u = usersService.findByEmail(email);
      return new OutputResults(u.getId(),u.getName(),u.getEmail());

    }


    @RequestMapping(value = "/users/current", method = RequestMethod.PATCH, consumes = {MediaType.ALL_VALUE})
    public OutputResults updateUser(@Validated({Users.ValidationStepTwo.class})  @RequestBody Users users){
        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        Users u = usersService.findByEmail(email);
        u = updateUserData(u,users);
        usersService.saveUsers(u);



        return new OutputResults(u.getId(),u.getName(),u.getEmail());

    }

    private Users updateUserData(Users old, Users brand_new){
        if(brand_new.getEmail() != null){
            old.setEmail(brand_new.getEmail());
        }

        if(brand_new.getName() != null){
            old.setName(brand_new.getName());
        }

        if(brand_new.getPassword() != null){
            String encoded = passwordEncoder.encode(brand_new.getPassword());
            old.setPassword(encoded);
            old.setPassword_confirmation(encoded);
        }
        return old;
    }

}
