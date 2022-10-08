package com.devchats.model;

import static javax.persistence.GenerationType.SEQUENCE;

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

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity(name = "Post")
public class Post {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "post_seq")
  @SequenceGenerator(name = "post_seq", sequenceName = "post_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @NonNull
  @Column(name = "content",
      length = 160
  )
  private String content;

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
    return id != null && Objects.equals(id, pojo.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}

