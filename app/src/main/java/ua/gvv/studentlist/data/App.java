package ua.gvv.studentlist.data;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by gvv on 16.12.16.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("students.realm")
                .schemaVersion(2)
                .migration(new MigrationStudentSearchName())
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
