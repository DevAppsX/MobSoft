package a.basic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.snackbar_text, Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds tasks to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Snackbar sb = Snackbar.make(findViewById(R.id.coordinatorLayout),
                R.string.snackbar_text, Snackbar.LENGTH_LONG);
        View sbView = sb.getView();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.green_item:
                // Green item was selected
                sbView.setBackgroundColor(Color.GREEN);
                sb.show();
                return true;
            case R.id.yellow_item:
                // Yellow item was selected
                sbView.setBackgroundColor(Color.YELLOW);
                sb.show();
                return true;
            case R.id.red_item:
                sbView.setBackgroundColor(Color.RED);
                sb.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
