package com.devchats.model;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
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
  @JsonProperty(value = "post")
  private String post;


  @OneToMany(fetch = EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "post_comments",
      joinColumns = @JoinColumn(
          name = "postId", referencedColumnName = "postId"),
      inverseJoinColumns = @JoinColumn(
          name = "commentId", referencedColumnName = "commentId"))
  @Exclude
  @JsonProperty
  private Collection<Comments> comments;



  @OneToMany
  @JoinTable(
      name = "post_likes",
      joinColumns = @JoinColumn(
          name = "postId", referencedColumnName = "postId"),
      inverseJoinColumns = @JoinColumn(
          name = "likeId", referencedColumnName = "likeId"))
  @Exclude
  @JsonProperty
  private Collection<Likes> likes;

  @NonNull
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
  @JsonIgnore
  private AppUser user;




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

