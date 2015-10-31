package es.upm.miw.rayovallecano.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static es.upm.miw.rayovallecano.models.FutbolistaContract.*;

/**
 * Created by bm0806 on 30/10/2015.
 */
public class RepositorioFutbolistas extends SQLiteOpenHelper {
    private static final String DATABASE_FILENAME = "futbolistas.db";

    private static final int DATABASE_VERSION = 2;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public RepositorioFutbolistas(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sentenciaSQL = "CREATE TABLE " + tablaFutbolista.TABLE_NAME + "("
                + tablaFutbolista.COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tablaFutbolista.COL_NAME_NOMBRE + " TEXT, "
                + tablaFutbolista.COL_NAME_DORSAL + " INTEGER, "
                + tablaFutbolista.COL_NAME_LESIONADO + " INTEGER, "
                + tablaFutbolista.COL_NAME_EQUIPO + " TEXT, "
                + tablaFutbolista.COL_NAME_URL_IMAGEN + " TEXT) ";

        db.execSQL(sentenciaSQL);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sentenciaSQL = "DROP TABLE IF EXISTS " + tablaFutbolista.TABLE_NAME;
        db.execSQL(sentenciaSQL);
    }

    public long add(Futbolista futbolista){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put(tablaFutbolista.COL_NAME_ID, futbolista.get_id());
        valores.put(tablaFutbolista.COL_NAME_NOMBRE, futbolista.get_nombre());
        valores.put(tablaFutbolista.COL_NAME_DORSAL, futbolista.get_dorsal());
        valores.put(tablaFutbolista.COL_NAME_LESIONADO, futbolista.is_lesionado());
        valores.put(tablaFutbolista.COL_NAME_EQUIPO, futbolista.get_equipo());
        valores.put(tablaFutbolista.COL_NAME_URL_IMAGEN, futbolista.get_URL_imagen());

        return db.insert(tablaFutbolista.TABLE_NAME,null, valores);
    }

    public ArrayList<Futbolista> getAll(){
        ArrayList<Futbolista> futbolistas = new ArrayList<>();
        String consultaSQL = "SELECT * FROM " + tablaFutbolista.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        //hacer la consulta y recuperar informacion
        Cursor cursor = db.rawQuery(consultaSQL,null);
        if (cursor.moveToFirst()){ //Si hay datos
            while (!cursor.isAfterLast()){
                Futbolista futbolista = new Futbolista(
                        cursor.getInt(cursor.getColumnIndex(tablaFutbolista.COL_NAME_ID)),
                        cursor.getString(cursor.getColumnIndex(tablaFutbolista.COL_NAME_NOMBRE)),
                        cursor.getInt(cursor.getColumnIndex(tablaFutbolista.COL_NAME_DORSAL)),
                        cursor.getInt(cursor.getColumnIndex(tablaFutbolista.COL_NAME_LESIONADO)) !=0,
                        cursor.getString(cursor.getColumnIndex(tablaFutbolista.COL_NAME_EQUIPO)),
                        cursor.getString(cursor.getColumnIndex(tablaFutbolista.COL_NAME_URL_IMAGEN))
                );
                futbolistas.add(futbolista);
                cursor.moveToNext();
            }
        }

        return futbolistas;
    }

    public long deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String consulta = "";
        return db.delete(tablaFutbolista.TABLE_NAME,"1",null);
    }
}
