package com.example.pnattawut.androidsearchview_practice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;


import org.apache.commons.collections.IteratorUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;

    private List<Color> colors; //Full Color Names
    private List<Color> filteredColors; //Full Color Names
    private ArrayAdapter<Color> colorArrayAdapter;

    //Get color's JSON object from URL and parse to List<Color>
    // [ { color: "red", value: "#ff0000" }, { color: "green", value: "#00ff00" }, { color: "blue", value: "#000ff" }, { color: "cyan", value: "#00ffff" }, { color: "magenta", value: "#ff00ff" }, { color: "yellow", value: "#ffff00" }, { color: "black", value: "#000000" } ]
    private final String URL = "http://54.254.187.201:8080/ListAPIs/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.simpleSearchView);
        listView = (ListView) findViewById(R.id.lstvw_softwares);


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
    public void filter(String query){
        colorItr = filteredColors.listIterator();
        while(colorItr.hasNext()){
            if(!colorItr.next().getColor().contains(query)){
                Log.d("contain", "KKO");
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
}
