package by.dzmitryslutskiy.hw.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import by.dzmitryslutskiy.hw.R;

/**
 * VkLogin
 * Version information
 * 04.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class VkLogin extends ActionBarActivity {

    public static final String URL = "https://oauth.vk.com/authorize?client_id=4581205&scope=offline,wall,photos,status&redirect_uri=vkauth://vk4581205&display=touch&response_type=token";
    public VkLogin() {/*   code    */}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_login);
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
