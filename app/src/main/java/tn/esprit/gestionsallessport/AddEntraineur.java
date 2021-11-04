package tn.esprit.gestionsallessport;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class AddEntraineur extends AppCompatActivity {
    EditText prenom_text, nom_text, adresse_text, specialite_text, id_text;
    Button insert, update, delete, view;
    DBHelper DB;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_entraineur);
        prenom_text = findViewById(R.id.prenom_text);
        nom_text = findViewById(R.id.nom_text);
        adresse_text = findViewById(R.id.adresse_text);
        specialite_text= findViewById(R.id.specialite_text);
        id_text=findViewById(R.id.id_text);
        insert = findViewById(R.id.addE_button);
        update = findViewById(R.id.updateE_button);
        delete = findViewById(R.id.deleteE_button);
        view = findViewById(R.id.showE_button);
        DB = new DBHelper(this);

        List<String> spinnerArray = new ArrayList<String>();
        cursor = DB.getnomsalles();
        if(cursor.getCount()>0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                @SuppressLint("Range") String s = cursor.getString(cursor.getPosition());
                spinnerArray.add(s);
            }
        }
        cursor.close();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);

        insert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String prenomTXT = prenom_text.getText().toString();
                String nameTXT = nom_text.getText().toString();
                String specialiteTXT = specialite_text.getText().toString();
                String adresseTXT = adresse_text.getText().toString();
                String salleTXT= sItems.getSelectedItem().toString();

                Boolean checkinsertdata = DB.insertEntraineur(prenomTXT, nameTXT,  adresseTXT,  specialiteTXT,  salleTXT);
                if(checkinsertdata==true)
                    Toast.makeText(AddEntraineur.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AddEntraineur.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String prenomTXT = prenom_text.getText().toString();
                String nameTXT = nom_text.getText().toString();
                String specialiteTXT = specialite_text.getText().toString();
                String adresseTXT = adresse_text.getText().toString();
                String idTXT= id_text.getText().toString();

                Boolean checkupdatedata = DB.updateEntraineur(idTXT, prenomTXT, nameTXT, specialiteTXT, adresseTXT);
                if(checkupdatedata==true)
                    Toast.makeText(AddEntraineur.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AddEntraineur.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String idTXT= id_text.getText().toString();
                Boolean checkudeletedata = DB.deleteEntraineur(idTXT);
                if(checkudeletedata==true)
                {
                    Toast.makeText(AddEntraineur.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                    id_text.setText("");
                }
                else
                    Toast.makeText(AddEntraineur.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String salleTXT= sItems.getSelectedItem().toString();
                Cursor res = DB.getEntraineurs(salleTXT);
                if(res.getCount()==0){
                    Toast.makeText(AddEntraineur.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Id :" + res.getString(0) + "\n");
                    buffer.append("Prenom :" + res.getString(1) + "\n");
                    buffer.append("Nom :" + res.getString(2) + "\n");
                    buffer.append("Adresse :" + res.getString(3) + "\n");
                    buffer.append("Specialite :" + res.getString(4) + "\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AddEntraineur.this);
                builder.setCancelable(true);
                builder.setTitle("Entraineurs:");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}
