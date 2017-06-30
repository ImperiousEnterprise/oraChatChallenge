package ora.chat.application.repositories;

import ora.chat.application.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message,Long> {
    Page<Message> findByChat_Id(Long id,Pageable pageable);
}
