package ua.gvv.studentlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gvv on 04.11.16.
 */
public class GooglePlusUser {
    String gender;

    @SerializedName("avatar_url")
    String avatarUrl;

    @SerializedName("displayName")
    String name;

    String url;

    String location;

    Image image;

    class Image {
        @SerializedName("url")
        String avatarUrl;
    }
}
