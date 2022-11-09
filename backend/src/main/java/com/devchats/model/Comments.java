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
@Entity(name ="Comments")
public class Comments {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "comments_seq")
  @SequenceGenerator(name = "comments_seq", sequenceName = "comments_seq", allocationSize = 1)
  private Long commentId;

  @NonNull
  @Column(name = "comment")
  @Nationalized
  private String comment;


  //id of the user that added the comment

}



