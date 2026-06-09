package com.di.endemic.network;

import com.di.endemic.model.Endemic;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("endemik.json")
    Call<List<Endemic>> getEndemics();
}
