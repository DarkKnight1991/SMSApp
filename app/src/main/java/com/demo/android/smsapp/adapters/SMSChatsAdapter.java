package com.demo.android.smsapp.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.android.smsapp.R;
import com.demo.android.smsapp.models.SMSModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SMSChatsAdapter extends RecyclerView.Adapter<SMSChatsAdapter.MainViewHolder> {

    Activity activity;
    ArrayList<SMSModel> smsModels;
    Calendar calendar,calendar2;

    String devieName;

    SimpleDateFormat msgTimeDateFormat,msgTimeFormat;

    AdapterView.OnItemClickListener listener;

    public SMSChatsAdapter(Activity activity, ArrayList<SMSModel> smsModels){
        this.activity = activity;
        this.smsModels = smsModels;

        msgTimeDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aa");
        msgTimeFormat = new SimpleDateFormat("HH:mm aa");

        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ReceivedChatHolder extends MainViewHolder implements View.OnClickListener{

        TextView tvWho;
        TextView tvMsg;
        TextView tvTime;
        TextView tvId;

        public ReceivedChatHolder(View itemView) {
            super(itemView);
            tvWho = (TextView) itemView.findViewById(R.id.tv_who);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_chat_msg);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvId = (TextView) itemView.findViewById(R.id.tv_msg_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public class SentChatHolder extends MainViewHolder implements View.OnClickListener{

        TextView tvWho;
        TextView tvMsg;
        TextView tvTime;
        TextView tvId;

        public SentChatHolder(View itemView) {
            super(itemView);
            tvWho = (TextView) itemView.findViewById(R.id.tv_who);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_chat_msg);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvId = (TextView) itemView.findViewById(R.id.tv_msg_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MainViewHolder holder;

        if(viewType == 2){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_item_chat_sent, parent, false);
            holder = new SentChatHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_item_chat_received, parent, false);
            holder = new ReceivedChatHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        SMSModel smsModel = smsModels.get(position);
        if(holder instanceof SentChatHolder){
            SentChatHolder sentChatHolder = (SentChatHolder) holder;
            sentChatHolder.tvMsg.setText(smsModel.getBody());

            calendar.setTime(new Date(Long.parseLong(smsModel.getDate())));
            calendar2.setTime(new Date(System.currentTimeMillis()));
            if(calendar.get(Calendar.DATE) == calendar2.get(Calendar.DATE)){
                sentChatHolder.tvTime.setText("Today "+msgTimeFormat.format(new Date(Long.parseLong(smsModel.getDate()))));
            } else if(calendar.get(Calendar.DATE) == (calendar2.get(Calendar.DATE)-1)){
                sentChatHolder.tvTime.setText("Yesterday "+msgTimeFormat.format(new Date(Long.parseLong(smsModel.getDate()))));
            }else {
                sentChatHolder.tvTime.setText(msgTimeDateFormat.format(new Date(Long.parseLong(smsModel.getDate()))));
            }

            sentChatHolder.tvWho.setText("Me :");

        }else{
            ReceivedChatHolder receivedChatHolder = (ReceivedChatHolder) holder;
            receivedChatHolder.tvMsg.setText(smsModel.getBody());
            calendar.setTime(new Date(Long.parseLong(smsModel.getDate())));
            calendar2.setTime(new Date(System.currentTimeMillis()));
            if(calendar.get(Calendar.DATE) == calendar2.get(Calendar.DATE)){
                receivedChatHolder.tvTime.setText("Today "+msgTimeFormat.format(new Date(Long.parseLong(smsModel.getDate()))));
            } else if(calendar.get(Calendar.DATE) == (calendar2.get(Calendar.DATE)-1)){
                receivedChatHolder.tvTime.setText("Yesterday "+msgTimeFormat.format(new Date(Long.parseLong(smsModel.getDate()))));
            }else {
                receivedChatHolder.tvTime.setText(msgTimeDateFormat.format(new Date(Long.parseLong(smsModel.getDate()))));
            }

            receivedChatHolder.tvWho.setText(smsModel.getAddress()+" :");
        }
    }

    @Override
    public int getItemCount() {
        return smsModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(smsModels.get(position).getType()) == 2 ? 2 : 1; // viewType 2 means sentChatHolder
    }


}

