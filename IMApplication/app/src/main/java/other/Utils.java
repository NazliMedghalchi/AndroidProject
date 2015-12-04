package other;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Statement;

/**
 * Created by nazlimedghalchi on 2015-11-14.
 */
/*Ref:http://www.androidhive.info/2014/10/android-building-group-chat-app-using-sockets-part-2*/
public class Utils {
    private Context context;
    private SharedPreferences sharedPreferences;

    private static final String KEY_SHARED_PREF = "ANDROID_WEB_CHAT";
    private static final int KEY_MODE_PRIVATE = 0;
    private static final String KEY_SESSION_ID = "sessionId",
                                FLAG_MESSAGE = "message";
    public Utils (Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(KEY_SHARED_PREF, KEY_MODE_PRIVATE);
    }

    public void storeSessionId (String sessionId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SESSION_ID, sessionId);
        editor.commit();
    }
    public String getSessionId() {
        return sharedPreferences.getString(KEY_SESSION_ID, null);
    }
    public String getSendMessageJSON(String message) {
        String json = null;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag", FLAG_MESSAGE);
            jsonObject.put("sessionId", getSessionId());
            jsonObject.put("message", message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


}
