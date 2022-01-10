package io.micronaut.live.security.services;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.live.security.model.Role;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface RoleService {

    @NonNull
    Optional<Role> save(@NonNull @NotBlank String authority);
}
