package com.example.tushar.expensemanager.model;

/**
 * Created by tushar on 16/8/16.
 */

import android.os.Parcelable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Transaction implements Serializable {
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    private long timestamp;

    @DatabaseField
    private String title;

    @DatabaseField
    private String amount;

    @DatabaseField
    private String notes;

    @DatabaseField
    private int repeat_mode;

    @DatabaseField
    private String categoryId;

    @DatabaseField
    private String tagId;

    @DatabaseField
    private String t_type;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getRepeat_mode() {
        return repeat_mode;
    }

    public void setRepeat_mode(int repeat_mode) {
        this.repeat_mode = repeat_mode;
    }

    public String getCategory() {
        return categoryId;
    }

    public void setCategory(String category) {
        this.categoryId = category;
    }

    public String getTag() {
        return tagId;
    }

    public void setTag(String tag) {
        this.tagId = tag;
    }

    public String getT_type() {
        return t_type;
    }

    public void setT_type(String t_type) {
        this.t_type = t_type;
    }
}
