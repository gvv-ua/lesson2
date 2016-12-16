package ua.gvv.studentlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import ua.gvv.studentlist.data.Student;

/**
 * Created by gvv on 30.10.16.
 */

public class FragmentRecyclerView extends Fragment {
    RecyclerView rvStudents;

    private final RealmChangeListener<RealmResults<Student>> changeListener = new RealmChangeListener<RealmResults<Student>>() {
        @Override
        public void onChange(RealmResults<Student> elements) {
            updateUI(elements);
        }
    };

    private void updateUI(RealmResults<Student> elements) {
        if (rvStudents.getAdapter() == null) {
            rvStudents.setAdapter(new RecyclerViewAdapter(getActivity(), elements));
        } else {
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) rvStudents.getAdapter();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvStudents = (RecyclerView)view.findViewById(R.id.recyclerview_lesson2);
        rvStudents.setLayoutManager(new LinearLayoutManager(getActivity()));

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Student> students = realm.where(Student.class).findAllAsync();
        students.addChangeListener(changeListener);
    }
}
