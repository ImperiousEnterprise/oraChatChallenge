package ora.chat.application.models.wrapper;

import lombok.Builder;
import lombok.Getter;
import ora.chat.application.models.Message;
import ora.chat.application.models.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zstaff on 2017/06/30.
 */
public class OutputChats {

    private HashMap<String, String> meta;
    private OutputChats.Data data;

    public OutputChats(Long id, List<Users> user, String name, Message last){
        data = OutputChats.Data.builder().name(name).id(Math.toIntExact(id))
                .last_chat_message(last).userss(user.toArray(new OutputResults[user.size()])).build();
        meta = new HashMap<String, String>();
    }

    @Getter
    @Builder
    class Data {
        private int id;
        private String name;
        private OutputResults[] userss;
        private Message last_chat_message;
    }
}
