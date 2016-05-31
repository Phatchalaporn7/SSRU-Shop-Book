package ssru.phatchalaporn.ssrushopbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Pc on 31/5/2559.
 */
public class MyManage {

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public  MyManage(Context context) {

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getReadableDatabase();

    } //Constructor
}//Main class
