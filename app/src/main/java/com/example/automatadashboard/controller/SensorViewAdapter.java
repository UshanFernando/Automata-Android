package com.example.automatadashboard.controller;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.automatadashboard.R;
import com.example.automatadashboard.model.Sensor;

import java.util.List;

public class SensorViewAdapter extends RecyclerView.Adapter<SensorViewAdapter.MyView> {

    private List<Sensor> list;
    private int mItemWidth;
    private Context mContext;
    private int mColor[];

    public class MyView extends RecyclerView.ViewHolder {

        public TextView sensorName;
        public TextView sensorSign;
        public TextView sensorValue;
        public CardView mCardView;
        public ImageView sensorIcon;

        public MyView(View view) {
            super(view);

            sensorName = (TextView) view.findViewById(R.id.switch_name_text);
            sensorSign = (TextView) view.findViewById(R.id.switch_status_text);
            sensorValue = (TextView) view.findViewById(R.id.stst_lbl);
            mCardView = view.findViewById(R.id.cardview);
            sensorIcon = view.findViewById(R.id.switch_icon);

        }
    }


    public SensorViewAdapter(List<Sensor> horizontalList , Context context) {
        this.list = horizontalList;
        this.mContext = context;
        mColor = new int[5];
        mColor[0] = ContextCompat.getColor(context,R.color.s1);
        mColor[1] = ContextCompat.getColor(context,R.color.s2);
        mColor[2] = ContextCompat.getColor(context,R.color.s3);
        mColor[3] = ContextCompat.getColor(context,R.color.s4);
        mColor[4] = ContextCompat.getColor(context,R.color.s5);

    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_item, parent, false);
        itemView.getLayoutParams().width = mItemWidth;
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        holder.sensorName.setText(list.get(position).getSensorName());
        holder.sensorSign.setText(list.get(position).getSensorSign());
        holder.sensorValue.setText(list.get(position).getSensorValue());
        holder.mCardView.setCardBackgroundColor(mColor[position]);

        int iconDrawable = 0;
        if(list.get(position).getSensorType().equals(Sensor.SENSOR_TYPE_TEMP)){
            iconDrawable = R.drawable.temp_ico;
        }else if(list.get(position).getSensorType().equals(Sensor.SENSOR_TYPE_HUMIDITY)){
            iconDrawable = R.drawable.hum_ico;
        }else if(list.get(position).getSensorType().equals(Sensor.SENSOR_TYPE_AIR_QUALITY)){
            iconDrawable = R.drawable.air_quality_icon;
        }else if(list.get(position).getSensorType().equals(Sensor.SENSOR_TYPE_PEOPLE)){
            iconDrawable = R.drawable.people_sensor_ico;
        }

        if (iconDrawable != 0) {
            Glide.with(mContext).load(iconDrawable).into(holder.sensorIcon);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemWidth(int parentWidth) {
        mItemWidth = (parentWidth / 2) -  5;
    }

    public void setItems(List<Sensor> sensors) {
        this.list = sensors;
    }
}
