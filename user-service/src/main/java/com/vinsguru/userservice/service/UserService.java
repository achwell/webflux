package com.vinsguru.userservice.service;

import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.repository.UserRepository;
import com.vinsguru.userservice.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<UserDto> getAll() {
        return this.userRepository.findAll().map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> getUserById(Integer id) {
        return userRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> insertUser(Mono<UserDto> userDtoMono) {
        return userDtoMono
                .map(EntityDtoUtil::toEntity)
                .flatMap(userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> updateUser(Integer id, Mono<UserDto> userDtoMono) {
        return userRepository.findById(id)
                .flatMap(p -> userDtoMono
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(userRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUser(Integer id) {
        return userRepository.deleteById(id);
    }
}
