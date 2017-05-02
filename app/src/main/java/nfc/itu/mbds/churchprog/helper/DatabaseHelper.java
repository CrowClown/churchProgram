package nfc.itu.mbds.churchprog.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nfc.itu.mbds.churchprog.modele.Contenu;
import nfc.itu.mbds.churchprog.modele.Programme;

/**
 * Created by Proprietaire on 30/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "church_db";
    // Table Names
    private static final String TABLE_CONTENU = "contenu";
    private static final String TABLE_PROGRAMME = "programme";
    // Column names Programme
    private static final String PROG_ID = "prog_id";
    private static final String PROG_DATE = "prog_date";
    private static final String PROG_DESC = "prog_desc";
    // Column names contenu
    private static final String C_ID = "c_id";
    private static final String ID_PROG = "id_prog";
    private static final String C_TYPE = "c_type";
    private static final String C_TITRE = "c_titre";
    private static final String C_CONTENU = "c_contenu";
    private static final String C_ORDRE = "c_ordre";
    // CREATE TABLES
    private static final String CREATE_TABLE_PROGRAMME = "CREATE TABLE "
            + TABLE_PROGRAMME + "("
            + PROG_ID + " INTEGER PRIMARY KEY,"
            + PROG_DATE + " TEXT,"
            + PROG_DESC + " TEXT"
            + ")";

    private static final String CREATE_TABLE_CONTENU = "CREATE TABLE "
            + TABLE_CONTENU + "("
            + C_ID + " INTEGER PRIMARY KEY,"
            + ID_PROG + " INTEGER,"
            + C_TYPE + " TEXT,"
            + C_TITRE + " TEXT,"
            + C_CONTENU + " TEXT,"
            + C_ORDRE + " INTEGER,"
            + "FOREIGN KEY("+ ID_PROG +") REFERENCES "+ TABLE_PROGRAMME +"("+ ID_PROG +") "
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_PROGRAMME);
        db.execSQL(CREATE_TABLE_CONTENU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAMME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENU);

        onCreate(db);
    }

    public void open() {
        //Log.i(LOGTAG, "Database Opened");
        db = this.getWritableDatabase();
    }

    public void closeDB() {
        //SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /************* Programme methods ****************/

    public long createProgramme(Programme todo) {
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROG_ID, todo.getProg_id());
        values.put(PROG_DATE, todo.getProg_date());
        values.put(PROG_DESC, todo.getProg_desc());

        long todo_id = db.insert(TABLE_PROGRAMME, null, values);

        return todo_id;
    }

    public Programme getProgrammeById(int id) {
        //SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_PROGRAMME, new String[] { PROG_ID,
                        PROG_DATE, PROG_DESC }, PROG_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

            Programme contact = new Programme(c.getInt(c.getColumnIndex(PROG_ID)), c.getString(c.getColumnIndex(PROG_DATE)), c.getString(c.getColumnIndex(PROG_DESC)));
            return contact;
        }

        return null;
    }

    public List<Programme> getAllProgramme() {
        List<Programme> prog = new ArrayList<Programme>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROGRAMME;

        Log.e(LOG, selectQuery);

        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Programme td = new Programme();
                td.setProg_id(c.getInt((c.getColumnIndex(PROG_ID))));
                td.setProg_date((c.getString(c.getColumnIndex(PROG_DATE))));
                td.setProg_desc(c.getString(c.getColumnIndex(PROG_DESC)));

                prog.add(td);
            } while (c.moveToNext());
        }

        return prog;
    }

    public int updateProgramme(Programme prog) {
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        return db.update(TABLE_PROGRAMME, values, PROG_ID + " = ?",
                new String[] { String.valueOf(prog.getProg_id()) });
    }

    public void deleteProgramme(long id) {
        //SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROGRAMME, PROG_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    /************* Contenu methods ****************/

    public long createContenu(Contenu contenu) {
        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(C_ID, contenu.getC_id());
        values.put(ID_PROG, contenu.getId_prog());
        values.put(C_TYPE, contenu.getC_type());
        values.put(C_TITRE, contenu.getC_titre());
        values.put(C_CONTENU, contenu.getC_contenu());
        values.put(C_ORDRE, contenu.getC_ordre());

        long todo_id = db.insert(TABLE_CONTENU, null, values);

        return todo_id;
    }

    public List<Contenu> getAllContenu() {
        List<Contenu> contenu = new ArrayList<Contenu>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTENU;

        Log.e(LOG, selectQuery);

        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Contenu td = new Contenu();
                td.setC_id(c.getInt((c.getColumnIndex(C_ID))));
                td.setId_prog(c.getInt(c.getColumnIndex(ID_PROG)));
                td.setC_type(c.getString(c.getColumnIndex(C_TYPE)));
                td.setC_titre(c.getString(c.getColumnIndex(C_TITRE)));
                td.setC_contenu(c.getString(c.getColumnIndex(C_CONTENU)));
                td.setC_ordre(c.getInt(c.getColumnIndex(C_ORDRE)));

                contenu.add(td);
            } while (c.moveToNext());
        }

        return contenu;
    }

    public List<Contenu> getContenuByIdprog(int id_prog) {
        //SQLiteDatabase db = this.getReadableDatabase();
        List<Contenu> contenu = new ArrayList<Contenu>();
        Cursor c = db.query(TABLE_CONTENU, new String[] { C_ID, ID_PROG, C_TYPE, C_TITRE, C_CONTENU, C_ORDRE },
                ID_PROG + "=?", new String[] { String.valueOf(id_prog) }, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                Contenu td = new Contenu();
                td.setC_id(c.getInt((c.getColumnIndex(C_ID))));
                td.setId_prog(c.getInt(c.getColumnIndex(ID_PROG)));
                td.setC_type(c.getString(c.getColumnIndex(C_TYPE)));
                td.setC_titre(c.getString(c.getColumnIndex(C_TITRE)));
                td.setC_contenu(c.getString(c.getColumnIndex(C_CONTENU)));
                td.setC_ordre(c.getInt(c.getColumnIndex(C_ORDRE)));

                contenu.add(td);
            } while (c.moveToNext());
        }

        //Log.e(LOG, "CONTENU "+contenu.size());

        /*
        if (c != null)
            c.moveToFirst();

        Contenu contenu = new Contenu(c.getInt(c.getColumnIndex(C_ID)),
                c.getInt(c.getColumnIndex(ID_PROG)), c.getString(c.getColumnIndex(C_TYPE)),
                c.getString(c.getColumnIndex(C_TITRE)), c.getString(c.getColumnIndex(C_CONTENU)), c.getInt(c.getColumnIndex(C_ORDRE)));
        */
        return contenu;
    }

    public void deletePContenu(long id) {
        //SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTENU, C_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public List<Programme> getAll(){
        try {
            open();
            List<Programme> lprog = getAllProgramme();
            for (int i = 0; i < lprog.size(); i++) {
                lprog.get(i).setContenu(getContenuByIdprog(lprog.get(i).getProg_id()));
            }
            return lprog;
        }catch(Exception e){

        }finally {
            closeDB();
        }
        return null;
    }
}
