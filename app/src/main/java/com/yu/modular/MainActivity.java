package com.yu.modular;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yu.common.PathManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void jumpOrder(View view) {
        Class clazz = PathManager.get("order", "Order_MainActivity");
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void jumpShoppingCar(View view) {
        try {
            Class clazz = Class.forName("com.yu.modular.shoppingcar.ShoppdingCar_MainActivity");
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
