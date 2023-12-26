package com.example.wisebridge;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registeration extends AppCompatActivity {
    EditText Rusername, Rpassword,REmail,Ruid, Raccount, Rifsc;
    DatabaseReference databaseReference;
    Button Reg;
    Spinner userTypeSpinnerreg;
    public static  String username,password,email, ruid,raccounts, rifscs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Rusername = (EditText)findViewById(R.id.Rusername);
        Rpassword = findViewById(R.id.Rpassword);
        REmail = findViewById(R.id.REmail);
        Ruid = findViewById(R.id.Ruid);
        Reg= findViewById(R.id.Registerbtn);
        Raccount = findViewById(R.id.Raccount);
        Rifsc = findViewById(R.id.Rifsc);
        userTypeSpinnerreg = findViewById(R.id.userTypeSpinnerreg);


        FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");

        userTypeSpinnerreg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                         @Override
                                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                             String userType = parent.getItemAtPosition(position).toString();
                                                             if(userType.equals("Expert")){
                                                                 Raccount.setVisibility(View.VISIBLE);
                                                                 Rifsc.setVisibility(View.VISIBLE);
                                                                 //databaseReference = ref1.getReference().child("ExpRegister").child(ruid);
                                                             }
                                                             else if(userType.equals("Student")){
                                                                 //databaseReference = ref1.getReference().child("Register").child(ruid);
                                                                 Raccount.setVisibility(View.GONE);
                                                                 Rifsc.setVisibility(View.GONE);
                                                             }
                                                         }

                                                         @Override
                                                         public void onNothingSelected(AdapterView<?> parent) {

                                                         }
                                                     });

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userTypeSpinnerreg = findViewById(R.id.userTypeSpinnerreg);
                String userType = userTypeSpinnerreg.getSelectedItem().toString();
                if(userType.equals("Student")){
                     username = Rusername.getText().toString().trim();
                     password = Rpassword.getText().toString().trim();
                     email = REmail.getText().toString().trim();
                     ruid= Ruid.getText().toString().trim();
                }else if(userType.equals("Expert")){
                    username = Rusername.getText().toString().trim();
                    password = Rpassword.getText().toString().trim();
                    email = REmail.getText().toString().trim();
                    ruid= Ruid.getText().toString().trim();
                    raccounts = Raccount.getText().toString();
                    rifscs = Rifsc.getText().toString();


                }


                if(!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !ruid.isEmpty()) {

                    if (password.length() >= 7 && Character.isUpperCase(password.charAt(0))) {
                        if (userType.equals("Expert")) {
                            databaseReference = ref1.getReference().child("ExpRegister").child(ruid);
                        } else if (userType.equals("Student")) {
                            databaseReference = ref1.getReference().child("Register").child(ruid);
                            Raccount.setVisibility(View.GONE);
                            Rifsc.setVisibility(View.GONE);
                        }


                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    // User id already exists
                                    Toast.makeText(Registeration.this, "User ID already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    // User id does not exist, create a new entry
                                    databaseReference.child("name").setValue(username);
                                    databaseReference.child("email").setValue(email);
                                    String ps = encrypt(password).trim();
                                    databaseReference.child("password").setValue(ps);
                                    databaseReference.child("verify").setValue("0");
                                    if (userType.equals("Expert")) {
                                        String acc = Raccount.getText().toString();
                                        String ifs = Rifsc.getText().toString();
                                        databaseReference.child("account").setValue(acc);
                                        databaseReference.child("ifsc").setValue(ifs);

                                    }
                                    Log.d("RegistrationActivity", "User registered successfully.");
                                    Toast.makeText(Registeration.this, "User registered successfully, after verification you will be able to login", Toast.LENGTH_SHORT).show();
                                    Intent inte=new Intent(Registeration.this,Login.class);
                                    startActivity(inte);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.e("RegistrationActivity", "Error getting data: " + error.getMessage());
                            }
                        });
                    }else {
                        // Password does not meet the conditions
                        Toast.makeText(Registeration.this, "Password must be at least 7 characters long and start with a capital letter.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    // EditText fields are empty
                    Toast.makeText(Registeration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    public static String encrypt(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                char base = Character.isLowerCase(chars[i]) ? 'a' : 'A';
                chars[i] = (char) (base + (chars[i] - base + 1) % 26);
            }
        }
        return new String(chars);
    }
}
