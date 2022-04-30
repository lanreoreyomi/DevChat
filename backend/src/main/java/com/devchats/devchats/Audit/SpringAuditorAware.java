package com.devchats.devchats.Audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class SpringAuditorAware implements AuditorAware {


  //you can return logged in user
  @Override
  public Optional getCurrentAuditor() {
    return Optional.ofNullable("Lanre").filter(s -> !s.isEmpty());
  }
}
