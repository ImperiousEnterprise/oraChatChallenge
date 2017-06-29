package ora.chat.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
public class ChatApplication {


    public static void main(String[] args) {SpringApplication.run(ChatApplication.class, args);
    }
}
