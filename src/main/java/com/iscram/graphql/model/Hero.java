package com.iscram.graphql.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Hero implements Character {
    private String name;
    private String surname;
    private Double height;
    private Integer mass;
    private Boolean darkSide;
    private LightSaber lightSaber;
    private List<Integer> episodeIds = new ArrayList<>();
}
