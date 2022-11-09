package com.devchats.model;

import static javax.persistence.GenerationType.SEQUENCE;

import com.devchats.Audit.AuditTrail;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity(name = "UserDetails")
public class UserDetails extends AuditTrail implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(
      name = "user_details_seq",
      sequenceName = "user_details_seq",
      allocationSize = 1

  )
  @GeneratedValue(strategy = SEQUENCE,
      generator = "user_details_seq")
  @Column(updatable = false,
      name = "id")
  @JsonProperty(value = "userDetailsId")
  private Long id;

  @Column(
      name = "phone",
      columnDefinition = "TEXT",
      nullable = false
  )
  @JsonProperty(value = "phone")
  @NonNull
  private String phone;

  @Column(
      name = "about",
      columnDefinition = "TEXT"
  )
  @JsonProperty(value = "about")
  @NonNull
  private String about;


  @Column(
      name = "occupation",
      columnDefinition = "TEXT",
      nullable = false
  )
  @JsonProperty(value = "occupation")
  @NonNull
  private String occupation;

  @Column(
      name = "education",
      columnDefinition = "TEXT",
      nullable = false
  )
  @JsonProperty(value = "education")
  @NonNull
  @Nationalized
  private String education;
  @Column(
      name = "birthday",
      columnDefinition = "TEXT",
      nullable = false
  )
  @JsonProperty(value = "birthday")
  @NonNull
  @JsonFormat(pattern="dd-MM-yyyy")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate birthday;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
  @JsonProperty
  private AppUser user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    UserDetails userdetails = (UserDetails) o;
    return id != null && Objects.equals(id, userdetails.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
