package com.example.duarte.mediaplayerandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class ListFiles extends AppCompatActivity {
    private ListView lv;
    private String[] items;
    private boolean isPlaying = false;
    private Context context = ListFiles.this;
    private ArrayList adapter = new ArrayList();
    private int img = R.drawable.play; //add
    private int position;
    private EditText editText; // Var lable Search
    private int tabSelected; // tab selected
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
        setContentView(R.layout.activity_list_files);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabSelected = 0;


        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        switch (position) {
                            case 0:
                                tabSelected = 0;
                                getFiles("all");
                                break;
                            case 1:
                                tabSelected = 1;
                                getFiles("music");
                                break;
                            case 2:
                                tabSelected = 2;
                                getFiles("video");
                                break;
                        }
                    }
                });


        if (android.os.Build.VERSION.SDK_INT >= 21) { // Jorge
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Black_F2));
        }

        editText = (EditText) findViewById(R.id.txtsearch);// Jorge
        getFiles("all");
        initSearch();//Jorge


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isPlaying == false){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("position",position).putExtra("items",items));
                    finish();
                }
                else
                {
                    Intent returnMainAct =new Intent();
                    returnMainAct.putExtra("position",position).putExtra("items",items).putExtra("isPlaying", isPlaying);
                    setResult(Activity.RESULT_OK,returnMainAct);
                    finish();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_files, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_files, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);


            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:

                    break;
                case 1:

                    break;
                case 2:
                    break;
            }

            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


            return rootView;
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
            return PlaceholderFragment.newInstance(position + 1);
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
                    return "ALl";
                case 1:

                    return "Music";
                case 2:
                    return "Video";
            }
            return null;
        }
    }


    private void getDataInList(){
        for (int i = 0; i<items.length; i++) {
            // Create a new object for each list item
            playList ld = new playList();
            ld.setTitle(items[i]);
            // Add this object into the ArrayList myList
            adapter.add(ld);
        }
        if(isPlaying){
            playList pl = (playList) adapter.get(position);
            pl.setImgResId(img);
        }

    }






    public ArrayList<File> findFiles(File root, String type){
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory()&&!singleFile.isHidden()){
                findFiles(singleFile , type);
            }
            else {
                switch (type) {
                    case "video":
                        if(singleFile.getName().endsWith(".mp4") || singleFile.getName().endsWith(".mov") ){
                            al.add(singleFile);
                        }
                        break;
                    case "music":
                        if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wmv")) {
                            al.add(singleFile);
                        }
                        break;
                    case "all":
                        if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wmv") || singleFile.getName().endsWith(".mp4") || singleFile.getName().endsWith(".mov") ){
                            al.add(singleFile);
                        }
                        break;


                }
            }
        }
        return al;
    }


    public  void getFiles(String type){


        lv = (ListView) findViewById(R.id.playListLv);
        ArrayList<File> mySongs = findFiles(Environment.getExternalStorageDirectory(), type);
        items = new String[mySongs.size()];
        for(int i = 0; i<mySongs.size(); i++){

            items[i] = mySongs.get(i).getName().toString();
        }

        lv.setAdapter(new MyBaseAdapter(context,adapter)); //add
        adapter.clear();
        getDataInList();



    }

    public void initSearch()
    {

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editText.getText().toString().toLowerCase(Locale.getDefault());
                //Log.i("editText", text);
                String type = "all";
                switch (tabSelected) {
                    case 0:
                        type = "all";
                       break;
                    case 1:
                        type = "music";
                        break;
                    case 2:
                        type = "video";
                        break;
                }

               ArrayList<File> mySongs = findFiles(Environment.getExternalStorageDirectory(), type);
                search(mySongs, text);

                editText.setImeActionLabel("Custom text", KeyEvent.KEYCODE_ENTER);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub

            }
        });


    }

    public  void search(ArrayList<File> mySongs , String text){

        items = new String[mySongs.size()];
        for(int i = 0; i<mySongs.size(); i++){

            if(mySongs.get(i).getName().contains(text)) {
                items[i] = mySongs.get(i).getName().toString();
            }
        }

        lv.setAdapter(new MyBaseAdapter(context,adapter)); //add
        adapter.clear();
        getDataInList();

    }

}
