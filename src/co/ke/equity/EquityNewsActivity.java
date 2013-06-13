
package co.ke.equity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * This is the activity for Equity News feature of Equity application.
 * It displays latest equity news and provides a way to get back to the home activity.
 *
 */

public class EquityNewsActivity extends ParentActivity 
{
	//Instance variables
	WebView equityNewsWebView = null;
	private  String username = MainAppLoginActivity.USERNAME;

/**
 * onCreate
 *
 * Called when the activity is first created. 
 * This is where you should do all of your normal static set up: create views, bind data to lists, etc. 
 * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
 * 
 * Always followed by onStart().
 *
 * @param savedInstanceState Bundle
 */

protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_equity_news);
    setTitleFromActivityLabel (R.id.title_text);
    TextView tv = (TextView)findViewById(R.id.equity_app_footer);
    displayAdverts(username,tv);
    
    equityNewsWebView = (WebView)findViewById(R.id.equityNewsWebkit);
    
    equityNewsWebView.canGoBack();
    equityNewsWebView.canGoForward();
    
    //open equity news in the webview
    equityNewsWebView.loadUrl("http://www.equitybank.co.ke/newsdetail.php?subcat=30");
}//end of onCreate()
    
}//end class
