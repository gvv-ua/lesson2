package ua.gvv.studentlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by gvv on 03.12.16.
 */

public class FragmentContactAdd extends Fragment implements View.OnClickListener {
    private EditText name;
    private EditText phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_add, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Adding Contact");

        name = (EditText)view.findViewById(R.id.contact_add_name);
        phone = (EditText)view.findViewById(R.id.contact_add_phone);

        Button ok = (Button)view.findViewById(R.id.contact_add_ok);
        ok.setOnClickListener(this);
        Button cancel = (Button)view.findViewById(R.id.contact_add_cancel);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.contact_add_ok) {
            Intent intent = new Intent();
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("phone", phone.getText().toString());
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        } else if (view.getId() == R.id.contact_add_cancel) {
            Intent intent = new Intent();
            getActivity().setResult(RESULT_CANCELED, intent);
            getActivity().finish();
        }
    }
}
