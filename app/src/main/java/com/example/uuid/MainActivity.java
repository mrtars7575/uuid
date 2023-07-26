package com.example.uuid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv);


        String androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);


       /* String androidId = "cd8d6753069e9ef2";*/
        ArrayList<Integer> intList = new ArrayList<>();
        ArrayList<Integer> intList2 = new ArrayList<>();

        StringBuilder resultBuilder = new StringBuilder();
        StringBuilder uniqueID = new StringBuilder();

        for (int i = 0; i < 16; i += 1) {
            String hexPair = androidId.substring(i, i + 1);
            System.out.println("hexPair :" + hexPair );
            int decimalValue = Integer.parseInt(hexPair, 16);
            System.out.println("decimalValue : " + decimalValue);
            intList.add(decimalValue);
            resultBuilder.append(decimalValue);

        }
        int sum;
        for(int n = 0 ; n < intList.size() - 1  ; n += 2 ){

            sum = intList.get(n) + intList.get(n+1);
            System.out.println("sum : " + sum);
            if(sum>=10){
                intList2.add((sum % 10));
            }else{
                intList2.add(sum);
            }
        }

        for (int a : intList2){
            System.out.println("intList2 : " + a);
        }

        for (int i = 0; i < intList2.size(); i++) {
            uniqueID.append(intList2.get(i));
        }


        tv.setText(uniqueID);

    }


}