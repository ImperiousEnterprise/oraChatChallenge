package ora.chat.application.services;

import ora.chat.application.models.Users;
import ora.chat.application.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MR.F on 6/28/2017.
 */
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository userRepository;

    @Override
    public Users findByUsername(String username ) throws UsernameNotFoundException {
        Users u = userRepository.findByUsername( username );
        return u;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Users findById(Long id ) throws AccessDeniedException {
        Users u = userRepository.findOne( id );
        return u;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Users> findAll() throws AccessDeniedException {
        List<Users> result = userRepository.findAll();
        return result;
    }
}
