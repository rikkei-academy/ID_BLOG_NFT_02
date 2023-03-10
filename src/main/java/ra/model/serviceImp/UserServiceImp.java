
package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.Users;
import ra.model.repository.UserRepository;
import ra.model.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Users> sortByStudentName(String direction) {
        if(direction.equals("asc")){
            return userRepository.findAll(Sort.by("userName").ascending());
        }else {
            return userRepository.findAll(Sort.by("userName").descending());
        }
    }

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

    @Override
    public Page<Users> pagging(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Users updateStatus(Users users) {
        return userRepository.save(users);
    }

    @Override
    public List<Users> listFilter(Integer option) {
        List<Users> userList = userRepository.findAll();
        List<Users> listFilter = new ArrayList<>();
        for (Users users : userList ) {
            if (users.getListRoles().size()==option){
                listFilter.add(users);
            }
        }
        return listFilter;

    }




}