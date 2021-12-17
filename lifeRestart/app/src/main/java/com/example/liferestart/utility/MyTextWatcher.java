package com.example.liferestart.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyTextWatcher implements TextWatcher {
    private int allAttr;
    private EditText attr1, attr2, attr3;
    private TextView leftAttr;

    public MyTextWatcher(int allAttr, EditText attr1, EditText attr2, EditText attr3, TextView leftAttr) {
        this.allAttr = allAttr;
        this.attr1 = attr1;
        this.attr2 = attr2;
        this.attr3 = attr3;
        this.leftAttr = leftAttr;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int attrValue1 = getData(attr1);
        int attrValue2 = getData(attr2);
        int attrValue3 = getData(attr3);
        int attrValue = getData(editable);
        if(attrValue>10){
            Toast.makeText(attr1.getContext(), "属性值不能超过10", Toast.LENGTH_SHORT).show();
            int temp = allAttr-attrValue1-attrValue2-attrValue3-attrValue;
            leftAttr.setText(""+temp);
        }
        else if(attrValue+attrValue1+attrValue2+attrValue3>allAttr){
            Toast.makeText(attr1.getContext(), "没有可分配的点数了", Toast.LENGTH_SHORT).show();
            int temp = allAttr-attrValue1-attrValue2-attrValue3-attrValue;
            leftAttr.setText(""+temp);
        }
        else{
            int temp = allAttr-attrValue1-attrValue2-attrValue3-attrValue;
            //Toast.makeText(attr1.getContext(), "剩余点数: "+temp, Toast.LENGTH_SHORT).show();
            leftAttr.setText(""+temp);
        }
    }

    public int getData(EditText editText){
        int data;
        try{
            data = Integer.parseInt(editText.getText().toString());
        }catch (NumberFormatException e){
            data = 0;
        }
        return data;
    }
    public int getData(Editable editable){
        int data;
        try{
            data = Integer.parseInt(editable.toString());
        }catch (NumberFormatException e){
            data = 0;
        }
        return data;
    }
}
