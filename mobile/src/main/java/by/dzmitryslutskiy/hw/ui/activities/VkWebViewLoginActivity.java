package by.dzmitryslutskiy.hw.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.utils.AuthUtils;

/**
 * Classname
 * Version information
 * 03.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class VkWebViewLoginActivity extends ActionBarActivity {

    private static final String TAG = VkWebViewLoginActivity.class.getSimpleName();
    private WebView mWebView;
    private View mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_vk);
        getSupportActionBar().hide();

        mProgress = findViewById(android.R.id.progress);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebViewClient(new VkWebViewClient());
        mWebView.loadUrl(AuthUtils.AUTHORIZATION_URL);
    }

    private class VkWebViewClient extends WebViewClient {

        public VkWebViewClient() {
            super();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "page started " + url);
            showProgress();
            view.setVisibility(View.INVISIBLE);
        }



        /* (non-Javadoc)
         * @see android.webkit.WebViewClient#shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String)
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "overr " + url);
            if (AuthUtils.proceedRedirectURL(VkWebViewLoginActivity.this, url)) {
                Log.d(TAG, "overr redr");
                view.setVisibility(View.INVISIBLE);
                Log.d(TAG, "Parsing url" + url);
//                setResult(RESULT_OK);
                finish();
                return true;
            } else {
                //view.loadUrl(url);
                return false;
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //showProgress("Error: " + description);
            view.setVisibility(View.VISIBLE);
            dismissProgress();
            Log.d(TAG, "error " + failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG, "finish " + url);
         /*   if (url.contains("&amp;")) {
                url = url.replace("&amp;", "&");
                Log.d(TAG, "overr after replace " + url);
                view.loadUrl(url);
                return;
            }*/
            view.setVisibility(View.VISIBLE);
            //if (!VkOAuthHelper.proceedRedirectURL(VkLoginActivity.this, url, success)) {
            dismissProgress();
            //}
        }

    }

    private void dismissProgress() {
        mProgress.setVisibility(View.GONE);
    }

    private void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

}