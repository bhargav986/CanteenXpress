package com.example.canteenxpress;

import static com.example.canteenxpress.Myapp.db;
import static com.example.canteenxpress.Myapp.dbHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenxpress.Models.CartHorModel;
import com.google.android.material.button.MaterialButton;
import com.razorpay.PaymentResultListener;

public class AddToCartActivity extends AppCompatActivity implements PaymentResultListener {
    MaterialButton cartBtn, paymentBtn;
    TextView name, price;
    ImageView image, backBtn;
    CartHorModel cartHorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        cartBtn = findViewById(R.id.cartBtn);
        paymentBtn = findViewById(R.id.paymentBtn);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        if (intent != null) {
            String itemName = intent.getStringExtra("itemName");
            int itemImage = intent.getIntExtra("itemImage", 0);
            double itemPrice = intent.getDoubleExtra("itemPrice", 0);

            name.setText(itemName);
            price.setText(String.format("Price: %.2f Rs.", itemPrice));
            image.setImageResource(itemImage);

            cartHorModel = new CartHorModel();
            cartHorModel.setName(itemName);
            cartHorModel.setImage(itemImage);
            cartHorModel.setPrice(itemPrice);
        }

        updateCartButtonText();

        cartBtn.setOnClickListener(v -> {
            if (cartHorModel != null && cartHorModel.getName() != null) {
                if (dbHelper.isUserExistsCart(cartHorModel.getName())) {
                    removeFromCart();
                } else {
                    addToCart();
                }
                updateCartButtonText();
            } else {
                Toast.makeText(AddToCartActivity.this, "Item data is not complete.", Toast.LENGTH_SHORT).show();
            }
        });

        paymentBtn.setOnClickListener(v -> {
            if (cartHorModel != null) {
                PaymentUtility.PaymentNow(AddToCartActivity.this, cartHorModel.getPrice());
            }
        });
    }

    private void addToCart() {
        if (cartHorModel != null && cartHorModel.getName() != null) {
            dbHelper.insertcartdata(db, cartHorModel);
            Toast.makeText(AddToCartActivity.this, "Added to Cart.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add to cart. Item name is null.", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeFromCart() {
        if (cartHorModel != null && cartHorModel.getName() != null) {
            int id = dbHelper.getUserIdByNameCart(cartHorModel.getName());
            dbHelper.deleteUserCart(db, id);
            Toast.makeText(AddToCartActivity.this, "Removed from Cart.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to remove from cart. Item name is null.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCartButtonText() {
        if (cartHorModel != null && cartHorModel.getName() != null) {
            if (dbHelper.isUserExistsCart(cartHorModel.getName())) {
                cartBtn.setText("Remove from Cart");
            } else {
                cartBtn.setText("Add to Cart");
            }
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("AddToCartActivity", "Payment Success: Transaction ID: " + s);
        Toast.makeText(this, "Order Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("AddToCartActivity", "Payment Error: Code: " + i + ", Message: " + s);
        Toast.makeText(this, "Order Failed", Toast.LENGTH_SHORT).show();
    }
}
