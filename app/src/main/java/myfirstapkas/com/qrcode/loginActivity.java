package myfirstapkas.com.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
public class loginActivity extends AppCompatActivity{
    FirebaseAuth mfirebaseAuth;

    ImageView image;
    TextView logotext;
    Button signupbtn,login;
    TextInputLayout loginemail,loginpassword;

    private FirebaseAuth.AuthStateListener mAuthStatelisterner;
    public void hidekeyboard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }


    @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);


            mfirebaseAuth =FirebaseAuth.getInstance();
             image= findViewById(R.id.logoimage);
             logotext= findViewById(R.id.logoname);

            signupbtn =findViewById(R.id.loginsignup);
            login= findViewById(R.id.loginbtn);
             loginemail= findViewById(R.id.loginemail);
            loginpassword = findViewById(R.id.loginpassword);
                     mAuthStatelisterner = new FirebaseAuth.AuthStateListener() {

                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser mfirebaseuser=  mfirebaseAuth.getCurrentUser();
                            if(mfirebaseuser != null){
                                Toast.makeText(loginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                Intent i =new Intent(loginActivity.this,dashboard.class);
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
                    Pair[] pairs = new Pair[6];
                    pairs[0] =new Pair<View,String>(image,"logo_image");
                    pairs[1] =new Pair<View,String>(logotext,"logo_text");
                    pairs[2] =new Pair<View,String>(loginemail,"username_tran");
                    pairs[3] =new Pair<View,String>(loginpassword,"password_tran");
                    pairs[4] =new Pair<View,String>(login,"button_tran");
                    pairs[5] =new Pair<View,String>(signupbtn,"login_signup_tran");

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(loginActivity.this,pairs);
                        startActivity(intent,options.toBundle());

                    }




                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String email = loginemail.getEditText().getText().toString();
                    String password = loginpassword.getEditText().getText().toString();


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
                                        Intent intohome = new Intent(loginActivity.this,dashboard.class);
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
