package com.example.appbowling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class addReservationActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private static final int SECRET_KEY = 12;
    private FirebaseUser user;

    EditText nameET;
    EditText emailET;
    DatePicker dateDP;
    Spinner timeSP;
    EditText numberET;
    Button addReservationB;

    private SharedPreferences preferences;

    List<ReservationItem> reservation;

    private DatabaseReference databaseReservation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
        } else {
            finish();
        }

        databaseReservation = FirebaseDatabase.getInstance().getReference("reservation");

        nameET = findViewById(R.id.editTextName);
        emailET = findViewById(R.id.editTextEmail);
        dateDP = findViewById(R.id.datePickerDate);
        timeSP = findViewById(R.id.editTextTime);
        numberET = findViewById(R.id.editTextNumber);
        addReservationB = findViewById(R.id.buttonAddReservation);

        timeSP.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_bowling, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSP.setAdapter(adapter);

        reservation = new ArrayList<>();

        addReservationB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReservation();
                startlistReservation();
            }
        });
    }

    private void startlistReservation() {
        Intent intent = new Intent(this, listReservationActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    private void addReservation() {
        String name = nameET.getText().toString();
        String email = emailET.getText().toString();
        String date = dateDP.getTag().toString();
        String time = timeSP.getSelectedItem().toString();
        String number = numberET.getText().toString();


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(time) && !TextUtils.isEmpty(number)) {

            String id = databaseReservation.push().getKey();

            ReservationItem item = new ReservationItem(id, name, email, date, time, number);

            databaseReservation.child(id).setValue(item);

            Toast.makeText(this, "Foglalás rögzítve", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(this, "Kérem töltse ki az össze mezőt", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseReservation.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String selectedItem = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Username", nameET.getText().toString());
        editor.putString("Email", emailET.getText().toString());
        editor.putString("Username", numberET.getText().toString());
        editor.apply();
    }
}
