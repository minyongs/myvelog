package com.hellovelog.myvelog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TagCountDTO {
    private String name;
    private Long count;

    // getters and setters
}
