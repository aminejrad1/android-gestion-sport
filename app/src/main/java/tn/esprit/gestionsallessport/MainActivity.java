package tn.esprit.gestionsallessport;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button  pageentraineur, pagesalle;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pageentraineur= findViewById(R.id.boutton_entraineur);
        pagesalle= findViewById(R.id.boutton_salles);

        pageentraineur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, AddEntraineur.class);
                startActivity(i);
            }
        });

        pagesalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, AddSalle.class);
                startActivity(i);
            }
        });
    }
}
