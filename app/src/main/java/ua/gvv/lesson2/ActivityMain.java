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

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {
    private FragmentManager manager;
    private FragmentListView fragList;
    private FragmentRecyclerView fragRecycler;

    private ArrayList<Student> students = new ArrayList<Student>(20);

    private int current = 0;

    private void FillStudentsList() {
        students.add(new Student("Valerii Gubskyi", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FillStudentsList();

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putParcelableArrayList("StudentsList", students);
            Fragment fragment = new FragmentListView();
            fragment.setArguments(args);

            manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.activity_main, fragment)
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
                Bundle args = new Bundle();
                args.putParcelableArrayList("StudentsList", students);
                Fragment fragment = (Fragment) manager.findFragmentById(getNextFragmentId());

                if (fragment == null) {
                    if (current == 0) {
                        fragment = new FragmentRecyclerView();
                        current = 1;
                    } else {
                        fragment = new FragmentListView();
                        current = 0;
                    }
                    fragment.setArguments(args);
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
