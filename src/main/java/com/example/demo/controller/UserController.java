package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.util.annotation.ApiMessage;
import com.example.demo.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor  // Dùng @RequiredArgsConstructor thay vì @AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // Tìm all user
    @GetMapping("")
    @ApiMessage("fetch all users")
    public ResponseEntity<?> getAllUser(
            @Filter Specification<User> spec,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.fetchAllUser(spec, pageable));
    }

    @PostMapping("")
    @ApiMessage("Create a new user")
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User postManUser)
            throws IdInvalidException {
        boolean isEmailExist = userService.isEmailExist(postManUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                    "Email " + postManUser.getEmail() + " đã tồn tại, vui lòng sử dụng email khác.");
        }
        String hashPassword = passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User newUser = userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/{id}")
    @ApiMessage(value = "delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("id khong duoc > 1500");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }
}
