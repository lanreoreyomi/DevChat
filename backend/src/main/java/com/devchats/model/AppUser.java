package com.devchats.model;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

import com.devchats.Audit.AuditTrail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity(name = "Users")
public class AppUser extends AuditTrail implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
  private Long userId;

  @NonNull
  @Column(nullable = false, unique = true)
  @Nationalized
  private String userName;

  @NonNull
  @Column(nullable = false)
  @Nationalized
  private String password;

  @NonNull
  @Column(nullable = false)
  @Nationalized
  private String email;

  @NonNull
  @Column(nullable = false)
  @Nationalized
  private String firstName;

  @NonNull
  @Column(nullable = false)
  @Nationalized
  private String lastName;


  @OneToOne(fetch = EAGER, cascade = CascadeType.ALL, mappedBy = "user")
  @JsonIgnore
  @Exclude
  private UserDetails userdetails;

  @OneToOne(fetch = EAGER, cascade = CascadeType.ALL, mappedBy = "user")
  @JsonIgnore
  private Address address;

  @ManyToMany(fetch=EAGER)
  private Collection<Role> roles = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    AppUser user = (AppUser) o;
    return userId != null && Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}

