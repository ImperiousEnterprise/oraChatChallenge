package ora.chat.application.controllers;


import com.fasterxml.jackson.databind.node.ObjectNode;
import ora.chat.application.globalerrors.ChatException;
import ora.chat.application.models.*;
import ora.chat.application.models.wrapper.OutputChats;
import ora.chat.application.services.ChatMessageService;
import ora.chat.application.services.ChatService;
import ora.chat.application.services.UsersService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
public class ChatController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMessageService chatMessageService;

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

    @RequestMapping(value="/chats/{id}", method = RequestMethod.PATCH, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public OutputChats updateChat(@PathVariable(value="id") Long id, @RequestBody ObjectNode name) throws Exception {
        String n = name.get("name").asText();

        if(n == null){
            throw new ChatException("Name Problem","name is required");
        }else if(n.isEmpty()){
            throw new ChatException("Name Problem","No empty names please.");
        }

        Users current = usersService.getCurrentUser();
        Chat chat = chatService.findByIdAndUser(id,current);
        if(chat == null){
            throw new ChatException("Authorization","Not your chat to update");
        }
        chat.setName(n);
        chatService.saveChat(chat);

       return new OutputChats(chat.getId(),chat.getUsers(),chat.getName(),chat.getLast_chat_message());
    }

    @RequestMapping(value="/chats/{id}/chat_messages", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public OutputChats createMessageForChat(@PathVariable(value="id") Long id, @RequestBody ObjectNode message) throws Exception{
        String mes = message.get("message").asText();

        if(mes == null){
            throw new ChatException("Message Problem","message is required");
        }else if(mes.isEmpty()){
            throw new ChatException("Message Problem","No empty message please.");
        }

        Chat chat = chatService.findById(id);

        if(chat == null){
            throw new ChatException("Chat Problem","Chat doesn't exist");
        }

        Users current = usersService.getCurrentUser();

        Message messagetoAdd = generateMessage(mes,current);
        messagetoAdd.setChat(chat);

        chat.getMessages().add(messagetoAdd);
        chat = chatService.saveChat(chat);
        messagetoAdd = chat.getLast_chat_message();

        return new OutputChats(messagetoAdd,current,chat);
    }
    @RequestMapping(value="/chats/{id}/chat_messages", method = RequestMethod.GET)
    @ResponseBody
    public OutputChats PaginateChatMessages(@PathVariable(value="id") Long id,
                                            @NotNull(message = "error.page.notnull")
                                                @Size(min=1,message = "error.page.size")
                                                @RequestParam("page") int page,
                                            @NotNull(message = "error.limit.notnull")
                                                @Size(min=1,message = "error.limit.size")
                                                @RequestParam("limit") int limit){

        Page<Message> messagePage = chatMessageService.findByChatId(id,page-1,limit);
        OutputChats output = new OutputChats();
        output.PaginateMessages(messagePage,limit);
        return output;

    }


    @RequestMapping(value="/chats", method = RequestMethod.GET)
    @ResponseBody
    public OutputChats PaginateChatMessages(@NotNull(message = "error.page.notnull")
                                                @Size(min=1,message = "error.page.size")
                                                @RequestParam("page") int page,
                                            @NotNull(message = "error.limit.notnull")
                                            @Size(min=1,message = "error.limit.size")
                                            @RequestParam("limit") int limit){
        Page<Chat> pageResults = chatService.findByPage(page-1,limit);
        OutputChats output = new OutputChats();
        output.PaginateChats(pageResults,limit);
        return output;
    }




    private Message generateMessage(String message, Users user){
        Message message1 = new Message();
        message1.setMessage(message);
        message1.setUser(user);
        return message1;
    }
}
