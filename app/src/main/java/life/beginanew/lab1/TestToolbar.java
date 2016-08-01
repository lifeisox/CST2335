package life.beginanew.lab1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    private String item1Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "FloatingActionButton is clicked.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        item1Message = "HOME menu is selected (default)";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        switch (id) {
            case R.id.action_home:
                Log.d("Toolbar", "Home menu selected.");
                Snackbar.make(findViewById(android.R.id.content), item1Message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_exit:
                Log.d("Toolbar", "Exit menu selected.");
                showChangeMessageDialog();
                break;
            case R.id.action_favorite:
                Log.d("Toolbar", "Favorite menu selected.");
                Snackbar.make(findViewById(android.R.id.content), "Favorite menu selected.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_about:
                Toast toast = Toast.makeText(this, getString(R.string.about_message), Toast.LENGTH_LONG);
                toast.show();
                break;
        }
        return true;
    }

    private void showChangeMessageDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.new_message);

        dialogBuilder.setPositiveButton(R.string.change_message, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                item1Message = edt.getText().toString();
                Snackbar.make(findViewById(android.R.id.content), "Message is changed.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        dialogBuilder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                onBackPressed();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
