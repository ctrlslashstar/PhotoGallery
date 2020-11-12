package org.maktab.photogallery.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class QueryPreferences {
    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_LAST_ID = "lastId";

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

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}
