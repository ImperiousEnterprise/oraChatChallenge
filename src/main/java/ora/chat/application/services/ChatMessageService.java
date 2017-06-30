package ora.chat.application.services;

import ora.chat.application.models.Message;
import org.springframework.data.domain.Page;

/**
 * Created by MR.F on 7/3/2017.
 */
public interface ChatMessageService {
    Page<Message> findByChatId(Long id, int page, int limit);
    Page<Message> FindAll();
}
