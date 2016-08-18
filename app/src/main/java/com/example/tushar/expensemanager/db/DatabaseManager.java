package com.example.tushar.expensemanager.db;

import android.content.Context;
import android.util.Log;

import com.example.tushar.expensemanager.model.Transaction;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
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

}
