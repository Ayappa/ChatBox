package com.example.chatbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    Button newUser;
    Button signin;
    Button forgotPassword;

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "SignIn again", Toast.LENGTH_SHORT).show();
 email.setText("");
 password.setText("");

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

     //  FirebaseAuth.getInstance().signOut();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            Toast.makeText(getApplicationContext(), "Welcome back "+ currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
            if(currentUser.getEmail()!= null){
                Intent intent = new Intent(getApplicationContext(),chatList.class);
                startActivity(intent);
            }
        }

        //updateUI(currentUser);
    }


    private boolean validation () {

         if(email.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(), "Enter valid Email Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if(password.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(), "Enter valid password", Toast.LENGTH_SHORT).show();
            password.setText("");
            return false;
        }

        else return  true;
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
         newUser= findViewById(R.id.newUser);
        signin=findViewById(R.id.signin);
        forgotPassword=findViewById(R.id.forgotPassword);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SignUp.class);
                startActivity(intent);
            }
        });



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (validation()) {

                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("demo", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        Toast.makeText(getApplicationContext(), "SignIn successfull", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent((Activity) view.getContext(),chatList.class);
                                        startActivity(intent);
                                        //  updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d("demo", "createUserWithEmail:failure", task.getException());
                                        // Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                        // Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });


                }
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent((Activity) view.getContext(),passwordReset.class);
                startActivity(intent);
            }

        });


    }
}
