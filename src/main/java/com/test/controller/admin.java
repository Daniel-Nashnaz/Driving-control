package com.test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class admin {


    //@PreAuthorize("permitAll")
    @GetMapping("/all")
    public String allAccess() {
        return "all can.";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/a")
    public String isAuthentic() {
        return "if Authenticated ";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String testquery() {
        return "bbb";
    }
}
