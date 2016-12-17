package ua.gvv.studentlist.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gvv on 30.10.16.
 */

public class Student extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String googleName;
    private String gitHubName;
    private String searchName;

    public Student() { }

    public Student(int id, String name, String googleLink, String githubLink) {
        this.id = id;
        this.name = name;
        this.googleName = googleLink;
        this.gitHubName = githubLink;
        this.searchName = name.toLowerCase();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGitHubName(String gitHubName) {
        this.gitHubName = gitHubName;
    }

    public void setGoogleName(String googleName) {
        this.googleName = googleName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() { return id; }

    public String getName() {
        return this.name;
    }

    public String getGoogleName() {
        return this.googleName;
    }

    public String getGitHubName() {
        return this.gitHubName;
    }

    public String getSearchName() { return searchName; }

    public void setSearchName(String searchName) { this.searchName = searchName; }
}
