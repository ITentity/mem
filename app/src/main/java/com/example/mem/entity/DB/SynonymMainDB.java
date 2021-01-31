package com.example.mem.entity.DB;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class SynonymMainDB extends LitePalSupport {
    private int id;
    private String mianName;
    private List<SynonymDB> synonymDBList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMianName() {
        return mianName;
    }

    public void setMianName(String mianName) {
        this.mianName = mianName;
    }

    public List<SynonymDB> getSynonymDBList() {
        return synonymDBList;
    }

    public void setSynonymDBList(List<SynonymDB> synonymDBList) {
        this.synonymDBList = synonymDBList;
    }
}
