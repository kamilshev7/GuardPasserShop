package net.guard.passer.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    @Test
    public void testEncoderPassword(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String alexPassword = "alex";
        String encoderPassword = passwordEncoder.encode(alexPassword);
        System.out.println(encoderPassword);
        boolean matches = passwordEncoder.matches(alexPassword, encoderPassword);

        assertThat(matches).isTrue();
    }
}
