package ru.isaev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.domain.userDtos.UserDto;
import ru.isaev.domain.userDtos.UserIdAndLikedIdsDto;
import ru.isaev.domain.users.Roles;
import ru.isaev.domain.users.User;
import ru.isaev.service.mapper.IMyMapper;
import ru.isaev.service.userService.IUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    private final IMyMapper mapper;

    @Autowired
    public UserController(IUserService userService, IMyMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                mapper.userToUserDto(userService.getUserById(id)),
                HttpStatus.OK
        );
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> getByEmail(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(
                mapper.userToUserDto(userService.getUserByEmail(email)),
                HttpStatus.OK
        );
    }

    @GetMapping("/login")
    public ResponseEntity<UserIdAndLikedIdsDto> login(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password
            ) {
        UserIdAndLikedIdsDto response = userService.login(email, password);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/role")
    public ResponseEntity<List<UserDto>> getByRole(@RequestParam(name = "role") Roles role) {
        return new ResponseEntity<>(
                mapper.mapListOfUsersToListOfDtos(userService.getUserByRole(role)),
                HttpStatus.OK
        );
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        User user = mapper.userDtoToUser(userDto);
        userService.addUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PatchMapping("/edit")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userDto) {
        User owner = mapper.userDtoToUser(userDto);
        userService.updateUser(owner);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.removeUserById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

