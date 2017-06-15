package com.tryon.xuanhong.tryon.object3D.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tryon.xuanhong.tryon.R;

import java.util.List;

//import com.tryon.xuanhong.new3d.R;

/**
 * Created by Pinky on 26-Apr-17.
 */

public class ListAdapter extends ArrayAdapter<Item> {

    public ListAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.row_item, null);
        }
        Item p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txtTen = (TextView) view.findViewById(R.id.textViewTen);

            txtTen.setText(p.ten);
            TextView txtMoTa = (TextView) view.findViewById(R.id.textViewMoTa);

            txtMoTa.setText(p.mota);
            TextView txtGia = (TextView) view.findViewById(R.id.textViewGia);

            txtGia.setText(String.valueOf(p.gia));

            ImageView imgv = (ImageView) view.findViewById(R.id.imageView);
            imgv.setImageResource(p.hinh);

        }
        return view;
    }

}
