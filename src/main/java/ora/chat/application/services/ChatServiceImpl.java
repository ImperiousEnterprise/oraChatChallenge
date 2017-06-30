package ora.chat.application.services;

import lombok.Setter;
import ora.chat.application.models.Chat;
import ora.chat.application.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zstaff on 2017/06/30.
 */
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat saveChat(Chat c) {
        return chatRepository.save(c);
    }
}
