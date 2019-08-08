package com.example.vacationdeals;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor spEditor;
        Context context;
        private static final String FIRST_LAUNCH = "firstLaunch";
        private static final String LOGEDIN= "logedIn";
        int MODE = 0;
        private static final String PREFERENCE = "RichVacations";

        public PrefManager(Context context) {
            this.context = context;
            sharedPreferences = context.getSharedPreferences(PREFERENCE, MODE);
            spEditor = sharedPreferences.edit();
        }
        public void setLogedIn(boolean loged){
            spEditor.putBoolean(FIRST_LAUNCH, loged);
            spEditor.commit();
        }

        public void setFirstTimeLaunch(boolean isFirstTime) {
            spEditor.putBoolean(LOGEDIN, isFirstTime);
            spEditor.commit();
        }

        public boolean FirstLaunch() {
            return sharedPreferences.getBoolean(FIRST_LAUNCH, true);
        }
        public boolean Logged(){return sharedPreferences.getBoolean(LOGEDIN,false);}//default value set to false.
}
