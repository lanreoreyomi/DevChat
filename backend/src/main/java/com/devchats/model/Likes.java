package com.devchats.model;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Entity(name ="Likes")
public class Likes {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "likes_seq")
  @SequenceGenerator(name = "likes_seq", sequenceName = "likes_seq", allocationSize = 1)
  private Long likeId;

  @NonNull
  @Column(name = "liked")
  @Nationalized
  private boolean like;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Likes)) {
      return false;
    }

    Likes likes = (Likes) o;

    return likeId.equals(likes.likeId);
  }

  @Override
  public int hashCode() {
    return likeId.hashCode();
  }
}
