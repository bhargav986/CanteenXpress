package com.example.canteenxpress.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.canteenxpress.Adapters.FavouriteHorAdapter;
import com.example.canteenxpress.Adapters.HomeHorAdapter;
import com.example.canteenxpress.Models.FavouriteHorModel;
import com.example.canteenxpress.Models.HomeHorModel;
import com.example.canteenxpress.R;
import com.example.canteenxpress.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends AppCompatActivity {
    RecyclerView favourite_recycler;
    FavouriteHorAdapter favouriteHorAdapter;
    HomeHorAdapter homeHorAdapter;
    List<FavouriteHorModel> favouriteHorModelList;
    List<HomeHorModel> itemsList = new ArrayList<>();
    public DatabaseHelper dbHelper;
    public SQLiteDatabase db;
    ImageView backIcon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favourite);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        backIcon=findViewById(R.id.backIcon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favouriteHorModelList = new ArrayList<>();

        favouriteHorModelList = dbHelper.getAllfavourite();

        Log.e("TAG", "onCreateView: " + favouriteHorModelList.size());

        // Initialize RecyclerView and adapter
        favouriteHorAdapter = new FavouriteHorAdapter(this, favouriteHorModelList);
        favourite_recycler = findViewById(R.id.favourite_recycler);
        favourite_recycler.setAdapter(favouriteHorAdapter);
        favourite_recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}