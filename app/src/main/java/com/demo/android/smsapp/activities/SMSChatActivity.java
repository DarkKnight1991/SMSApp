package com.demo.android.smsapp.activities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.demo.android.smsapp.R;
import com.demo.android.smsapp.adapters.SMSChatsAdapter;
import com.demo.android.smsapp.models.SMSModel;

import java.util.ArrayList;


public class SMSChatActivity extends AppCompatActivity implements View.OnClickListener {

    ActionBar actionBar;
    RecyclerView recyclerView;
    String number;
    ArrayList<SMSModel> smsModelsSearch,smsModels;
    SMSChatsAdapter smsChatsAdapter;
    EditText etMsg;
    Button btnSend;

    SmsManager smsManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        number = getIntent().getStringExtra("number");
        smsModels = new ArrayList<>();
        smsModelsSearch = new ArrayList<>();

        actionBar = getSupportActionBar();
        actionBar.setTitle(number);

        smsManager = SmsManager.getDefault();

        etMsg = (EditText) findViewById(R.id.et_msg);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_chats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getChatWithNumber();

        smsChatsAdapter = new SMSChatsAdapter(this,smsModels);
        recyclerView.setAdapter(smsChatsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        //region AutoCompleteTextView
        /*autoCompleteTextView = (AutoCompleteTextView)item.getActionView();
        autoCompleteTextView.setWidth(ProjectUtil.dpToPx(200,this));
        autoCompleteTextView.setMaxLines(1);
        autoCompleteTextView.setHint(getResources().getString(R.string.hint_dashboard_search));
        suggestions = new ArrayList<>(Arrays.asList(AppConstants.DASHBOARD_SEARCH_SUGGESTIONS));
        listAdapter = new ArrayAdapter<String>(this,R.layout.layout_autocomplete_item_dropdown,R.id.text_name,AppConstants.DASHBOARD_SEARCH_SUGGESTIONS);
        listAdapter.notifyDataSetChanged();
        autoCompleteTextView.setAdapter(listAdapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(this);*/
        //endregion

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.hint_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                smsModelsSearch = new ArrayList<SMSModel>();
                for(int i=0; i<smsModels.size(); i++){
                    if(smsModels.get(i).getBody().toLowerCase().contains(newText.toLowerCase())){
                       smsModelsSearch.add(smsModels.get(i));
                    }
                }
                smsChatsAdapter = new SMSChatsAdapter(SMSChatActivity.this,smsModelsSearch);
                recyclerView.setAdapter(smsChatsAdapter);
                smsChatsAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_search){

        }

        return super.onOptionsItemSelected(item);
    }

    private void getChatWithNumber(){
        try {
            Uri uri = Uri.parse("content://sms/");
            String[] columns = new String[] { "_id", "address", "person", "date", "body", "type" };
            Cursor cursor = getContentResolver().query(uri, columns, "address='"+number+"'", null, "date asc");
            cursor.moveToFirst();
            Log.e("total:",cursor.getCount()+"");
            String id,address,body,person,type,date;

            for(int i=0; i<cursor.getCount(); i++){

                address = cursor.getString(cursor.getColumnIndex("address"));
                body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                person = cursor.getString(cursor.getColumnIndex("person"));
                type = cursor.getString(cursor.getColumnIndex("type"));
                date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                smsModels.add(SMSModel.getSMSModelObject(id,address,body,type,date));

                Log.e("readSMS","Number:" + address + ",Person:"+person+",Message: " + body+",type:"+type+",date:"+date+",id:"+id);

                cursor.moveToNext();
            }
        } catch (SQLiteException ex) {
            Log.e("SQLiteException", ex.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_send){
            smsManager.sendTextMessage(number,null,etMsg.getText().toString(),null,null);
            etMsg.setText("");
        }
    }
}
