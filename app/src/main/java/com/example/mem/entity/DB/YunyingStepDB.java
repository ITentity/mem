package com.example.mem.entity.DB;

import org.litepal.crud.LitePalSupport;

public class YunyingStepDB extends LitePalSupport {
    private String stepName;
    private String imagePath;

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "YunyingStepDB{" +
                "stepName='" + stepName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
