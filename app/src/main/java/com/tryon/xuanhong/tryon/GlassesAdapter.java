package com.tryon.xuanhong.tryon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pinky on 13-Jun-17.
 */

public class GlassesAdapter extends SearchAdapter<Glasses> {
    class ViewHolder {
        @InjectView(R.id.txt_idglasses)    TextView txt_id;
        @InjectView(R.id.txt_priceglasses) TextView txt_price;
        @InjectView(R.id.txt_statusglasses) TextView txt_status;
        @InjectView(R.id.imgv_glasses)      ImageView imgv_glasses;


        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public GlassesAdapter(List<Glasses> glasses, Context context) {
        super(glasses, context);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_glasses, null);
            viewHolder = new GlassesAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.txt_price.setText(filteredContainer.get(position).getPrice() + "");
        viewHolder.txt_status.setText(filteredContainer.get(position).getStatus());
        viewHolder.txt_id.setText(filteredContainer.get(position).getId());

        byte[] arr = Base64.decode(filteredContainer.get(position).getThumnail(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        viewHolder.imgv_glasses.setImageBitmap(bmp);

        return convertView;
    }



}
