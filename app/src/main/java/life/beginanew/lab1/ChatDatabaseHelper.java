package life.beginanew.lab1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ByungSeon on 2016-06-18.
 */
public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String CHAT_TABLE = "tbl_chat"; // Table Name
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";
    public static final String CREATE_CHAT_TABLE = "CREATE TABLE " + CHAT_TABLE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + " TEXT NOT NULL)";
    public static final String DROP_CHAT_TABLE = "DROP TABLE IF EXISTS " + CHAT_TABLE;
    public static final String READALL_CHAT_TABLE = "SELECT " + KEY_ID + ", " + KEY_MESSAGE
            + " FROM " + CHAT_TABLE;
    private static final String ACTIVITY_NAME = "ChatDatabaseHelper"; // Step 7 for Lab 5
    private static final String DATABASE_NAME = "mydatabase";   // Database name
    private static final int VERSION_NUM = 2;   // Version Number

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CHAT_TABLE);
        Log.i(ACTIVITY_NAME, "Calling onCreate"); // Step 7 for Lab 5
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_CHAT_TABLE);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVertion=" + oldVersion + " newVersion=" + newVersion); // Step 7 for Lab 5
    }
}
