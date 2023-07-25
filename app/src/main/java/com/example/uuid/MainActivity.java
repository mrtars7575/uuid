package com.example.uuid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv);
        TextView tv2 = findViewById(R.id.textView2);

        String androidId = getUniqueId(getApplicationContext());

        tv.setText("android id : " + androidId );

        String uuid = getUniqueId();
        tv2.setText("uuid with sha256 :" + uuid);



    }
    public static String getUniqueId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        // Android ID'nin son 10 hanesini alın
        String lastTenDigits = androidId.substring(Math.max(0, androidId.length() - 10));

        // Android ID'nin son 10 hanesini yalnızca numerik karakterlere dönüştürün
        String numericId = toNumeric(lastTenDigits);

        // En az 8, en fazla 10 haneli benzersiz kimliği döndürün
        return numericId.substring(0, Math.min(numericId.length(), 10));
    }

    // Heksadesimal formattaki stringi yalnızca numerik forma dönüştürme metodu
    public static String toNumeric(String hexString) {
        StringBuilder numericBuilder = new StringBuilder();

        for (int i = 0; i < hexString.length(); i++) {
            char c = hexString.charAt(i);
            if (Character.isDigit(c)) {
                numericBuilder.append(c);
            }
        }

        return numericBuilder.toString();
    }


    // SHA 256 metotları

    public static String getUniqueId() {
        String deviceInfo = getDeviceInfo(); // Cihaz özelliklerini alın
        String uniqueId = sha256(deviceInfo); // SHA-256 kullanarak hash oluşturun
        return bytesToNumeric(uniqueId.getBytes()).substring(0, 10); // İlk 10 karakteri alarak 10 haneli numerik kimlik elde edin
    }

    // Cihazın özelliklerini birleştirme metodu
    private static String getDeviceInfo() {
        // Cihazın modeli, seri numarası, üretici bilgisi gibi özellikleri birleştirin
        return Build.MODEL + Build.SERIAL + Build.MANUFACTURER;
    }

    // SHA-256 ile hash oluşturma metodu
    private static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes());
            return bytesToNumeric(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Byte dizisini sadece numerik formata dönüştürme metodu
    private static String bytesToNumeric(byte[] bytes) {
        StringBuilder numericString = new StringBuilder();
        for (byte aByte : bytes) {
            int numericValue = 0xFF & aByte;
            numericString.append(numericValue);
        }
        return numericString.toString();
    }

}