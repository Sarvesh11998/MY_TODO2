package com.example.lenovo.my_todo;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Add_Detail_Activity extends AppCompatActivity {
    EditText TASK;
    EditText RANK;
    static final int Result_code = 2;
    Button datebutton;
    Button time;
    TextView datetextview;
    TextView timetextview;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int date;
    int hour;
    int minute;
    int second = 0;

    public static final String My_Action = "action";
    BroadcastReceiver broadcastReceiver22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_activity);

        datebutton = (Button) findViewById(R.id.date);
        datetextview = (TextView) findViewById(R.id.datetext);
        timetextview = (TextView) findViewById(R.id.timetext);


        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(Add_Detail_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        month = i1;
                        i1 = i1 + 1;
                        datetextview.setText(i2 + "/" + i1 + "/" + i);
                        date = i2;

                        year = i;
                    }
                }, 2017, 9, 3);
                datePickerDialog.show();
            }
        });


        time = (Button) findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                timePickerDialog = new TimePickerDialog(Add_Detail_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int il) {
                        if (i > 12) {
                            i = i - 12;
                            timetextview.setText(i + ":" + il + "pm");
                        } else {
                            timetextview.setText(i + ":" + il + "am");

                        }
                        hour = i;
                        minute = il;

                    }
                }, 12, 0, false);


                timePickerDialog.show();


            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        broadcastReceiver22 = new My_Notification();
        IntentFilter intentFilter = new IntentFilter(My_Action);
        registerReceiver(broadcastReceiver22, intentFilter);

    }


    public void OnClick(View view) {

        long currentmillisec = System.currentTimeMillis();
        Log.i("CURRENT TIME", String.valueOf(currentmillisec));


        Calendar c2 = Calendar.getInstance();
        c2.set(year, month, date, hour, minute, second);
        long ep = c2.getTimeInMillis();
        Log.i("CURRENT SELECTED TIME", String.valueOf(ep));


        long difference = currentmillisec - ep;


        TASK = (EditText) findViewById(R.id.TASKEDIT);
        RANK = (EditText) findViewById(R.id.RANKEDIT);

        String New_TASK = TASK.getEditableText().toString();
        String Rank_TEXT = RANK.getEditableText().toString();

        if (New_TASK.isEmpty() || Rank_TEXT.isEmpty()) {
            Intent intent = new Intent();
            setResult(Result_code, intent);
            finish();
            return;
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent Notification_intent = new Intent(this, My_Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, Notification_intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + difference, pendingIntent);


        Intent intentN = new Intent(this, My_Notification.class);
        intentN.setAction(My_Action);
        sendBroadcast(intentN);


        int NEW_RANK = Integer.parseInt(Rank_TEXT);

        My_TODO_Open_Helper open_helper = My_TODO_Open_Helper.getinstance(getApplicationContext());
        SQLiteDatabase db = open_helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.MY_TODO_Title, New_TASK);
        contentValues.put(Contract.MY_TODO_Rank, NEW_RANK);
        long id = db.insert(Contract.MY_TODO_Table_Name, null, contentValues);
        Intent intent = new Intent();
        intent.putExtra(Contract.MY_TODO_ID, id);
        setResult(Result_code, intent);
        finish();

    }

    @Override
    protected void onPause() {

        unregisterReceiver(broadcastReceiver22);


        super.onPause();
    }

}
