/*Copyright 2017 Asutosh Nayak

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.demo.android.smsapp.activities;


import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.demo.android.smsapp.R;

public class SendSMSActivity extends ParentActivity implements View.OnClickListener {

    EditText etNumber,etMsg;
    Button btnSend;

    SmsManager smsManager;

    public void onCreate(Bundle onSaveStateInstanceState){
        super.onCreate(onSaveStateInstanceState);
        setContentView(R.layout.activity_send_sms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Send SMS");

        etNumber = (EditText) findViewById(R.id.et_number);
        etMsg = (EditText) findViewById(R.id.et_msg);
        btnSend = (Button) findViewById(R.id.btn_send);

        btnSend.setOnClickListener(this);

        smsManager = SmsManager.getDefault();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_send){
            smsManager.sendTextMessage(etNumber.getText().toString(),null,etMsg.getText().toString(),null,null);
            etMsg.setText("");

            Intent intent = new Intent(this,SMSChatActivity.class);
            intent.putExtra("number",etNumber.getText().toString());
            startActivity(intent);
        }
    }
}
