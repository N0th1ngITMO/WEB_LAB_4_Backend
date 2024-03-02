package ru.kkettch.service.serviceInterface;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import ru.kkettch.dataTransferObject.JWTReqDto;
import ru.kkettch.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserService extends UserDetailsService {
    UserEntity addUser(JWTReqDto jwtRequestDto, String userRole);

    Boolean isUserExistByName(String name);

    List<UserEntity> findAllUsers();

    Optional<UserEntity> findUserByUserName(String name);

}
