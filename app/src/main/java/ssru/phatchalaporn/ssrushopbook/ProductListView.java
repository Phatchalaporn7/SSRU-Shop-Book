package ssru.phatchalaporn.ssrushopbook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.List;

public class ProductListView extends AppCompatActivity {

    //ประการศตัวแปร
    private TextView nametextView, surnameTextView, moneyTextView;
    private ListView listView;
    private String[] loginStrings,nameStrings,priceStrings, coverStrings, eBookStrings;
    private String urlJSON = "http://swiftcodingthai.com/ssru/get_product.php";
    private String moneyString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_view);

        //Bind Widget
        nametextView = (TextView) findViewById(R.id.textView7);
        surnameTextView = (TextView) findViewById( R.id.textView8);
        moneyTextView = (TextView) findViewById(R.id.textView9);
        listView = (ListView) findViewById(R.id.listView);

        //Receive Value From Intent
        loginStrings = getIntent().getStringArrayExtra("Login");
        moneyString = loginStrings[5];

        //show View
        nametextView.setText(loginStrings[1]);
        surnameTextView.setText(loginStrings[2]);
        moneyTextView.setText(loginStrings[5] + "THB.");

        SynchronizProduct synchronizgProduct = new SynchronizProduct(this, urlJSON);
        synchronizgProduct.execute();
    }// Main Method


    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d("2JuneV1", "onRestart Work");

        SynUserTABLE synUserTABLE = new SynUserTABLE();
        synUserTABLE.execute();
    }

    private class SynUserTABLE extends AsyncTask<Void, Void, String> {

        private String myResult = null;


        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("isAdd", "true")
                        .add("User", loginStrings[3])
                        .build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/ssru/get_user_where.php")
                        .post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        Log.d("2JuneV1", "response ==> " + response.body().string());



                        try {
                            JSONArray jsonArray = new JSONArray(response.body().string());
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            moneyString = jsonObject.getString("Money");

                            Log.d("2JuneV1", "moneyString ==> " + moneyString);

                            moneyTextView.setText(moneyString + "THB.");

                        } catch (Exception e) {
                            Log.d("2JuneV1", "e ==> " + e.toString());
                            e.printStackTrace();
                        }

                    }
                });

            } catch (Exception e) {
                Log.d("2JuneV1", "doIn Error ==> " + e.toString());
                return null;
            }

            return null;

        }// doInBack


    }  //class Syn

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

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                        if (checkMoney(priceStrings[i])) {

                            confirmDailog(nameStrings[i], priceStrings[i], eBookStrings[i]);

                        } else {
                            MyAlert myAlert = new MyAlert();
                            myAlert.myDialog(context, "เงินไม่พอ!!", "กรุณาเลือกเล่มใหม่ เงินไม่พอ");
                        }

                    }// onItem Click
                });

            } catch (Exception e) {
                Log.d("1JuneV2", "onPost e ==> " + e.toString());
            }

        }// onPost

        private boolean checkMoney(String priceString) {

            int intMyMoney = Integer.parseInt(moneyString);
            int intPrice = Integer.parseInt(priceString);


            if (intMyMoney >= intPrice) {
                return true;
            } else {
                return false;
            }


        }// Syn Class
    }

    private void confirmDailog(final String nameString,
                               final String priceString,
                               final String eBookString) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_myaccount);
        builder.setCancelable(false);
        builder.setTitle("Confirm Order");
        builder.setMessage(nameString + " ราคา " + priceString + " THB." + "\n" + "จริงๆ หรือ ?");
        builder.setNegativeButton("CanCel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface , int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(ProductListView.this, MapsActivity.class );
                intent.putExtra("Login", loginStrings);
                intent.putExtra("NameBook", nameString);
                intent.putExtra("PriceBook", priceString);
                intent.putExtra("urlEBook", eBookString);
                intent.putExtra("Money", moneyString);
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });
        builder.show();
        }
    }//confirm


