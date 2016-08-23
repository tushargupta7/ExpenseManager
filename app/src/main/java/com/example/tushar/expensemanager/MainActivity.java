package com.example.tushar.expensemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tushar.expensemanager.db.DatabaseManager;
import com.example.tushar.expensemanager.fragment.AddTransactionFragment;
import com.example.tushar.expensemanager.fragment.SelectCategoryFragment;
import com.example.tushar.expensemanager.fragment.TransactionDetailsFragment;
import com.example.tushar.expensemanager.fragment.TransactionListFragment;
import com.example.tushar.expensemanager.model.Category;
import com.example.tushar.expensemanager.model.Tag;
import com.example.tushar.expensemanager.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,AddTransactionFragment.OnFragmentInteractionListener,TransactionListFragment.OnFragmentInteractionListener,TransactionDetailsFragment.OnFragmentInteractionListener,SelectCategoryFragment.OnListFragmentInteractionListener{
    private static final String TAG = "Main Activity";
    public   Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                CustomDialog();

            }
        });
        DatabaseManager.init(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void CustomDialog() {

            dialog = new Dialog(MainActivity.this);
            // it remove the dialog title
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // set the laytout in the dialog
            dialog.setContentView(R.layout.dialogbox);
            // set the background partial transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Window window = dialog.getWindow();
            WindowManager.LayoutParams param = window.getAttributes();
            // set the layout at right bottom
            param.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            // it dismiss the dialog when click outside the dialog frame
            dialog.setCanceledOnTouchOutside(true);
            // initialize the item of the dialog box, whose id is demo1
        RelativeLayout addButton=(RelativeLayout)dialog.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

               final Fragment fragment= getFragmentManager().findFragmentById(R.id.fragment_container);
                if(fragment!=null && fragment.getTag().equalsIgnoreCase("CAT_LIST")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    // Get the layout inflater
                    LayoutInflater inflater = getLayoutInflater();

                    // Inflate and set the layout for the dialog
                    // Pass null as the parent view because its going in the dialog layout
                    final View view1=inflater.inflate(R.layout.custom_dialog,null);
                    builder.setView(view1)
                            // Add action buttons
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                  String name=  ((EditText)view1.findViewById(R.id.add_category_name)).getText().toString();
                                    if(((SelectCategoryFragment)fragment).getmType().equalsIgnoreCase("category")){
                                    Category category=new Category();
                                    category.setCategoryName(name);
                                    DatabaseManager.getInstance().addCategory(category);}
                                    else {
                                        Tag tag=new Tag();
                                        tag.setTagName(name);
                                        DatabaseManager.getInstance().addTags(tag);
                                    }
                                    ((SelectCategoryFragment)fragment).updateList();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            }).show();

                }
                else
                loadAddFragment(null);
            }
        });




            View demodialog =(View) dialog.findViewById(R.id.cross);
            // it call when click on the item whose id is demo1.
            demodialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // diss miss the dialog
                    dialog.dismiss();
                }
            });

            // it show the dialog box
            dialog.show();


    }


    private void loadAddFragment(Transaction item) {
        Log.d(TAG, "mainActivity: removing Transactions");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddTransactionFragment fragment = AddTransactionFragment.newInstance(this,item);
        fragmentTransaction.replace(R.id.fragment_container, fragment,"ADD_FRAG");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_transactions) {
            loadTransactionListFragment();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void loadTransactionListFragment() {
        Log.d(TAG, "mainActivity: onplaceselected");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TransactionListFragment fragment = TransactionListFragment.newInstance(this);
        fragmentTransaction.replace(R.id.fragment_container, fragment,"TLIST_FRAG");
        fragmentTransaction.commit();
    }

    @Override
    public void loadCategoryTagList(String type) {
        Log.d(TAG, "mainActivity: onplaceselected");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SelectCategoryFragment fragment = SelectCategoryFragment.newInstance(1,type,this);
        fragmentTransaction.addToBackStack("CAT_LIST");
        fragmentTransaction.replace(R.id.fragment_container, fragment,"CAT_LIST");
        fragmentTransaction.commit();
    }

    @Override
    public void showTransactionDetailFragment(Transaction item) {
        Log.d(TAG, "mainActivity: load Tansaction details");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TransactionDetailsFragment fragment = TransactionDetailsFragment.newInstance(item,this);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDeleted() {
        Toast.makeText(this,getResources().getString(R.string.deleted_transaction),Toast.LENGTH_SHORT).show();
     loadTransactionListFragment();
    }

    @Override
    public void onEditing(Transaction item) {
        loadAddFragment(item);
    }

    @Override
    public void onCategoryTagsSelected(List<String> selected,String type) {
        onBackPressed();
        AddTransactionFragment fr=(AddTransactionFragment)(getFragmentManager().findFragmentByTag("ADD_FRAG"));
        fr.setTypeAndSelected(type, (ArrayList<String>) selected);
    }
}