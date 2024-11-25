package com.example.fishco.model;

import lombok.Data;

@Data
public class Warning {
    private String status;
    private String name;
    private String description;
    private String solution;

    public Warning(String warning, String volumeTooLow, String s, String s1) {
    }
}
