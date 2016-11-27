package ua.gvv.studentlist;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGitHubInfo extends Fragment {
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        service.getData(user).enqueue(new Callback<GitHubUser>() {
                                          @Override
                                          public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                                              GitHubUser gitHubUser = response.body();
                                              if (gitHubUser != null) {
                                                  showDetailInfo(gitHubUser);
                                              }
                                          }

                                          @Override
                                          public void onFailure(Call<GitHubUser> call, Throwable t) {

                                          }
                                      }
        );
    }

    private void showDetailInfo(final GitHubUser gitHubUser) {
        View root = getView();

        ImageView avatar = (ImageView) root.findViewById(R.id.git_hub_user_avatar);
        Picasso.with(root.getContext()).load(gitHubUser.getAvatarUrl()).transform(new CropCircleTransformation()).into(avatar);

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
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gitHubUser.getUrl()));
                v.getContext().startActivity(intent);
            }
        });
    }

    public interface GitHubService {
        public static final String BASE_URL = "https://api.github.com/";

        @GET("users/{user}")
        Call<GitHubUser> getData(@Path("user") String user);
    }

}
