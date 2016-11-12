package ua.gvv.studentlist;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class ActivityDetail extends AppCompatActivity {
    public static final String DETAIL_TYPE = "detail_type";
    public static final int DETAIL_TYPE_WRONG = 0;
    public static final int DETAIL_GIT_HUB = 1;
    public static final int DETAIL_GOOGLE_PLUS = 2;
    public static final int DETAIL_IMAGE_SELECTOR = 3;

    public static final String USER = "user";

    private int detailType = DETAIL_TYPE_WRONG;
    private String user = "";

    private HeadsetReceiver headsetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null) {
            Uri data = intent.getData();
            if (data != null) {
                parseData(data);
            } else  if (intent.hasExtra(DETAIL_TYPE)){
                detailType = intent.getIntExtra(DETAIL_TYPE, DETAIL_TYPE_WRONG);
                user = intent.getStringExtra(USER);
            }
        }
        if (detailType != DETAIL_TYPE_WRONG) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment;
            Bundle bundle = new Bundle();
            bundle.putString(USER, user);
            if (detailType == DETAIL_GIT_HUB) {
                fragment = new FragmentGitHubInfo();
                fragment.setArguments(bundle);
                transaction.add(R.id.activity_detail, fragment, "CurrentFragment").commit();
            } else if (detailType == DETAIL_GOOGLE_PLUS) {
                fragment = new FragmentGooglePlusInfo();
                fragment.setArguments(bundle);
                transaction.add(R.id.activity_detail, fragment, "CurrentFragment").commit();
            } else if (detailType == DETAIL_IMAGE_SELECTOR) {
                fragment = new FragmentImageSelector();
                transaction.add(R.id.activity_detail, fragment, "CurrentFragment").commit();
            }
        }
        headsetReceiver = new HeadsetReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(headsetReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetReceiver, filter);
    }

    private void parseData(Uri data) {
        List<String> path = data.getPathSegments();
        String host = data.getHost();
        if (path.size() == 1) {
            if (host.toLowerCase().equals(getString(R.string.host_name_git_hub))) {
                this.user = path.get(0);
                this.detailType = DETAIL_GIT_HUB;
            } else if (host.toLowerCase().equals(getString(R.string.host_name_google_plus))) {
                this.user = path.get(0);
                this.detailType = DETAIL_GOOGLE_PLUS;
            }
        }
    }
}
