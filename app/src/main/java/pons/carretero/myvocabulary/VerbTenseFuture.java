package pons.carretero.myvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class VerbTenseFuture extends Fragment {

    ArrayAdapter<String> itemsAdapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_verb_tense_future, container, false);

        listView = (ListView) rootView.findViewById(R.id.listVTfuture);
        final ArrayList<String> tenses = new ArrayList<String>();
        tenses.add("Future simple (Will)");
        tenses.add("Future simple (Be going to)");
        tenses.add("Present continuos (future)");
        tenses.add("Future continuous");
        tenses.add("Future perfect");
        tenses.add("Future perfect continuous");

        itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, tenses);
        //SIRVE PARA REFERENCIAR LA LISTA AL LISTENER DE CREAR MENU
        registerForContextMenu(listView);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final VerbTenses verbT = (VerbTenses)getActivity();
                verbT.selectTab(2);

                String tense = tenses.get(position);

                //ON SELECT SOME CATEGORY
                Intent presentSActivity = new Intent(getContext(), Tense.class);
                presentSActivity.putExtra("Tense", tense);
                startActivity(presentSActivity);
            }
        });


        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            final VerbTenses verbT = (VerbTenses)getActivity();
            verbT.selectTab(2);
            onResume();
        }
    }

}
