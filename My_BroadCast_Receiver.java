package com.example.lenovo.my_todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class My_BroadCast_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        String action = intent.getAction();
        if (action.equals(Intent.ACTION_BATTERY_LOW)) {

            Toast.makeText(context, "LOW", Toast.LENGTH_LONG).show();

        }else

        {



            Toast.makeText(context, "OKAY", Toast.LENGTH_LONG).show();

        }

    }
}
