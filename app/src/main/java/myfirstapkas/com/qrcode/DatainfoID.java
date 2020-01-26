package myfirstapkas.com.qrcode;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    String siname,idate,itime,serviceinfo,message,noofservice,firenoofservice,update ,repairtime,stringpredate,stringworkingtime;
    String stringupptime,stringinstallby;
    Double mtbf;
    Long mttr;
    Long uptime;
    double predate;
    Long postdate;
    Long workingtime;
    TextView infoi,name,date,time,service,installtime,efficiency,installby;
    Button updatese;
    EditText servdate;
    DatabaseReference myRef;

    //Might I need to remove this one
    String stringOfmtbf;
    public void hidekeyboard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }


    int x = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datainfo_id);

            installby =findViewById(R.id.installby);
           infoi =findViewById(R.id.infoid);
           name =findViewById(R.id.infoname);
           date= findViewById(R.id.infodate);
           time =findViewById(R.id.infotime);
           service = findViewById(R.id.infotime);
           updatese = findViewById(R.id.nexts);
           efficiency =findViewById(R.id.efficiency);
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
                          idate = dataSnapshot.child(message).child("DateofInstallation").getValue().toString();
                          itime = dataSnapshot.child(message).child("Time").getValue().toString();
                       //   serviceinfo = dataSnapshot.child(message).child("Service").getValue().toString();
                          firenoofservice=dataSnapshot.child(message).child("Noofservice").getValue().toString();
                          stringinstallby= dataSnapshot.child(message).child("ServiceBy").getValue().toString();
                          int noOfServiceinInt = Integer.parseInt(firenoofservice);
                          predate = 270120.00;
                            if(noOfServiceinInt==1){

                                    efficiency.setText("99%");

                            }else
                            {
                                stringpredate = dataSnapshot.child(message).child("Upcoming").child("2").getValue().toString();

                                postdate = Long.parseLong(stringpredate);
                                stringworkingtime= dataSnapshot.child(message).child("RepairTime").child("2").getValue().toString();
                                workingtime = Long.parseLong(stringworkingtime);
                                mtbf = (Double)(((postdate-predate)*(0.3))-workingtime);
                                mttr = workingtime;
                                uptime = Math.round(((mtbf/(mtbf+mttr))*100));
                                //efficiency.setText(uptime);
                                stringupptime = Double.toString(uptime);
                                stringOfmtbf = Double.toString(mtbf);
                            }



                            name.setText(siname);
                            date.setText(idate);
                            time.setText(stringpredate);
                            service.setText(serviceinfo);
                            infoi.setText(message);
                            installby.setText(stringinstallby);
                        time.setText(idate);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(DatainfoID.this, "Error in Entering Data", Toast.LENGTH_SHORT).show();
                    }
                });

        updatese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update = servdate.getText().toString();
                repairtime = installtime.getText().toString();
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

        efficiency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                efficiency.setText(stringupptime);
            }
        });


    }
}
