package myfirstapkas.com.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.pushbots.push.Pushbots;

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    MenuItem scan_btn;
    AppCompatActivity activity;
    String scanid;
    CardView installInventory, scannerInventory, locateInventory, groupChat;

    //Might need to delete it
    public static final String ARG_FROM_MAIN = "arg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Pushbots.sharedInstance().registerForRemoteNotifications();

        //Might  we have to remove it

        scan_btn =  findViewById(R.id.scanner);
        activity = this;
        //till this

        installInventory = findViewById(R.id.installInventory);
        installInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(Navigation.this, "Sucessfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Navigation.this, DataEntry.class);
                startActivity(intent);
                finish();
            }
        });


        //2nd CardView Details
        scannerInventory = findViewById(R.id.scannerInventory);
        scannerInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });

        // 3rd CardView Details
        locateInventory = findViewById(R.id.locateInventory);
        locateInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Navigation.this, MapsActivityCurrentPlace.class);
                startActivity(intent2);
                finish();
            }
        });

        //4rd CardView Details
        groupChat = findViewById(R.id.groupChat);
        groupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Detials here
            }
        });
        
        
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.scanner:{

                break;}
            case R.id.qrGenerator:


                break;
            case R.id.map:
                Intent intent2 = new Intent(Navigation.this, MapsActivityCurrentPlace.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent4 = new Intent(Navigation.this,loginActivity.class);
                startActivity(intent4);
                finish();                break;
            case R.id.data:
                Intent intent1 = new Intent(Navigation.this,DatainfoID.class);
                startActivity(intent1);
                finish();
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                 scanid =(result.getContents());
                 Intent intent = new Intent(Navigation.this,DatainfoID.class);
                 //intent.putExtra("arg", getText()); // getText() SHOULD NOT be static!!!
                //String value = ed1.getText().toString()

                intent.putExtra("message", scanid);
                 startActivity(intent);
                Toast.makeText(this, result.getContents(),Toast.LENGTH_LONG).show();


            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
