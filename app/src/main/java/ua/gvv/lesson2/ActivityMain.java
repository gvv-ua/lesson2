package ua.gvv.lesson2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {
    private FragmentManager manager;
    private FragmentListView fragList;
    private FragmentRecyclerView fragRecycler;

    private ArrayList<Student> students = new ArrayList<Student>(20);

    private void FillStudentsList() {
        students.add(new Student("Valerii Gubskyi", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi1", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi2", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi3", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi4", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi5", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi6", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi7", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi8", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi9", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi10", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi11", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi12", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi13", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Valerii Gubskyi14", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));

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
            transaction.add(R.id.activity_main, fragment, "CurrentFragment")
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private Fragment getNextFragment() {
        if (manager == null) {
            manager = getSupportFragmentManager();
        }
        Fragment fragment = manager.findFragmentByTag("CurrentFragment");
        return (fragment instanceof FragmentRecyclerView) ? new FragmentListView() : new FragmentRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_switch:
                Bundle args = new Bundle();
                args.putParcelableArrayList("StudentsList", students);
                Fragment fragment = getNextFragment();

                fragment.setArguments(args);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.activity_main, fragment, "CurrentFragment")
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
