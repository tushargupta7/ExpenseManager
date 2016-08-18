package com.example.tushar.expensemanager.model;

/**
 * Created by tushar on 16/8/16.
 */

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Transaction {
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
    private String repeat_mode;

    @DatabaseField
    private String category;

    @DatabaseField
    private String tag;

    @DatabaseField
    private String t_type;

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

    public String getRepeat_mode() {
        return repeat_mode;
    }

    public void setRepeat_mode(String repeat_mode) {
        this.repeat_mode = repeat_mode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getT_type() {
        return t_type;
    }

    public void setT_type(String t_type) {
        this.t_type = t_type;
    }
}
