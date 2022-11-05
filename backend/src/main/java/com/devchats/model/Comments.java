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
@Entity(name = "Comments")
public class Comments {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "comments_seq")
  @SequenceGenerator(name = "comments_seq", sequenceName = "comments_seq", allocationSize = 1)
  private Long commentId;

  @NonNull
  @Column(name = "content")
  @Nationalized
  private String content;


  //id of the user that added the comment
  @NonNull
  @ManyToOne
  @JoinColumn(name = "userId")
  private AppUser user;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonIgnore
  private Interactions interaction;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Comments)) {
      return false;
    }

    Comments comments = (Comments) o;

    if (!comments.equals(comments.commentId)) {
      return false;
    }
    if (!content.equals(comments.content)) {
      return false;
    }
    if (!user.equals(comments.user)) {
      return false;
    }
    return interaction.equals(comments.interaction);
  }

  @Override
  public int hashCode() {
    int result = commentId.hashCode();
    result = 31 * result + content.hashCode();
    result = 31 * result + user.hashCode();
    result = 31 * result + interaction.hashCode();
    return result;
  }
}



