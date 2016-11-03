package ua.gvv.lesson2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

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

    private void showDetailInfo(GitHubUser gitHubUser) {
        TextView view = (TextView)getView().findViewById(R.id.git_hub_user_name);
        view.setText(gitHubUser.name);
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
}
