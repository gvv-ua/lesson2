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
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ua.gvv.studentlist.data.GooglePlusUser;

/**
 * Created by gvv on 03.11.16.
 */

public class FragmentGooglePlusInfo extends Fragment {
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FragmentGooglePlusInfo.GooglePlusService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GooglePlusService service = retrofit.create(GooglePlusService.class);

        service.getData(user, API_KEY).enqueue(new Callback<GooglePlusUser>() {
            @Override
            public void onResponse(Call<GooglePlusUser> call, Response<GooglePlusUser> response) {
                GooglePlusUser googlePlusUser = response.body();
                if (googlePlusUser != null) {
                    showDetailInfo(googlePlusUser);
                }
            }

            @Override
            public void onFailure(Call<GooglePlusUser> call, Throwable t) {

            }
        });

    }

    private void showDetailInfo(final GooglePlusUser googlePlusUser) {
        View root = getView();

        ImageView avatar = (ImageView) root.findViewById(R.id.google_plus_user_avatar);
        //new FragmentGooglePlusInfo.DownLoadImageTask(avatar).execute(getAvatarUrl(googlePlusUser.getImage().getAvatarUrl(), 200));
        Picasso.with(root.getContext()).load(getAvatarUrl(googlePlusUser.getImage().getAvatarUrl(), 200)).transform(new CropCircleTransformation()).into(avatar);

        TextView view = (TextView) root.findViewById(R.id.google_plus_user_name);
        view.setText(googlePlusUser.getName());

        view = (TextView) root.findViewById(R.id.google_plus_user_gender);
        view.setText(googlePlusUser.getGender());

        Button button = (Button) root.findViewById(R.id.google_plus_open_url);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googlePlusUser.getUrl()));
                v.getContext().startActivity(intent);
            }
        });
    }

    private String getAvatarUrl(String defaultUrl, int size) {
        HttpUrl url = HttpUrl.parse(defaultUrl)
                .newBuilder()
                .removeAllQueryParameters("sz")
                .addQueryParameter("sz", new Integer(size).toString())
                .build();
        return url.toString();
    }

    public interface GooglePlusService {
        public static final String BASE_URL = "https://www.googleapis.com/";

        @GET("plus/v1/people/{user}")
        retrofit2.Call<GooglePlusUser> getData(@Path("user") String user, @Query("key") String apiKey);
    }
}
