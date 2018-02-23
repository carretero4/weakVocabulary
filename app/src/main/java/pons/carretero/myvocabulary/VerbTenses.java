package pons.carretero.myvocabulary;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class VerbTenses extends AppCompatActivity {
    public int tabSelected;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verb_tenses);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarVT);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerVT);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsVT);
        tabLayout.setupWithViewPager(mViewPager);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        String tabS = prefs.getString("TabSelected", "0");

        tabLayout.setScrollPosition(1,0f,true);
        mViewPager.setCurrentItem(Integer.parseInt(tabS));


    }

    public void selectTab (int tab){
        SharedPreferences prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String tabS="0";
        switch (tab){
            case 0:
                editor.putString("TabSelected", "0");
                editor.commit();

                break;
            case 1:
                editor.putString("TabSelected", "1");
                editor.commit();
                break;
            case 2:
                editor.putString("TabSelected", "2");
                editor.commit();
                break;
            default:
                editor.putString("TabSelected", "0");
                editor.commit();
                break;
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
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    VerbTensePresent vtpresent = new VerbTensePresent();
                    return vtpresent;
                case 1:
                    VerbTensePast vtpast = new VerbTensePast();
                    return vtpast;
                case 2:
                    VerbTenseFuture vtfuture = new VerbTenseFuture();
                    return vtfuture;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "PRESENT";
                case 1:
                    return "PAST";
                case 2:
                    return "FUTURE";
            }
            return null;
        }
    }

}
