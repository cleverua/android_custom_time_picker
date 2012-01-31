package com.cleverua.timepicker;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {

    private static final int STEP = 15; /* a 15 minute step for time picker */
    
    private static final int TIME_DIALOG_ID = 0;
    
    private TextView timeDisplay;
    private Button pickTime;

    private int hour;
    private int minute;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // capture our View elements
        timeDisplay = (TextView) findViewById(R.id.time_display);
        pickTime    = (Button)   findViewById(R.id.pick_time);

        // add a click listener to the button
        pickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });

        // get the current time
        final Calendar c = Calendar.getInstance();
        hour   = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // display the current time
        updateDisplay();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new CustomTimePickerDialog(
                    this,
                    timeSetListener, 
                    hour, 
                    minute, 
                    true,
                    STEP
                );
        }
        return null;
    }
    
    // updates the time we display in the TextView
    private void updateDisplay() {
        String time = new StringBuilder(5)
            .append(pad(hour))
            .append(':')
            .append(pad(minute))
                .toString();
        
        timeDisplay.setText(time);
    }

    private static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }
    
    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener timeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            MainActivity.this.hour   = hourOfDay;
            MainActivity.this.minute = minute;
            updateDisplay();
        }
    };
}