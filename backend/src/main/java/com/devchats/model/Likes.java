package com.devchats.model;

import static javax.persistence.GenerationType.SEQUENCE;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity(name = "Likes")
public class Likes {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "likes_seq")
  @SequenceGenerator(name = "likes_seq", sequenceName = "likes_seq", allocationSize = 1)
  private Long likeId;

  @NonNull
  @Column(name = "liked")
  @Nationalized
  private boolean like;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonIgnore
  private Interactions interaction;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "userId")
  private AppUser user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Likes)) {
      return false;
    }

    Likes likes = (Likes) o;

    if (like != likes.like) {
      return false;
    }
    if (!likes.equals(likes.likeId)) {
      return false;
    }
    if (!interaction.equals(likes.interaction)) {
      return false;
    }
    return user.equals(likes.user);
  }

  @Override
  public int hashCode() {
    int result = likeId.hashCode();
    result = 31 * result + (like ? 1 : 0);
    result = 31 * result + interaction.hashCode();
    result = 31 * result + user.hashCode();
    return result;
  }
}
