package com.example.canteenxpress;

import static com.example.canteenxpress.Myapp.dbHelper;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canteenxpress.Adapters.DailyMealHorAdapter;
import com.example.canteenxpress.Adapters.HomeHorAdapter;
import com.example.canteenxpress.Adapters.ImageSliderAdapter;
import com.example.canteenxpress.Fragment.CartFragment;
import com.example.canteenxpress.Fragment.DailyMealFragment;
import com.example.canteenxpress.Fragment.FavouriteFragment;
import com.example.canteenxpress.Models.DailyMealHorModel;
import com.example.canteenxpress.Models.HomeHorModel;
import com.example.canteenxpress.database.DatabaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    ImageView menuImage, cartIcon;
    NavigationView navigationView;
    MaterialButton signOutBtn;
    TextView nav_name;
    TextView yourname, youremail;
    CircleImageView circleImageView;

    public static HomeActivity homeActivity;
    private AlertDialog dialog;
    RecyclerView home_recycler;
    RecyclerView daily_meal_recycler;
    List<HomeHorModel> homeHorModelList;
    List<DailyMealHorModel> dailyMealHorModelList;
    HomeHorAdapter homeHorAdapter;
    DailyMealHorAdapter dailyMealHorAdapter;
    private ViewPager viewPager;
    private ImageSliderAdapter imageSliderAdapter;
    private int[] images = {R.drawable.rich_flavours,
            R.drawable.asian_cooking, R.drawable.homemade_flavour,
            R.drawable.south_indian, R.drawable.gulab_jamun,
            R.drawable.tiffin_service, R.drawable.bhelpuri};
    private int currentPage = 0;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeActivity = this;

        dbHelper = new DatabaseHelper(homeActivity);
        Myapp.db = dbHelper.getWritableDatabase();

        drawerLayout = findViewById(R.id.drawerLayout);

        menuImage = findViewById(R.id.menuImage);
        cartIcon = findViewById(R.id.cartIcon);
        navigationView = findViewById(R.id.navigationView);
        signOutBtn = findViewById(R.id.signOutBtn);
        nav_name = findViewById(R.id.nav_name);

        circleImageView = navigationView.getHeaderView(0).findViewById(R.id.circleImageView);
        yourname = navigationView.getHeaderView(0).findViewById(R.id.yourname);
        youremail = navigationView.getHeaderView(0).findViewById(R.id.youremail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(HomeActivity.this, CartFragment.class);
                startActivity(intent4);
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String firstname = sharedPreferences.getString("FIRSTNAME", "");
        String lastname = sharedPreferences.getString("LASTNAME", "");
        String email = sharedPreferences.getString("EMAIL", "");

        yourname.setText(firstname + " " + lastname);
        youremail.setText(email);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent;
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.home:
                        intent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.dailymeal:
                        intent = new Intent(HomeActivity.this, DailyMealFragment.class);
                        startActivity(intent);
                        break;
                    case R.id.favourite:
                        intent = new Intent(HomeActivity.this, FavouriteFragment.class);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                        intent = new Intent(HomeActivity.this, CartFragment.class);
                        startActivity(intent);
                        break;
                    default:
                }

                return true;
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
            }
        });

        home_recycler = findViewById(R.id.home_recycler);
        daily_meal_recycler = findViewById(R.id.daily_meal_recycler);
        homeHorModelList = new ArrayList<>();
        homeHorModelList.add(new HomeHorModel(R.drawable.samosa, 10.00, "Samosa"));
        homeHorModelList.add(new HomeHorModel(R.drawable.kachori, 15.00, "Kachori"));
        homeHorModelList.add(new HomeHorModel(R.drawable.puff, 12.00, "Puff"));
        homeHorModelList.add(new HomeHorModel(R.drawable.burger, 40.00, "Burger"));
        homeHorModelList.add(new HomeHorModel(R.drawable.sandwich, 50.00, "Sandwich"));

        homeHorAdapter = new HomeHorAdapter(this, homeHorModelList);

        home_recycler.setAdapter(homeHorAdapter);
        home_recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        home_recycler.setHasFixedSize(true);
        home_recycler.setNestedScrollingEnabled(false);

        dailyMealHorModelList = new ArrayList<>();

        dailyMealHorModelList.add(new DailyMealHorModel(R.drawable.sabji, "Sabji",25));
        dailyMealHorModelList.add(new DailyMealHorModel(R.drawable.roti, "Roti",6));
        dailyMealHorModelList.add(new DailyMealHorModel(R.drawable.dal_makhni, "Dal Makhani",40));
        dailyMealHorModelList.add(new DailyMealHorModel(R.drawable.buttermilk, "Buttermilk",10));
        dailyMealHorModelList.add(new DailyMealHorModel(R.drawable.papad, "Papad",7));

        dailyMealHorAdapter = new DailyMealHorAdapter(this,dailyMealHorModelList);
        daily_meal_recycler = findViewById(R.id.daily_meal_recycler);
        daily_meal_recycler.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        daily_meal_recycler.setLayoutManager(staggeredGridLayoutManager);
        daily_meal_recycler.setAdapter(dailyMealHorAdapter);

        // ViewPager in activity_main.xml
        viewPager = findViewById(R.id.viewPager);
        imageSliderAdapter = new ImageSliderAdapter(this, images);
        viewPager.setAdapter(imageSliderAdapter);

        final Handler handler = new Handler(Looper.myLooper());
        final Runnable update = () -> {
            if (currentPage == images.length) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, true);
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 2000, 2000);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void showExitDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.exit_dialog, null);

        MaterialButton positive = dialogView.findViewById(R.id.positiveBtn);
        MaterialButton negative = dialogView.findViewById(R.id.negativeBtn);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}