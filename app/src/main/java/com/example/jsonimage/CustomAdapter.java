package com.example.jsonimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int sample;
    List<CarModel> carModels;


    public CustomAdapter(MainActivity mainActivity, int sample, List<CarModel> carModels) {
        this.context = mainActivity;
        this.sample = sample;
        this.carModels = carModels;

    }

    @Override
    public int getCount() {
        return carModels.size();
    }

    @Override
    public Object getItem(int position) {
        return carModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.sample,parent,false);
        }
        TextView t1 = convertView.findViewById(R.id.t1);
        ImageView img = convertView.findViewById(R.id.imgeID);

        t1.setText(carModels.get(position).getName());
        ImageLoader.getInstance().displayImage(carModels.get(position).getImg(), img); // Default options will be used


        return convertView;
    }
}
