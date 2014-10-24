package com.gb.raman.moneyme.helperclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gb.raman.moneyme.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by raman on 24/10/14.
 */
public class CustomArrayAdapter extends ArrayAdapter<ExpenseObject> {

    private final Context context;
    private final ExpenseObject[] values;

    public CustomArrayAdapter(Context context,ExpenseObject[] values){
        super(context, R.layout.row_layout,values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        TextView upperView = (TextView) rowView.findViewById(R.id.Upper);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        if (values[position].category.equalsIgnoreCase("Food")) {
            imageView.setImageResource(R.drawable.food);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher);
        }

        upperView.setText("Expenditure" + values[position].category + ":" + values[position].notes);

        TextView lowerView = (TextView) rowView.findViewById(R.id.Lower);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = dateFormat.parse(values[position].current_Date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            String[] Week = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

            int day = cal.get(Calendar.DAY_OF_WEEK);

            int dayMonth = cal.get(Calendar.DAY_OF_MONTH);

            lowerView.setText("Expenditure" + values[position].spendMoney + Week[day - 1] + " " + dayMonth);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
            return rowView;
    }
}
