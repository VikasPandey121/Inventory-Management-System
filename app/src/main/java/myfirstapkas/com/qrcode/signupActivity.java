package myfirstapkas.com.qrcode;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.BatchUpdateException;


public class signupActivity extends AppCompatActivity {
    FirebaseAuth  firebaseAuth;
    ProgressDialog progressDialog;
    ImageView image;
    TextView logotext,text;
    DatabaseReference databaseuser;

    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthStatelisterner;

    public void hidekeyboard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth =FirebaseAuth.getInstance();
       databaseuser = firebaseDatabase.getInstance().getReferenceFromUrl("https://qrcode-2727b.firebaseio.com/");

        final Button loginbtn = findViewById(R.id.signuplogin);
         Button signupbtn =  findViewById(R.id.signup);
        final TextInputLayout username =findViewById(R.id.signupusername);
        final TextInputLayout signemail =findViewById(R.id.signupemail);
        final TextInputLayout signpassword =findViewById(R.id.signuppassword);
        image= findViewById(R.id.logoimage1);
        logotext= findViewById(R.id.logoname1);
        text= findViewById(R.id.textlogo);



       progressDialog = new ProgressDialog(this);


        mAuthStatelisterner = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseuser=  firebaseAuth.getCurrentUser();
                if(mfirebaseuser != null){
                    Intent i =new Intent(signupActivity.this,dashboard.class);
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
                finish();

            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              String name = username.getEditText().getText().toString();
               String email = signemail.getEditText().getText().toString();
           String password = signpassword.getEditText().getText().toString();


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
                            finish();
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

