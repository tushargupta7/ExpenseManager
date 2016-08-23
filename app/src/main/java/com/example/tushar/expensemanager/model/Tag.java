package com.example.tushar.expensemanager.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tushar on 19/8/16.
 */
@DatabaseTable
public class Tag {
@DatabaseField(generatedId=true)
    private int tag_id;

    @DatabaseField
    private String tag_name;

    public int getTag_id() {
        return tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTagName(String tag_name) {
        this.tag_name = tag_name;
    }
}
