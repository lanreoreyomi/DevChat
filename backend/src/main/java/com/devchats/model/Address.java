package com.devchats.model;

import static javax.persistence.GenerationType.SEQUENCE;

import com.devchats.Audit.AuditTrail;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
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
  @Column(name = "addressId")
  private Long addressId;

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
  @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
  @JsonProperty
  @Exclude
  private AppUser user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Address)) {
      return false;
    }

    Address address = (Address) o;

    if (!addressId.equals(address.addressId)) {
      return false;
    }
    if (!addressLine1.equals(address.addressLine1)) {
      return false;
    }
    if (!city.equals(address.city)) {
      return false;
    }
    if (!state.equals(address.state)) {
      return false;
    }
    if (!country.equals(address.country)) {
      return false;
    }
    if (!zipcode.equals(address.zipcode)) {
      return false;
    }
    return user.equals(address.user);
  }

  @Override
  public int hashCode() {
    int result = addressId.hashCode();
    result = 31 * result + addressLine1.hashCode();
    result = 31 * result + city.hashCode();
    result = 31 * result + state.hashCode();
    result = 31 * result + country.hashCode();
    result = 31 * result + zipcode.hashCode();
    result = 31 * result + user.hashCode();
    return result;
  }
}



