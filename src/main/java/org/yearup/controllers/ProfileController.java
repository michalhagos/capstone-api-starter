package org.yearup.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;


@RestController
@RequestMapping("profile")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;

    public ProfileController(ProfileService profileService, UserService userService)
    {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping("")
    public Profile getProfile(Principal principal)
    {
        int userId = getCurrentUserId(principal);
        Profile profile = profileService.getByUserId(userId);

        if (profile == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found.");
        }
        return profile;
    }

    @PutMapping("")
    public Profile updateProfile(@RequestBody Profile profile, Principal principal) {
        int userId = getCurrentUserId(principal);
        Profile updated = profileService.update(userId, profile);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found.");
        }
        return updated;
    }

    private int getCurrentUserId(Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required.");
        }
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required.");
        }
        return user.getId();
    }

}
