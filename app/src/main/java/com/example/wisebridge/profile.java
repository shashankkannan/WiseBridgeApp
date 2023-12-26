package com.example.wisebridge;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(profile.this,Login.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog and continue with the current flow
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static DatabaseReference userRef;
    Button signOutButton,Homebtn,hm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String username = getIntent().getStringExtra("username");
        String usertype = getIntent().getStringExtra("type");

        TextView un,uid,umail;
        un = findViewById(R.id.un);
        uid = findViewById(R.id.uid);
        umail = findViewById(R.id.umail);
        signOutButton = findViewById(R.id.signOutButton);
        Homebtn = findViewById(R.id.Homebtn);
        hm = findViewById(R.id.Hm);

        hm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(usertype.equals("Student")){
                    Intent intent = new Intent(profile.this,Subscriptions.class);
                    intent.putExtra("username2", username);
                    intent.putExtra("type1",usertype);
                    startActivity(intent);
                }
                else if(usertype.equals("Expert")){
                    Intent intent = new Intent(profile.this,Subscriptions.class);
                    intent.putExtra("username2", username);
                    intent.putExtra("type1",usertype);
                    startActivity(intent);
                }

            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profile.this,Login.class);
                startActivity(intent);
            }
        });
        if(usertype.equals("Expert")){
            Homebtn.setText("Upload Files");
            hm.setText("My Content");
        }
        else if(usertype.equals("Student")){
            Homebtn.setText("Home");
            hm.setText("Subscriptions");
        }

        Homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usertype.equals("Student")){
                    Intent intent = new Intent(profile.this,Homeuser.class);
                    intent.putExtra("username2", username);
                    startActivity(intent);
                }
                else if(usertype.equals("Expert")){
                    Intent intent = new Intent(profile.this,Homeexpert.class);
                    intent.putExtra("username3", username);
                    //intent.putExtra("type",usertype);
                    startActivity(intent);
                }
            }
        });





        // Use the username to access corresponding children in the Firebase database
        if(usertype.equals("Student")){
             userRef = FirebaseDatabase.getInstance().getReference().child("Register").child(username);
        }
        else if(usertype.equals("Expert")){
             userRef = FirebaseDatabase.getInstance().getReference().child("ExpRegister").child(username);
        }

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Access the children and display the data as needed
                    String name = dataSnapshot.child("name").getValue(String.class).trim();
                    String email = dataSnapshot.child("email").getValue(String.class).trim();

                    // Use the retrieved data as desired
                    un.setText(name);
                    uid.setText(username);
                    umail.setText(email);
                    //Toast.makeText(profile.this, username, Toast.LENGTH_SHORT).show();

                } else {
                    // Handle the case when the user does not exist in the database
                    Log.d("Profile", "User does not exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Profile", "Error getting data: " + databaseError.getMessage());
            }
        });
    }
}
