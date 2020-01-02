package a.e;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/** простой подкласс фрагмента */
public class FragmentB extends Fragment {

    public FragmentB() { /* конструктор должен быть обязательно */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // заполнение макета для фрагмента
        return inflater.inflate(R.layout.b_fragment, container, false);
    }
}
