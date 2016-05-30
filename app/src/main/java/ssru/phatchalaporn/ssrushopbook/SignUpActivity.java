package ssru.phatchalaporn.ssrushopbook;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    //Explicit ประกาศตัวเเปร
    private EditText nameEditText, surEditText, userEditText, passwordEditText;
    private String nameString, surnameString, userString, passwordString;
    private static final String urlUpload = "http://swiftcodingthai.com/ssru/add_user_milk.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget การผูกตัวแปรที่ประกาศไว้
        nameEditText = (EditText) findViewById(R.id.editText);
        surEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);


    } //Main Method

    public void clickSingUpSign(View view){
        nameString = nameEditText.getText().toString().trim();
        surnameString = surEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space ตรวจสอบว่ามีการกรอกครบไหม
        if (nameString.equals("") || surnameString.equals("") ||
                userString.equals("") ||passwordString.equals("")) {
            //Have Space เงื่อนไขที่มีช่องว่าง
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณาตอบทุกช่อง ค่ะ!!");


        } else {

            //No Space เงื่อนไขไม่มีช่องว่าง
            uploadNewUser();        }




    }// clickSign

    private void uploadNewUser() {



    }//uploadNewUser

}//Main class
