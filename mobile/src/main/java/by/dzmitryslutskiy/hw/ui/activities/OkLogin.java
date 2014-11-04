package by.dzmitryslutskiy.hw.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import by.dzmitryslutskiy.hw.R;

/**
 * Classname
 * Version information
 * 04.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class OkLogin extends ActionBarActivity {

    public static final String APP_ID = "1107517696";
    public static final String URL = "http://www.odnoklassniki.ru/oauth/authorize?client_id=" + APP_ID
            + "&scope=VALUABLE_ACCESS&response_type=token&redirect_uri=okauth://ok1107517696&layout=m&state=";

    public OkLogin() {/*   code    */}

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
        //в Intent получим либо токен:
        // okauth://ok1107517696#access_token=62fe...&session_secret_key=3f5e...
        // либо сообщение с ошибкой типа
        // okauth://ok1107517696#error=access_denied
    }
}
