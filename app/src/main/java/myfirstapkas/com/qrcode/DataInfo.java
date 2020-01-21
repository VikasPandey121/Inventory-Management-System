package myfirstapkas.com.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DataInfo extends AppCompatActivity {

    Button scan;
    Button id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_info);
        scan = findViewById(R.id.scan);
        id = findViewById(R.id.find);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               //ReaderActivity Intent


            }
        });

        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataInfo.this,DatainfoID.class);
                startActivity(intent);



            }
        });
    }
}
