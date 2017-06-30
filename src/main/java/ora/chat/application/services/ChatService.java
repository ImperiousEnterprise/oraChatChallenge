package ora.chat.application.services;

import ora.chat.application.models.Chat;
import ora.chat.application.models.Users;
import org.springframework.data.domain.Page;

/**
 * Created by zstaff on 2017/06/30.
 */
public interface ChatService {
    Chat saveChat(Chat c);
    Chat findByIdAndUser(Long id,Users u);
    Chat findById(Long id);
    Page<Chat> findByPage(int page, int limit);
}
