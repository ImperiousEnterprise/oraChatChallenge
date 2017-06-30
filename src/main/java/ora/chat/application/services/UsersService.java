package ora.chat.application.services;

import ora.chat.application.models.Users;

import java.util.List;

/**
 * Created by MR.F on 6/28/2017.
 */
public interface UsersService {
    public Users findById(Long id);
    public Users findByUsername(String username);
    public Users findByEmail(String email);
    public List<Users> findAll();
    public Users saveUsers(Users user);
    public Users getCurrentUser();
}
