package ua.gvv.studentlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ActivityDetail extends AppCompatActivity {
    public static final String API_TYPE = "api_type";
    public static final int API_WRONG = 0;
    public static final int API_GIT_HUB = 1;
    public static final int API_GOOGLE_PLUS = 2;

    public static final String USER = "user";

    private int apiType = API_WRONG;
    private String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if ((intent != null) && (intent.hasExtra(API_TYPE))) {
            apiType = intent.getIntExtra(API_TYPE, API_WRONG);
            user = intent.getStringExtra(USER);
        }
        if (apiType != API_WRONG) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment;
            Bundle bundle = new Bundle();
            bundle.putString(USER, user);
            if (apiType == API_GIT_HUB) {
                fragment = new FragmentGitHubInfo();
                fragment.setArguments(bundle);
                transaction.add(R.id.activity_detail, fragment, "CurrentFragment").commit();
            } else if (apiType == API_GOOGLE_PLUS) {
                fragment = new FragmentGooglePlusInfo();
                fragment.setArguments(bundle);
                transaction.add(R.id.activity_detail, fragment, "CurrentFragment").commit();
            }
        }
    }

}
