package org.example.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lesson {
    private UUID id;
    private String forVedio;
    private String name;
    private Integer number;
    private String coursName;
    private String lesson_question;
    private Boolean lesson_status;

}
