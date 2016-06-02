package ssru.phatchalaporn.ssrushopbook;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Explicit
    private String nameBookString, priceBookString, urlPDFString, moneyString;
    private String[] loginStrings;
    private String urlEdit = "http://swiftcodingthai.com/ssru/edit_money_master.php";
    private GoogleMap mMap;
    private double centerLat = 13.775787;
    private double centerLng = 100.508542;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
//Get Value From Intent
        nameBookString = getIntent().getStringExtra("NameBook");
        priceBookString = getIntent().getStringExtra("PriceBook");
        urlPDFString = getIntent().getStringExtra("urlEbook");
        loginStrings = getIntent().getStringArrayExtra("Login");
        moneyString = getIntent().getStringExtra("Money");

        //updateAccount


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }//Main Method

    private void updateAccount() {

        int intCurrentMoney = Integer.parseInt(moneyString);
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
    }   // updateAccount


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng latLng = new LatLng(centerLat, centerLng);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mMap.clear();

                mMap.addMarker(new MarkerOptions()
                .position(latLng));


            }// on maps Click
        });

    }// onMap
}// Main class
