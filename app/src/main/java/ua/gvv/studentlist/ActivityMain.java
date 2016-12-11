package ua.gvv.studentlist;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ua.gvv.studentlist.data.Student;

public class ActivityMain extends AppCompatActivity {
    private static final String FRAGMENT_CURRENT = "CurrentFragment";
    private static final int FRAGMENT_LIST_VIEW = 1;
    private static final int FRAGMENT_RECYCLER_VIEW = 2;
    private static final int FRAGMENT_CONTACT_LIST = 3;
    private static final int FRAGMENT_IMAGE_SELECTOR = 4;

    private int currentFragment = FRAGMENT_LIST_VIEW;


    private HeadsetReceiver headsetReceiver;

    private ArrayList<Student> students = new ArrayList<>(20);

    private BottomNavigationView navMain;

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

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_CURRENT);
        if (fragment == null) {
            switch (currentFragment) {
                case FRAGMENT_LIST_VIEW:
                    showListView();
                    break;
                case FRAGMENT_RECYCLER_VIEW:
                    showRecyclerView();
                    break;
                case FRAGMENT_CONTACT_LIST:
                    showContactList();
                    break;
                case FRAGMENT_IMAGE_SELECTOR:
                    showImageSelector();
                    break;
                default:
                    showImageSelector();
            }
        }

        headsetReceiver = new HeadsetReceiver(this);

        navMain = (BottomNavigationView) findViewById(R.id.navMain);
        setCurrentBottomNavigationItem();
        navMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_list_view:
                        showListView();
                        setCurrentBottomNavigationItem();
                        return true;
                    case R.id.action_recycler_view:
                        showRecyclerView();
                        setCurrentBottomNavigationItem();
                        return true;
                    case R.id.action_contacts:
                        showContactList();
                        setCurrentBottomNavigationItem();
                        return true;
                    case R.id.action_image_select:
                        showImageSelector();
                        setCurrentBottomNavigationItem();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setCurrentBottomNavigationItem() {
        Menu menuBottom = navMain.getMenu();
        for (int i = 0; i < menuBottom.size(); i++) {
            menuBottom.getItem(i).setChecked((currentFragment) -1 == i);
        }
    }

    private void showImageSelector() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_CURRENT);

        if ((fragment == null) || (!(fragment instanceof FragmentImageSelector))) {
            fragment = new FragmentImageSelector();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_main, fragment, FRAGMENT_CURRENT)
                    .commit();
            currentFragment = FRAGMENT_IMAGE_SELECTOR;
        }
    }

    private void showContactList() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_CURRENT);

        if ((fragment == null) || (!(fragment instanceof FragmentContactList))) {
            fragment = new FragmentContactList();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_main, fragment, FRAGMENT_CURRENT)
                    .commit();
            currentFragment = FRAGMENT_CONTACT_LIST;
        }
    }

    private void showRecyclerView() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_CURRENT);
        if ((fragment == null) || (!(fragment instanceof FragmentRecyclerView))) {
            Bundle args = new Bundle();
            args.putParcelableArrayList("StudentsList", students);

            fragment = new FragmentRecyclerView();
            fragment.setArguments(args);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_main, fragment, FRAGMENT_CURRENT)
                    .commit();
            currentFragment = FRAGMENT_RECYCLER_VIEW;
        }
    }

    private void showListView() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_CURRENT);
        if ((fragment == null) || (!(fragment instanceof FragmentListView))) {
            Bundle args = new Bundle();
            args.putParcelableArrayList("StudentsList", students);

            fragment = new FragmentListView();
            fragment.setArguments(args);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_main, fragment, FRAGMENT_CURRENT)
                    .commit();
            currentFragment = FRAGMENT_LIST_VIEW;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(FRAGMENT_CURRENT, currentFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
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

    private Fragment getFragmentByType(int fragmentType) {
        switch (fragmentType) {
            case FRAGMENT_LIST_VIEW: return new FragmentListView();
            case FRAGMENT_RECYCLER_VIEW: return  new FragmentRecyclerView();
            case FRAGMENT_CONTACT_LIST: return new FragmentContactList();
            case FRAGMENT_IMAGE_SELECTOR: return new FragmentImageSelector();
            default: return new FragmentListView();
        }
    }

}
