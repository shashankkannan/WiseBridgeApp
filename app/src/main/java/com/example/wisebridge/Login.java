package com.example.wisebridge;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button loginbtn;
    EditText username, password;
    DatabaseReference databaseReference;
    TextView Registerpage,forgotpass;
    Spinner userTypeSpinner;
    @Override
    public void onBackPressed() {
        finishAffinity(); // Close all activities in the task, including the login page
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //FirebaseApp.initializeApp(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
        loginbtn = (Button)findViewById(R.id.Registerbtn);
        username = (EditText)findViewById(R.id.Rusername);
        password = (EditText)findViewById(R.id.Rpassword);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        forgotpass = findViewById(R.id.forgotpass);

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, forgotpassword.class);
                startActivity(intent);
            }
        });


        Registerpage = findViewById(R.id.signup);
        Registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Registration activity
                Intent intent = new Intent(Login.this, Registeration.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                //Toast.makeText(Login.this,ps,Toast.LENGTH_SHORT).show();
                String userType = userTypeSpinner.getSelectedItem().toString();

                if(!uname.isEmpty() && !pass.isEmpty() && !userType.isEmpty()){
                    if(isValidEmail(uname)){
                        Toast.makeText(Login.this, "Enter your student ID as username", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(userType.equals("Admin")){
                            databaseReference = ref1.getReference().child("Admin").child(uname);
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        String pascheck = snapshot.child("password").getValue().toString().trim();
                                        //String ps = decrypt(pascheck).trim();
                                        if(pascheck.equals(pass)){
                                            //Toast.makeText(Login.this, "You can login", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Login.this, Adminhome.class);
                                            intent.putExtra("username", uname);
                                            intent.putExtra("type",userType);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(Login.this, "The password you entered is incorrect", Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(Login.this, "The admin does not exist, please try with a valid admin id", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Log.e("Login", "Error getting data: " + error.getMessage());
                                }
                            });

                        }
                        else if(userType.equals("Expert")){
                            databaseReference = ref1.getReference().child("ExpRegister").child(uname);
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        if(snapshot.child("verify").getValue().toString().equals("0")){
                                            Toast.makeText(Login.this, "Your account is not yet verified", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(snapshot.child("verify").getValue().toString().equals("1")){
                                            String pascheck = snapshot.child("password").getValue().toString().trim();
                                            String ps = decrypt(pascheck).trim();
                                            if(pass.equals(ps)){
                                                // Toast.makeText(Login.this, "You can login", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Login.this, profile.class);
                                                intent.putExtra("username", uname);
                                                intent.putExtra("type",userType);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(Login.this, "The password you entered is incorrect", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }else{
                                        Toast.makeText(Login.this, "The expert user id does not exist, please try with a valid user id", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Log.e("Login", "Error getting data: " + error.getMessage());
                                }
                            });

                        }else if(userType.equals("Student")){
                            databaseReference = ref1.getReference().child("Register").child(uname);
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        if(snapshot.child("verify").getValue().toString().equals("0")){
                                            Toast.makeText(Login.this, "Your account is not yet verified", Toast.LENGTH_SHORT).show();
                                        }
                                        else if(snapshot.child("verify").getValue().toString().equals("1")){
                                            String pascheck = snapshot.child("password").getValue().toString().trim();
                                            String ps = decrypt(pascheck).trim();
                                            // Toast.makeText(Login.this, ps, Toast.LENGTH_SHORT).show();
                                            if(pass.equals(ps)){
                                                //  Toast.makeText(Login.this, "You can login", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Login.this, profile.class);
                                                intent.putExtra("username", uname);
                                                intent.putExtra("type",userType);
                                                startActivity(intent);
                                            }
                                            else{
                                                Toast.makeText(Login.this, "The password you entered is incorrect", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }else{
                                        Toast.makeText(Login.this, "The student user id does not exist, please try with a valid user id", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Log.e("Login", "Error getting data: " + error.getMessage());
                                }
                            });
                        }
                    }


                } else {
                    // EditText fields are empty
                    Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }
    // Decrypt the string

    public static String decrypt(String encryptedInput) {
        char[] chars = encryptedInput.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                char base = Character.isLowerCase(chars[i]) ? 'a' : 'A';
                chars[i] = (char) (base + (chars[i] - base - 1 + 26) % 26);
            }
        }
        return new String(chars);
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
