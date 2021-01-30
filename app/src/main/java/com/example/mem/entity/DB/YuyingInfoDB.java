package com.example.mem.entity.DB;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class YuyingInfoDB extends LitePalSupport {
    private int id;
    private String name;
    private List<YunyingStepDB> steps = new ArrayList<YunyingStepDB>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<YunyingStepDB> getSteps() {
        return steps;
    }

    public void setSteps(List<YunyingStepDB> steps) {
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "YuyingInfoDB{" +
                "name='" + name + '\'' +
                ", steps=" + steps +
                '}';
    }
}
