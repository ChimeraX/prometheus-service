package org.chimerax.prometheus.configuration;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.entity.User;
import org.chimerax.prometheus.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 2:39 PM
 */

@Component
@AllArgsConstructor
public class MockDataRunner implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        User tony = new User()
                .setUsername("IronMan")
                .setPassword(passwordEncoder.encode("IronManTheBestAvenger"))
                .setCountry("US")
                .setEmail("IronMan@stark.industries")
                .setProfilePicture("https://ae01.alicdn.com/kf/HTB1SwWCcBKw3KVjSZFOq6yrDVXa4.jpg_q50.jpg")
                .setFirstName("Tony")
                .setLastName("Stark")
                .setPhoneNumber("+4074213213");

        // userRepository.save(tony);
    }
}
