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

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText fullName;
    EditText email;
    EditText password;
    EditText password2;
    Button signUp;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().signOut();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // Toast.makeText(getApplicationContext(), currentUser.getEmail(), Toast.LENGTH_SHORT).show();

        //updateUI(currentUser);
    }
 private boolean validation () {
        if(fullName.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(), "Enter valid user name", Toast.LENGTH_SHORT).show();
        return false;
        }
        else  if(email.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(), "Enter valid Email Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if(password.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(), "Enter valid password", Toast.LENGTH_SHORT).show();
            password2.setText("");
            password.setText("");
            return false;
        }
        else  if(!(password.getText().toString().contentEquals(password2.getText().toString()) )){
            Toast.makeText(getApplicationContext(), "Password Should match", Toast.LENGTH_SHORT).show();
            password.setText("");
            password2.setText("");

            return false;
        }
        else return  true;
 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        fullName= findViewById(R.id.fullName);
         email= findViewById(R.id.email);
         password= findViewById(R.id.password);
         password2= findViewById(R.id.password2);
         signUp=findViewById(R.id.signUp);





        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (validation()) {

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("demo", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        
                                        if(user==null) {
                                            Toast.makeText(getApplicationContext(),"no user", Toast.LENGTH_SHORT).show();

                                        }

                                        final UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(fullName.getText().toString())
                                              //  .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                                .build();

                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), profileUpdates.getDisplayName(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                        //Toast.makeText(getApplicationContext(), "SignUp successfull", Toast.LENGTH_SHORT).show();
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


    }
}
