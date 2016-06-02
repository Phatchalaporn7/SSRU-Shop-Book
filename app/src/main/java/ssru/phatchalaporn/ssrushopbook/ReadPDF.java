package ssru.phatchalaporn.ssrushopbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class ReadPDF extends AppCompatActivity {

    //ประกาศตัวแปร
    private String nameBookString, priceBookString, urlPDFString;
    private String[] loginStrings;
    private String urlEdit = "http://swiftcodingthai.com/ssru/edit_money_nam.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pdf);

        //Get Value From Intent
        nameBookString = getIntent().getStringExtra("NameBook");
        priceBookString = getIntent().getStringExtra("PriceBook");
        urlPDFString = getIntent().getStringExtra("urlEbook");
        loginStrings = getIntent().getStringArrayExtra("Login");

        //updateAccount
        updateAccount();

    }//Main Method

    private void updateAccount() {

        int intCurrentMoney = Integer.parseInt(loginStrings[5]);
        int intPriceBook = Integer.parseInt(priceBookString);
        int intMyMoney = intCurrentMoney - intPriceBook;

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("User", loginStrings[3])
                .add("Money", Integer.toString(intMyMoney))
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(urlEdit).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }//updateAccount


}//Main Class