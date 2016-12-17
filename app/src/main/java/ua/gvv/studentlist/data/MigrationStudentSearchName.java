package ua.gvv.studentlist.data;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;

/**
 * Created by gvv on 17.12.16.
 */

public class MigrationStudentSearchName implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        if (oldVersion == 1) {
            RealmObjectSchema user = realm.getSchema().get("Student");
            user.addField("searchName", String.class)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.setString("searchName", obj.getString("name").toLowerCase());
                        }
                    });
            oldVersion++;
        }
    }
}
