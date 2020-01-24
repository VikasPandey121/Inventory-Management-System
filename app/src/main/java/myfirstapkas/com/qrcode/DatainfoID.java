package myfirstapkas.com.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatainfoID extends Navigation {

    String siname;
    String idate;
    String itime;
    String serviceinfo;
    EditText infoi;
    TextView name;
   TextView date;
    TextView time;
    TextView service;
    Button updatese;
    EditText servdate;
    DatabaseReference myRef;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datainfo_id);

          infoi =findViewById(R.id.dataid);
           name =findViewById(R.id.infoname);
         date= findViewById(R.id.infodate);
          time =findViewById(R.id.infotime);
          service = findViewById(R.id.serviceinfo);
          updatese = findViewById(R.id.nexts);
          servdate = findViewById(R.id.servicedate);
          //Need to delete after try
         Bundle bundle = getIntent().getExtras();
         message = bundle.getString("message");
        

        final Button  info =findViewById(R.id.enter);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();

               // DatabaseReference
                        myRef = database.getReference("Inventory");
              // servicehj = scanid;

                //remove below line


             myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       siname = dataSnapshot.child(message).child("Name").getValue().toString();
                         idate = dataSnapshot.child(message).child("Date").getValue().toString();
                             itime = dataSnapshot.child(message).child("Time").getValue().toString();
                            serviceinfo = dataSnapshot.child(message).child("Service").getValue().toString();


                            name.setText("NAME  "+siname);
                            date.setText("DATE  "+idate);
                            time.setText("TIME  "+itime);
                            service.setText("SERVICE "+ serviceinfo);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(DatainfoID.this, "Error in Entering Data", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        updatese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = servdate.getText().toString();
                myRef.child(message).child("Upcoming").child(update).setValue(update);
            }
        });


    }
}
