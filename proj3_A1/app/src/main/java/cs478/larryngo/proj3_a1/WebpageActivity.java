package cs478.larryngo.proj3_a1;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebpageActivity extends AppCompatActivity {

    private WebView mWebView = null;
    private String websiteURL;
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Bundle extras = getIntent().getExtras();
        websiteURL = extras.getString("EXTRA_WEB_ID"); //gets the id from the A1BroadcastReceiver

        if(websiteURL != null)
        {
            mWebView = (WebView) findViewById(R.id.webView); //finds the view
            mWebView.getSettings().setJavaScriptEnabled(true); //enables java script
            mWebView.setWebViewClient(new WebViewClient() // makes sure there are no errors
            {
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                }
            });
            mWebView.loadUrl(websiteURL); //loads up the website
        }
    }

}
