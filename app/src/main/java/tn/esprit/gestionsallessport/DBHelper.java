package tn.esprit.gestionsallessport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "db.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Entraineurs(id INTEGER primary key autoincrement, prenom TEXT , nom TEXT, adresse TEXT, specialite TEXT, photo BLOB, salle TEXT, FOREIGN KEY(salle) REFERENCES Salles(nom))");
        DB.execSQL("create Table Salles(nom TEXT primary key, adresse TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Salles");
        DB.execSQL("drop Table if exists Entraineurs");
    }

    public Boolean insertsalle(String name, String adresse)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", name);
        contentValues.put("adresse", adresse);
        long result=DB.insert("Salles", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }


    public Boolean updatesalle(String name, String adresse) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("adresse", adresse);
        Cursor cursor = DB.rawQuery("Select * from Salles where nom = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("Salles", contentValues, "nom=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }}


    public Boolean deletesalle (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Salles where nom = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Salles", "nom=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getsalles ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Salles", null);
        return cursor;
    }

    public Cursor getnomsalles()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select nom from Salles", null);
        return cursor;
    }

    public Boolean insertEntraineur(String prenom, String name, String adresse, String specialite, String salle, byte[] image)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("prenom", prenom);
        contentValues.put("nom", name);
        contentValues.put("specialite", specialite);
        contentValues.put("salle", salle);
        contentValues.put("adresse", adresse);
        contentValues.put("photo", image);
        long result=DB.insert("Entraineurs", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }


    public Boolean updateEntraineur(String id, String prenom, String nom, String specialite, String adresse) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("prenom", prenom);
        contentValues.put("nom", nom);
        contentValues.put("specialite", specialite);
        contentValues.put("adresse", adresse);
        Cursor cursor = DB.rawQuery("Select * from Entraineurs where id=?", new String[]{id});
        if (cursor.getCount() > 0) {
            long result = DB.update("Entraineurs", contentValues, "id=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }}


    public Boolean deleteEntraineur (String id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Entraineurs where id = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Entraineurs", "id=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getEntraineurs (String salle)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Entraineurs WHERE salle=?", new String[]{salle});
        return cursor;
    }

    public Cursor getEntraineur(String id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Entraineurs WHERE id=?", new String[]{id});
        return cursor;
    }
}