package ua.gvv.studentlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by gvv on 12.11.16.
 */

public class HeadsetReceiver extends BroadcastReceiver {
    private Context context;

    public HeadsetReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            int message;

            switch (state) {
                case 0:
                    message = R.string.headset_unplugged;
                    break;
                case 1:
                    message = R.string.headset_plugged;
                    break;
                default:
                    message = R.string.headset_unknown;
            }

            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
