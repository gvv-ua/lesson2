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
    private static final String FRAGMENT_CURRENT = "CurrentFragment";
    private static final int FRAGMENT_LIST_VIEW = 1;
    private static final int FRAGMENT_RECYCLER_VIEW = 2;

    private int currentFragment = FRAGMENT_LIST_VIEW;

    private FragmentListView fragList;
    private FragmentRecyclerView fragRecycler;

    private ArrayList<Student> students = new ArrayList<Student>(20);

    private void FillStudentsList() {
        students.add(new Student("Valerii Gubskyi", "https://plus.google.com/u/0/107910188078571144657", "https://github.com/gvv-ua"));
        students.add(new Student("Евгений Жданов", "https://plus.google.com/u/0/113264746064942658029", "https://github.com/zhdanov-ek"));
        students.add(new Student("Edgar Khimich", "https://plus.google.com/u/0/102197104589432395674", "https://github.com/lyfm"));
        students.add(new Student("Alexander Storchak", "https://plus.google.com/u/0/106553086375805780685", "https://github.com/new15"));
        students.add(new Student("Yevhenii Sytnyk", "https://plus.google.com/u/0/101427598085441575303", "https://github.com/YevheniiSytnyk"));
        students.add(new Student("Alyona Prelestnaya", "https://plus.google.com/u/0/107382407687723634701", "https://github.com/HelenCool"));
        students.add(new Student("Богдан Рибак", "https://plus.google.com/u/0/103145064185261665176", "https://github.com/BogdanRybak1996"));
        students.add(new Student("Ірина Смалько", "https://plus.google.com/u/0/113994208318508685327", "https://github.com/IraSmalko"));
        students.add(new Student("Владислав Винник", "https://plus.google.com/u/0/117765348335292685488", "https://github.com/vlads0n"));
        students.add(new Student("Ігор Пахаренко", "https://plus.google.com/u/0/108231952557339738781", "https://github.com/IhorPakharenko"));
        students.add(new Student("Андрей Рябко", "https://plus.google.com/u/0/110288437168771810002", "https://github.com/RyabkoAndrew"));
        students.add(new Student("Ivan Leshchenko", "https://plus.google.com/u/0/111088051831122657934", "https://github.com/ivleshch"));
        students.add(new Student("Микола Піхманець", "https://plus.google.com/u/0/110087894894730430086", "https://github.com/NikPikhmanets"));
        students.add(new Student("Ruslan Migal", "https://plus.google.com/u/0/106331812587299981536", "https://github.com/rmigal"));
        students.add(new Student("Руслан Воловик", "https://plus.google.com/u/0/109719711261293841416", "https://github.com/RuslanVolovyk"));
        students.add(new Student("Иван Сергеенко", "https://plus.google.com/u/0/111389859649705526831", "https://github.com/dogfight81"));
        students.add(new Student("Вова Лымарь", "https://plus.google.com/u/0/109227554979939957830", "https://github.com/VovanNec"));
        students.add(new Student("Даша Кириченко", "https://plus.google.com/u/0/103130382244571139113", "https://github.com/dashakdsr"));
        students.add(new Student("Michael Tyoply", "https://plus.google.com/u/0/110313151428733681846", "https://github.com/RedGeekPanda"));
        students.add(new Student("Павел Сакуров", "https://plus.google.com/u/0/108482088578879737406", "https://github.com/sakurov"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FillStudentsList();

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt(FRAGMENT_CURRENT);
        }

        Bundle args = new Bundle();
        args.putParcelableArrayList("StudentsList", students);
        Fragment fragment = getFragmentByType(currentFragment);
        fragment.setArguments(args);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_main, fragment, "CurrentFragment")
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(FRAGMENT_CURRENT, currentFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private Fragment getFragmentByType(int fragmentType) {
        return (fragmentType == FRAGMENT_RECYCLER_VIEW) ? new FragmentRecyclerView() : new FragmentListView();
    }

    private int getNextFragmentType() {
        return (currentFragment == FRAGMENT_RECYCLER_VIEW) ? FRAGMENT_LIST_VIEW : FRAGMENT_RECYCLER_VIEW;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_switch:
                Bundle args = new Bundle();
                args.putParcelableArrayList("StudentsList", students);
                currentFragment = getNextFragmentType();
                Fragment fragment = getFragmentByType(currentFragment);

                fragment.setArguments(args);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.activity_main, fragment, "CurrentFragment")
                        .commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
