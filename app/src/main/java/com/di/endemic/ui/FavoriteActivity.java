package com.di.endemic.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.di.endemic.R;
import com.di.endemic.adapter.FavoriteAdapter;
import com.di.endemic.database.AppDatabase;
import com.di.endemic.model.Favorite;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private FavoriteAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_24px);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoriteAdapter();
        recyclerView.setAdapter(adapter);

        db = AppDatabase.getInstance(this);

        adapter.setOnItemClickListener(favorite -> {
            Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
            intent.putExtra("ENDEMIC_ID", favorite.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        new Thread(() -> {
            List<Favorite> favorites = db.favoriteDao().getAll();
            runOnUiThread(() -> adapter.setFavorites(favorites));
        }).start();
    }
}
