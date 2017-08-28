package com.example.pnattawut.androidsearchview_practice;

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
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;

    private ListView listView;
    private ArrayAdapter colorNameListAdapter;

    private List<String> colorNames; //Full Color Names
    private List<String> filteredColorNames; //Filtered Color Names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Color's List
        colorNames = getColorNames(getColors());
        if (filteredColorNames == null) {
            filteredColorNames = new ArrayList<>();
        }
        filteredColorNames.addAll(this.colorNames);

        //Initialize View
        searchView = (SearchView) findViewById(R.id.simpleSearchView);
        listView = (ListView) findViewById(R.id.lstvw_softwares);
        colorNameListAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, filteredColorNames);
        listView.setAdapter(colorNameListAdapter);

        //Set SearchView Listener up
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // If submit key is pressed then ...
            @Override
            public boolean onQueryTextSubmit(String nameLike) {
                Log.d("onQueryTextSubmit >> ", nameLike);
                filterColorList(nameLike);
                notifyChanged();
                return false;
            }

            // If text on SearchView is changed then ...
            @Override
            public boolean onQueryTextChange(String nameLike) {
                Log.d("onQueryTextChange >> ", nameLike);
                filterColorList(nameLike);
                notifyChanged();
                return false;
            }
        });
    }

    //Get color's JSON object from URL and parse to List<Color>
    private final String URL = "http://54.254.187.201:8080/ListAPIs/";
    public List<Color> getColors() {
        String response = null;
        try {
            response = Ion.with(this)
                    .load(URL)
                    .asString()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(response, new TypeToken<ArrayList<Color>>() {
        }.getType());
    }

    //Get a color name's list(List<String>) from color list (List<Color>)
    public List<String> getColorNames(List<Color> colorList) {
        if (colorList == null) {
            return null;
        }// If 'colorList' is null then return null
        List<String> colorNames = new ArrayList<>();
        for (Color color : colorList) {
            colorNames.add(color.getColor());
        }
        return colorNames;
    }

    //Filter a color names by a color's keyword
    public void filterColorList(String nameLike) {
        Iterator<String> colorNamesItr = filteredColorNames.iterator(); //Prevent from Concurrent$Modification Exception
        while (colorNamesItr.hasNext()) {
            if (!colorNamesItr.next().contains(nameLike)) {
                colorNamesItr.remove();
            }
        }
    }

    //Notify ListView for data changed.
    public void notifyChanged() {
        Log.d("notifyChanged", colorNames.toString());
        Log.d("notifyChanged", filteredColorNames.toString());
        colorNameListAdapter.notifyDataSetChanged();
    }

    //Restore color's list to ListView.
    public void restore(View v) {
        filteredColorNames.clear();
        filteredColorNames.addAll(this.colorNames);
        colorNameListAdapter.notifyDataSetChanged();

    }


}
