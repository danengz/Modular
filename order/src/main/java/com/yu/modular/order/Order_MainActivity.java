package com.yu.modular.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yu.common.PathManager;

public class Order_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_main);
    }

    public void jumpApp(View view) {
        Class clazz = PathManager.get("app", "MainActivity");
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void jumpShoppingCar(View view) {
        Class clazz = PathManager.get("shoppingcar", "ShoppingCar_MainActivity");
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
