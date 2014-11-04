package by.dzmitryslutskiy.hw.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Classname
 * Version information
 * 03.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class AuthUtils {
    private static final String TAG = AuthUtils.class.getSimpleName();

    public static final String APP_ID = "4581205";

    public static final String ACCOUNT_TYPE = "com.vk.account";

    public static final String REDIRECT = "https://oauth.vk.com/blank.html";
    public static final String REDIRECT_URL = "&redirect_uri=" + REDIRECT;
    public static final String RESPONSE_TYPE_TOKEN = "&response_type=token";

    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_ID_PARAM = CLIENT_ID + "=" + APP_ID;
    public static final String SCOPE = "scope";
    public static final String SCOPE_DEFAULT = "&" + SCOPE + "=offline,wall,photos,status";
    public static final String AUTHORIZATION_URL = "https://oauth.vk.com/authorize?" + CLIENT_ID_PARAM + SCOPE_DEFAULT + REDIRECT_URL + "&display=touch" + RESPONSE_TYPE_TOKEN;
    public static final String ACCESS_TOKEN = "access_token";
    public static final String USER_ID = "user_id";
    public static final String EXPIRES_IN = "expires_in";


    public static boolean proceedRedirectURL(Activity activity, String url) {
        //https://oauth.vk.com/blank.html#
        //fragment: access_token=token&expires_in=0&user_id=308327
        //https://oauth.vk.com/blank.html#error=
        if (url.startsWith(REDIRECT)) {
            Uri uri = Uri.parse(url);
            String fragment = uri.getFragment();
            Uri parsedFragment = Uri.parse(REDIRECT + "?" + fragment);

            String accessToken = parsedFragment.getQueryParameter(ACCESS_TOKEN);
            String userId = parsedFragment.getQueryParameter(USER_ID);
            String expiresIn = parsedFragment.getQueryParameter(EXPIRES_IN);

            if (! TextUtils.isEmpty(accessToken)) {
                //TODO save token to the secure store
                //TODO create account in account manager
                Intent resultIntent = new Intent();
                resultIntent.putExtra(AuthUtils.ACCESS_TOKEN, accessToken);
                resultIntent.putExtra(AuthUtils.USER_ID, userId);
                resultIntent.putExtra(AuthUtils.EXPIRES_IN, expiresIn);

                activity.setResult(Activity.RESULT_OK, resultIntent);
                Toast.makeText(activity, "UserID: " + userId, Toast.LENGTH_SHORT).show();
                return true;
            } else {
                //TODO check access denied/finish
                //#error=access_denied&error_reason=user_denied&error_description=User denied your request
            }

        }
        return false;
    }

    public AuthUtils() {/*   code    */}

    public static void setLogged(boolean b) {

    }

    public static void account(Context context, String userId, String token) {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length == 0) {
            Account vkAccount = new Account(userId, ACCOUNT_TYPE);
            manager.addAccountExplicitly(vkAccount,null,null);
        }


    }

    /*  public methods  */

    /*  private methods */

}
