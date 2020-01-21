package myfirstapkas.com.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class DataEntry extends AppCompatActivity {
    Button btn;
    Button Enter;
    Button Generate;
    EditText itemid;
    EditText itemname;
    EditText itemdate;
    EditText itemtime;
   // FirebaseDatabase Database;
 // DatabaseReference myRef;


    //private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        //Database = FirebaseDatabase.getInstance();
       // final DatabaseReference myRef = Database.getReference("ID");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Inventory");





        btn = findViewById(R.id.logout);
        itemdate =findViewById(R.id.date);
        Enter = findViewById(R.id.button);
        Generate=findViewById(R.id.generate);
        itemid = findViewById(R.id.itemid);
        itemname = findViewById(R.id.name);
        itemtime = findViewById(R.id.time);





        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = itemid.getText().toString();
                String name = itemname.getText().toString();
                 String time = itemtime.getText().toString();
                  String date = itemdate.getText().toString();

                if(!id.equalsIgnoreCase(""))
                {
                    if(!name.equalsIgnoreCase(""))
                    {
                        if(!time.equalsIgnoreCase("")){

                        if(!date.equalsIgnoreCase("")){
                            myRef.child("ID").setValue(id);
                            myRef.child(id).child("ID").setValue(id);
                myRef.child(id).child("Name").setValue(name);
                myRef.child(id).child("Time").setValue(time);
                myRef.child(id).child("Date").setValue(date).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Data Enter Successfully",Toast.LENGTH_SHORT).show();
                    }
                });}else {
                            Toast.makeText(getApplicationContext(),"Error In Entering Data",Toast.LENGTH_SHORT).show();
                        }
                        }
                    }

                }




            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(DataEntry.this,loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DataEntry.this,GeneratorActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
