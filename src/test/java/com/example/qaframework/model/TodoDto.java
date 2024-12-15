package com.example.qaframework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {
    private Integer id;
    private String text;
    private Boolean completed;

    public Boolean isCompleted() {
        return completed;
    }
}
