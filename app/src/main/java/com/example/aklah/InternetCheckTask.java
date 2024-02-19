package com.example.aklah;

import android.os.AsyncTask;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InternetCheckTask extends AsyncTask<Void, Void, Boolean> {

    private InternetCheckListener listener;

    public interface InternetCheckListener {
        void onInternetCheckResult(boolean isInternetAvailable);
    }

    public InternetCheckTask(InternetCheckListener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://www.google.com")
                    .build();

            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (Exception e) {
            // Log error
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean isInternetAvailable) {
        if (listener != null) {
            listener.onInternetCheckResult(isInternetAvailable);
        }
    }
}
