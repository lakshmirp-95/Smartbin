package com.example.bin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText uid,name,phone,mail,pass,confirm;
    Button register;
    Pattern p;
    Matcher m;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        uid=(EditText)findViewById(R.id.editText3);
        name=(EditText)findViewById(R.id.editText4);
        phone=(EditText)findViewById(R.id.editText5);
        mail=(EditText)findViewById(R.id.editText6);
        pass=(EditText)findViewById(R.id.editText7);
        confirm=(EditText)findViewById(R.id.editText8);
        register=(Button)findViewById(R.id.button2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname=name.getText().toString();
                String phoneno=phone.getText().toString();
                String email=mail.getText().toString();
                String password=pass.getText().toString();
                String check=String.valueOf(isValidMobile(phoneno));
                String check1=String.valueOf(isValidMail(email));
                if(fullname.isEmpty()){
                    name.setError("field name");

                }
                else if(phoneno.isEmpty()){
                    phone.setError("enter mobile");

                }
                else if(phoneno.length()!=10){
                    phone.setError("enter 10 numbers");
                }
                else if(check.equals("false")){
                    phone.setError("invalid format");
                }
                else if(email.isEmpty()){
                    mail.setError("enter emailid");
                }
                else if(check1.equals("false")){
                    mail.setError("invalid email");
                }



                else  if(password.isEmpty()){
                    pass.setError("enter password");
                }

                else {
                    FirebaseUser user = mAuth.getCurrentUser();

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        //  Log.d(TAG, "createUserWithEmail:success");

                                        Log.e("working ", "success");
                                        Intent intent = new Intent(Register.this, MainActivity.class);
                                        startActivity(intent);

                                        //updateUI(user);
                                    } else {


                                        Log.e("working", "error");
                                        // If sign in fails, display a message to the user.
                                        //   Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        // Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                        //       Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });


                } }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
