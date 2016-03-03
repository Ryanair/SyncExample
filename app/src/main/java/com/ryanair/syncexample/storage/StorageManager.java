package com.ryanair.syncexample.storage;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class StorageManager {
    private static final String TAG = "StorageManager";
    private static final String DATABASE_NAME = "reference_data";
    private static final String DB_FILE_EXT = ".cblite";
    private static final String syncUrl = "http://192.168.56.1:4984/reference_data";
    private static final String AIRPORTS_VIEW = "getAirports";
    private static final String[] channels = new String[]{"ref_data_v1"};
    private static final String MARKETS_VIEW = "getMarkets";

    private Context mContext;
    private Manager mManager;
    private Database mDatabase;
    private AirportsListener mAirportsListener;

    public StorageManager(Context context) {
        mContext = context;
        mAirportsListener = airportsListener;

        openDb();
    }

    private void openDb() {
        if (!Manager.isValidDatabaseName(DATABASE_NAME)) {
            Log.e(TAG, "Bad database name");
            return;
        }

        try {
            mManager = new Manager(new AndroidContext(mContext), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.e(TAG, "Cannot create manager object");
            return;
        }

        try {
            mDatabase = mManager.getExistingDatabase(DATABASE_NAME);

            // the database does not exist
            // copy it from the assets folder
            if (mDatabase == null) {
                InputStream assetDb = mContext.getAssets().open(DATABASE_NAME + DB_FILE_EXT);
                mManager.replaceDatabase(DATABASE_NAME, assetDb, null);

                // open the database after replacing
                mDatabase = mManager.getDatabase(DATABASE_NAME);
            }

        } catch (CouchbaseLiteException | IOException e) {
            Log.e(TAG, e.getMessage());
        }

        startSync();
    }
}
