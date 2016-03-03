package com.ryanair.syncexample;

import android.app.Application;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

public class AppController extends Application {
    private static final String TAG = "AppController";

    private static Database database;

    @Override
    public void onCreate() {
        super.onCreate();

        openDatabase();
    }

    private void openDatabase() {
        Manager manager;

        if (!Manager.isValidDatabaseName(Constants.DATABASE_NAME)) {
            Log.e(TAG, "Bad database name");
            return;
        }

        try {
            manager = new Manager(new AndroidContext(getBaseContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.e(TAG, "Cannot create manager object");
            return;
        }

        try {
            database = manager.getDatabase(Constants.DATABASE_NAME);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Database getDatabase() {
        if(database == null) {
            openDatabase();
        }

        return database;
    }

}
