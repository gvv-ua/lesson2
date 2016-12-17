package ua.gvv.studentlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import ua.gvv.studentlist.data.Student;

public class FragmentListView extends Fragment {
    private final String TAG = "FragmentListView";
    private RealmResults<Student> students;
    private String filter = "";

    ListView lvStudents;


    private final RealmChangeListener<RealmResults<Student>> changeListener = new RealmChangeListener<RealmResults<Student>>() {
        @Override
        public void onChange(RealmResults<Student> elements) {
            updateUI(elements);
        }
    };

    private void updateUI(RealmResults<Student> elements) {
        lvStudents.setAdapter(new ListViewAdapter(getActivity(), elements));
//        if (lvStudents.getAdapter() == null) {
//            lvStudents.setAdapter(new ListViewAdapter(getActivity(), elements));
//        } else {
//            ListViewAdapter adapter = (ListViewAdapter) lvStudents.getAdapter();
//            adapter.notifyDataSetChanged();
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("List View");

        lvStudents = (ListView) view.findViewById(R.id.listview_lesson2);

        Realm realm = Realm.getDefaultInstance();
        students = realm.where(Student.class).findAllSortedAsync("searchName", Sort.ASCENDING);
        students.addChangeListener(changeListener);

        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        students.removeChangeListeners();
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_tool_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals(filter)) {
                    Realm realm = Realm.getDefaultInstance();
                    students.removeChangeListeners();
                    students = realm.where(Student.class).contains("searchName", newText.toLowerCase()).findAllSortedAsync("searchName", Sort.ASCENDING);
                    students.addChangeListener(changeListener);
                    filter = newText;
                    return true;
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}

