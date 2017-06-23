package com.tryon.xuanhong.tryon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pinky on 23-Jun-17.
 */

public class GlassesFullAdapter extends ArrayAdapter<Glasses> {

    public GlassesFullAdapter(Context context, int resource, List<Glasses> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.row_glasses_full, null);
        }
        Glasses p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView name = (TextView) view.findViewById(R.id.txt_name);
            name.setText("Name: " + p.getName());

            TextView id = (TextView) view.findViewById(R.id.txt_id);
            id.setText("Id: " + p.getId());

            TextView price = (TextView) view.findViewById(R.id.txt_price);
            price.setText("Price: " + p.getPrice());

            TextView producer = (TextView) view.findViewById(R.id.txt_producer);
            producer.setText("Producer: " + p.getProducer());

            TextView eye = (TextView) view.findViewById(R.id.txt_eye);
            eye.setText("Eye: " + p.getEye());

            TextView bridge = (TextView) view.findViewById(R.id.txt_bridge);
            bridge.setText("Bridge: " + p.getBridge());

            TextView temple = (TextView) view.findViewById(R.id.txt_temple);
            temple.setText("Temple: " + p.getTemple());

            TextView color = (TextView) view.findViewById(R.id.txt_color);
            color.setText("Color: " + p.getColor());

            TextView status = (TextView) view.findViewById(R.id.txt_status);
            status.setText("Status: " + p.getStatus());

            ImageView imgv = (ImageView) view.findViewById(R.id.imv_thumnail);
            byte[] arr = Base64.decode(p.getThumnail(), Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            imgv.setImageBitmap(bmp);


        }
        return view;
    }

}

