package by.dzmitryslutskiy.hw.processing;

import org.json.JSONObject;

import by.dzmitryslutskiy.hw.bo.User;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class UserArrayProcessor extends WrapperArrayProcessor<User> {

    @Override
    public User createObject(JSONObject jsonObject) {
        return new User(jsonObject);
    }
}