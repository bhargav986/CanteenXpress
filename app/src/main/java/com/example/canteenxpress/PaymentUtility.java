package com.example.canteenxpress;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


public class PaymentUtility implements PaymentResultListener {
    public static void PaymentNow(Activity activity, Double amount) {
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

            Log.d("PaymentUtility", "Opening Razorpay Checkout");
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("PaymentUtility", "Error in Checkout: " + e.getMessage(), e);
            Toast.makeText(activity, "Error in Checkout", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("CartFragment", "Payment Success: Transaction ID: " + s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("CartFragment", "Payment Error: Code: " + i + ", Message: " + s);
    }
}
