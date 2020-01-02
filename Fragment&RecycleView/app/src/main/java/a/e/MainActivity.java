package a.e;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import a.e.dummy.DummyContent;
import static a.e.DetailActivity.ARG_ITEM_ID;

public class MainActivity extends AppCompatActivity implements
        TaskFragment.OnListFragmentInteractionListener{

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(ARG_ITEM_ID, item.id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
