package by.dzmitryslutskiy.hw.bo;

import java.io.Serializable;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class NoteGsonModel implements Serializable {

    private String title;

    private transient String content;

    private Long id;

    public NoteGsonModel(Long id, String title, String content) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "NoteGsonModel{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", id=" + id +
                '}';
    }
}