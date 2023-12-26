package com.example.wisebridge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Reviewpage extends AppCompatActivity {
    public static String RKeys,RDesc,Rido,types,users,titl;
    public static String revs,rats;
    public static double sums=0.0;
    public static double ix = 0;
    TextView Cnamee,Cdesc,Cowner,Cavgrate,revshow;
    DatabaseReference databaseReference,databaseReference1;
    EditText revdes,revrate;
    Button revbut, logoutadmin2;
    public static RecyclerView ReviewsRecyclerView;
    public static List<Reviews> Reviewsz = new ArrayList<>();
    public static ReviewsAdaptor reviewsAdaptors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewpage);
        RKeys = getIntent().getStringExtra("Keys");
        RDesc = getIntent().getStringExtra("Description");
        Rido = getIntent().getStringExtra("IDowner");
        types = getIntent().getStringExtra("types");
        users = getIntent().getStringExtra("users");
        titl = getIntent().getStringExtra("titl");
        revbut = findViewById(R.id.revbut);
        revdes = findViewById(R.id.revdes);
        revrate = findViewById(R.id.revrate);
        revshow = findViewById(R.id.revshow);
        logoutadmin2 = findViewById(R.id.logoutadmin2);
        logoutadmin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reviewpage.this, Login.class);
                startActivity(intent);
            }
        });
        //Toast.makeText(Reviewpage.this, types, Toast.LENGTH_SHORT).show();
        Cavgrate = findViewById(R.id.Cavgrate);
        ReviewsRecyclerView = findViewById(R.id.ReviewsRecyclerView21);
        reviewsAdaptors = new ReviewsAdaptor(Reviewsz);
        ReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
        databaseReference = ref1.getReference().child("Content").child(RKeys).child("reviews");
        ReviewsRecyclerView.setAdapter(reviewsAdaptors);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                Reviewsz.clear();
                reviewsAdaptors.notifyDataSetChanged();
                sums=0.0;
                ix=0.0;
                if(dataSnapshot.exists()){
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                        if(childSnapshot.exists()){

                            String owner = childSnapshot.getKey(); // Get the child root value (ID)
                            String rev = childSnapshot.child("rev").getValue(String.class); // Get the name
                            String rate = childSnapshot.child("rate").getValue().toString();
                            Double y = Double.parseDouble(rate);
                            ix+=1;
                            sums+=sums+y;
                            Reviews review = new Reviews(owner, rev, rate); // Create a new Post object
                            Reviewsz.add(review);

                            //Toast.makeText(Reviewpage.this, rev, Toast.LENGTH_SHORT).show();
                        }
                        Double ans = (sums/ix);
                        Cavgrate.setText(ans.toString().trim());
                        reviewsAdaptors.notifyDataSetChanged();




                    }
                }


                else{
                    //Toast.makeText(Reviewpage.this, "No reviews yet", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) { }
        });


        //Toast.makeText(Reviewpage.this, "You pressed: "+RKeys +"\nwith description: " + RDesc + "\nwith ID: "+ Rido, Toast.LENGTH_SHORT).show();
        //Log.d("Tag","You pressed: "+RKeys +"\nwith description: " + RDesc + "\nwith ID: "+ Rido);
        Cnamee =findViewById(R.id.Cnamee);
        Cdesc= findViewById(R.id.Cdesc);
        Cowner=findViewById(R.id.Cowner);

        Cnamee.setText(titl);
        Cdesc.setText(RDesc);
        Cowner.setText(Rido);

        if(types.equals("Expert")){
            revrate.setVisibility(View.GONE);
            revdes.setVisibility(View.GONE);
            revbut.setVisibility(View.GONE);

            databaseReference = ref1.getReference().child("Content").child(RKeys).child("reviews");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    sums=0.0;
                    ix=0.0;
                    if(snapshot.exists()){
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                            if(childSnapshot.exists()){

                                String owner = childSnapshot.getKey(); // Get the child root value (ID)
                                String rev = childSnapshot.child("rev").getValue(String.class); // Get the name
                                String rate = childSnapshot.child("rate").getValue().toString();
                                Double y = Double.parseDouble(rate);

                                ix+=1;
                                sums+=y;
                                //Toast.makeText(Reviewpage.this, sums.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("TAG",String.valueOf(sums));


                                //Toast.makeText(Reviewpage.this, rev, Toast.LENGTH_SHORT).show();
                            }
                            Double ans = (sums/ix);
                            Cavgrate.setText(ans.toString().trim());




                        }
                    }


                    else{
                        //Toast.makeText(Reviewpage.this, "No reviews yet", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
        else if(types.equals("Student")){

                FirebaseDatabase ref2 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                databaseReference1 = ref2.getReference().child("Content").child(RKeys).child("reviews").child(users);

                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            //Toast.makeText((getApplicationContext()), "Already Reviewed", Toast.LENGTH_SHORT).show();
                            revrate.setVisibility(View.GONE);
                            revdes.setVisibility(View.GONE);
                            revbut.setVisibility(View.GONE);
                            revshow.setVisibility(View.VISIBLE);
                            revshow.setText("Already Reviewed");
                            databaseReference = ref1.getReference().child("Content").child(RKeys).child("reviews");
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    sums=0.0;
                                    ix=0.0;
                                    if(snapshot.exists()){
                                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                                            if(childSnapshot.exists()){

                                                String owner = childSnapshot.getKey(); // Get the child root value (ID)
                                                String rev = childSnapshot.child("rev").getValue(String.class); // Get the name
                                                String rate = childSnapshot.child("rate").getValue().toString();
                                                Double y = Double.parseDouble(rate);

                                                ix+=1;
                                                sums+=y;
                                                //Toast.makeText(Reviewpage.this, sums.toString(), Toast.LENGTH_SHORT).show();
                                                Log.d("TAG",String.valueOf(sums));


                                                //Toast.makeText(Reviewpage.this, rev, Toast.LENGTH_SHORT).show();
                                            }
                                            Double ans = (sums/ix);
                                            Cavgrate.setText(ans.toString().trim());




                                        }
                                    }


                                    else{
                                        //Toast.makeText(Reviewpage.this, "No reviews yet", Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });

                        }
                        else{
                           // Toast.makeText((getApplicationContext()), "It is not there", Toast.LENGTH_SHORT).show();
                            revbut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    revs = revdes.getText().toString().trim();
                                    rats = revrate.getText().toString().trim();

                                    if(revs.isEmpty() || rats.isEmpty()){
                                        Toast.makeText(Reviewpage.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        if(Double.parseDouble(rats)<=5){
                                            databaseReference1.child("rate").setValue(rats);
                                            databaseReference1.child("rev").setValue(revs);
                                            revrate.setVisibility(View.GONE);
                                            revdes.setVisibility(View.GONE);
                                            revbut.setVisibility(View.GONE);
                                            revshow.setVisibility(View.VISIBLE);
                                            revshow.setText("Reviewed Successfully");

                                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {


                                                @Override
                                                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                                    Reviewsz.clear();
                                                    reviewsAdaptors.notifyDataSetChanged();
                                                    sums=0.0;
                                                    ix=0.0;
                                                    if(dataSnapshot.exists()){
                                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                                            if(childSnapshot.exists()){

                                                                String owner = childSnapshot.getKey(); // Get the child root value (ID)
                                                                String rev = childSnapshot.child("rev").getValue(String.class); // Get the name
                                                                String rate = childSnapshot.child("rate").getValue().toString();
                                                                Double y = Double.parseDouble(rate);

                                                                ix+=1;
                                                                sums+=y;
                                                                //Toast.makeText(Reviewpage.this, sums.toString(), Toast.LENGTH_SHORT).show();
                                                               Log.d("TAG",String.valueOf(sums));
                                                                Reviews review = new Reviews(owner, rev, rate); // Create a new Post object
                                                                Reviewsz.add(review);

                                                                //Toast.makeText(Reviewpage.this, rev, Toast.LENGTH_SHORT).show();
                                                            }
                                                            Double ans = (sums/ix);
                                                            Cavgrate.setText(ans.toString().trim());
                                                            reviewsAdaptors.notifyDataSetChanged();




                                                        }
                                                    }


                                                    else{
                                                        //Toast.makeText(Reviewpage.this, "No reviews yet", Toast.LENGTH_SHORT).show();
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NotNull DatabaseError databaseError) { }
                                            });
                                        }
                                        else{
                                            Toast.makeText(Reviewpage.this, "Enter your rating (1-5)", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            });



                        }
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) { }
                });


            }
        }

    }

