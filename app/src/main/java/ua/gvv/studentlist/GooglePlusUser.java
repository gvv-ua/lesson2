package ua.gvv.studentlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gvv on 04.11.16.
 */
public class GooglePlusUser {
    private String gender;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("displayName")
    private String name;

    private String url;

    private String location;

    private Image image;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public Image getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    class Image {
        @SerializedName("url")
        private String avatarUrl;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }
    }
}
