package com.drax.draxproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class Dashboard extends AppCompatActivity {
    EditText date;
    Spinner spinsap;
    TextView tempcollage;
    TextView temppercage;
    TextView tempmontage;
    TextView tempembal;
    TextView temptotal;
    TextView nodata;
    LinearLayout lineardata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        date = findViewById(R.id.date);
        spinsap = findViewById(R.id.sapname);
        tempcollage = findViewById(R.id.tempcollage);
        temppercage = findViewById(R.id.temppercage);
        tempmontage = findViewById(R.id.tempmontage);
        tempembal = findViewById(R.id.tempemballage);
        temptotal = findViewById(R.id.temptotal);
        nodata = findViewById(R.id.no_data);
        lineardata = findViewById(R.id.lineardata);
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        String dy = String.valueOf(day);
        String mnth = String.valueOf(month+1);
        if (month+1<10)
            mnth='0'+mnth;
        if(day<10)
            dy = '0'+dy;
        String dt = dy + "/" + (mnth) + "/" + year;
        if(dt.equals("10/06/2022")) {
            nodata.setVisibility(View.GONE);
            lineardata.setVisibility(View.VISIBLE);

        }
        else{
            lineardata.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);

        }
        date.setText(dy + "/" + (mnth) + "/" + year);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(Dashboard.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String dy = String.valueOf(dayOfMonth);
                                String mnth = String.valueOf(monthOfYear+1);
                                if (monthOfYear+1<10)
                                    mnth='0'+mnth;
                                if(dayOfMonth<10)
                                    dy = '0'+dy;
                                String dt = dy + "/" + (mnth) + "/" + year;
                                if(dt.equals("10/06/2022")) {
                                    nodata.setVisibility(View.GONE);
                                    lineardata.setVisibility(View.VISIBLE);

                                }
                                else{
                                    lineardata.setVisibility(View.GONE);
                                    nodata.setVisibility(View.VISIBLE);

                                }
                                date.setText(dy+ "/" + (mnth) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        spinsap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    tempcollage.setText("00:10:39");
                    temppercage.setText("00:14:30");
                    tempmontage.setText("00:57:12");
                    tempembal.setText("00:31:11");
                    temptotal.setText("01:53:32");
                }else if(i==1)
                {
                    tempcollage.setText("00:11:20");
                    temppercage.setText("00:12:40");
                    tempmontage.setText("01:01:10");
                    tempembal.setText("00:30:05");
                    temptotal.setText("01:55:15");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
