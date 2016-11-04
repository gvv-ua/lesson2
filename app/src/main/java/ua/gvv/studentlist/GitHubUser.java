package ua.gvv.studentlist;

/**
 * Created by gvv on 03.11.16.
 */

import com.google.gson.annotations.SerializedName;

public class GitHubUser {
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    private String name;

    @SerializedName("html_url")
    private String url;

    private String location;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getLogin() {
        return login;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
