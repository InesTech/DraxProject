package com.drax.draxproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    String dt;
    ArrayList<String> sapList = new ArrayList<>();
    boolean doubleBackToExitPressedOnce = false;

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
        dt = dy + "-" + (mnth) + "-" + year;
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
                                dt = dy + "-" + (mnth) + "-" + year;
                                getData(spinsap.getSelectedItem().toString(),dt);
                                date.setText(dy+ "/" + (mnth) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("SAPName");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sapList.clear();
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1 : snapshot.getChildren())
                    {
                        sapList.add(snapshot1.getValue().toString());
                    }
                }else
                    sapList.add("Liste vide !");
                spinsap.setAdapter( new ArrayAdapter<>(Dashboard.this, android.R.layout.simple_spinner_dropdown_item,sapList));
                getData(spinsap.getSelectedItem().toString(),dt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinsap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) adapterView.getChildAt(0)).setTextSize(15);
                ((TextView) adapterView.getChildAt(0)).setTypeface(((TextView)adapterView.getChildAt(0)).getTypeface(),Typeface.BOLD);
                getData(spinsap.getSelectedItem().toString(),dt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public boolean dateExist(final String date){
        final boolean[] exist = {false};
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Data");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot datasnapshot :snapshot.getChildren()){
                        if(datasnapshot.getKey().equals(date)){
                            exist[0] = true;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return exist[0];
    }
    public void getData(final String SapName, final String date){
        final boolean[] dateexist = {false};
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Data");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot datasnapshot :snapshot.getChildren()){
                        if(datasnapshot.getKey().equals(date)){
                            dateexist[0] = true;
                            if(datasnapshot.child(SapName).exists()){
                                tempcollage.setText(datasnapshot.child(SapName).child("Collage").getValue().toString());
                                temppercage.setText(datasnapshot.child(SapName).child("Percage").getValue().toString());
                                tempmontage.setText(datasnapshot.child(SapName).child("Montage").getValue().toString());
                                tempembal.setText(datasnapshot.child(SapName).child("Emballage").getValue().toString());
                                temptotal.setText(datasnapshot.child(SapName).child("Total").getValue().toString());
                                nodata.setVisibility(View.GONE);
                                lineardata.setVisibility(View.VISIBLE);
                            }else{

                            }

                        }

                    }
                    if(dateexist[0] == false){
                        lineardata.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }

                }else{
                    lineardata.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Veuillez faire appuyer/glisser le doigt à nouveau.", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

    }
}
