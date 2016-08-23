package com.example.tushar.expensemanager.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tushar.expensemanager.model.Category;
import com.example.tushar.expensemanager.model.Tag;
import com.example.tushar.expensemanager.model.Transaction;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tushar on 16/8/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    private static final String DATABASE_NAME = "ExpenseManagerDB.sqlite";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<Transaction, Integer> transactionsDao = null;
    private Dao<Category,Integer> categoryDao=null;
    private Dao<Tag,Integer> tagDao =null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Transaction.class);
            TableUtils.createTable(connectionSource, Category.class);
            TableUtils.createTable(connectionSource, Tag.class);
            createCategoryData();
            createTagData();
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    private void createCategoryData() {
        Category cat1=new Category();
        Category cat2=new Category();
        Category cat3=new Category();
        cat1.setCategoryName("Utilities");
        cat2.setCategoryName("Food/Drinks");
        cat3.setCategoryName("Life");
        try {
            getCategoryDao().create(cat1);
            getCategoryDao().create(cat2);
            getCategoryDao().create(cat3);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    public void createTagData(){
    Tag cat1=new Tag();
    Tag cat2=new Tag();
    Tag cat3=new Tag();
    cat1.setTagName("Utilities");
    cat2.setTagName("Food/Drinks");
    cat3.setTagName("Life");
    try {
        getTagDao().create(cat1);
        getTagDao().create(cat2);
        getTagDao().create(cat3);
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }

}

    @Override
    public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            List<String> allSql = new ArrayList<String>();
            switch(oldVersion)
            {
                case 1:
                    //allSql.add("alter table AdData add column `new_col` VARCHAR");
                    //allSql.add("alter table AdData add column `new_col2` VARCHAR");
            }
            for (String sql : allSql) {
                db.execSQL(sql);
            }
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }

    }

    public Dao<Transaction, Integer> getTransactionDao() {
        if (null == transactionsDao) {
            try {
                transactionsDao= getDao(Transaction.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return transactionsDao;
    }

    public Dao<Tag, Integer> getTagDao() {
        if (null == tagDao) {
            try {
                tagDao= getDao(Tag.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return tagDao;
    }


    public Dao<Category, Integer> getCategoryDao() {
        if (null == categoryDao) {
            try {
                categoryDao= getDao(Category.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return categoryDao;
    }
}
