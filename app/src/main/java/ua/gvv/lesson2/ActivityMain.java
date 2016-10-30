package ua.gvv.lesson2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ActivityMain extends AppCompatActivity {
    private FragmentManager manager;
    private FragmentListView fragList;
    private FragmentRecyclerView fragRecycler;

    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.activity_main, new FragmentListView())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private int getNextFragmentId() {
        return (current == 0) ? R.id.recyclerview_lesson2 : R.id.listview_lesson2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_switch:
                Log.v("ActivityMain", "SWITCH");
                Log.v("ActivityMain", "SWITCH:" + getNextFragmentId());
                Fragment fragment = (Fragment) manager.findFragmentById(getNextFragmentId());
                if (fragment == null) {
                    if (current == 0) {
                        fragment = new FragmentRecyclerView();
                        current = 1;
                    } else {
                        fragment = new FragmentListView();
                        current = 0;
                    }

                    Log.v("ActivityMain", "SWITCH111");
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.activity_main, fragment)
                            .commit();

                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
