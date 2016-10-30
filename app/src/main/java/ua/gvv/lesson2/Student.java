package ua.gvv.lesson2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gvv on 30.10.16.
 */

public class Student implements Parcelable {
    private String name;
    private String googleLink;
    private String githubLink;

    public Student(Parcel in) {
        super();
        readFromParcel(in);
    }

    Student(String name, String googleLink, String githubLink) {
        this.name = name;
        this.googleLink = googleLink;
        this.githubLink = githubLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(googleLink);
        dest.writeString(githubLink);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        googleLink = in.readString();
        githubLink = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public String getGoogleLink() {
        return this.googleLink;
    }

    public String getGithubLinkleLink() {
        return this.githubLink;
    }

}
