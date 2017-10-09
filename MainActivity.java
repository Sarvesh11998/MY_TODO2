package com.example.lenovo.my_todo;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Tododetail> main_list;
    ListView listView;
    BroadcastReceiver br;
    CustomAdaptor adaptor;
    static final int request_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv1);
        main_list = new ArrayList<>();

        My_TODO_Open_Helper open_helper = My_TODO_Open_Helper.getinstance(getApplicationContext());
        final SQLiteDatabase db = open_helper.getReadableDatabase();
        Cursor cursor = db.query(Contract.MY_TODO_Table_Name, null, null, null, null, null, Contract.MY_TODO_Rank + " ASC", null);
        while (cursor.moveToNext()) {
            String task = cursor.getString(cursor.getColumnIndex(Contract.MY_TODO_Title));
            int rank = cursor.getInt(cursor.getColumnIndex(Contract.MY_TODO_Rank));
            long id = cursor.getLong(cursor.getColumnIndex(Contract.MY_TODO_ID));
            Tododetail temp = new Tododetail(task, rank, id);
            main_list.add(temp);

        }
        cursor.close();

        adaptor = new CustomAdaptor(this, main_list, new DeleteButtonClickListner() {
            @Override
            public void onbuttonclick(int position, View view) {

                db.delete(Contract.MY_TODO_Table_Name, Contract.MY_TODO_ID + " = ?", new String[]{main_list.get(position).getId() + ""});


                main_list.remove(position);
                adaptor.notifyDataSetChanged();
            }
        });

        listView.setAdapter(adaptor);


    }

    @Override
    protected void onStart() {


        br = new My_BroadCast_Receiver();


        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);

        IntentFilter intentFilter2 = new IntentFilter(Intent.ACTION_BATTERY_OKAY);

        registerReceiver(br, intentFilter);

        registerReceiver(br, intentFilter2);

        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(br);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add, menu);
        return true;


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Add) {
            Intent intent = new Intent(MainActivity.this, Add_Detail_Activity.class);
            startActivityForResult(intent, request_code);
        } else if (id == R.id.aboutus) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com"));
            startActivity(intent);


        } else if (id == R.id.callus) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:100"));
            startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == request_code) {
            if (resultCode == Add_Detail_Activity.Result_code) {


                long id = data.getLongExtra(Contract.MY_TODO_ID, -1);
                if (id > -1) {
                    My_TODO_Open_Helper open_helper = My_TODO_Open_Helper.getinstance(this);
                    SQLiteDatabase db = open_helper.getReadableDatabase();
                    Cursor cursor = db.query(Contract.MY_TODO_Table_Name, null, Contract.MY_TODO_ID + " = ?", new String[]{id + ""}, null, null, null);
                    if (cursor.moveToFirst()) {
                        String task = cursor.getString(cursor.getColumnIndex(Contract.MY_TODO_Title));
                        int rank = cursor.getInt(cursor.getColumnIndex(Contract.MY_TODO_Rank));
                        Tododetail temp2 = new Tododetail(task, rank, id);
                        int flag = 0;
                        for (int i = 0; i < main_list.size(); i++) {
                            Tododetail temp = main_list.get(i);
                            int checkrank = temp.getRank();
                            if (checkrank > rank) {
                                main_list.add(i, temp2);
                                adaptor.notifyDataSetChanged();
                                flag = 1;
                                break;
                            }

                        }

                        if (main_list.size() == 0 || flag == 0) {
                            main_list.add(temp2);
                            adaptor.notifyDataSetChanged();
                        }
                    }
                }
            }


            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}