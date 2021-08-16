package sg.edu.rp.c346.id20004713.taskscheduler;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class item implements Serializable {
    private int id;
    private String title;
    private String desc;
    private String date;
    private String time;

    public item(String title, String desc, String date, String time) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @NonNull
    @Override
    public String toString() {
        return title + "\n" + desc + "\n" + date + "\n" + time;
    }
}
