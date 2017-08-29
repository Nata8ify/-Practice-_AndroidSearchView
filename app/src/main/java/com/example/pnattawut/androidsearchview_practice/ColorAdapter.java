package com.example.pnattawut.androidsearchview_practice;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PNattawut on 29-Aug-17.
 */

public class ColorAdapter extends ArrayAdapter<Color> {

    private Context context;
    private int resource;
    private List<Color> colors;

    public ColorAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Color> colors) {
        super(context, resource, colors);
        this.context = context;
        this.resource = resource;
        this.colors = colors;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = LayoutInflater.from(this.context).inflate(resource, parent, false);
        LinearLayout colorBlock =  ((LinearLayout)row.findViewById(R.id.colorBlock));
        colorBlock.setBackgroundColor(android.graphics.Color.parseColor(colors.get(position).getValue()));
        TextView colorName = ((TextView)row.findViewById(R.id.colorName));
        colorName.setText(colors.get(position).getColor());
        TextView colorValue = ((TextView)row.findViewById(R.id.colorValue));
        colorValue.setText(colors.get(position).getValue());
        return  row;
    }
}
