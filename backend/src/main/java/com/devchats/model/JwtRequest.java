package com.devchats.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class JwtRequest {


    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
}

