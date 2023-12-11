package org.example.bot.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Users {
    private String chatid;
    private String name;
    private String username;
    private String phonenomer;
    private UserStatus userstatus;
}
