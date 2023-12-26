package com.example.wisebridge;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;

public class Adminhome extends AppCompatActivity implements contentsAdaptor.OnDownButtonClickListener{




    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Adminhome.this,Login.class);
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
    public static String url;
    DatabaseReference databaseReference,databaseReference1;
    Button logout;
    Spinner userTypeSpinnerad;
    public static RecyclerView postRecyclerView;
    public static PostAdapter postAdapter;
    public static contentsAdaptor contentsAdaptors;
    public static List<Post> posts = new ArrayList<>();
    public static List<contents> contentsz = new ArrayList<>();
    public static String userin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        logout = findViewById(R.id.logoutadmin);
        userTypeSpinnerad = findViewById(R.id.userTypeSpinnerad);
        String userInfo = "admin";


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Adminhome.this, Login.class);
                startActivity(intent);
            }
        });


        postRecyclerView = findViewById(R.id.postRecyclerView);
        postAdapter = new PostAdapter(posts,"Student");
        contentsAdaptors = new contentsAdaptor(contentsz,userInfo);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentsAdaptors.setOnDownButtonClickListener(this);


        userTypeSpinnerad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posts.clear();
                contentsz.clear();
                postAdapter.notifyDataSetChanged();
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(Adminhome.this, "Selected item: " + selectedItem, Toast.LENGTH_SHORT).show();

                if(selectedItem.equals("Verify Student")){
                    userin="Student";
                    postAdapter = new PostAdapter(posts,userin);
                    postRecyclerView.setAdapter(postAdapter);

                    postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Post post) {
                            Toast.makeText(Adminhome.this, "Verified: " + post.getName() + " : "+ post.getId(), Toast.LENGTH_SHORT).show();
                            databaseReference.child(post.getId()).child("verify").setValue("1");

                            // Refresh the page (RecyclerView) by clearing and re-fetching the data
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                    posts.clear(); // Clear the list before re-fetching the data

                                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                        String name = childSnapshot.child("name").getValue(String.class);
                                        String id = childSnapshot.getKey();
                                        String ver = childSnapshot.child("verify").getValue().toString();

                                        if (ver.equals("0")) {
                                            Post updatedPost = new Post(name, id);
                                            posts.add(updatedPost);
                                        }
                                    }

                                    postAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NotNull DatabaseError databaseError) {
                                    // Handle the error if any
                                }
                            });
                        }
                    });


                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference = ref1.getReference().child("Register");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                String name = childSnapshot.child("name").getValue(String.class); // Get the name
                                String id = childSnapshot.getKey(); // Get the child root value (ID)
                                String ver = childSnapshot.child("verify").getValue().toString();
                                if(ver.equals("0")){
                                    Post post = new Post(name, id); // Create a new Post object

                                    posts.add(post);
                                }

                            }
                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) { }
                    });
                }
                else if(selectedItem.equals("Verify Expert")){
                    userin="Expert";
                    postAdapter = new PostAdapter(posts,userin);
                    postRecyclerView.setAdapter(postAdapter);


                    postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Post post) {
                            Toast.makeText(Adminhome.this, "Verified: " + post.getName() + " : "+ post.getId(), Toast.LENGTH_SHORT).show();
                            databaseReference.child(post.getId()).child("verify").setValue("1");

                            // Refresh the page (RecyclerView) by clearing and re-fetching the data
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                                    posts.clear(); // Clear the list before re-fetching the data

                                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                        String name = childSnapshot.child("name").getValue(String.class);
                                        String id = childSnapshot.getKey();
                                        String ver = childSnapshot.child("verify").getValue().toString();

                                        if (ver.equals("0")) {
                                            Post updatedPost = new Post(name, id);
                                            posts.add(updatedPost);
                                        }
                                    }

                                    postAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NotNull DatabaseError databaseError) {
                                    // Handle the error if any
                                }
                            });
                        }
                    });

                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference = ref1.getReference().child("ExpRegister");

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                String name = childSnapshot.child("name").getValue(String.class); // Get the name
                                String id = childSnapshot.getKey(); // Get the child root value (ID)
                                String ver = childSnapshot.child("verify").getValue().toString();
                                if(ver.equals("0")){
                                    Post post = new Post(name, id); // Create a new Post object

                                    posts.add(post);
                                }

                            }
                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) { }
                    });
                }
                else if(selectedItem.equals("Verify Content")){
                    //Button subscribeButton = findViewById(R.id.Subscribe);
                    //subscribeButton.setText("Verify");
                    postRecyclerView.setAdapter(contentsAdaptors);
                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference1 = ref1.getReference().child("Content");

                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                String name = childSnapshot.child("title").getValue(String.class); // Get the name
                                String id = childSnapshot.child("owner").getValue().toString().trim();
                                String price = childSnapshot.child("price").getValue().toString().trim();
                                String descr = childSnapshot.child("description").getValue().toString().trim();
                                String ver = childSnapshot.child("verify").getValue().toString();
                                String keys = childSnapshot.child("keys").getValue().toString();
                                if(ver.equals("0")){
                                    contents content = new contents(name, id,descr,price,keys); // Create a new Post object

                                    contentsz.add(content);
                                }


                            }
                            contentsAdaptors.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) { }


                    }
                    );



                }
                else if(selectedItem.equals("All Users")){
                    userin="All Users";
                    postAdapter = new PostAdapter(posts,userin);
                    postRecyclerView.setAdapter(postAdapter);
                    posts.clear();
                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference1 = ref1.getReference().child("Register");

                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                String name = childSnapshot.child("name").getValue(String.class); // Get the name
                                String id = childSnapshot.getKey(); // Get the child root value (ID)
                                String ver = childSnapshot.child("verify").getValue().toString();
                                if(ver.equals("1")){
                                    Post post = new Post(name, id); // Create a new Post object

                                    posts.add(post);
                                }

                            }
                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) { }
                    });
                }
                else if(selectedItem.equals("All Experts")){
                    userin="All Users";
                    postAdapter = new PostAdapter(posts,userin);
                    postRecyclerView.setAdapter(postAdapter);
                    posts.clear();
                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference1 = ref1.getReference().child("ExpRegister");


                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                String name = childSnapshot.child("name").getValue(String.class); // Get the name
                                String id = childSnapshot.getKey(); // Get the child root value (ID)
                                String ver = childSnapshot.child("verify").getValue().toString();
                                if(ver.equals("1")){
                                    Post post = new Post(name, id); // Create a new Post object

                                    posts.add(post);
                                }

                            }
                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) { }
                    });
                }

                else if(selectedItem.equals("All Content")){
                    userin="All Users";
                    postAdapter = new PostAdapter(posts,userin);
                    postRecyclerView.setAdapter(postAdapter);
                    posts.clear();
                    FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                    databaseReference1 = ref1.getReference().child("Content");

                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                                String name = childSnapshot.child("title").getValue().toString(); // Get the name
                                String id = childSnapshot.child("owner").getValue().toString().trim(); // Get the child root value (ID)
                                String ver = childSnapshot.child("verify").getValue().toString();
                                if(ver.equals("1")){
                                    Post post = new Post(name, id); // Create a new Post object

                                    posts.add(post);
                                }

                            }
                            postAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NotNull DatabaseError databaseError) { }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }


        });



        contentsAdaptors.setOnItemClickListener(new contentsAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(contents content) {
                String keys = content.getKeys();
               // Toast.makeText(getApplicationContext(), keys, Toast.LENGTH_SHORT).show();
                FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
                databaseReference1 = ref1.getReference().child("Content");
                databaseReference1.child(keys).child("verify").setValue("1");


                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        contentsz.clear();
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                            String name = childSnapshot.child("title").getValue(String.class); // Get the name
                            String id = childSnapshot.child("owner").getValue().toString().trim();
                            String price = childSnapshot.child("price").getValue().toString().trim();
                            String descr = childSnapshot.child("description").getValue().toString().trim();
                            String ver = childSnapshot.child("verify").getValue().toString();
                            String keys = childSnapshot.child("keys").getValue().toString();
                            if(ver.equals("0")){
                                contents content = new contents(name, id,descr,price,keys); // Create a new Post object

                                contentsz.add(content);
                            }

                        }
                        contentsAdaptors.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) { }
                });
            }
        });















        // Get the list of posts from your data source
        //posts= getPostsFromDataSource();
        //getPostsFromDataSource();
        // Add the fetched posts to the existing list
        //posts.addAll(fetchedPosts);
        //Toast.makeText(Adminhome.this, posts.getName(), Toast.LENGTH_SHORT).show();

        // Notify the adapter about the data change
        //postAdapter.notifyDataSetChanged();

        // Add some example posts
        //Post post1 = new Post("Shashank", "110122650");
        //Post post2 = new Post("Kannan", "11012345");
        //posts.add(post1);
        //posts.add(post2);
        //postAdapter.notifyDataSetChanged();





    }
    private void getPostsFromDataSource() {



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
        Toast.makeText(Adminhome.this,"Your file will be downloaded, please check your downloads folder",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownButtonClick(int position) {
        // Implement the logic to handle the download button click here.
        // You can get the corresponding 'contents' object from the contentsz list using the position,
        // and then access its keys and other properties as needed.
        contents content = contentsz.get(position);
        //String keys = content.getKeys();
        //Toast.makeText(getApplicationContext(), "Keys: " + keys, Toast.LENGTH_SHORT).show();
        FirebaseDatabase ref1 = FirebaseDatabase.getInstance("https://wisebridge-c303a-default-rtdb.firebaseio.com/");
        databaseReference1 = ref1.getReference().child("Content").child(content.getKeys());
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                url = snapshot.child("url").getValue().toString().trim();
                //Toast.makeText(Subscriptions.this, url, Toast.LENGTH_SHORT).show();
                if (url != null && !url.isEmpty()) {
                    // Call the method to download the file using the URL
                    downloadedFile(Adminhome.this, url, content.getName());
                } else {
                    Toast.makeText(Adminhome.this, "URL not found in database or empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        // Perform any other actions you need for handling the download button click.
    }
}
