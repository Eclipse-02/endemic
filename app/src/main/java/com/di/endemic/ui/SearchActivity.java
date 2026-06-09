package com.di.endemic.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.di.endemic.R;
import com.di.endemic.adapter.EndemicAdapter;
import com.di.endemic.database.AppDatabase;
import com.di.endemic.model.Endemic;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private EndemicAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_24px);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EndemicAdapter();
        recyclerView.setAdapter(adapter);

        db = AppDatabase.getInstance(this);

        adapter.setOnItemClickListener(endemic -> {
            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
            intent.putExtra("ENDEMIC_ID", endemic.getId());
            startActivity(intent);
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
    }

    private void search(String query) {
        new Thread(() -> {
            List<Endemic> results = db.endemicDao().search(query);
            runOnUiThread(() -> adapter.setEndemics(results));
        }).start();
    }
}
