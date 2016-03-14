package com.wordpress.jonyonandroidcraftsmanship.interprocessdataexchangeapp2;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText etMessage = null;
    private TextView tvStatus = null;
    private String packageName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        packageName = "com.wordpress.jonyonandroidcraftsmanship.interprocessdataexchangeapp1";
        etMessage = (EditText) findViewById(R.id.etMessage);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
    }

    public void loadFile(View view) {
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
//            tvStatus.setText(applicationInfo.dataDir+"/files/jony.txt");
            String fullPath = applicationInfo.dataDir + "/files/jony.txt";
            readFile(fullPath);
        } catch (PackageManager.NameNotFoundException e) {
            tvStatus.setTextColor(Color.RED);
            tvStatus.setText(e.toString());
        }
    }

    private void readFile(String fullPath) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(fullPath));
            int read = -1;
            StringBuffer stringBuffer = new StringBuffer();
            while ((read = fileInputStream.read()) != -1) {
                stringBuffer.append((char) read);
            }
            etMessage.setText(stringBuffer);
            tvStatus.setTextColor(Color.GREEN);
            tvStatus.setText(stringBuffer + "\n was successfully read from \n" + fullPath);
        } catch (FileNotFoundException e) {
            tvStatus.setTextColor(Color.RED);
            tvStatus.setText(e.toString());
        } catch (IOException e) {
            tvStatus.setTextColor(Color.RED);
            tvStatus.setText(e.toString());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    tvStatus.setTextColor(Color.RED);
                    tvStatus.setText(e.toString());
                }
            }
        }

    }
}
