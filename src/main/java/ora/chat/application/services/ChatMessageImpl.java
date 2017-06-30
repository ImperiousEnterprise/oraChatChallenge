package ora.chat.application.services;

import ora.chat.application.models.Message;
import ora.chat.application.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChatMessageImpl implements ChatMessageService {

    @Autowired
    private MessageRepository chatMessageRepository;

    public Page<Message> findByChatId(Long id, int page, int limit){
        Pageable pageable = new PageRequest(page,limit, new Sort(new Order(Sort.Direction.DESC,"createdat")));
        return chatMessageRepository.findByChat_Id(id,pageable);
    }

    public Page<Message> FindAll(){
        Iterable<Message> b = chatMessageRepository.findAll();
        return chatMessageRepository.findAll(new PageRequest(0,5, new Sort(new Order(Sort.Direction.DESC,"createdat"))));
    }
}
