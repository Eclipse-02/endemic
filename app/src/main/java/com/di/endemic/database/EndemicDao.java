package com.di.endemic.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.di.endemic.model.Endemic;
import java.util.List;

@Dao
public interface EndemicDao {
    @Query("SELECT * FROM endemic WHERE id = :id")
    Endemic getById(String id);

    @Query("SELECT * FROM endemic WHERE tipe = :tipe")
    List<Endemic> getByType(String tipe);

    @Query("SELECT * FROM endemic WHERE tipe = :tipe AND sebaran LIKE '%' || :region || '%'")
    List<Endemic> getByTypeAndRegion(String tipe, String region);

    @Query("SELECT * FROM endemic WHERE nama LIKE '%' || :query || '%'")
    List<Endemic> search(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Endemic> endemics);

    @Query("DELETE FROM endemic")
    void deleteAll();
}
