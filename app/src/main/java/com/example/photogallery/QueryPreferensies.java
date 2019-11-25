package com.example.photogallery;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;

public class QueryPreferensies {
    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String LAST_RESULT_ID = "lastResultId";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_SEARCH_QUERY, null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

    public static String getLastResultId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(LAST_RESULT_ID, null);
    }

    public static void setLastResultId(Context context, String lastResId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(LAST_RESULT_ID, lastResId)
                .apply();
    }
}
