package com.example.canteenxpress.Fragment;

import static com.example.canteenxpress.Myapp.dbHelper;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.canteenxpress.Adapters.CartHorAdapter;
import com.example.canteenxpress.Models.CartHorModel;
import com.example.canteenxpress.Myapp;
import com.example.canteenxpress.R;
import com.google.android.material.button.MaterialButton;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;
import java.util.List;

public class CartFragment extends AppCompatActivity implements PaymentResultListener {
    RecyclerView cart_recycler;
    MaterialButton paymentBtn;
    CartHorAdapter cartHorAdapter;
    List<CartHorModel> cartHorModelList;
    ImageView backIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cart);

        paymentBtn = findViewById(R.id.paymentBtn);
        backIcon = findViewById(R.id.backIcon);

        backIcon.setOnClickListener(v -> finish());

        Checkout.preload(this);

        cartHorModelList = Myapp.dbHelper.getAllCart();

        cartHorAdapter = new CartHorAdapter(this, cartHorModelList);
        cart_recycler = findViewById(R.id.cart_recycler);
        cart_recycler.setAdapter(cartHorAdapter);
        cart_recycler.setLayoutManager(new LinearLayoutManager(this));

        paymentBtn.setOnClickListener(v -> {
            Log.d("CartFragment", "Payment button clicked");
            PaymentNow(37);
            cartHorModelList.clear();
        });
    }

    public void PaymentNow(int amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_zSzkhCxWihlrIT");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "RezorPay");
            options.put("description", "Test Payment");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(amount * 100)); // Amount in paise

            JSONObject prefill = new JSONObject();
            prefill.put("email", "bhargav123@gmail.com");
            prefill.put("contact", "8530656200");
            options.put("prefill", prefill);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);

            options.put("retry", retryObj);

            Log.d("CartFragment", "Opening Razorpay Checkout");
            checkout.open(this, options);
        } catch (Exception e) {
            Log.e("CartFragment", "Error in Checkout: " + e.getMessage(), e);
            Toast.makeText(this, "Error in Checkout", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("CartFragment", "Payment Success: Transaction ID: " + s);
        Toast.makeText(this, "Order Successful", Toast.LENGTH_SHORT).show();

        List<CartHorModel> cartItems = Myapp.dbHelper.getAllCart();
        for (CartHorModel item : cartItems) {
            Myapp.dbHelper.deleteUserCart(Myapp.db, item.getId());
        }

        cartHorModelList.clear();
        cartHorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("CartFragment", "Payment Error: Code: " + i + ", Message: " + s);
        Toast.makeText(this, "Order Failed", Toast.LENGTH_SHORT).show();
    }
}
