package ua.gvv.studentlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gvv on 03.11.16.
 */

public class FragmentGooglePlusInfo extends Fragment implements Callback {
    private String LOG_TAG = "FragmentGooglePlusInfo";
    private String user = "";
    private String API_KEY = "AIzaSyB50K0_1LI45_Wn7_fhl7_AfungsUr52J8";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_plus_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle extras = getArguments();
        if (extras != null) {
            user = extras.getString(ActivityDetail.USER);
        }
        try {
            requestInfo(user);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e(LOG_TAG, "Error", e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        String jsonInfo = response.body().string();
        Gson gson = new Gson();
        final GooglePlusUser googlePlusUser = gson.fromJson(jsonInfo, GooglePlusUser.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDetailInfo(googlePlusUser);

            }
        });
    }

    private void showDetailInfo(GooglePlusUser googlePlusUser) {
        View root = getView();
        TextView view = (TextView) root.findViewById(R.id.google_plus_user_name);
        view.setText(googlePlusUser.name);

        view = (TextView) root.findViewById(R.id.google_plus_user_gender);
        view.setText(googlePlusUser.gender);

        ImageView avatar = (ImageView) root.findViewById(R.id.google_plus_user_avatar);
        new FragmentGooglePlusInfo.DownLoadImageTask(avatar).execute(getAvatarUrl(googlePlusUser.image.avatarUrl, 200));
    }

    private String getAvatarUrl(String defaultUrl, int size) {
        HttpUrl url = HttpUrl.parse(defaultUrl)
                .newBuilder()
                .removeAllQueryParameters("sz")
                .addQueryParameter("sz", new Integer(size).toString())
                .build();
        return url.toString();
    }

    public void requestInfo(String user) throws IOException {
        HttpUrl url = HttpUrl.parse("https://www.googleapis.com/plus/v1/people/")
                .newBuilder()
                .addPathSegment(user)
                .addQueryParameter("key", API_KEY)
                .build();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(this);
    }

    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}
