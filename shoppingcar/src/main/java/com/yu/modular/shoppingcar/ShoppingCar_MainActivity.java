package com.yu.modular.shoppingcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yu.router_annotation.Router;
import com.yu.router_api.RouterManager;

@Router(group = "shoppingCar", path = "ShoppingCar_MainActivity")
public class ShoppingCar_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcar_activity_main);
    }

    public void jumpApp(View view) {
        Class clazz = RouterManager.getInstance().get("app", "MainActivity");
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void jumpOrder(View view) {
        Class clazz = RouterManager.getInstance().get("order", "Order_MainActivity");
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
