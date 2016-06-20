package life.beginanew.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    ListView chatListView;
    EditText chatEditText;
    Button sendButton;
    ArrayList<String> chatMessage;
    ChatAdapter messageAdapter;
    ChatDatabaseHelper dbHelper;    // Step 5 of Lab 5
    SQLiteDatabase db;  // Step 5 of Lab 5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        dbHelper = new ChatDatabaseHelper(ChatWindow.this); // Step 5 of Lab 5
        db = dbHelper.getWritableDatabase(); // Step 5 of Lab 5

        // Step 4
        chatMessage = new ArrayList<>();
        chatListView = (ListView) findViewById(R.id.chatListView);

        messageAdapter = new ChatAdapter(this);
        chatListView.setAdapter(messageAdapter);

        chatEditText = (EditText) findViewById(R.id.textInput);
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatMessage.add(chatEditText.getText().toString());
                ContentValues contentValues = new ContentValues(); // Step 6 for Lab 5
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, chatEditText.getText().toString()); // Step 6 for Lab 5
                db.insert(ChatDatabaseHelper.CHAT_TABLE, "", contentValues); // Step 6 for Lab 5
                messageAdapter.notifyDataSetChanged(); // This restarts the process of getCount() / getView()
                chatEditText.setText("");
            }
        });

        // Step 5 of Lab 5
        Cursor cursor;
        cursor = db.rawQuery(ChatDatabaseHelper.READALL_CHAT_TABLE, null);
        int messageIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            chatMessage.add(cursor.getString(messageIndex));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(messageIndex));
            cursor.moveToNext();
        }
        // Print an information message about the Cursor
        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());
        // Then use a for loop to print out the name of each column returned by the cursor.
        for (int colIndex = 0; colIndex < cursor.getColumnCount(); colIndex++) {
            Log.i(ACTIVITY_NAME, "Column name of " + colIndex + " = " + cursor.getColumnName(colIndex));
        }

    }

    // Step 8 for Lab 5
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            db.close();
            dbHelper.close();
        } catch (Exception e) {
        }
    }

    // Step 5
    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context context) {
            super(context, 0);
        }

        public int getCount() {
            return chatMessage.size();
        }

        public String getItem(int position) {
            return chatMessage.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // Step 9
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }

}
