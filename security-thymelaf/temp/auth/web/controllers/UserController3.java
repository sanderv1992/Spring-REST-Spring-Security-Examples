package com.example.auth.web.controllers;

import com.example.auth.domain.User;
import com.example.auth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserController3 {

    private final UserRepository userRepository;

    @Autowired
    public UserController3(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Resources<User>> getUsers() {
        List<User> users = userRepository.findAll();

        Resources<User> resources = new Resources<>(users);

        resources.add(linkTo(methodOn(UserController3.class).getUsers()).withSelfRel());

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") Long userId, PersistentEntityResourceAssembler entityAssembler) {

        return ResponseEntity.ok(entityAssembler.toResource(userRepository.findByUsername("Sander")
                .orElseThrow(() -> new IllegalArgumentException("No report found with ID: " + userId))));

//        return userRepository.findByUsername("Sander")
//                .map(user -> new Resource<>(user, getSingleItemLinks(user.getId())))
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }

    private List<Link> getSingleItemLinks(long id) {
        return Arrays.asList(
                linkTo(methodOn(UserController3.class).getUsers()).withRel("users")
        );

//        return Arrays.asList(linkTo(methodOn(UserController.class).findOne(id)).withSelfRel()
//                        .andAffordance(afford(methodOn(UserController.class).updateEmployee(null, id)))
//                        .andAffordance(afford(methodOn(UserController.class).deleteEmployee(id))),
//                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));
    }
}
