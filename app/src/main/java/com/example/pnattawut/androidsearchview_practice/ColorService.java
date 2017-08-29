package com.example.pnattawut.androidsearchview_practice;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by PNattawut on 29-Aug-17.
 */

public interface ColorService {
    @GET(value = "/ListAPIs")
    Call<List<Color>> colorList();
}
