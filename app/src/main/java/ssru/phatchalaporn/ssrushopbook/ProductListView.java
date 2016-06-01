package ssru.phatchalaporn.ssrushopbook;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ProductListView extends AppCompatActivity {

    //ประการศตัวแปร
    private TextView nametextView, surnameTextView, moneyTextView;
    private ListView listView;
    private String[] loginStrings,nameStrings,priceStrings, coverStrings, eBookStrings;
    private String urlJSON = "http://swiftcodingthai.com/ssru/get_product.php";
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

        SynchronizProduct synchronizgProduct = new SynchronizProduct(this, urlJSON);
        synchronizgProduct.execute();
    }// Main Method

    private class SynchronizProduct extends AsyncTask<Void, Void, String> {

        private Context context;
        private String urlString;
        private ProgressDialog progressDialog;


        public SynchronizProduct(Context context,
                                  String urlString) {
            this.context = context;
            this.urlString = urlString;
        }//Constructor

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(context, "Load Product",
                    "Load Product Process ...");

        } //onPre

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlString).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("1JuneV1", "doIn e ==>" + e.toString());
                return null;
            }


        } //doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() != 0) {
                progressDialog.dismiss();
                Log.d("1JuneV1", "s ==>" + s);
            }

            try {

                JSONArray jsonArray = new JSONArray(s);

                nameStrings = new String[jsonArray.length()];
                priceStrings = new String[jsonArray.length()];
                coverStrings = new String[jsonArray.length()];
                eBookStrings = new String[jsonArray.length()];

                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    nameStrings[i] = jsonObject.getString("Name");
                    priceStrings[i] = jsonObject.getString("Price");
                    coverStrings[i] = jsonObject.getString("Cover");
                    eBookStrings[i] = jsonObject.getString("Ebook");
                }//for

                MyAdapter myAdapter = new MyAdapter(context, nameStrings,
                        priceStrings, coverStrings);
                listView.setAdapter(myAdapter);

            } catch (Exception e) {
                Log.d("1JuneV2", "onPost e ==> " + e.toString());
            }

        }// onPost
    }



}//Main Class
