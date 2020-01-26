package myfirstapkas.com.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.pushbots.push.Pushbots;

public class dashboard extends AppCompatActivity implements OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    AppCompatActivity activity;
    Menu menu;
    String scanid;
    TextView textView;
    ImageView installInventory, scannerInventory, locateInventory, groupChat;

    public void hidekeyboard(View v){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Pushbots.sharedInstance().registerForRemoteNotifications();

       // scan_btn =  findViewById(R.id.scanner);
        activity = this;

        /*---------------------Hooks------------------------*/
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        /*------------------------Tool Bar-------------------*/
        setSupportActionBar(toolbar);


        //Hide or show items

        menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);


        /*------------------------Navigation Drawer Menu-------------------*/
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        menu.findItem(R.id.nav_logout).setVisible(true);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);



        installInventory = findViewById(R.id.dashinstallInventory);
        installInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Toast.makeText(Navigation.this, "Sucessfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(dashboard.this, DataEntry.class);
                startActivity(intent);
                finish();
            }
        });
        //2nd Card
        scannerInventory = findViewById(R.id.dashscannerInventory);
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
        locateInventory = findViewById(R.id.dashlocateInventory);
        locateInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(dashboard.this, MapsActivityCurrentPlace.class);
                startActivity(intent2);
                finish();
            }
        });

        //4rd CardView Details
        groupChat = findViewById(R.id.dashgroupChat);
        groupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Detials here
            }
        });


      //  drawerLayout = findViewById(R.id.drawer);
     //   toolbar = findViewById(R.id.toolbar);
        //navigationView = findViewById(R.id.navigationView);
     ///   setSupportActionBar(toolbar);
      ///  getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
      ///  getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  //toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
     //   drawerLayout.addDrawerListener(toggle);
     //   toggle.syncState();
     //   navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;


            case R.id.nav_logout:
                //menu.findItem(R.id.nav_logout).setVisible(false);

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(dashboard.this,loginActivity.class);
                startActivity(intent);
                finish();
                break;


            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
                Intent intent = new Intent(dashboard.this,DatainfoID.class);
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
