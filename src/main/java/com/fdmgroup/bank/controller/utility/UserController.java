package com.fdmgroup.bank.controller.utility;

import com.fdmgroup.bank.models.User;
import com.fdmgroup.bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

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

    @GetMapping("/SeeUser/{username}")
    public ResponseEntity<User> seeUser(@PathVariable("username") String username) {
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/AddUser")
    public ResponseEntity<HttpStatus> addUser(@RequestBody User user) {
        Optional<User> userFromDB = userService.findByUserId(user.getUserId());
        if(userFromDB.isPresent()) {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }
        try {
            userService.save(user);
        }catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


}
