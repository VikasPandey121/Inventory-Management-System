package myfirstapkas.com.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class DatainfoID extends dashboard{

    String siname;
    String idate;
    String itime;
    String serviceinfo;
    TextView infoi,name,date,time,service,installtime;
    Button updatese;
    EditText servdate;
    DatabaseReference myRef;
    String message;
    String noofservice;
    String firenoofservice;
    int x = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datainfo_id);


         infoi =findViewById(R.id.infoid);
           name =findViewById(R.id.infoname);
         date= findViewById(R.id.infodate);
          time =findViewById(R.id.infotime);
          service = findViewById(R.id.serviceinfo);
          updatese = findViewById(R.id.nexts);
          servdate = findViewById(R.id.infoservicedate);
          installtime = findViewById(R.id.infoinstalltime);
         Bundle bundle = getIntent().getExtras();
         message = bundle.getString("message");
        


                final FirebaseDatabase database = FirebaseDatabase.getInstance();

                        myRef = database.getReference("Inventory");

             myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       siname = dataSnapshot.child(message).child("Name").getValue().toString();
                         idate = dataSnapshot.child(message).child("Date").getValue().toString();
                             itime = dataSnapshot.child(message).child("Time").getValue().toString();
                            serviceinfo = dataSnapshot.child(message).child("Service").getValue().toString();
                            firenoofservice=dataSnapshot.child(message).child("Noofservice").getValue().toString();




                            name.setText(siname);
                            date.setText(idate);
                            time.setText(itime);
                            service.setText(serviceinfo);
                            infoi.setText(message);




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(DatainfoID.this, "Error in Entering Data", Toast.LENGTH_SHORT).show();
                    }
                });


            //}
     // });
        updatese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String update = servdate.getText().toString();
                String repairtime = installtime.getText().toString();
                x = Integer.parseInt(firenoofservice);
                x=x+1;
                noofservice = Integer.toString(x);
                if(!update.equalsIgnoreCase("")){
                    if(!repairtime.equalsIgnoreCase("")){
                        myRef.child(message).child("Upcoming").child(noofservice).setValue(update);
                        myRef.child(message).child("RepairTime").child(noofservice).setValue(repairtime);
                            myRef.child(message).child("Noofservice").setValue(x);

                        Toast.makeText(DatainfoID.this, "Data Enter Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(DatainfoID.this,dashboard.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(DatainfoID.this, "Enter Repair Time", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DatainfoID.this, "Enter ServiceDate", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
