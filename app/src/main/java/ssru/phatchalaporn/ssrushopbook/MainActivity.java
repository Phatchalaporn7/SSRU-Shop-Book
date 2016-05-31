package ssru.phatchalaporn.ssrushopbook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManage myManage;
    private static final String urlJSON ="http://swiftcodingthai.com/ssru/get_user_milk.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request SQLite
        myManage = new MyManage(MainActivity.this);

        //Test Add Value to SQLite
        //myManage.addNewUser("name", "sur", "user", "pass", "money");

        //Delete All userTABLE
        deleteAlluserTABLE();

        synJSONtoSQLite();

    } //Main Method

    private void synJSONtoSQLite() {
        ConnectedUserTABLE connectedUserTABLE = new ConnectedUserTABLE();
        connectedUserTABLE.execute();
    }

    public class ConnectedUserTABLE extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("31May", "my Error ==> " + e.toString());
                return null;
            }

        }//doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.d("31May", "JSON ==> " + s);

                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strName = jsonObject.getString(MyManage.column_name);
                    String strSurname = jsonObject.getString(MyManage.column_surname);
                    String strUser = jsonObject.getString(MyManage.column_user);
                    String strPassword = jsonObject.getString(MyManage.column_password);
                    String strMoney = jsonObject.getString(MyManage.column_money);

                    myManage.addNewUser(strName, strSurname, strUser, strPassword, strMoney);
                }//for

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    } //Connect Class


    private void deleteAlluserTABLE() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);

    }//delete

    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this, SignUpActivity.class));
    }

}   //Main Class
