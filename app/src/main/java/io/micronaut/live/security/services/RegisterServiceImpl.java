package io.micronaut.live.security.services;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Singleton
public class RegisterServiceImpl implements RegisterService {
    private final UserService userService;
    private final RoleService roleService;

    public RegisterServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void register(@NonNull @NotBlank @Email String email,
                         @NonNull@NotBlank String rawPassword,
                         @NonNull List<String> authorities) {

    }
}
