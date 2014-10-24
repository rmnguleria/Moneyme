package com.gb.raman.moneyme;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gb.raman.moneyme.enumerations.DBS;
import com.gb.raman.moneyme.helperclass.CustomArrayAdapter;
import com.gb.raman.moneyme.helperclass.DatabaseHelper;
import com.gb.raman.moneyme.helperclass.ExpenseObject;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void createNewExpense(View v){
        Intent intent = new Intent(this,ExpenseActivity.class);
        startActivity(intent);
    }

    public void testMe(View v){
        DatabaseHelper mdHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = mdHelper.getWritableDatabase();

        String[] projection = {"Category"};

        Log.v("Hello","World");

        Cursor c  = db.query("category_table",projection,null,null,null,null,null);

      //  c.moveToFirst();

        while(c.moveToNext()){
            Log.v("DEBUG",c.getString(c.getColumnIndex("Category")));
        }


    }

    public void createNewBill(View v){
        Intent intent = new Intent(this,BillsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.new_expense){
            onNewExpense();
            return true;
        }
        else if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNewExpense(){
        Intent intent = new Intent(this,ExpenseActivity.class);
        startActivity(intent);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);

            ListView listView = (ListView)rootView.findViewById(R.id.listview_forecast);

            List<ExpenseObject> expenseObjects = new ArrayList<ExpenseObject>();

            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor c = db.query(DBS.log_table.toString(),new String[]{DBS.Created_Date.toString(),DBS.Category.toString(),DBS.Spend.toString(),DBS.Description.toString()},null,null,null,null,null);

            while(c.moveToNext()){
                String category = c.getString(c.getColumnIndex(DBS.Category.toString()));
                String notes = c.getString(c.getColumnIndex(DBS.Description.toString()));
                Double money = c.getDouble(c.getColumnIndex(DBS.Spend.toString()));
                String created_Date = c.getString(c.getColumnIndex(DBS.Created_Date.toString()));

                expenseObjects.add(new ExpenseObject(category,notes,money,created_Date));
            }

            ExpenseObject[] tempArray = new ExpenseObject[expenseObjects.size()];

            int i = 0;

            for(ExpenseObject object : expenseObjects){
                tempArray[i] = object;
                i++;
            }

            CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(getActivity(),tempArray);

            listView.setAdapter(customArrayAdapter);

            return rootView;
        }
    }
}
