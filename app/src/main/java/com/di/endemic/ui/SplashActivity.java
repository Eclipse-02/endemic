package com.di.endemic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.di.endemic.R;
import com.di.endemic.database.AppDatabase;
import com.di.endemic.model.Endemic;
import com.di.endemic.network.ApiClient;
import com.di.endemic.network.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        fetchDataAndStart();
    }

    private void fetchDataAndStart() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getEndemics().enqueue(new Callback<List<Endemic>>() {
            @Override
            public void onResponse(Call<List<Endemic>> call, Response<List<Endemic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveToDatabase(response.body());
                } else {
                    Toast.makeText(SplashActivity.this, getString(R.string.toast_fetch_failed), Toast.LENGTH_SHORT).show();
                    startHome();
                }
            }

            @Override
            public void onFailure(Call<List<Endemic>> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                startHome();
            }
        });
    }

    private void saveToDatabase(List<Endemic> endemics) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(SplashActivity.this);
            db.endemicDao().deleteAll();
            db.endemicDao().insertAll(endemics);
            runOnUiThread(this::startHome);
        }).start();
    }

    private void startHome() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }
}
