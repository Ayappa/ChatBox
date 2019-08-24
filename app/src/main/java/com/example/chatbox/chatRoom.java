package com.example.chatbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.example.chatbox.R.layout.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
public class chatRoom extends AppCompatActivity {
 TextView chatroom;
 EditText message;
 Button send;
    String chatRoom;
   RecyclerView recycle;
    RecyclerView.Adapter rAdapter;
    RecyclerView.LayoutManager rmanager;
   // private FirebaseAuth mAuth;

    public static final String DATE_FORMAT_1 = "hh:mm a";
    ArrayList<Message> arrayMsgs=new ArrayList<Message>();
    ArrayList<String> users=new ArrayList<String>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulayout, menu);


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logOut:
                removeUser();
               ;
                return true;
            case R.id.online:
                 onlineUsers();

                return true;
            case R.id.update:
                // showHelp();
                Intent intent = new Intent(getApplicationContext(),Update.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeUser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("chatRooms").child(chatRoom).child("users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child(user.getUid()).removeValue();

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }




    private void onlineUsers() {
        String[] usr = new String[users.size()];
        usr = users.toArray(usr);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Online Users");
        builder.setItems(usr, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                dialog.dismiss();
            }
        });
        builder.show();

        usr = new String[0];
        users.clear();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("chatRooms").child(chatRoom).child("users");



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                     Log.d("demo1", String.valueOf(child.getValue()));
                      users.add(child.getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_chat_room);
        //Intent intent=new Intent();

        recycle = findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        rmanager = new LinearLayoutManager(getApplicationContext());
        recycle.setLayoutManager(rmanager);

         chatRoom= getIntent().getExtras().getString("roomName");

        Toast.makeText(getApplicationContext(), chatRoom, Toast.LENGTH_SHORT).show();

        chatroom= (TextView)findViewById(R.id.chatroom);
        message= (EditText) findViewById(R.id.message);
        send= findViewById(R.id.send);


        chatroom.setText(chatRoom);





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chatRooms");

        myRef = database.getReference("chatRooms").child(chatRoom);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child("users").child(user.getUid()).setValue(user.getDisplayName());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    child.getKey();
                    // Log.d("demo1", String.valueOf(child.getValue()));

                    Message msgs=child.getValue(Message.class);
                    arrayMsgs.add(msgs);
                    rAdapter = new Adapter(arrayMsgs);
                    recycle.setAdapter(rAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(message.getText().toString().length()==0){
            Toast.makeText(view.getContext(),"Type to send",Toast.LENGTH_LONG).show();
        }
        else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chatRooms");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
          //  dateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
            Date today = Calendar.getInstance().getTime();
            String currentDateandTime= dateFormat.format(today);

            String key = myRef.child(chatRoom).push().getKey();



//  User userobj=new User(user.getDisplayName(),true);
//        myRef.child(chatRoom).child(user.getUid()).setValue(userobj);

 Message msgobj=new Message(user.getDisplayName(),message.getText().toString(),currentDateandTime,false,true);

            myRef.child(chatRoom).child(key).setValue(msgobj);


             myRef = database.getReference("chatRooms").child(chatRoom);

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        child.getKey();
                       // Log.d("demo1", String.valueOf(child.getValue()));

                            Message msgs=child.getValue(Message.class);
                            arrayMsgs.add(msgs);
                        rAdapter = new Adapter(arrayMsgs);
                        recycle.setAdapter(rAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }
});

    }
}
