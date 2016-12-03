package ua.gvv.studentlist.data;

import android.net.Uri;

/**
 * Created by gvv on 03.12.16.
 */

public class Contact {
    private String name;
    private String phone;
    private Uri photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }
}
