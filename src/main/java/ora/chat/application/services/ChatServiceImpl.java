package ora.chat.application.services;

import lombok.Setter;
import ora.chat.application.models.Chat;
import ora.chat.application.models.Users;
import ora.chat.application.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort.*;

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

    @Override
    public Chat findByIdAndUser(Long id, Users u) {
        return chatRepository.findByIdAndUsers(id,u);
    }

    @Override
    public Chat findById(Long id){return chatRepository.findOne(id);}

    public Page<Chat> findByPage(int page, int limit){

        Pageable pageable = new PageRequest(page,limit, new Sort(new Order(Direction.DESC,"name")));
        return chatRepository.findAll(pageable);
    }
}
