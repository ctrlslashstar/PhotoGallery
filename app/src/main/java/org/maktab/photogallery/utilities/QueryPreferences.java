package org.maktab.photogallery.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class QueryPreferences {
    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_LAST_ID = "lastId";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";

    public static String getSearchQuery(Context context) {
        return getSharedPreferences(context).getString(PREF_SEARCH_QUERY, null);
    }

    public static void setSearchQuery(Context context, String query) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

    public static String getLastId(Context context) {
        return getSharedPreferences(context).getString(PREF_LAST_ID, null);
    }

    public static void setLastId(Context context, String lastId) {
        getSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_ID, lastId)
                .apply();
    }

    public static boolean isAlarmOn(Context context) {
        return getSharedPreferences(context).getBoolean(PREF_IS_ALARM_ON, false);
    }

    public static void setIsAlarmOn(Context context, boolean isAlarmOn) {
        getSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON, isAlarmOn)
                .apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}
