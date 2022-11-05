package com.devchats.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "Interactions")
public class Interactions {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "interactions_seq")
  @SequenceGenerator(name = "interactions_seq", sequenceName = "interactions_seq", allocationSize = 1)
  @Column(name = "interactionId")
  private Long interactionId;

  @NonNull
  @OneToMany(mappedBy = "commentId")
  private List<Comments> comments = new ArrayList<>();

  @NonNull
  @OneToMany(mappedBy = "likeId")
  private Set<Likes> likes = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Interactions)) {
      return false;
    }

    Interactions interaction = (Interactions) o;

    if (!interaction.equals(interaction.interactionId)) {
      return false;
    }
    if (!comments.equals(interaction.comments)) {
      return false;
    }
    return likes.equals(interaction.likes);
  }

  @Override
  public int hashCode() {
    int result = interactionId.hashCode();
    result = 31 * result + comments.hashCode();
    result = 31 * result + likes.hashCode();
    return result;
  }
}
