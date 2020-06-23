package com.fdmgroup.bank.controller.utility;

import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/AllUsers")
    public ResponseEntity<Page<User>> allUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(userService.findAll(page, size));
    }


}
