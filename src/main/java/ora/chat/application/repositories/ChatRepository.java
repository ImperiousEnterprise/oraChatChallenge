package ora.chat.application.repositories;

import ora.chat.application.models.Chat;
import ora.chat.application.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zstaff on 2017/06/30.
 */
public interface ChatRepository extends JpaRepository<Chat,Long> {

}
