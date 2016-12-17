package ua.gvv.studentlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import ua.gvv.studentlist.data.Student;

public class FragmentListView extends Fragment {
    ListView lvStudents;

    private final RealmChangeListener<RealmResults<Student>> changeListener = new RealmChangeListener<RealmResults<Student>>() {
        @Override
        public void onChange(RealmResults<Student> elements) {
            updateUI(elements);
        }
    };

    private void updateUI(RealmResults<Student> elements) {
        if (lvStudents.getAdapter() == null) {
            lvStudents.setAdapter(new ListViewAdapter(getActivity(), elements));
        } else {
            ListViewAdapter adapter = (ListViewAdapter) lvStudents.getAdapter();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvStudents = (ListView) view.findViewById(R.id.listview_lesson2);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Student> students = realm.where(Student.class).findAllAsync();
        students.addChangeListener(changeListener);
    }
}

