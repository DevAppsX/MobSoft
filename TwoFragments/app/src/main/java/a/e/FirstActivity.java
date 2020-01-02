package a.e;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

public class FirstActivity extends AppCompatActivity {
    FragmentA fragmentA;
    FragmentB fragmentB;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        fragmentA = new FragmentA();
        fragmentB = new FragmentB();

        checkBox = findViewById(R.id.checkBox);
    }

    public void onClick(View view) {
        // Создание транзакции менеджером фрагментов
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.b_add:
                // Добавление фрагмента в контейнер (FrameLayout в first_activity)
                transaction.add(R.id.container, fragmentB);
                break;
            case R.id.b_remove:
                // Удаление фрагмента из контейнера активности
                transaction.remove(fragmentB);
                break;
            case R.id.b_replace:
                // Замена фрагмента, который находится в контейнере на fragmentA
                transaction.replace(R.id.container, fragmentA);
            default:
                break;
        }
        // Если включен чек-бокс, все транзакции записываются в стек
        if (checkBox.isChecked()) transaction.addToBackStack(null);
        transaction.commit();
    }
}