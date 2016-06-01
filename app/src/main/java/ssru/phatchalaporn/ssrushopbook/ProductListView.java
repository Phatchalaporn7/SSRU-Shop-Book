package ssru.phatchalaporn.ssrushopbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ProductListView extends AppCompatActivity {

    //ประการศตัวแปร
    private TextView nametextView, surnameTextView, moneyTextView;
    private ListView listView;
    private String[] loginStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_view);

        //Bind Widget
        nametextView = (TextView) findViewById(R.id.textView7);
        surnameTextView = (TextView) findViewById(R.id.textView8);
        moneyTextView = (TextView) findViewById(R.id.textView9);
        listView = (ListView) findViewById(R.id.listView);

        //Receive Value From Intent
        loginStrings = getIntent().getStringArrayExtra("Login");

        //show View
        nametextView.setText(loginStrings[1]);
        surnameTextView.setText(loginStrings[2]);
        moneyTextView.setText(loginStrings[5] + "THB.");


    }// Main Method

}//Main Class
