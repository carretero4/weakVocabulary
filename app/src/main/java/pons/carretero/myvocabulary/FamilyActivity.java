package pons.carretero.myvocabulary;

/**
 * Created by carre on 27/11/2017.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import pons.carretero.myvocabulary.data.CategoryContract;
import pons.carretero.myvocabulary.data.CategoryContract.CategoryEntry;
import pons.carretero.myvocabulary.data.WordContract;
import pons.carretero.myvocabulary.data.categoriasDbHelper;

import java.util.ArrayList;
import java.util.logging.Logger;

import pons.carretero.myvocabulary.data.CategoryContract;

import static android.R.attr.category;
import static android.R.attr.name;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static android.widget.Toast.makeText;
import static pons.carretero.myvocabulary.data.CategoryContract.PATH_CATEGORYS;

public class FamilyActivity extends Fragment{
    private static final int CATEGORYS_LOADER = 0;

    ArrayList<String> categorys;
    ArrayAdapter<String> itemsAdapter;
    ListView listView;
    String nombreDeLaCategoria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.family_fragment, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);


        newListCategoty();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = categorys.get(position);
                //ON SELECT SOME CATEGORY
                if(!category.equals("Add a new Category...")){
                    Intent wordsActivity = new Intent(getContext(), MyWords.class);
                    wordsActivity.putExtra("Category", category);
                    startActivity(wordsActivity);
                }else{
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.showNewCategorry();
                }


            }
        });

        activarFab();

        return rootView;
    }

   public void newListCategoty () {
        categorys = new ArrayList<String>();
        String unknown = "";
        ////GENERA UN NUMERO ALEATORIO ENTRE 1 Y EL MAXIMO DE PREGUNTAS
        String[] projection = {
                CategoryEntry.COLUMN_NAME};

        Cursor cursor =  getActivity().getApplicationContext().getContentResolver().query(
                CategoryEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        int nameColumna = cursor.getColumnIndex(CategoryEntry.COLUMN_NAME);

        while (cursor.moveToNext()) {
            if(!cursor.getString(nameColumna).equals("Unknown")){
                String name = cursor.getString(nameColumna);
                categorys.add(name);
            }else{
                unknown = cursor.getString(nameColumna);
            }
        }

        categorys.add(unknown);
        categorys.add("Add a new Category...");
        final int sic = categorys.size();
        itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, categorys){
           @Override
           public View getView(int position, View convertView, ViewGroup parent){
               // Get the Item from ListView
               View view = super.getView(position, convertView, parent);

               if(position==sic-1){
                   // Initialize a TextView for ListView each Item
                   TextView tv = (TextView) view.findViewById(android.R.id.text1);

                   // Set the text color of TextView (ListView Item)
                   tv.setTextColor(Color.parseColor("#0e77a4"));
               }


               // Generate ListView Item using TextView
               return view;
           }
       };

        //SIRVE PARA REFERENCIAR LA LISTA AL LISTENER DE CREAR MENU
        registerForContextMenu(listView);
        listView.setAdapter(itemsAdapter);
    }

    private int selectId (String category){

        Uri uriCat = Uri.withAppendedPath(CategoryEntry.CONTENT_URI, category);
        //Log.d("uri", uriCat.toString());
        String[] projection = {
                CategoryEntry._ID};

        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(
                uriCat,
                projection,
                null,
                null,
                null);

        int idCat = cursor.getColumnIndex(CategoryEntry._ID);

        cursor.moveToFirst();
        int idC = cursor.getInt(idCat);

        return idC;
    }

    ///LISTENER DE CREAR MENU
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Delete");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        //  info.position will give the index of selected item
        int IndexSelected=info.position;
        if(item.getTitle()=="Delete")
        {
            String category = categorys.get(info.position);
            deleteCategory(category);

            //words = new ArrayList<String>();
        }
        else
        {
            return false;
        }
        return true;
    }

    public void deleteCategory (String catName){
        if(!catName.equals("Add a new Category...")&&!catName.equals("Unknown")&&!catName.equals("Noun")&&!catName.equals("Verb")&&!catName.equals("Adjective")&&
                !catName.equals("Adverb")&&!catName.equals("Phrasal Verbs")&&!catName.equals("Interjection")&&!catName.equals("Conjunction")){
            nombreDeLaCategoria = catName;
            createDialogYesNo();

        }else{
            Toast.makeText(getActivity(), "Can't delete this category", Toast.LENGTH_SHORT).show();
        }
    }

    private void createDialogYesNo(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Do your Yes progress
                        doOnTrueResult();
                        break;
                }
            }
        };
        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        ab.setMessage("Are you sure to delete this category?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void doOnTrueResult() {
        //pasa las palabras de la categoria a unknow
        int idCategoria = selectId(nombreDeLaCategoria);
        updateWord(null,null,selectId("Unknown"), idCategoria);

        //elimina la categoria
        String selection = CategoryEntry.COLUMN_NAME + "=?";
        String[] selectionArgs = new String[] {nombreDeLaCategoria};
        int newDelete = getActivity().getApplicationContext().getContentResolver().delete
                (CategoryEntry.CONTENT_URI, selection, selectionArgs);

        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();

        newListCategoty();
    }

    public void updateWord(String eng, String esp, int newIdCat, int oldIdCat){
        ContentValues values = new ContentValues();
        values.put(WordContract.WordEntry.COLUMN_CATEGORY, newIdCat);

        String selection = WordContract.WordEntry.COLUMN_CATEGORY + "=?";
        String[] selectionArgs = new String[] {};

        Uri urii = Uri.withAppendedPath(WordContract.WordEntry.CONTENT_URI, Integer.toString(oldIdCat));

        int newUpdate = getActivity().getContentResolver().update(urii, values, selection, selectionArgs);
    }


    ///LISENER PARA EL FLOATING ACTION BUTTON QUE ESTA EN EL MAIN
    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            final MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.fab.setVisibility(View.VISIBLE);
            mainActivity.fab.setImageResource(R.drawable.ic_create_white_24dp);
            activarFab();

            //Static toolBar
            AppBarLayout appBarLayout = (AppBarLayout) mainActivity.findViewById(R.id.appbar);
            appBarLayout.setExpanded(true, true); ///setExpanded(expanded, animation)

            //Cierra el teclado
            View view = getActivity().getCurrentFocus();
            view.clearFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    public void activarFab()
    {
        if (!getUserVisibleHint())
        {
            return;
        }
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), addWordActivity.class);
                intent.putExtra("From", "familyActivity");
                startActivity(intent);
            }
        });
    }


}
