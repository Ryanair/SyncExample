package com.ryanair.syncexample;

import android.util.Log;

import com.couchbase.lite.Database;
import com.couchbase.lite.replicator.Replication;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class ReplicationManager {
    private static final String TAG = "ReplicationManager";

    public void startContinuous(Database database) {
        URL url;

        try {
            url = new URL(Constants.syncUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
            return;
        }

        Replication replication = database.createPullReplication(url);
        replication.setChannels(Arrays.asList(Constants.channels));
        replication.setContinuous(true);
        replication.start();
    }

}
