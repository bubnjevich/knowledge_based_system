package com.ftn.sbnz.service.services.implementation;
import com.ftn.sbnz.model.DTO.UserDTO;
import com.ftn.sbnz.model.User;
import com.ftn.sbnz.service.repositories.UserRepository;
import com.ftn.sbnz.service.security.SecurityUser;
import com.ftn.sbnz.service.security.UserFactory;
import com.ftn.sbnz.service.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.print.Pageable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public SecurityUser findByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username '%s' is not found!", username)));

        return UserFactory.create(user);
    }

    @Override
    public UserDTO createUserDTO(UserDTO passengerDTO) {
        User passenger = new User();
        passenger.setName(passengerDTO.getName());
        passenger.setLastName(passengerDTO.getLastName());
        passenger.setEmail(passengerDTO.getEmail());
        passenger.setPassword(passwordEncoder.encode(passengerDTO.getPassword()));
        return new UserDTO(this.userRepository.save(passenger));
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User findOne(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public Set<User> getUsers() {
        return new HashSet<>(this.userRepository.findAll());
    }

    @Override
    public Page<User> findAll(Pageable page) {
        return this.userRepository.findAll((org.springframework.data.domain.Pageable) page);
        }
}
