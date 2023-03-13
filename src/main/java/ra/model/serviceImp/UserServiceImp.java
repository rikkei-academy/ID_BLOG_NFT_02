
package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.User;
import ra.model.repository.UserRepository;
import ra.model.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> sortByUserName(String direction) {
        if(direction.equals("asc")){
            return userRepository.findAll(Sort.by("userName").ascending());
        }else {
            return userRepository.findAll(Sort.by("userName").descending());
        }
    }



    @Override
    public List<User> findByUserName(String userName) {
        return userRepository.findAll();
    }

    @Override
    public List<User> searchUserName(String userName) {
        return userRepository.findByUserNameContaining(userName);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByUserEmail(email);
    }


    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUserId(int users) {
        return userRepository.findById(users).get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> pagging(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User updateStatus(User users) {
        return userRepository.save(users);
    }

    @Override
    public List<User> listFilter(Integer option) {
        List<User> userList = userRepository.findAll();
        List<User> listFilter = new ArrayList<>();
        for (User users : userList ) {
            if (users.getListRoles().size()==option){
                listFilter.add(users);
            }
        }
        return listFilter;

    }




}