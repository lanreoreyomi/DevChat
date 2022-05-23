package com.devchats.devchats.model;

import static javax.persistence.GenerationType.SEQUENCE;

import com.devchats.devchats.Audit.AuditTrail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
public class User extends AuditTrail implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
  private Long id;

  @NonNull
  @Column(nullable = false, unique = true)
  @Nationalized
  private String username;

  @NonNull
  @Column(nullable = false)
  @Nationalized
  private String password;


  //this email is here for log in purposes. Only one table will be queried
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

  @Column(nullable = false)
  @Nationalized
  private String salt;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
  @JsonIgnore
  @Exclude
  private UserDetails userdetails;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
  @JsonIgnore
  private Address address;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    User user = (User) o;
    return id != null && Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}

