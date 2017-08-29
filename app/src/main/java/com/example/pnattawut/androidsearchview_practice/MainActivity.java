package com.example.pnattawut.androidsearchview_practice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;

    private List<Color> colors; //Full Color Names
    private List<Color> filteredColors; //Full Color Names
    private ArrayAdapter<Color> colorArrayAdapter;


    //Get color's JSON object from URL and parse to List<Color>
    // [ { color: "red", value: "#ff0000" }, { color: "green", value: "#00ff00" }, { color: "blue", value: "#000ff" }, { color: "cyan", value: "#00ffff" }, { color: "magenta", value: "#ff00ff" }, { color: "yellow", value: "#ffff00" }, { color: "black", value: "#000000" } ]
    private final String URL = "http://54.254.187.201:8080/ListAPIs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = (SearchView) findViewById(R.id.simpleSearchView);
        listView = (ListView) findViewById(R.id.lstvw_softwares);

//        new LoadAsyncTask().execute();

        //Initialize Color's List
        try {
            colors = new Gson().fromJson(Ion.with(this).load(URL).asString().get(), new TypeToken<ArrayList<Color>>() {}.getType());
            filteredColors =  new ArrayList<>(colors);
            colorArrayAdapter = new ColorAdapter(this, R.layout.color_row, filteredColors);
            listView.setAdapter(colorArrayAdapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private Iterator<Color> colorItr;

    public void filter(String query) {
        colorItr = filteredColors.listIterator();
        while (colorItr.hasNext()) {
            if (!colorItr.next().getColor().contains(query)) {
                colorItr.remove();
            }
        }
        colorArrayAdapter.notifyDataSetChanged();

        /*  */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                filteredColors.clear();
                filteredColors.addAll(MainActivity.this.colors);
            }
        }, 100L);
    }


    class LoadAsyncTask extends AsyncTask {
        private Retrofit retrofit;
        private ColorService colorService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            colorService = retrofit.create(ColorService.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                Log.d("doInBackground", colorService.colorList().execute().toString());
                colorService.colorList().execute();
/*
            filteredColors =  new ArrayList<>(colors);
            colorArrayAdapter = new ColorAdapter(this, R.layout.color_row, filteredColors);
            listView.setAdapter(colorArrayAdapter);
*/
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
