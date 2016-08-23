package com.example.tushar.expensemanager.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tushar on 19/8/16.
 */
@DatabaseTable
public class Category {

    @DatabaseField(generatedId = true)
    private int category_id;

    @DatabaseField
    private String category_name;

    public String getCategoryName() {
        return category_name;
    }

    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }

    public int getCategoryId() {
        return category_id;
    }
}
