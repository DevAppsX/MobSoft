package a.tcalc;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.EXTRA_SUBJECT;
import static android.content.Intent.EXTRA_TEXT;

public class ThankActivity extends AppCompatActivity {
    static final String EXTRA_TIP = "extra_tip";
    private double tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thank_activity);

        Intent intent = getIntent();
        tip = intent.getDoubleExtra(EXTRA_TIP, 50);
    }
    // Обработка нажатия кнопки Button:
    public void onButtonClick(View view) {
        Toast.makeText(this, "Tip = " + Double.toString(tip), LENGTH_SHORT)
                .show();
/*      Intent intent = new Intent(ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(EXTRA_SUBJECT, getString(R.string.b_thank));
        intent.putExtra(EXTRA_TEXT, "Delicious! Tip = " + Double.toString(tip));
        startActivity(intent);
*/
    }
}
