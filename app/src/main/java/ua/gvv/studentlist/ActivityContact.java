package ua.gvv.studentlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityContact extends Activity  implements View.OnClickListener {
    private EditText name;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        name = (EditText)findViewById(R.id.contact_add_name);
        phone = (EditText)findViewById(R.id.contact_add_phone);

        Button ok = (Button) findViewById(R.id.contact_add_ok);
        ok.setOnClickListener(this);
        Button cancel = (Button) findViewById(R.id.contact_add_cancel);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.contact_add_ok) {
            Intent intent = new Intent();
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("phone", phone.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        } else if (v.getId() == R.id.contact_add_cancel) {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }
}
