package myfirstapkas.com.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class DataEntry extends AppCompatActivity {
    Button btn;
    Button Enter;
    Button Generate;
    TextInputLayout itemid,itemname,itemdate,itemtime,upcomingservice,service;
    // FirebaseDatabase Database;
    // DatabaseReference myRef;
    Calendar calendar;
    String currentDate;
    String servicedatest;

    //private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatePickerDialog.OnDateSetListener mdateset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        //Database = FirebaseDatabase.getInstance();
       // final DatabaseReference myRef = Database.getReference("ID");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Inventory");
         calendar =  Calendar.getInstance();





        btn = findViewById(R.id.logout);
       // itemdate =findViewById(R.id.servicedate);
        Enter = findViewById(R.id.button);
      //  Generate=findViewById(R.id.);
        itemid = findViewById(R.id.itemid);
        itemname = findViewById(R.id.itemname);
       itemtime = findViewById(R.id.time);

        service = findViewById(R.id.servicedate);
        upcomingservice = findViewById(R.id.next);
        currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        service.getEditText().setText(currentDate);
        upcomingservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year =cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =new DatePickerDialog(DataEntry.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mdateset,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mdateset = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
            {
                Calendar c= Calendar.getInstance();
                c.set(Calendar.YEAR,i);
                c.set(Calendar.MONTH,i1);
                c.set(Calendar.DAY_OF_MONTH,i2);
                 servicedatest = DateFormat.getDateInstance().format(c.getTime());
                upcomingservice.getEditText().setText(servicedatest);
            }
        };


        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = itemid.getEditText().getText().toString();
                String name = itemname.getEditText().getText().toString();
                 String time = itemtime.getEditText().getText().toString();
               //   String date = itemdate.getEditText().getText().toString();
                  String upcoming = upcomingservice.getEditText().getText().toString();
                 String Service = service.getEditText().getText().toString();


                if(!id.equalsIgnoreCase(""))
                {
                    if(!name.equalsIgnoreCase(""))
                    {
                        if(!time.equalsIgnoreCase("")){

                        {//if(!service.equalsIgnoreCase(""))
                            myRef.child("ID").setValue(id);
                            myRef.child(id).child("ID").setValue(id);
                myRef.child(id).child("Name").setValue(name);
                myRef.child(id).child("Time").setValue(time);
                myRef.child(id).child("Service").setValue(currentDate);
                myRef.child(id).child("Upcoming").child(upcoming).setValue(upcoming);
                myRef.child(id).child("Date").setValue(Service).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        Toast.makeText(getApplicationContext(),"Data Enter Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(DataEntry.this,GeneratorActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });}
                        }else {
                            Toast.makeText(getApplicationContext(),"Error In Entering Data",Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });




      /*  btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(DataEntry.this,loginActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
      //  Generate.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View view) {

       //     }
       // });

    }


}
