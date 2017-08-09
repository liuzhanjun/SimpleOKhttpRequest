package liu.zhan.jun.simplerequest.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yunniu on 2017/8/9.
 */

public class StoreSp {
    public final  static String CONFIG = "Token";
    public final static String MAC_ADRESS="mac_adress";
    public static void storeMacAdress(Context context, String macAdress){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MAC_ADRESS,macAdress);
        editor.commit();
    }
    public static String getMacAdress(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MAC_ADRESS, "");
    }
}
