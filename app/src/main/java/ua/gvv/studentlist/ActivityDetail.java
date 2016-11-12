package ua.gvv.studentlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class ActivityDetail extends AppCompatActivity {
    public static final String DETAIL_TYPE = "detail_type";
    public static final int DETAIL_TYPE_WRONG = 0;
    public static final int DETAIL_GIT_HUB = 1;
    public static final int DETAIL_GOOGLE_PLUS = 2;
    public static final int DETAIL_IMAGE_SELECTOR = 3;

    public static final String USER = "user";

    private int apiType = DETAIL_TYPE_WRONG;
    private String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if ((intent != null) && (intent.hasExtra(DETAIL_TYPE))) {
            apiType = intent.getIntExtra(DETAIL_TYPE, DETAIL_TYPE_WRONG);
            user = intent.getStringExtra(USER);
        }
        if (apiType != DETAIL_TYPE_WRONG) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment;
            Bundle bundle = new Bundle();
            bundle.putString(USER, user);
            if (apiType == DETAIL_GIT_HUB) {
                fragment = new FragmentGitHubInfo();
                fragment.setArguments(bundle);
                transaction.add(R.id.activity_detail, fragment, "CurrentFragment").commit();
            } else if (apiType == DETAIL_GOOGLE_PLUS) {
                fragment = new FragmentGooglePlusInfo();
                fragment.setArguments(bundle);
                transaction.add(R.id.activity_detail, fragment, "CurrentFragment").commit();
            } else if (apiType == DETAIL_IMAGE_SELECTOR) {
                fragment = new FragmentImageSelector();
                transaction.add(R.id.activity_detail, fragment, "CurrentFragment").commit();
            }

        }
    }

}
