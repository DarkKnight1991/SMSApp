package com.demo.android.smsapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.demo.android.smsapp.R;
import com.demo.android.smsapp.activities.SMSHistoryActivity;
import com.demo.android.smsapp.models.SMSModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SMSHistoryAdapter extends RecyclerView.Adapter<SMSHistoryAdapter.MainViewHolder> {

    Activity activity;
    ArrayList<SMSModel> smsModels;
    ArrayList<String> numbers;
    AdapterView.OnItemClickListener listener;
    SimpleDateFormat msgTimeFormat,msgTimeDateFormat;

    Calendar calendar,calendar2;

    /*public SMSHistoryAdapter(Activity activity, ArrayList<SMSModel> smsModels){
        this.activity = activity;
        this.smsModels = smsModels;

        msgTimeFormat = new SimpleDateFormat("HH:mm aa");
        msgTimeDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aa");

        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
    }*/

    public SMSHistoryAdapter(Activity activity, ArrayList<String> numbers){
        this.activity = activity;
        this.smsModels = smsModels;
        this.numbers = numbers;

        msgTimeFormat = new SimpleDateFormat("HH:mm aa");
        msgTimeDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aa");

        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ChatDetailHolder extends MainViewHolder implements View.OnClickListener{

        TextView tvNumber;
        TextView tvLastMsg;
        TextView tvTime;

        public ChatDetailHolder(final View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            tvLastMsg = (TextView) itemView.findViewById(R.id.tv_last_msg);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ((SMSHistoryActivity)activity).openChat(v,getAdapterPosition(),numbers.get(getAdapterPosition()));
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_sms_history, parent, false);
        ChatDetailHolder chatDetailHolder = new ChatDetailHolder(view);
        return chatDetailHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        if(holder instanceof ChatDetailHolder){
            ChatDetailHolder chatDetailHolder = (ChatDetailHolder) holder;
            chatDetailHolder.tvNumber.setText(numbers.get(position));

            /*String str = smsModels.get(position).getBody();
            if(str.length() > 25){
                str = str.substring(0,22) + "...";
            }
            chatDetailHolder.tvLastMsg.setText(str);
            calendar.setTime(new Date(Long.parseLong(smsModels.get(position).getDate())));
            calendar2.setTime(new Date(System.currentTimeMillis()));
            if(calendar.get(Calendar.DATE) == calendar2.get(Calendar.DATE)){
                chatDetailHolder.tvTime.setText("Today "+msgTimeFormat.format(new Date(Long.parseLong(smsModels.get(position).getDate()))));
            } else if(calendar.get(Calendar.DATE) == (calendar2.get(Calendar.DATE)-1)){
                chatDetailHolder.tvTime.setText("Yesterday "+msgTimeFormat.format(new Date(Long.parseLong(smsModels.get(position).getDate()))));
            }else {
                chatDetailHolder.tvTime.setText(msgTimeDateFormat.format(new Date(Long.parseLong(smsModels.get(position).getDate()))));
            }*/
        }
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }


}

