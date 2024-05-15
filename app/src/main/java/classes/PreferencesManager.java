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
<<<<<<< HEAD
        boolean isAccessTokenEmpty = sharedPreferences.getString("Access_token", "").isEmpty();
        boolean isRefreshTokenEmpty = sharedPreferences.getString("Refresh_token", "").isEmpty();
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        boolean isIdEmpty = sharedPreferences.getString("Id", "").isEmpty();
        return isAccessTokenEmpty || isRefreshTokenEmpty || isEmailEmpty || isPasswordEmpty || isIdEmpty;
    }

    public void removeLoginDetails() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("Access_token");
        editor.remove("Refresh_token");
        editor.remove("Email");
        editor.remove("Password");
        editor.remove("Id");
        editor.apply();
=======
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        boolean isIdEmpty = sharedPreferences.getString("Id", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty || isIdEmpty;
>>>>>>> 7087403bfc9bb3554a30fb428e2064bd079c0c3a
    }
}
