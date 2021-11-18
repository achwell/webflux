package com.vinsguru.userservice.controller;

import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public Flux<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserDto>> insertUser(@RequestBody Mono<UserDto> userDto) {
        return userService.insertUser(userDto).map(p -> ResponseEntity.created(URI.create("/" + p.getId())).body(p));
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDto>> updateUser(@PathVariable Integer id, @RequestBody Mono<UserDto> userDto) {
        return userService.updateUser(id, userDto).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return userService.deleteUser(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
