package pons.carretero.myvocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import pons.carretero.myvocabulary.data.CategoryContract;
import pons.carretero.myvocabulary.data.categoriasDbHelper;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static pons.carretero.myvocabulary.addWordActivity.RequestPermissionCode;

public class MainActivity extends AppCompatActivity{
    private categoriasDbHelper mDbHelper;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    public FloatingActionButton fab;
    public boolean drawerOpened;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDbHelper = new categoriasDbHelper(this);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        boolean principalCategoriasCreadas = prefs.getBoolean("catCreadas", false);
        if(!principalCategoriasCreadas){
            createPrincipalCategorys();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //MENU LATERAL
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View view) {
                drawerOpened = false;
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                drawerOpened= true;
            }
        };

        mDrawerLayout.addDrawerListener((mToggle));
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewPager.getCurrentItem() == 0) {
                    Intent intent = new Intent(view.getContext(), addWordActivity.class);
                    startActivity(intent);
                }else if ( mViewPager.getCurrentItem() == 1){

                    //Toast.makeText(MainActivity.this, "Text can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        menulateral();


    }


    public void menulateral(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment
                    case R.id.irregular:
                        Intent view_irregularV = new Intent(MainActivity.this, IrregularVerbsActivity.class);
                        startActivity(view_irregularV);
                        return true;
                    case R.id.verbTenses:
                        Intent view_verbTenses = new Intent(MainActivity.this, VerbTenses.class);
                        startActivity(view_verbTenses);
                        return true;
                    case R.id.pronouns:
                        Intent view_pronouns = new Intent(MainActivity.this, pronounsActivity.class);
                        startActivity(view_pronouns);
                        return true;
                }
                return false;
            }
        });
    }

    public void createPrincipalCategorys(){
        insertCategory("Noun");
        insertCategory("Verb");
        insertCategory("Adjective");
        insertCategory("Adverb");
        insertCategory("Interjection");
        insertCategory("Conjunction");
        insertCategory("Unknown");

        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        prefs.edit().putBoolean("catCreadas", true).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Opcion Menu new Category
        if (id == R.id.newCategory) {
            showNewCategorry();
        }
        //Opcion menu new Word
        if (id == R.id.newWord) {
            Intent view_addword = new Intent(this, addWordActivity.class);
            view_addword.putExtra("From", "mainActivity");
            startActivity(view_addword);
        return true;
        }
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

            return super.onOptionsItemSelected(item);
        }

        public void showNewCategorry(){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.new_category, null);
            final EditText categoriTxt = (EditText) mView.findViewById(R.id.textCategory);
            Button acceptarCat = (Button) mView.findViewById(R.id.aceptarNewCategory);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();

            acceptarCat.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(!categoriTxt.getText().toString().isEmpty()){
                        //add to bbdd
                        int inserit = insertCategory(categoriTxt.getText().toString());
                        if (inserit == 1){
                            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                            // Set up the ViewPager with the sections adapter.
                            mViewPager = (ViewPager) findViewById(R.id.container);
                            mViewPager.setAdapter(mSectionsPagerAdapter);
                            //finish();
                            //startActivity(getIntent());
                            Toast.makeText(MainActivity.this, "New category created", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Category alredy existent", Toast.LENGTH_SHORT).show();
                        }

                        /////Close the floting popup
                        dialog.dismiss();
                    }else {
                        Toast.makeText(MainActivity.this, "Text can't be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            dialog.show();
        }

    public int insertCategory (String categoryName) {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(CategoryContract.CategoryEntry.COLUMN_NAME, categoryName);

        //POR URI
        Uri newUri = getContentResolver().insert(CategoryContract.CategoryEntry.CONTENT_URI, values);
        //DIRECTAMENTE EN BBDD
        //long newRowId = db.insert(PreguntasEntry.TABLE_NAME, null, values);

        //NULL = NO SE HA ONSEGUIDO INSERTAR EL NUEVO VALOR
        if (newUri == null) {
            //SI NO SE INSERTA
            return -1;
        } else {
            //SI SE INSERTA
            return 1;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    FamilyActivity fa = new FamilyActivity();
                    return fa;
                case 1:
                    QuizActivity qa = new QuizActivity();
                    return qa;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "WORDS CATEGORYS";
                case 1:
                    return "QUIZ";
            }
            return null;
        }
    }

    /////BACK KEY PRESSED
    @Override
    public void onBackPressed() {
        if(!drawerOpened){
            super.onBackPressed();
            return;
        }else{
            mDrawerLayout.closeDrawers();
        }
    }



}
