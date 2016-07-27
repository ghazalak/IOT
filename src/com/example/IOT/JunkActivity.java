package com.example.IOT;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class JunkActivity extends Activity {
    @Override
    public void onCreate(Bundle bdl) {
        super.onCreate(bdl);
        setContentView(R.layout.zzzzzzsetting_layout);
        ListView listView = (ListView) findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();
        View row = inflater.inflate(R.layout.zzzzzzlist_row_setting, null, true);
        TextView textView = (TextView) row.findViewById(R.id.name);
        textView.setText(" ‰Ÿ?„« ");
        textView.setText("ê—ÊÂ Â«");

    }
}
