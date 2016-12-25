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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ua.gvv.studentlist.data.Student;

public class ActivityMain extends AppCompatActivity {
    private static final String FRAGMENT_CURRENT = "CurrentFragment";
    private static final String TAG = "ActivityMain";
    private static final int FRAGMENT_RECYCLER_VIEW = 1;
    private static final int FRAGMENT_LIST_VIEW = 2;
    private static final int FRAGMENT_CONTACT_LIST = 3;
    private static final int FRAGMENT_IMAGE_SELECTOR = 4;

    private int currentFragment = FRAGMENT_RECYCLER_VIEW;
    private int[] tabColors;

    private HeadsetReceiver headsetReceiver;

    private BottomNavigationView navMain;
    private Toolbar tbMain;
    private Realm realm;

    private List<Student> getStudentsList() {
        List<Student> students = new ArrayList<>(20);
        int id = 1;
        students.add(new Student(id++, "Valerii Gubskyi", "107910188078571144657", "gvv-ua"));
        students.add(new Student(id++, "Евгений Жданов", "113264746064942658029", "zhdanov-ek"));
        students.add(new Student(id++, "Edgar Khimich", "102197104589432395674", "lyfm"));
        students.add(new Student(id++, "Alexander Storchak", "106553086375805780685", "new15"));
        students.add(new Student(id++, "Yevhenii Sytnyk", "101427598085441575303", "YevheniiSytnyk"));
        students.add(new Student(id++, "Alyona Prelestnaya", "107382407687723634701", "HelenCool"));
        students.add(new Student(id++, "Богдан Рибак", "103145064185261665176", "BogdanRybak1996"));
        students.add(new Student(id++, "Ірина Смалько", "113994208318508685327", "IraSmalko"));
        students.add(new Student(id++, "Владислав Винник", "117765348335292685488", "vlads0n"));
        students.add(new Student(id++, "Ігор Пахаренко", "108231952557339738781", "IhorPakharenko"));
        students.add(new Student(id++, "Андрей Рябко", "110288437168771810002", "RyabkoAndrew"));
        students.add(new Student(id++, "Ivan Leshchenko", "111088051831122657934", "ivleshch"));
        students.add(new Student(id++, "Микола Піхманець", "110087894894730430086", "NikPikhmanets"));
        students.add(new Student(id++, "Ruslan Migal", "106331812587299981536", "rmigal"));
        students.add(new Student(id++, "Руслан Воловик", "109719711261293841416", "RuslanVolovyk"));
        students.add(new Student(id++, "Иван Сергеенко", "111389859649705526831", "dogfight81"));
        students.add(new Student(id++, "Вова Лымарь", "109227554979939957830", "VovanNec"));
        students.add(new Student(id++, "Даша Кириченко", "103130382244571139113", "dashakdsr"));
        students.add(new Student(id++, "Michael Tyoply", "110313151428733681846", "RedGeekPanda"));
        students.add(new Student(id++, "Павел Сакуров", "108482088578879737406", "sakurov"));
        return students;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tbarMain = (Toolbar) findViewById(R.id.tbar_main);
        setSupportActionBar(tbarMain);

        realm = Realm.getDefaultInstance();
        saveUsers(getStudentsList());

        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt(FRAGMENT_CURRENT);
        }
        navMain = (BottomNavigationView) findViewById(R.id.navMain);
        tbMain = (Toolbar) findViewById(R.id.tbar_main);
        tabColors = getResources().getIntArray(R.array.tabColors);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_CURRENT);
        if (fragment == null) {
            showFragment(currentFragment);
        }

        headsetReceiver = new HeadsetReceiver(this);

        setCurrentBottomNavigationItem();
        navMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_list_view:
                        showFragment(FRAGMENT_LIST_VIEW);
                        return true;
                    case R.id.action_recycler_view:
                        showFragment(FRAGMENT_RECYCLER_VIEW);
                        return true;
                    case R.id.action_contacts:
                        showFragment(FRAGMENT_CONTACT_LIST);
                        return true;
                    case R.id.action_image_select:
                        showFragment(FRAGMENT_IMAGE_SELECTOR);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setCurrentBottomNavigationItem() {
//        v25.0.1
//        Menu menuBottom = navMain.getMenu();
//        for (int i = 0; i < menuBottom.size(); i++) {
//            menuBottom.getItem(i).setChecked((currentFragment) -1 == i);
//        }

//        v25.1.0
        Menu menuBottom = navMain.getMenu();
        menuBottom.getItem((currentFragment) -1).setChecked(true);

        int color = tabColors[currentFragment - 1];
        navMain.setBackgroundColor(color);
        tbMain.setBackgroundColor(color);
    }

    private void showFragment(int fragmentType) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_CURRENT);

        if ((fragment == null)
                || ((fragmentType == FRAGMENT_IMAGE_SELECTOR) && !(fragment instanceof FragmentImageSelector))
                || ((fragmentType == FRAGMENT_LIST_VIEW) && !(fragment instanceof FragmentListView))
                || ((fragmentType == FRAGMENT_RECYCLER_VIEW) && !(fragment instanceof FragmentRecyclerView))
                || ((fragmentType == FRAGMENT_CONTACT_LIST) && !(fragment instanceof FragmentContactList))
                ) {
            switch (fragmentType) {
                case FRAGMENT_RECYCLER_VIEW: fragment = new FragmentRecyclerView();
                    break;
                case FRAGMENT_LIST_VIEW: fragment = new FragmentListView();
                    break;
                case FRAGMENT_CONTACT_LIST: fragment = new FragmentContactList();
                    break;
                case FRAGMENT_IMAGE_SELECTOR: fragment = new FragmentImageSelector();
                    break;
                default: fragment = new FragmentRecyclerView();
            }
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.replace(R.id.frame_main, fragment, FRAGMENT_CURRENT)
                    .commit();
            currentFragment = fragmentType;
            setCurrentBottomNavigationItem();
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

    private void saveUsers(final List<Student> students) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(students);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Save to db failed");
            }
        });
    }


}
