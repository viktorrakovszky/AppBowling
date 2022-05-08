package com.example.appbowling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    EditText userNameET;
    EditText emailET;
    EditText password1ET;
    EditText password2ET;
    EditText phoneET;
    Spinner phoneSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != 10) {
            finish();
        }

        userNameET = findViewById(R.id.userNameET);
        emailET = findViewById(R.id.emailET);
        phoneET = findViewById(R.id.phoneNumberET);
        password1ET = findViewById(R.id.password1ET);
        password2ET = findViewById(R.id.password2ET);
        phoneSpinner = findViewById(R.id.phoneSpinner);


        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userName = preferences.getString("Username", "");
        String password = preferences.getString("Password", "");

        userNameET.setText(userName);
        password1ET.setText(password);
        password2ET.setText(password);

        phoneSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phoneTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phoneSpinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();

    }

    public void register(View view) {
        String userName = userNameET.getText().toString();
        String email = emailET.getText().toString();
        String password1 = password1ET.getText().toString();
        String password2 = password2ET.getText().toString();
        String phone = phoneET.getText().toString();
        String phoneType = phoneSpinner.getSelectedItem().toString();

        mAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startReservation();
                } else {
                    Toast.makeText(RegistrationActivity.this, "A felhasználót nem sikerült létrehozni", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void startReservation() {
        Intent intent = new Intent(this, addReservationActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
    }
}