package com.ryanair.syncexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    AppController app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (AppController) getApplication();

        findViewById(R.id.btn_continuous).setOnClickListener(onContinuousClick);
    }

    View.OnClickListener onContinuousClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ReplicationManager manager = new ReplicationManager();
            manager.startContinuous(app.getDatabase());

            Toast.makeText(getBaseContext(), "Continuous replication started", Toast.LENGTH_SHORT).show();
        }
    };
}
