package a.e;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import a.e.dummy.DummyContent;

public class DetailActivity extends AppCompatActivity {
    /**
     * The fragment argument representing the item ID that this fragment  represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra(ARG_ITEM_ID);
        TextView tv = findViewById(R.id.item_detail);
        tv.setText("Выбрана задача:   " + id );
    }
}
