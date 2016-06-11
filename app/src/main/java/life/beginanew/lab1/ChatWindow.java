package life.beginanew.lab1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    ListView chatListView;
    EditText chatEditText;
    Button sendButton;
    ArrayList<String> chatMessage;
    ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
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
                messageAdapter.notifyDataSetChanged(); // This restarts the process of getCount() / getView()
                chatEditText.setText("");
            }
        });

    }

    // Step 5
    private class ChatAdapter extends ArrayAdapter<String> {
        private Context myContext;

        public ChatAdapter(Context context) {
            super(context, 0);
            myContext = context;
        }

        public int getCount() {
            return chatMessage.size();
        }

        public String getItem(int position) {
            return chatMessage.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // Step 9
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
