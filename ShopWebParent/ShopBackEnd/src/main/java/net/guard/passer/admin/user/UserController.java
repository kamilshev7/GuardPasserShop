package net.guard.passer.admin.user;

import net.guard.passer.common.entity.Role;
import net.guard.passer.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listAllUsers(Model model){
        List<User> listUsers = userService.listAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model){
        List<Role> listRoles = userService.listRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle",
                "Создание нового пользователя");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes){
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message",
                "Пользователь успешно сохранен");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           RedirectAttributes redirectAttributes, Model model){
        try {
            User user = userService.getUser(id);
            List<Role> listRoles = userService.listRoles();

            model.addAttribute("user", user);
            model.addAttribute("listRoles", listRoles);
            model.addAttribute("pageTitle",
                    "Редактирование пользователя (ID: " + id + ")");
            return "user_form";
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",
                    ex.getMessage());
        }

        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                           RedirectAttributes redirectAttributes){
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message",
                    "Пользователь c ID: " + id + " был успешно удален");
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",
                    ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String upateUserEnabledStatus(@PathVariable(name = "id") Integer id,
                             @PathVariable("status") boolean enabled,
                             RedirectAttributes redirectAttributes){
        userService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "активирован" : "заблокирован";
        String message = "Пользователь с ID: " + id + " был " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }

}
