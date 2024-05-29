package com.ftn.sbnz.service.services.interfaces;


import com.ftn.sbnz.model.DTO.UserDTO;
import com.ftn.sbnz.model.User;
import com.ftn.sbnz.service.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.Set;

public interface IUserService {
    SecurityUser findByUsername(String username) throws UsernameNotFoundException;

    UserDTO createUserDTO(UserDTO passengerDTO);

    Optional<User> findByEmail(String email);
    User save(User user);
    User findOne(Long id);
    Set<User> getUsers();
    Page<User> findAll(Pageable page);


}
