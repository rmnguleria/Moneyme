package com.gb.raman.moneyme;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gb.raman.moneyme.enumerations.DBS;
import com.gb.raman.moneyme.helperclass.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExpenseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    public void saveExpense(View v){
        SQLiteDatabase db = new DatabaseHelper(getApplicationContext()).getWritableDatabase();

        ContentValues values = new ContentValues();
        Log.v("I AM HERE","SUCKERS");
        // Money Spend
        EditText moneySpend = (EditText)findViewById(R.id.moneySpend);
        double money = Double.parseDouble(moneySpend.getText().toString());
        values.put(DBS.Spend.toString(),money);

        Log.v("I AM HERE",Double.toString(money));

        // Current Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String current_Date = dateFormat.format(new Date()).toString();
        values.put(DBS.Created_Date.toString(),current_Date);

        Log.v("I AM HERE",current_Date);

        // Category
        Spinner categorySpinner = (Spinner)findViewById(R.id.category_spinner);
        String category = categorySpinner.getSelectedItem().toString();
        values.put(DBS.Category.toString(),category);

        Log.v("I AM HERE",category);

        //Description
        String notes = ((EditText)findViewById(R.id.notes)).getText().toString();
        values.put(DBS.Description.toString(),notes);

        Log.v("I AM HERE",notes);

        long newRowId ;
        newRowId = db.insert(DBS.log_table.toString(),null,values);
        Log.v("NEW ROW ID IS",Long.toString(newRowId));
        Cursor c = db.query(DBS.log_table.toString(),new String[]{DBS.Category.toString(),DBS.Spend.toString()},"rowid=?",new String[]{Long.toString(newRowId)},null,null,null);
        while(c.moveToNext()){
            Log.v("TEST ME",c.getString(c.getColumnIndex(DBS.Category.toString())));
        }
        c.close();
        Toast.makeText(getApplicationContext(),"Saved FLana",Toast.LENGTH_LONG).show();

        Intent tempIntent = new Intent(getApplicationContext(),MyActivity.class);
        startActivity(tempIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expense, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            View rootView = inflater.inflate(R.layout.fragment_expense, container, false);
            DatabaseHelper mdHelper = new DatabaseHelper(getActivity());
            SQLiteDatabase db = mdHelper.getReadableDatabase();

            String[] projection = {"Category"};

            Log.v("Hello", "World ||| \\\\\\");

            Cursor c  = db.query(DBS.category_table.toString(),projection,null,null,null,null,null);

            //  c.moveToFirst();
            List<String> labels = new ArrayList<String>();
            while(c.moveToNext()){
                labels.add(c.getString(c.getColumnIndex(DBS.Category.toString())));
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,labels);
            Spinner spinner = (Spinner)rootView.findViewById(R.id.category_spinner);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
            return rootView;
        }
    }
}
