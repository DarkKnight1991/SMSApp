package com.demo.android.smsapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.demo.android.smsapp.R;
import com.demo.android.smsapp.adapters.SMSHistoryAdapter;
import com.demo.android.smsapp.asynctasks.SMSGroupByAsynTask;
import com.demo.android.smsapp.models.SMSModel;

import java.util.ArrayList;

public class SMSHistoryActivity extends ParentActivity {

    ActionBar actionBar;
    private static final int REQUEST_READ_SMS = 1;
    private static final int REQUEST_SEND_SMS = 2;
    private static final int REQUEST_RECEIVE_SMS = 3;
    private static final int REQUEST_ALL = 4;

    ArrayList<SMSModel> smsModels;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smshistory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Conversations");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SMSHistoryActivity.this,SendSMSActivity.class);
                startActivity(intent);
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, REQUEST_ALL);
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            smsModels = getMessages();
            getUniqueNumbers();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_smshistory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkIfPermissionGranted(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS}, REQUEST_READ_SMS);

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_RECEIVE_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_SMS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    finish();
                }
                break;

            case REQUEST_SEND_SMS :
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    finish();
                }
                break;
            case REQUEST_RECEIVE_SMS :
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    finish();
                }
                break;
            case REQUEST_ALL :
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    smsModels = getMessages();
                    getUniqueNumbers();
                } else {
                    Toast.makeText(this,"Permission Needed",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

        }
    }

    private ArrayList<SMSModel> getMessages(){
        //time in long, type:1 inbox- type:2 sent
        ArrayList<SMSModel> smsModels = new ArrayList<>();
        Uri smsUri = Uri.parse("content://sms");
        String[] columns = new String[] { "_id", "address", "person", "date", "body", "type" };
        //String selection = "SELECT " + CallLog.Calls._ID + \" FROM calls WHERE type != \" + CallLog.Calls.VOICEMAIL_TYPE + \" GROUP BY \" + CallLog.Calls.NUMBER+\")\"";
        Cursor cursor = getContentResolver().query(smsUri,columns,null,null,null);//this.getContentResolver().query(Uri.parse( "content://sms/conversations?simple=true"), null, null, null, "normalized_date desc" );//
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
        return smsModels;
    }

    public void getUniqueNumbers(){
        SMSGroupByAsynTask asyncTask = new SMSGroupByAsynTask(this);
        asyncTask.execute(smsModels);
    }

    public void setAdapter(ArrayList<String> numbers){
        SMSHistoryAdapter adapter = new SMSHistoryAdapter(this,numbers);
        recyclerView.setAdapter(adapter);
    }

    public void openChat(View view, int position,String number){
        Intent chatActivityIntent = new Intent(this,SMSChatActivity.class);
        chatActivityIntent.putExtra("number",number);
        startActivity(chatActivityIntent);
    }
}
