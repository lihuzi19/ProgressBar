package com.example.downloadprogressapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CustomProgressBar customProgressBar;
    private DownloadProgressBar downloadProgressBar;

    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customProgressBar = findViewById(R.id.custom_progress_bar);
        downloadProgressBar = findViewById(R.id.download_progress_bar);
        auto();

    }

    private void auto() {
        new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(100);
                    runOnUiThread(()->{
                        customProgressBar.setProgress(progress);
                        customProgressBar.setText(progress + "%");
                        downloadProgressBar.setProgress(progress);
                        downloadProgressBar.setProgressState(progress + "%");
                    });
                    progress++;
                    if (progress > 100) {
                        progress = 0;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}