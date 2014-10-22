package by.dzmitryslutskiy.hw.data;

import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

import by.dzmitryslutskiy.hw.CoreApplication;

/**
 * HttpDataSource
 * Version information
 * 21.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class HttpDataSource implements DataSource<InputStream, HttpRequestParam> {

    public static final String KEY = "HttpDataSource";


    public static HttpDataSource get(Context context) {
        return CoreApplication.get(context, KEY);
    }

    @Override
    public InputStream getResult(HttpRequestParam p) throws Exception {
        HttpRequestBase requestBase;
        switch (p.getType()) {

            case POST:
                requestBase = new HttpPost(p.getUrl());
                break;
            case PUT:
                requestBase = new HttpPut(p.getUrl());
                break;
            case DELETE:
                requestBase = new HttpDelete(p.getUrl());
                break;
            default:
            case GET:
                requestBase = new HttpGet(p.getUrl());
                break;
        }

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(requestBase);

        // Read all the text returned by the server
        return response.getEntity().getContent();
    }

}
