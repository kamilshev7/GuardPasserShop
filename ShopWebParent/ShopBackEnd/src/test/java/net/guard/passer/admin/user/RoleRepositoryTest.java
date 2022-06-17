package net.guard.passer.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import net.guard.passer.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole(){
        Role roleAdmin = new Role("Админ", "все функции");
        Role savedRole = roleRepository.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles(){
        Role roleSalesperson = new Role("Продавец", "может управлять ценой, " +
                "клиентами, поставками, заказами и отчетами о продажах");
        Role roleEditor = new Role("Редактор", "может управлять категориями, " +
                "продуктами, статьями и меню");
        Role roleShipper = new Role("Доставщик", "может просматривать продукты и " +
                "заказы, обновлять статус заказа");
        Role roleAssistant = new Role("Ассистент", "может отвечать на отзывы и " +
                "вопросы");
        roleRepository.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));

    }
}
