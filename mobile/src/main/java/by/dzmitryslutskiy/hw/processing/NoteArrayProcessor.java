package by.dzmitryslutskiy.hw.processing;

import org.json.JSONObject;

import by.dzmitryslutskiy.hw.bo.Note;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class NoteArrayProcessor extends WrapperArrayProcessor<Note> {

    @Override
    public Note createObject(JSONObject jsonObject) {
        return new Note(jsonObject);
    }
}
