package ora.chat.application.models.wrapper;


import lombok.Getter;
import lombok.Setter;
import ora.chat.application.models.Chat;
import ora.chat.application.models.Message;
import ora.chat.application.models.Users;
import org.springframework.data.domain.Page;

import java.util.*;

@Getter
@Setter
public class OutputChats {

    private HashMap<String, Object> meta;
    private Object data;

    public OutputChats(){}

    public OutputChats(Long id, List<Users> user, String name, Message last){
        data = new DataWrapper(Math.toIntExact(id),name,convertToOutputResutltsArray(user),last);
        meta = new HashMap<String, Object>();
    }
    public OutputChats( Message messagetoAdd, Users current, Chat chat){
        data = OutputMessages.builder().chat_id(chat.getId())
                .create_at(messagetoAdd.getCreatedat())
                .id(messagetoAdd.getId())
                .message(messagetoAdd.getMessage())
                .user_id(current.getId())
                .user(new OutputResults(current.getId(),current.getName(),current.getEmail()).getData()).build();
        meta = new HashMap<String, Object>();
    }

    public void PaginateChats(Page<Chat> pageResults, int limit){
        Pagination p = new Pagination(pageResults.getNumber()+1,
                limit,
                pageResults.getTotalPages(),
                pageResults.getTotalElements());


        data = convertPaginationtoArray(pageResults.getContent());

        meta = new HashMap<String, Object>();
        meta.put("pagination", p);
    }

    public void PaginateMessages(Page<Message> pageResults, int limit){
        Pagination p = new Pagination(pageResults.getNumber()+1,
                limit,
                pageResults.getTotalPages(),
                pageResults.getTotalElements());

        data = convertMessagetoPaginationArray(pageResults.getContent());

        meta = new HashMap<String, Object>();
        meta.put("pagination", p);
    }


    private ArrayList<OutputMessages> convertMessagetoPaginationArray(List<Message> messages){
        ArrayList<OutputMessages> wrapper = new ArrayList<OutputMessages>();
        for(Message s : messages){
            wrapper.add(OutputMessages.builder()
                    .id(s.getId())
                    .chat_id(s.getChat().getId())
                    .create_at(s.getCreatedat())
                    .message(s.getMessage())
                    .user_id(s.getUser().getId())
                    .user(new OutputResults(s.getUser().getId(),s.getUser().getName(),s.getUser().getEmail()).getData()).build());
        }

        return wrapper;
    }

    private ArrayList<DataWrapper> convertPaginationtoArray(List<Chat> chats){
        ArrayList<DataWrapper> wrapper = new ArrayList<DataWrapper>();
        for(Chat c : chats){
            wrapper.add(new DataWrapper(Math.toIntExact(c.getId()),c.getName(),convertToOutputResutltsArray(c.getUsers()),c.getLast_chat_message()));
        }
        return wrapper;
    }

    private List<OutputResults.Data> convertToOutputResutltsArray(List<Users> user){
        List<OutputResults.Data> resultss = new ArrayList<OutputResults.Data>();

        for(Users u : user){
            resultss.add(new OutputResults(u.getId(),u.getName(),u.getEmail()).getData());
        }
        return resultss;
    }

    @Getter
    class Pagination{
        private int current_page;
        private int per_page;
        private int page_count;
        private long total_count;

        public Pagination(int current_page, int per_page, int page_count, long total_count){
            this.current_page = current_page;
            this.page_count = page_count;
            this.per_page = per_page;
            this.total_count = total_count;
        }
    }

    @Getter
    class DataWrapper {
        private int id;
        private String name;
        private List<OutputResults.Data> users;
        private OutputMessages last_chat_message;

        public DataWrapper(int id, String name, List<OutputResults.Data> out, Message last){
            this.id = id;
            this.name = name;
            this.users = out;
            this.last_chat_message = OutputMessages.builder()
                    .id(last.getId())
                    .chat_id(last.getChat().getId())
                    .message(last.getMessage())
                    .create_at(last.getCreatedat())
                    .user_id(last.getUser().getId())
                    .user(new OutputResults(last.getUser().getId(),last.getUser().getName(),last.getUser().getEmail()).getData())
                    .build();
        }

    }
}
