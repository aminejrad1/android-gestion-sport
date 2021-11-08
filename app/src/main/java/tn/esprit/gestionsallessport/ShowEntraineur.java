package tn.esprit.gestionsallessport;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



public class ShowEntraineur extends AppCompatActivity {
    TextView prenom_text, nom_text, adresse_text, specialite_text, id_text, salle_text;
    ImageView downloadedimage;
    DBHelper DB;
    Cursor cursor;
    Button pageentraineur, pagesalle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_entraineur);
        DB = new DBHelper(this);
        Intent startingIntent = getIntent();
        String id = startingIntent.getStringExtra("id");
        prenom_text = findViewById(R.id.prenom);
        salle_text= findViewById(R.id.salle);
        nom_text = findViewById(R.id.nom);
        adresse_text = findViewById(R.id.adresse);
        specialite_text= findViewById(R.id.specialite);
        id_text=findViewById(R.id.id);
        downloadedimage=findViewById(R.id.photo);
        pageentraineur= findViewById(R.id.boutton_entraineur3);
        pagesalle= findViewById(R.id.boutton_salles3);

        pageentraineur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ShowEntraineur.this, AddEntraineur.class);
                startActivity(i);
            }
        });

        pagesalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ShowEntraineur.this, AddSalle.class);
                startActivity(i);
            }
        });

        cursor=DB.getEntraineur(id);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            do {
                String t = "Carte de l'entraineur: " + cursor.getString(0);
                id_text.setText(t);
                prenom_text.setText(cursor.getString(1));
                nom_text.setText(cursor.getString(2));
                adresse_text.setText(cursor.getString(3));
                specialite_text.setText(cursor.getString(4));
                @SuppressLint("Range") byte[] blob = cursor.getBlob(cursor.getColumnIndex("photo"));
                Bitmap b = BitmapFactory.decodeByteArray(blob , 0, blob .length);
                downloadedimage.setImageBitmap(b);
                salle_text.setText(cursor.getString(6));

            } while (cursor.moveToNext());

            cursor.close();
        }
    }
}
