package com.example.tushar.expensemanager.db;

import android.content.Context;
import android.util.Log;

import com.example.tushar.expensemanager.model.Category;
import com.example.tushar.expensemanager.model.Tag;
import com.example.tushar.expensemanager.model.Transaction;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tushar on 16/8/16.
 */
public class DatabaseManager {
    static private DatabaseManager instance;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseHelper helper;
    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    private DatabaseHelper getHelper() {
        return helper;
    }

    public Transaction newTransaction() {
        Transaction transaction = new Transaction();
        try {
            getHelper().getTransactionDao().create(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    public void updateTransaction(Transaction item) {
        try {
            getHelper().getTransactionDao().update(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getDatedTransaction(long sTimestamp ,long fTimestamp){
        List<Transaction> list=null;
        Log.v("start timestamp",String.valueOf(sTimestamp));
        Log.v("final timestamp",String.valueOf(fTimestamp));
        QueryBuilder<Transaction, Integer> qb=getHelper().getTransactionDao().queryBuilder() ;
        try {
            qb.where().gt("timestamp",sTimestamp).and().lt("timestamp",fTimestamp);
            list=qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteTransaction(int id)  {
        try {
            getHelper().getTransactionDao().deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Transaction getTransactionById(int id) {
        try {
            return getHelper().getTransactionDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCategory(Category category){
        try {
            getHelper().getCategoryDao().createIfNotExists(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTags(Tag tag){
        try {
            getHelper().getTagDao().createIfNotExists(tag);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category getCategoryById(int id){
        try {
            return getHelper().getCategoryDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Tag getTagById(int id){
        try {
            return getHelper().getTagDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllCategories(){
        try {
            List<String> categoryNames=new ArrayList<String>();
            GenericRawResults<String[]> result=getHelper().getCategoryDao().queryRaw("SELECT category_name FROM Category");
            for (String[] cat:result) {
                String category=cat[0];
                categoryNames.add(category);
            }
            return categoryNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllTags() {
        try {
            List<String> tagNames=new ArrayList<String>();
            GenericRawResults<String[]> result=getHelper().getTagDao().queryRaw("SELECT tag_name FROM Tag");
            for (String[] cat:result) {
                String tag=cat[0];
                tagNames.add(tag);
            }
            return tagNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
