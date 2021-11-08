package tn.esprit.gestionsallessport;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

public class AddSalle extends AppCompatActivity {
    EditText nom_salle, adresse_salle;
    Button insert, update, delete, view, pageentraineur, pagesalle;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_salle);
        nom_salle = findViewById(R.id.nom_salle);
        adresse_salle = findViewById(R.id.adresse_salle);
        insert = findViewById(R.id.add_button);
        update = findViewById(R.id.update_button);
        delete = findViewById(R.id.delete_button);
        view = findViewById(R.id.view_button);
        pageentraineur= findViewById(R.id.boutton_entraineur4);
        pagesalle= findViewById(R.id.boutton_salles4);
        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String nameTXT = nom_salle.getText().toString();
                String adresseTXT = adresse_salle.getText().toString();

                Boolean checkinsertdata = DB.insertsalle(nameTXT, adresseTXT);
                if(checkinsertdata==true)
                    Toast.makeText(AddSalle.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AddSalle.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String nameTXT = nom_salle.getText().toString();
                String adresseTXT = adresse_salle.getText().toString();

                Boolean checkupdatedata = DB.updatesalle(nameTXT, adresseTXT);
                if(checkupdatedata==true)
                    Toast.makeText(AddSalle.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AddSalle.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String nameTXT = nom_salle.getText().toString();
                Boolean checkudeletedata = DB.deletesalle(nameTXT);
                if(checkudeletedata==true)
                {
                    Toast.makeText(AddSalle.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                    nom_salle.setText("");
                    adresse_salle.setText("");
                }
                else
                    Toast.makeText(AddSalle.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Cursor res = DB.getsalles();
                if(res.getCount()==0){
                    Toast.makeText(AddSalle.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Nom :"+res.getString(0)+"\n");
                    buffer.append("Adresse :"+res.getString(1)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AddSalle.this);
                builder.setCancelable(true);
                builder.setTitle("Salles de sport:");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        pageentraineur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddSalle.this, AddEntraineur.class);
                startActivity(i);
            }
        });

        pagesalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddSalle.this, AddSalle.class);
                startActivity(i);
            }
        });
    }
}
