package com.di.endemic.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.di.endemic.model.Favorite;
import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    List<Favorite> getAll();

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    boolean isFavorite(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite favorite);
    
    @Query("DELETE FROM favorites WHERE id = :id")
    void deleteById(String id);
}
