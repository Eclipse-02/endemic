package com.di.endemic.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.di.endemic.R;
import com.di.endemic.database.AppDatabase;
import com.di.endemic.model.Endemic;
import com.di.endemic.model.Favorite;

public class DetailActivity extends AppCompatActivity {
    private ImageView ivFoto;
    private TextView tvNama, tvNamaLatin, tvDeskripsi, tvDetails;
    private AppDatabase db;
    private Endemic endemic;
    private boolean isFavorite = false;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_24px);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.inflateMenu(R.menu.detail_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_favorite) {
                toggleFavorite();
                return true;
            }
            return false;
        });

        ivFoto = findViewById(R.id.ivFoto);
        tvNama = findViewById(R.id.tvNama);
        tvNamaLatin = findViewById(R.id.tvNamaLatin);
        tvDeskripsi = findViewById(R.id.tvDeskripsi);
        tvDetails = findViewById(R.id.tvDetails);

        db = AppDatabase.getInstance(this);

        String endemicId = getIntent().getStringExtra("ENDEMIC_ID");
        if (endemicId != null) {
            loadDetail(endemicId);
        }
    }

    private void loadDetail(String id) {
        new Thread(() -> {
            endemic = db.endemicDao().getById(id);
            isFavorite = db.favoriteDao().isFavorite(id);
            runOnUiThread(() -> {
                if (endemic != null) {
                    displayEndemic();
                    updateFavoriteIcon();
                }
            });
        }).start();
    }

    private void displayEndemic() {
        tvNama.setText(endemic.getNama());
        tvNamaLatin.setText(endemic.getNamaLatin());
        tvDeskripsi.setText(endemic.getDeskripsi());
        
        String details = "Tipe: " + endemic.getTipe() + "\n" +
                "Famili: " + endemic.getFamili() + "\n" +
                "Genus: " + endemic.getGenus() + "\n" +
                "Asal: " + endemic.getAsal() + "\n" +
                "Sebaran: " + endemic.getSebaran() + "\n" +
                "Status: " + endemic.getStatus();
        tvDetails.setText(details);

        String imageUrl = endemic.getFoto();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            GlideUrl glideUrl = new GlideUrl(imageUrl, new LazyHeaders.Builder()
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .build());

            Glide.with(this)
                    .load(glideUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.stat_notify_error)
                    .into(ivFoto);
        }
    }

    private void updateFavoriteIcon() {
        MenuItem favoriteItem = toolbar.getMenu().findItem(R.id.action_favorite);
        if (favoriteItem != null) {
            favoriteItem.setIcon(isFavorite ? R.drawable.star_24px : R.drawable.star_outline_24px);
        }
    }

    private void toggleFavorite() {
        if (endemic == null) return;

        new Thread(() -> {
            if (isFavorite) {
                db.favoriteDao().deleteById(endemic.getId());
            } else {
                Favorite favorite = new Favorite(
                        endemic.getId(),
                        endemic.getNama(),
                        endemic.getTipe(),
                        endemic.getFoto(),
                        endemic.getAsal()
                );
                db.favoriteDao().insert(favorite);
            }
            isFavorite = !isFavorite;
            runOnUiThread(() -> {
                updateFavoriteIcon();
                Toast.makeText(this, isFavorite ? getString(R.string.toast_added) : getString(R.string.toast_removed), Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
