package net.guard.passer.admin.user;

import net.guard.passer.common.entity.Role;
import net.guard.passer.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAllUsers(){
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listRoles(){
        return (List<Role>) roleRepository.findAll();
    }

    public void saveUser(User user){
        boolean isUpdatingUser = (user.getId() != null);
        if(isUpdatingUser){
            User existingUser = userRepository.findById(user.getId()).get();

            if(user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            }else {
                encodePassword(user);
            }

        }else {
            encodePassword(user);
        }

        userRepository.save(user);
    }

    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email){
        User userByEmail = userRepository.getUserByEmail(email);

        if(userByEmail == null) return true;

        boolean isCreatingNew = (id == null);

        if(isCreatingNew){
            if(userByEmail != null) return false;
        }else {
            if(userByEmail.getId() != id){
                return false;
            }
        }
        return true;
    }

    public User getUser(Integer id) throws UserNotFoundException{
        try {
            return userRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new UserNotFoundException("Пользователь с ID: " + id + " не найден");
        }
    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if(countById == null || countById == 0){
            throw new UserNotFoundException("Пользователь с ID: " + id + " не найден");
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled){
        userRepository.updateEnableStatus(id, enabled);
    }
}
