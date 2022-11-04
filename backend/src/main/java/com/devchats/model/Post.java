package com.devchats.model;

import static javax.persistence.GenerationType.SEQUENCE;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Entity(name = "Post")
public class Post {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "post_generator")
  @SequenceGenerator(name = "post_generator", sequenceName = "post_sequence", allocationSize = 1)
  private Long postId;

  @NonNull
  @Column(name = "content",
      length = 160
  )
  @JsonProperty(value = "content")
  private String content;

  @NonNull
  @Column(nullable = false)
  @Nationalized
  @JsonProperty(value = "username")
  private String username;

  @ManyToMany(cascade = CascadeType.ALL)
  private Map<Long, Interactions> interactions;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Post pojo = (Post) o;
    return postId != null && Objects.equals(postId, pojo.postId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(postId);
  }
}

