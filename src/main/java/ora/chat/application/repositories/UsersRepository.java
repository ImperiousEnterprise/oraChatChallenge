package ora.chat.application.repositories;

import ora.chat.application.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
    public Users findByUsername( String username );
    public Users findByEmail( String email );
}
