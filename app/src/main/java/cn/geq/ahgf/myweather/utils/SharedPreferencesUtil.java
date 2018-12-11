package cn.geq.ahgf.myweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

   private static SharedPreferences preferences;
    //保存Boolean
    public static  void saveBoolean(Context context,String key,Boolean value){
        if (preferences==null){
            preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        preferences.edit().putBoolean(key,value).commit();
    }

    public static  boolean getBoolean(Context context,String key,Boolean defValue){
        if (preferences==null){
            preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
       return preferences.getBoolean(key,defValue);
    }
    //保存String
    public static  void saveString(Context context,String key,String value){
        if (preferences==null){
            preferences = context.getSharedPreferences("config",Context.MODE_MULTI_PROCESS);//MODE_MULTI_PROCESS//MODE_MULTI_PROCESS
        }
        preferences.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key,String defValue){
        if (preferences==null){
            preferences = context.getSharedPreferences("config",Context.MODE_MULTI_PROCESS);
        }
        return preferences.getString(key,defValue);
    }

    //保存int
    public static void saveInt(Context context,String key,int value){
        if (preferences==null){
            preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        preferences.edit().putInt(key,value).commit();
    }

    public static int getInt(Context context,String key,int defValue){
        if (preferences==null){
            preferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return preferences.getInt(key,defValue);
    }

}
