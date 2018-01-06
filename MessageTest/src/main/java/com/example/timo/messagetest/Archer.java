package com.example.timo.messagetest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Timo on 21.12.2017.
 */

public class Archer implements Serializable{

    private String name;
    private boolean rightHanded;

    public Archer(@JsonProperty("name")String _name,
                  @JsonProperty("rightHanded")boolean _rightHanded) {
        name = _name;
        rightHanded = _rightHanded;
    }

    public Archer(@JsonProperty("name")String _name) {
        name = _name;
        rightHanded = true;
    }

    public Archer() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        String res = "Archer: " + name + ", handedness: ";
        res += (rightHanded) ? "right" : "left";
        return res;
    }

}
