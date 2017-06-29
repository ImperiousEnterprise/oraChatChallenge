package ora.chat.application.repositories;

import ora.chat.application.models.Chat;
import ora.chat.application.models.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by zstaff on 2017/06/30.
 */
public interface ChatRepository extends JpaRepository<Chat,Long> {

    Chat findByIdAndUsers(Long id,Users u);
}
