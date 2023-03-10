
package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Users;
import ra.model.repository.UserRepository;
import ra.model.service.UserService;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Users> findByUserName(String userName) {
        return userRepository.findAll();
    }

    @Override
    public List<Users> searchUserName(String userName) {
        return userRepository.findByUserNameContaining(userName);
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public Users saveOrUpdate(Users user) {
        return userRepository.save(user);
    }

    @Override
    public Users findByUserId(int users) {
        return userRepository.findById(users).get();
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }
}