package com.devchats.Audit;

import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;

public class SpringAuditorAware implements AuditorAware {


  //you can return logged in user
  @Override
  public @NonNull Optional<String> getCurrentAuditor() {
    return Optional.of("Admin");
  }
}
