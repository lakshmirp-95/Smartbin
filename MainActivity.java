package com.example.bin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView t1;
    EditText email,password;
    Button Login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        Login=(Button)findViewById(R.id.button);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailid=email.getText().toString();
                String pass=password.getText().toString();



                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(mailid, pass)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                   // Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef = database.getReference("user");

// myRef.setValue("Hello, World!");
                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // This method is called once with the initial value and again
                                            // whenever data at this location is updated.
                                            String value = dataSnapshot.getValue(String.class);
                                            myRef.setValue("lakshmi");

                                            Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();

                                            Log.d("buggie", "Value is: " + value);
                                            Intent intent=new Intent(MainActivity.this,Home.class);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                            Log.d("buggie", "Failed to read value.", error.toException());
                                        }
                                    });
                                   // updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d("works", "signInWithEmail:failure", task.getException());
                                  //  Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                          //  Toast.LENGTH_SHORT).show();
                                  //  updateUI(null);
                                }

                                // ...

                            }
                        });




            }
        });
        t1=(TextView)findViewById(R.id.textView4);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);

            }


        });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

}
