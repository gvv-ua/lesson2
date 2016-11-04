package ua.gvv.studentlist;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * A simple {@link Fragment} subclass.
 */
public class FragmentGitHubInfo extends Fragment implements Callback {
    private String LOG_TAG = "FragmentGitHubInfo";
    private String user = "gvv-ua";

    public FragmentGitHubInfo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_git_hub_info, container, false);
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
        final GitHubUser gitHubUser = gson.fromJson(jsonInfo, GitHubUser.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDetailInfo(gitHubUser);

            }
        });
    }

    private void showDetailInfo(final GitHubUser gitHubUser) {
        View root = getView();

        ImageView avatar = (ImageView) root.findViewById(R.id.git_hub_user_avatar);
        new DownLoadImageTask(avatar).execute(gitHubUser.getAvatarUrl());

        TextView view = (TextView) root.findViewById(R.id.git_hub_user_name);
        view.setText(gitHubUser.getName());

        view = (TextView) root.findViewById(R.id.git_hub_user_login);
        view.setText(gitHubUser.getLogin());

        view = (TextView) root.findViewById(R.id.git_hub_user_location);
        view.setText(gitHubUser.getLocation());

        Button button = (Button) root.findViewById(R.id.git_hub_open_url);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(gitHubUser.getUrl()));
                v.getContext().startActivity(intent);
            }
        });

    }

    public void requestInfo(String user) throws IOException {
        HttpUrl url = HttpUrl.parse("https://api.github.com/users")
                .newBuilder()
                .addPathSegment(user)
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
