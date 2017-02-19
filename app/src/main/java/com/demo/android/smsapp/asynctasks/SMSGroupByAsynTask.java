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

package com.demo.android.smsapp.asynctasks;

import android.os.AsyncTask;

import com.demo.android.smsapp.activities.SMSHistoryActivity;
import com.demo.android.smsapp.models.SMSModel;

import java.util.ArrayList;

public class SMSGroupByAsynTask extends AsyncTask<ArrayList<SMSModel>,Void,ArrayList<String>> {

    SMSHistoryActivity activity;

    public SMSGroupByAsynTask(SMSHistoryActivity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ArrayList<String> doInBackground(ArrayList<SMSModel>... params) {
        ArrayList<SMSModel> allSMSModels = params[0];
        ArrayList<String> addresses = new ArrayList<>();

        for(int i=0; i<allSMSModels.size(); i++){
            if(!addresses.contains(allSMSModels.get(i).getAddress()))
            {
                addresses.add(allSMSModels.get(i).getAddress());
            }
        }

        return addresses;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        // execution of result of Long time consuming operation
        activity.setAdapter(result);
    }

}
