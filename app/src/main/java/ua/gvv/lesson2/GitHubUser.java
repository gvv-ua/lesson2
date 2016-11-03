package ua.gvv.lesson2;

/**
 * Created by gvv on 03.11.16.
 */

import com.google.gson.annotations.SerializedName;

public class GitHubUser {
    String login;

    @SerializedName("avatar_url")
    String avatarUrl;

    String name;

    @SerializedName("html_url")
    String url;

    String location;
}
