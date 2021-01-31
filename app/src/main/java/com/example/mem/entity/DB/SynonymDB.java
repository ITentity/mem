package com.example.mem.entity.DB;

import org.litepal.crud.LitePalSupport;

public class SynonymDB extends LitePalSupport {
    private String synonymName;

    public String getSynonymName() {
        return synonymName;
    }

    public void setSynonymName(String synonymName) {
        this.synonymName = synonymName;
    }
}
