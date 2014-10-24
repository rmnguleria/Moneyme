package com.gb.raman.moneyme.helperclass;

/**
 * Created by raman on 24/10/14.
 */
public class ExpenseObject {

    public String category;
    public String notes;
    public Double spendMoney;
    public String current_Date;

    public ExpenseObject(String _category,String _notes,Double _spendMoney , String _current_Date){
        category = _category;
        notes = _notes;
        spendMoney = _spendMoney;
        current_Date = _current_Date ;
    }

}
