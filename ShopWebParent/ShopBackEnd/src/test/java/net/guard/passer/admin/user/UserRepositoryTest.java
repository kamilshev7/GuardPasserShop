package net.guard.passer.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import net.guard.passer.common.entity.Role;
import net.guard.passer.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userAlex = new User("alex@", "alex", "alex", "dor");
        userAlex.addRole(roleAdmin);

        User savedUser = userRepository.save(userAlex);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles(){
        User userOleg = new User("oleg@", "oleg", "oleg", "zai");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        userOleg.addRole(roleEditor);
        userOleg.addRole(roleAssistant);

        User savedUser = userRepository.save(userOleg);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> listUser = userRepository.findAll();
        listUser.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        User userAlex = userRepository.findById(1).get();
        System.out.println(userAlex);
        assertThat(userAlex).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User userAlex = userRepository.findById(1).get();
        userAlex.setEnabled(true);
        userAlex.setEmail("alex@mail.ru");
        userRepository.save(userAlex);
    }

    @Test
    public void testUpdateUserRoles(){
        User userOleg = userRepository.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);
        userOleg.getRoles().remove(roleEditor);
        userOleg.addRole(roleSalesperson);

        userRepository.save(userOleg);
    }

    @Test
    public void testDeleteUser(){
        userRepository.deleteById(2);
    }

    @Test
    public void testFetUserByEmail(){
        String email = "alex@mail.ru";
        User user = userRepository.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById(){
        Integer id = 1;
        Long countById = userRepository.countById(id);
        assertThat(countById).isNotNull().isGreaterThan(0);
    }
}
