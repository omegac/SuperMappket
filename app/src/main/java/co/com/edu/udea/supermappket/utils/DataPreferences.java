package co.com.edu.udea.supermappket.utils;

        import android.app.Activity;
        import android.content.Context;
        import android.content.SharedPreferences;

public class DataPreferences extends Activity{
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PREFS_LOGIN = "prefsLogin";
    public static final String PREFS_USERID = "UserId";


    Context aContext;
    //
    SharedPreferences pref;
    SharedPreferences.Editor spEditor;

    public DataPreferences(Context cIn) {
        aContext = cIn;
    }

    public void setLoggedIn(boolean sLogin) {
        pref = aContext.getSharedPreferences(PREFS_NAME, Activity.MODE_APPEND);
        spEditor = pref.edit();
        spEditor.putBoolean(PREFS_LOGIN, sLogin);
        spEditor.commit();
    }

    public boolean getLoggedIn() {
        pref = aContext.getSharedPreferences(PREFS_NAME, 0);
        return (boolean) pref.getBoolean(PREFS_LOGIN, false);
    }

    public void setUserId(int userId){
        pref = aContext.getSharedPreferences(PREFS_NAME, Activity.MODE_APPEND);
        spEditor = pref.edit();
        spEditor.putInt(PREFS_USERID, userId);
        spEditor.commit();
    }

    public int getUserId() {
        pref = aContext.getSharedPreferences(PREFS_NAME, 0);
        return (int) pref.getInt(PREFS_USERID, 0);
    }

}

