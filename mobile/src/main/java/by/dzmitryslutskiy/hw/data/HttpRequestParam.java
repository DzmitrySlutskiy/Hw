package by.dzmitryslutskiy.hw.data;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class HttpRequestParam {

    public static enum HttpType {GET, POST, PUT, DELETE}

    private HttpType mType;
    private String mUrl;

    private HttpRequestParam() {/*   code    */}


    public static HttpRequestParam newInstance(HttpType type, String url) {
        HttpRequestParam param = new HttpRequestParam();
        param.mType = type;
        param.mUrl = url;

        return param;
    }

    public String getUrl() {
        return mUrl;
    }

    public HttpType getType() {
        return mType;
    }
}
