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
import com.example.automatadashboard.model.Switch;

import java.util.ArrayList;
import java.util.List;

public class SwitchViewAdapter extends RecyclerView.Adapter<SwitchViewAdapter.MyView> {

    private List<Switch> list;
    private int mItemWidth;
    private Context mContext;
    private int mColor[];

    public class MyView extends RecyclerView.ViewHolder {

        public TextView switchName;
        public TextView switchType;
        public TextView switchStatus;
        public CardView mCardView;
        public ImageView switchIcon;

        public MyView(View view) {
            super(view);

            switchName = (TextView) view.findViewById(R.id.switch_name_text);
            switchStatus = (TextView) view.findViewById(R.id.switch_status_text);
            mCardView = view.findViewById(R.id.cardview_sw);
            switchIcon = view.findViewById(R.id.switch_icon);
            switchType = view.findViewById(R.id.switch_type_text);

        }
    }


    public SwitchViewAdapter(List<Switch> list , Context context) {
        this.list = list;
        this.mContext = context;
//        mColor = new int[5];
//        mColor[0] = ContextCompat.getColor(context,R.color.s1);
//        mColor[1] = ContextCompat.getColor(context,R.color.s2);
//        mColor[2] = ContextCompat.getColor(context,R.color.s3);
//        mColor[3] = ContextCompat.getColor(context,R.color.s4);
//        mColor[4] = ContextCompat.getColor(context,R.color.s5);

    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.switch_item, parent, false);
//        itemView.getLayoutParams().width = mItemWidth;
        return new MyView(itemView);
    }


    @Override
    public void onBindViewHolder(final MyView holder, final int position) {

        int color = 0;
        holder.itemView.setLongClickable(true);
        holder.switchName.setText(list.get(position).getSwitchName());
        holder.switchType.setText(list.get(position).getSwitchType());
        if (list.get(position).getSwitchStatus().equals(Switch.SWITCH_STATUS_ON)){
            color = ContextCompat.getColor(mContext,R.color.sw_on);
            holder.switchStatus.setText("ON");
        } else   if (list.get(position).getSwitchStatus().equals(Switch.SWITCH_STATUS_OFF)){
            holder.switchStatus.setText("OFF");
            color = ContextCompat.getColor(mContext,R.color.sw_off);
        }else {
            holder.switchStatus.setText("N/A");
            color = ContextCompat.getColor(mContext,R.color.colorPrimaryDark);

        }

        holder.mCardView.setCardBackgroundColor(color);

        int iconDrawable = 0;
        if(list.get(position).getSwitchType().equals(Switch.SWITCH_TYPE_LIGHT_BULB)){
            iconDrawable = R.drawable.light_bulb_ico_;
        }else if(list.get(position).getSwitchType().equals(Switch.SWITCH_TYPE_FAN)){
            iconDrawable = R.drawable.fan_ico;
        }else if(list.get(position).getSwitchType().equals(Switch.SWITCH_TYPE_WALL_PLUG)){
            iconDrawable = R.drawable.plug_ico;
        }else if(list.get(position).getSwitchType().equals(Switch.SWITCH_TYPE_AC)){
            iconDrawable = R.drawable.ac_ico;
        }else if(list.get(position).getSwitchType().equals(Switch.SWITCH_TYPE_ANALOG)){
            iconDrawable = R.drawable.analaog_ico;
        }

        if (iconDrawable != 0) {
            Glide.with(mContext).load(iconDrawable).into(holder.switchIcon);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Switch getSwitch(int position) {
        return (list != null && list.size() != 0 ? list.get(position) : null);
    }
}
