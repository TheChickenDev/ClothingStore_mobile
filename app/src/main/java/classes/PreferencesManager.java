package classes;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    Context context;
    public PreferencesManager(Context context) {
        this.context = context;
    }
    public void saveLoginDetails(String access_token, String refresh_token, String email, String password, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Access_token", access_token);
        editor.putString("Refresh_token", refresh_token);
        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.putString("Id", id);
        editor.apply();
    }
    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }
    public String getPassword() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Password", "");
    }
    public String getId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Id", "");
    }
    public boolean isUserLoggedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        boolean isIdEmpty = sharedPreferences.getString("Id", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty || isIdEmpty;
    }
}
