package com.yu.modular.shoppingcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yu.common.order.OrderDrawable;
import com.yu.router_annotation.Router;
import com.yu.router_api.RouterManager;

@Router(group = "shoppingCar", path = "ShoppingCar_MainActivity")
public class ShoppingCar_MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcar_activity_main);

        imageView = findViewById(R.id.imageView);

        OrderDrawable orderDrawable = (OrderDrawable) RouterManager.getInstance().getResource("order", "OrderDrawable");
        imageView.setImageResource(orderDrawable.getDrawable());
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
