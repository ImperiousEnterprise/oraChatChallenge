package ora.chat.application.controllers;

import ora.chat.application.models.Chat;
import ora.chat.application.models.ChatStart;
import ora.chat.application.models.Message;
import ora.chat.application.models.Users;
import ora.chat.application.models.wrapper.OutputChats;
import ora.chat.application.services.ChatService;
import ora.chat.application.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
public class ChatController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ChatService chatService;

    @RequestMapping(value="/chats", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public OutputChats createChat(@Validated @RequestBody ChatStart start){

        Users current = usersService.getCurrentUser();

        Chat chat = new Chat();
        Message m = generateMessage(start.getMessage(),current);
        m.setChat(chat);
        chat.getMessages().add(m);
        chat.setName(start.getName());
        chat.getUsers().add(current);

        chat = chatService.saveChat(chat);

        OutputChats outputChats = new OutputChats(chat.getId(),chat.getUsers(),chat.getName(),chat.getLast_chat_message());
       return outputChats;

    }

    private Message generateMessage(String message, Users user){
        Message message1 = new Message();
        message1.setMessage(message);
        message1.setUser(user);
        return message1;
    }
}
