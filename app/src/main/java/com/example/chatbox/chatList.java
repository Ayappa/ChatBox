package com.example.chatbox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chatList extends AppCompatActivity {
    private final static int REQUEST_CODE_1 = 1;



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

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        final String[] chatList = {"ChatRoom1","ChatRoom2","ChatRoom3","ChatRoom4"};
        ListView listView = (ListView) findViewById(R.id.Listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, chatList);

        listView.setAdapter(adapter);
        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("chatRooms");
//
//        myRef.child("chatRoom1").s;
//        myRef.child("chatRoom2");
//        myRef.child("chatRoom3");
//        myRef.child("chatRoom4");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               // Toast.makeText(view.getContext(), i, Toast.LENGTH_SHORT).show();
                Log.d("demoo", chatList[i]);
                Intent intent=new Intent(view.getContext(),chatRoom.class);
                intent.putExtra("roomName",chatList[i]);
                startActivity(intent);
            }
        });

    }
}
