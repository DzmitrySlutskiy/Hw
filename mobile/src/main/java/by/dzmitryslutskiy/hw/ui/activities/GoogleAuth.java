package by.dzmitryslutskiy.hw.ui.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.AccountPicker;

import by.dzmitryslutskiy.hw.R;

/**
 * GoogleAuth
 * Version 1.0
 * 04.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class GoogleAuth extends ActionBarActivity {

    public static final String GOOGLE_ACCOUNT_TYPE = "com.google";

    private static final int REQUEST_CODE = 0;
    private RequestCallback mCallback = new RequestCallback();
    /*  UI  */
    private TextView mUserName;
    private TextView mUserToken;

    public GoogleAuth() {/*   code    */}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_auth);
        mUserName = (TextView) findViewById(R.id.user_name);
        mUserToken = (TextView) findViewById(R.id.user_token);
    }

    public void onLoginClick(View view) {
//        Intent intent = AccountManager.newChooseAccountIntent(null, null, //start from API 14
//                new String[]{GOOGLE_ACCOUNT_TYPE}, false, null, null, null, null);
//
//        startActivityForResult(intent, REQUEST_CODE);

        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                new String[]{GOOGLE_ACCOUNT_TYPE},
                false, null, null, null, null);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                //selected account in data:
                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

                //find selected account in accountManager
                AccountManager accountManager = AccountManager.get(this);
                Account userAccount = null;
                for (Account account : accountManager.getAccountsByType(GOOGLE_ACCOUNT_TYPE)) {
                    if (account.name.equals(accountName)) { //check if have 2 or more google accounts
                        userAccount = account;
                        break;
                    }
                }

                //scope from: https://developers.google.com/gmail/api/auth/scopes?hl=ru
                String scope = "oauth2:https://www.googleapis.com/auth/gmail.readonly";
                //get account token (
                accountManager.getAuthToken(userAccount, scope, null, this,
                        mCallback, null);

                mUserName.setText("User name (mail): " + accountName);
            }
        }
    }

    private void setErrorToken(Exception e) {
        mUserToken.setText("Get error on getToken: " + e.getMessage());
    }

    private void setValidToken(String token) {
        mUserToken.setText("Token part: " + token.substring(0, 10));
    }

    private class RequestCallback implements AccountManagerCallback<Bundle> {

        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try {
                Bundle bundle = result.getResult();
                setValidToken(bundle.getString(AccountManager.KEY_AUTHTOKEN));
            } catch (Exception e) {
                setErrorToken(e);
            }
        }
    }
}
