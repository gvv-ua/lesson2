package ua.gvv.studentlist;

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
        students.add(new Student("Valerii Gubskyi", "107910188078571144657", "gvv-ua"));
        students.add(new Student("Евгений Жданов", "113264746064942658029", "zhdanov-ek"));
        students.add(new Student("Edgar Khimich", "102197104589432395674", "lyfm"));
        students.add(new Student("Alexander Storchak", "106553086375805780685", "new15"));
        students.add(new Student("Yevhenii Sytnyk", "101427598085441575303", "YevheniiSytnyk"));
        students.add(new Student("Alyona Prelestnaya", "107382407687723634701", "HelenCool"));
        students.add(new Student("Богдан Рибак", "103145064185261665176", "BogdanRybak1996"));
        students.add(new Student("Ірина Смалько", "113994208318508685327", "IraSmalko"));
        students.add(new Student("Владислав Винник", "117765348335292685488", "vlads0n"));
        students.add(new Student("Ігор Пахаренко", "108231952557339738781", "IhorPakharenko"));
        students.add(new Student("Андрей Рябко", "110288437168771810002", "RyabkoAndrew"));
        students.add(new Student("Ivan Leshchenko", "111088051831122657934", "ivleshch"));
        students.add(new Student("Микола Піхманець", "110087894894730430086", "NikPikhmanets"));
        students.add(new Student("Ruslan Migal", "106331812587299981536", "rmigal"));
        students.add(new Student("Руслан Воловик", "109719711261293841416", "RuslanVolovyk"));
        students.add(new Student("Иван Сергеенко", "111389859649705526831", "dogfight81"));
        students.add(new Student("Вова Лымарь", "109227554979939957830", "VovanNec"));
        students.add(new Student("Даша Кириченко", "103130382244571139113", "dashakdsr"));
        students.add(new Student("Michael Tyoply", "110313151428733681846", "RedGeekPanda"));
        students.add(new Student("Павел Сакуров", "108482088578879737406", "sakurov"));
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
