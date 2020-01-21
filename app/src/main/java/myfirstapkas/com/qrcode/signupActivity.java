package myfirstapkas.com.qrcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class signupActivity extends AppCompatActivity {
    FirebaseAuth  firebaseAuth;
    ProgressDialog progressDialog;

    DatabaseReference databaseuser;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStatelisterner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth =FirebaseAuth.getInstance();
       databaseuser = firebaseDatabase.getInstance().getReferenceFromUrl("https://qrcode-2727b.firebaseio.com/");

        TextView loginbtn = findViewById(R.id.signuplogin);
         Button signup =  findViewById(R.id.signup);
        final TextView username =findViewById(R.id.signupusername);
        final TextView signemail =findViewById(R.id.signupemail);
        final EditText signpassword =findViewById(R.id.signuppassword);



       progressDialog = new ProgressDialog(this);


        mAuthStatelisterner = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseuser=  firebaseAuth.getCurrentUser();
                if(mfirebaseuser != null){
                    Intent i =new Intent(signupActivity.this,Navigation.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(signupActivity.this, "Please SignUP", Toast.LENGTH_SHORT).show();
                }
            }
        };


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signupActivity.this,loginActivity.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              String name = username.getText().toString();
               String email = signemail.getText().toString();
           String password = signpassword.getText().toString();


                if(!name.equalsIgnoreCase(""))
                {
                    if(!email.equalsIgnoreCase(""))
                    {
                        if(!password.equalsIgnoreCase(""))
                        {
                            registerUser(name,email,password);

                        }
                        else
                        {

                            Toast.makeText(signupActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });


    }
    public void registerUser(final String name, final String email,String password){

        progressDialog.setMessage("Please wait...");
       progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            progressDialog.dismiss();
                            databaseuser.child("USER").setValue(name);


                            Toast.makeText(signupActivity.this, "User is Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signupActivity.this,loginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(signupActivity.this, "Error registering user", Toast.LENGTH_SHORT).show();
                        }
                }
            });
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStatelisterner);
    }



}

