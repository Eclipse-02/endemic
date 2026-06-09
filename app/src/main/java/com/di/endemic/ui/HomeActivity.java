package com.di.endemic.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.di.endemic.R;
import com.di.endemic.adapter.EndemicAdapter;
import com.di.endemic.database.AppDatabase;
import com.di.endemic.model.Endemic;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private EndemicAdapter adapter;
    private AppDatabase db;
    private String currentFilter = "Hewan";
    private String currentRegion = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.top_bar_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_search) {
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            } else if (itemId == R.id.action_favorites) {
                startActivity(new Intent(this, FavoriteActivity.class));
                return true;
            }
            return false;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EndemicAdapter();
        recyclerView.setAdapter(adapter);

        db = AppDatabase.getInstance(this);

        adapter.setOnItemClickListener(endemic -> {
            Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
            intent.putExtra("ENDEMIC_ID", endemic.getId());
            startActivity(intent);
        });

        ChipGroup chipGroupRegions = findViewById(R.id.chipGroupRegions);
        chipGroupRegions.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty() || checkedIds.get(0) == R.id.chipAll) {
                currentRegion = "All";
            } else {
                Chip chip = findViewById(checkedIds.get(0));
                currentRegion = chip.getText().toString();
            }
            loadData();
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_animals) {
                currentFilter = "Hewan";
                loadData();
                return true;
            } else if (itemId == R.id.nav_plants) {
                currentFilter = "Tumbuhan";
                loadData();
                return true;
            }
            return false;
        });

        loadData();
    }

    private void loadData() {
        new Thread(() -> {
            List<Endemic> endemics;
            if (currentRegion.equals("All")) {
                endemics = db.endemicDao().getByType(currentFilter);
            } else {
                endemics = db.endemicDao().getByTypeAndRegion(currentFilter, currentRegion);
            }
            runOnUiThread(() -> adapter.setEndemics(endemics));
        }).start();
    }
}
