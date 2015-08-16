package com.example.igulhane73.appnew;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.igulhane73.appnew.dbOps.ConfigDatabaseOperations;

/**
 * Created by igulhane73 on 8/8/15.
 */
public class AddDialog extends ActionBarActivity {
    private int[] weekday = new int[7];
    int sun;
    int mon;
    int tue;
    int wed;
    int thur;
    int fri;
    int sat;
    private String mode="";
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_element);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spinner = (Spinner) findViewById(R.id.event_mode);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.event_mode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        mode="All";

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              mode=parent.getItemAtPosition(position).toString();
            }
        });*/

        final Button save_button = (Button) findViewById(R.id.save);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConfigDatabaseOperations cdo = new ConfigDatabaseOperations(getBaseContext());
                String endTime = null;
                int highestId = cdo.getHighestId(cdo.getWritableDatabase());
                EditText etName = (EditText) findViewById(R.id.event_id);
                TimePicker timeValue = (TimePicker) findViewById(R.id.time_event);
                int hour = timeValue.getCurrentHour();
                String temp = "AM";
                if (hour > 12) {
                    temp = "PM";
                    hour = hour % 12;
                }
                String time = hour + ":" + timeValue.getCurrentMinute() +" "+temp;
                Intent data = new Intent();
                UserEvent userEvent = new UserEvent();
                userEvent.setTitle(etName.getText().toString());
                userEvent.setTime(time);
                //userEvent.setWeekday(weekday);
                userEvent.setSun(sun);
                userEvent.setMon(mon);
                userEvent.setTue(tue);
                userEvent.setWed(wed);
                userEvent.setThur(thur);
                userEvent.setFri(fri);
                userEvent.setSat(sat);
                userEvent.setMode(mode);
                userEvent.setActive(true);
                userEvent.setId(highestId);
                data.putExtra("event", userEvent);
                setResult(RESULT_OK, data);
                cdo.addNewTimeConfig(cdo ,highestId , time,endTime , mode ,
                        "" + sun ,"" + mon ,"" + tue , "" + wed ,
                        "" + thur , "" + fri , "" + sat , etName.getText().toString() , true);
                finish();

            }
        });
        Button cancel_button = (Button) findViewById(R.id.cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent data = new Intent();

                setResult(RESULT_CANCELED, data);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    public void selectDeselect(View view) {
        TextView textView = (TextView) findViewById(view.getId());
        int position = 0;
        int i = textView.getCurrentTextColor();
        if (i == Color.BLACK) {
            textView.setTextColor(Color.RED);
            position = 1;
        } else {
            textView.setTextColor(Color.BLACK);
            position = 0;
        }
        switch (view.getId()) {
            case R.id.Sunday:
                sun=position;
                break;
            case R.id.Monday:
                mon = position;
                break;
            case R.id.Tuesday:
                tue = position;
                break;
            case R.id.Wednesday:
                wed=position;
                break;
            case R.id.Thursday:
                thur=position;
                break;
            case R.id.Friday:
                fri=position;
                break;
            case R.id.Saturday:
                sat=position;
                break;


        }


    }

}
