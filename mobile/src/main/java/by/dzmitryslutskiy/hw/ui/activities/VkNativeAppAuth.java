package by.dzmitryslutskiy.hw.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

import by.dzmitryslutskiy.hw.utils.AuthUtils;

/**
 * VkNativeAppAuth
 * Version 1.0
 * 03.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class VkNativeAppAuth extends ActionBarActivity {

    //cut from VK_SDK
    private static final String VK_APP_AUTH_ACTION = "com.vkontakte.android.action.SDK_AUTH";
    private static final String VERSION = "version";
    private static final String VK_SDK_VERSION = "1.0.0";

    public static final int REQUEST_CODE = 0;

    public VkNativeAppAuth() {/*   code    */}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_vk);
//        getSupportActionBar().hide();
//        activity without UI

        Intent intent;
        intent = new Intent(VK_APP_AUTH_ACTION, null);
        intent.putExtra(AuthUtils.CLIENT_ID, Integer.parseInt(AuthUtils.APP_ID));
        intent.putExtra(VERSION, VK_SDK_VERSION);
        String[] scope = new String[]{};
        intent.putExtra(AuthUtils.SCOPE, TextUtils.join(",", scope));

        //resolve intent - if VK not installed we will get empty list
        PackageManager manager = getPackageManager();
        List<ResolveInfo> listActivities = manager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (listActivities.size() > 0) {
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(this, "Native VK app not installed!\n Run Activity with WebView.",
                    Toast.LENGTH_LONG).show();

            intent = new Intent(this, VkWebViewLoginActivity.class);
            startActivityForResult(intent, REQUEST_CODE);

            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                setResult(RESULT_OK, data);     //in MainActivity process data
            } else {
                setResult(RESULT_CANCELED);
            }
            finish();
        }
    }

}
