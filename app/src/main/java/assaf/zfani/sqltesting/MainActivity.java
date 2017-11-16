package assaf.zfani.sqltesting;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText nameEd,familyEd;
    SQLiteDatabase database;
    private static String DATABASE_NAME  = "NamesDataBase";
    private static String TABLE_NAME = "tbl_names";
    private static String TABLE_ROW_NAME = "name";
    private static String TABLE_ROW_FAMILY = "family";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        String CreateTableCommand = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_ROW_NAME + " TEXT, " + TABLE_ROW_FAMILY +" TEXT);";
        database.execSQL(CreateTableCommand);

        initViews(); //יותר אלגנטי למציאת הווידגטים
        findViewById(R.id.buttonSaved).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEd.getText().toString();
                String family = familyEd.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put(TABLE_ROW_NAME,name);
                contentValues.put(TABLE_ROW_FAMILY,family);

                database.insert(TABLE_NAME,null,contentValues);


            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);

                if (cursor.moveToFirst())
                {
                    int nameRowIndex =cursor.getColumnIndex(TABLE_ROW_NAME);
                    int familyRowIndex = cursor.getColumnIndex(TABLE_ROW_FAMILY);
                    do  {
                        String name = cursor.getString(nameRowIndex);
                        String family = cursor.getString(familyRowIndex);
                        Log.i("Name And Family", name + " " + family);
                        }
                    while (cursor.moveToNext());
                    cursor.close();
                }

            }
        });

    }
    private void initViews()
    {
        nameEd = (EditText)findViewById(R.id.editTextName);
        familyEd = (EditText)findViewById(R.id.editTextFamily);

    }
}
