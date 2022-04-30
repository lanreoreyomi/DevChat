package com.devchats.devchats.model;

import static javax.persistence.GenerationType.SEQUENCE;

import com.devchats.devchats.Audit.AuditTrail;
import java.io.Serializable;
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
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Nationalized;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity(name = "Address")
public class Address extends AuditTrail implements Serializable {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "address_seq")
  @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @NonNull
  @Column(name = "address_line1")
  @Nationalized
  private String addressLine1;

  @NonNull
  @Column(name = "city")
  @Nationalized
  private String city;

  @NonNull
  @Column(name = "state")
  @Nationalized
  private String state;

  @NonNull
  @Column(name = "country")
  @Nationalized
  private String country;

  @NonNull
  @Column(name = "zipcode")
  @Nationalized
  private String zipcode;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @Exclude
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Address address = (Address) o;
    return id != null && Objects.equals(id, address.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}



