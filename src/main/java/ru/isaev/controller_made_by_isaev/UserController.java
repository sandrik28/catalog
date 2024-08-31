package ru.isaev.controller_made_by_isaev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.isaev.domain_made_by_isaev.userDtos.UserDto;
import ru.isaev.domain_made_by_isaev.userDtos.UserIdAndLikedIdsDto;
import ru.isaev.domain_made_by_isaev.users.Roles;
import ru.isaev.domain_made_by_isaev.users.User;
import ru.isaev.service_made_by_isaev.mapper.IMyMapper;
import ru.isaev.service_made_by_isaev.userService.IUserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final IUserService userService;
    private final IMyMapper mapper;

    @Autowired
    public UserController(IUserService userService, IMyMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        logger.info("UserController - Received request to get user by ID: {}", id);
        UserDto userDto = mapper.userToUserDto(userService.getUserById(id));
        logger.info("UserController - User with ID {} retrieved successfully", id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> getByEmail(@RequestParam(name = "email") String email) {
        logger.info("UserController - Received request to get user by email: {}", email);
        UserDto userDto = mapper.userToUserDto(userService.getUserByEmail(email));
        logger.info("UserController - User with email {} retrieved successfully", email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<UserIdAndLikedIdsDto> login(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password
    ) {
        logger.info("UserController - Received login request for email: {}", email);
        UserIdAndLikedIdsDto response = userService.login(email, password);
        logger.info("UserController - User with email {} logged in successfully", email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/role")
    public ResponseEntity<List<UserDto>> getByRole(@RequestParam(name = "role") Roles role) {
        logger.info("UserController - Received request to get users by role: {}", role);
        List<UserDto> userDtos = mapper.mapListOfUsersToListOfDtos(userService.getUserByRole(role));
        logger.info("UserController - Users with role {} retrieved successfully", role);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        logger.info("UserController - Received request to add user: {}", userDto);
        User user = mapper.userDtoToUser(userDto);
        userService.addUser(user);
        logger.info("UserController - User added successfully: {}", userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PatchMapping("/edit")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userDto) {
        logger.info("UserController - Received request to edit user: {}", userDto);
        User owner = mapper.userDtoToUser(userDto);
        userService.updateUser(owner);
        logger.info("UserController - User edited successfully: {}", userDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        logger.info("UserController - Received request to delete user by ID: {}", id);
        userService.removeUserById(id);
        logger.info("UserController - User with ID {} deleted successfully", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}