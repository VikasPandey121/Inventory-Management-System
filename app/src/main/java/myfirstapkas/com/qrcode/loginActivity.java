package myfirstapkas.com.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
public class loginActivity extends AppCompatActivity{
    FirebaseAuth mfirebaseAuth;



    private FirebaseAuth.AuthStateListener mAuthStatelisterner;


         @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);


            mfirebaseAuth =FirebaseAuth.getInstance();


            TextView signupbtn =findViewById(R.id.loginsignup);
            Button login= findViewById(R.id.loginbtn);
             final EditText loginemail= findViewById(R.id.loginemail);
             final EditText loginpassword = findViewById(R.id.loginpassword);
                     mAuthStatelisterner = new FirebaseAuth.AuthStateListener() {

                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser mfirebaseuser=  mfirebaseAuth.getCurrentUser();
                            if(mfirebaseuser != null){
                                Toast.makeText(loginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                Intent i =new Intent(loginActivity.this,Navigation.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(loginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                            }
                    }
                };


                signupbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent  intent = new Intent(loginActivity.this,signupActivity.class);
                    startActivity(intent);




                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String email = loginemail.getText().toString();
                    String password = loginpassword.getText().toString();


                        if(!email.equalsIgnoreCase(""))
                        {
                            if(!password.equalsIgnoreCase(""))
                            {
                            mfirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(loginActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Intent intohome = new Intent(loginActivity.this,Navigation.class);
                                        startActivity(intohome);
                                    }
                                }
                            });
                            }
                            else
                            {

                                Toast.makeText(loginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            });

         }

    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseAuth.addAuthStateListener(mAuthStatelisterner);
    }
}
