package com.example.canteenxpress.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.canteenxpress.Adapters.DailyMealHorAdapter;
import com.example.canteenxpress.Models.DailyMealHorModel;
import com.example.canteenxpress.R;

import java.util.ArrayList;
import java.util.List;

public class DailyMealFragment extends AppCompatActivity {
    RecyclerView daily_meal_recycler;
    DailyMealHorAdapter dailyMealHorAdapter;
    List<DailyMealHorModel> dailyMealHorModelList;
    ImageView backIcon;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_daily_meal);

        backIcon=findViewById(R.id.backIcon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
    }
}