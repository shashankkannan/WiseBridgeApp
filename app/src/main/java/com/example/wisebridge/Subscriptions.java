package com.example.wisebridge;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Subscriptions extends AppCompatActivity implements contentsAdaptor.OnDownButtonClickListener {
    DatabaseReference databaseReference,databaseReference1;
    Button logout;
    public static RecyclerView postRecyclerView;
    public static contentsAdaptor contentsAdaptors;
    public static List<contents> contentsz = new ArrayList<>();
    public static String username2,type1;
    public static String userInfo,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView subs;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        logout = findViewById(R.id.logoutadmin2);
        username2 = getIntent().getStringExtra("username2");
        type1 = getIntent().getStringExtra("type1");
        subs = findViewById(R.id.subs);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Subscriptions.this, Login.class);
                startActivity(intent);
            }
        });



        postRecyclerView = findViewById(R.id.postRecyclerView21);

        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(type1.equals("Student")){
            userInfo = "usersub";
            contentsAdaptors = new contentsAdaptor(contentsz,userInfo);
            contentsAdaptors.setOnDownButtonClickListener(this);
            subs.setText("My Subscriptions");
            contentsz.clear();
            postRecyclerView.setAdapter(contentsAdaptors);
            FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
            databaseReference1 = ref1.getReference().child("Content");
            contentsz.clear();

            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        if (childSnapshot.child("subscribers").child(username2).exists()) {

                            if(childSnapshot.child("subscribers").child(username2).child("payverify").exists()){
                                if(childSnapshot.child("subscribers").child(username2).child("payverify").getValue().toString().equals("1")){
                                    String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                    String id = childSnapshot.child("owner").getValue().toString().trim();
                                    String price = childSnapshot.child("price").getValue().toString().trim();
                                    String descr = childSnapshot.child("description").getValue().toString().trim();
                                    String ver = childSnapshot.child("verify").getValue().toString();
                                    String keys = childSnapshot.child("keys").getValue().toString();
                                    if(ver.equals("1")){contents content = new contents(name, id, descr, price, keys); // Create a new Post object
                                        contentsz.add(content);}
                                }
                            }
                            else{
                                String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                String id = childSnapshot.child("owner").getValue().toString().trim();
                                String price = childSnapshot.child("price").getValue().toString().trim();
                                String descr = childSnapshot.child("description").getValue().toString().trim();
                                String ver = childSnapshot.child("verify").getValue().toString();
                                String keys = childSnapshot.child("keys").getValue().toString();
                                if(ver.equals("1")){contents content = new contents(name, id, descr, price, keys); // Create a new Post object
                                    contentsz.add(content);}

                            }



                        } else {
                        }

                    }
                    contentsAdaptors.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                }
            });
        }
        else if(type1.equals("Expert")){
            userInfo = "expert";
            contentsAdaptors = new contentsAdaptor(contentsz,userInfo);
            contentsAdaptors.setOnDownButtonClickListener(this);

            subs.setText("My Content");
            contentsz.clear();
            postRecyclerView.setAdapter(contentsAdaptors);
            FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
            databaseReference1 = ref1.getReference().child("Content");
            contentsz.clear();

            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        if (childSnapshot.child("owner").getValue().toString().equals(username2) ){

                            String name = childSnapshot.child("title").getValue(String.class); // Get the name
                            String id = childSnapshot.child("owner").getValue().toString().trim();
                            String price = childSnapshot.child("price").getValue().toString().trim();
                            String descr = childSnapshot.child("description").getValue().toString().trim();
                            String ver = childSnapshot.child("verify").getValue().toString();
                            String keys = childSnapshot.child("keys").getValue().toString();

                            if(ver.equals("1")){contents content = new contents(name, id, descr, price, keys); // Create a new Post object
                                contentsz.add(content);}


                        } else {
                        }

                    }
                    contentsAdaptors.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                }
            });
        }

        contentsAdaptors.setOnItemClickListener(new contentsAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(contents content) {
                //Toast.makeText(Subscriptions.this, "You pressed: "+content.getKeys() +"\nwith description: " + content.getDescript() + "\nwith ID: "+ content.getId(), Toast.LENGTH_SHORT).show();
               // Log.d("Tag","You pressed: "+content.getKeys() +"\nwith description: " + content.getDescript() + "\nwith ID: "+ content.getId());
                Intent intent = new Intent(Subscriptions.this, Reviewpage.class);
                intent.putExtra("Keys", content.getKeys());
                intent.putExtra("Description",content.getDescript());
                intent.putExtra("IDowner",content.getId());
                intent.putExtra("types",type1);
                intent.putExtra("users",username2);
                intent.putExtra("titl",content.getName());
                startActivity(intent);
            }
        });


    }


    @Override
    public void onDownButtonClick(int position) {
        if(type1.equals("Student")){

            contents content = contentsz.get(position);
           // Toast.makeText(Subscriptions.this, "Hello, button is pressed at Student: "+ content.getKeys(), Toast.LENGTH_SHORT).show();
            FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
            databaseReference1 = ref1.getReference().child("Content").child(content.getKeys());
            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                     url = snapshot.child("url").getValue().toString().trim();
                    //Toast.makeText(Subscriptions.this, url, Toast.LENGTH_SHORT).show();
                    if (url != null && !url.isEmpty()) {
                        // Call the method to download the file using the URL
                        downloadedFile(Subscriptions.this, url, content.getName());
                    } else {
                        Toast.makeText(Subscriptions.this, "URL not found in database or empty", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

        }
        else if(type1.equals("Expert")){
                //Toast.makeText(Subscriptions.this, "Hello, button is pressed by Expert", Toast.LENGTH_SHORT).show();
            contents content = contentsz.get(position);

            // Extract the "keys" field from the contents object
            String keys1 = content.getKeys();
            //String pr = content.getPrice();

            // Display the "keys" field in a Toast message
            //Toast.makeText(Subscriptions.this, "Keys: " + keys1, Toast.LENGTH_SHORT).show();

            Intent intent2 = new Intent(Subscriptions.this, Verifyuserpayment.class);
            intent2.putExtra("keyss",keys1 );
            //intent2.putExtra("pr",pr );
            // You can use the "keys" variable to pass the data to the next activity if needed.
            //intent2.putExtra("keys", keys1);
            startActivity(intent2);


            }
        }
    public void downloadedFile(Context context, String fileUrl, String fileName) {
        StorageReference storageRef = FirebaseStorage.getInstance("gs://wisebridge-c303a.appspot.com").getReferenceFromUrl(fileUrl);
        storageRef.getDownloadUrl().addOnSuccessListener(downloadUrl -> {
            String url = downloadUrl.toString();
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(fileName)
                    .setDescription("Downloading...")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                downloadManager.enqueue(request);
            }
        }).addOnFailureListener(exception -> {
            Toast.makeText(context, "Failed to retrieve download URL", Toast.LENGTH_SHORT).show();
        });
        Toast.makeText(Subscriptions.this,"Your file will be downloaded, please check your downloads folder",Toast.LENGTH_SHORT).show();
    }
}



