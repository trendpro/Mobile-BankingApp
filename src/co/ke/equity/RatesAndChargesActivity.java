package co.ke.equity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * This is the activity for Rates and charges in Equity application.
 * It displays enables one to view money market, exchange rates and swift codes
 * and provides a way to get back to the home activity.
 *
 */

public class RatesAndChargesActivity extends ParentActivity 
{
	//Instance variables
	WebView exchangeRateWebview = null;
	WebView fixedDepositWebview = null;
	private ProgressDialog m_ProgressDialog = null;
	private Runnable viewOrders;
	private ArrayList<ExchangeRateDataType> exchangeRateList;
	private ArrayList<FixedDepositDataType> fixedRatesList;
	
	private  String username = MainAppLoginActivity.USERNAME;
	private QuestAccessClient qAccessClient = QuestAccessClient.getQuestAccessInstance();
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
    setContentView (R.layout.activity_rates_and_charges);
    setTitleFromActivityLabel (R.id.title_text);
    TextView tv = (TextView)findViewById(R.id.equity_app_footer);
    displayAdverts(username,tv);
    
    exchangeRateWebview = (WebView)findViewById(R.id.exchangeRateWebkit);
    fixedDepositWebview = (WebView)findViewById(R.id.fixedDepositRateWebkit);
    
    viewOrders = new Runnable(){
        public void run() {
        	Looper.prepare();
            getRates();
            Looper.loop();
        }
    };
Thread thread =  new Thread(null, viewOrders, "MagentoBackground");
    thread.start();
    m_ProgressDialog = ProgressDialog.show(RatesAndChargesActivity.this,    
          "Please wait...", "Retrieving data ...", true);
    
    //Wire in the tabs
    TabHost tabs = (TabHost)findViewById(R.id.tabhost);
    
    tabs.setup();
    
    //add exchange rates tab
    TabHost.TabSpec spec = tabs.newTabSpec("tag1");
    
    spec.setContent(R.id.exchange_rates_tab);
    spec.setIndicator("Exchange Rates");
    tabs.addTab(spec);
    
    //Add money market tab
    spec=tabs.newTabSpec("tag2");
    spec.setContent(R.id.money_market_tab);
    spec.setIndicator("Fixed Deposit Rates");
    tabs.addTab(spec);

    tabs.setCurrentTab(0);
    
    
}//end of onCreate

private void getRates(){
    try{
    	//Remove this line later
        Thread.sleep(5000);           	
       
        fixedRatesList = (ArrayList<FixedDepositDataType>)qAccessClient.getLatestFixedDepositRates();
        exchangeRateList = (ArrayList<ExchangeRateDataType>)qAccessClient.getLatestExchangeRates();
        
      } catch (Exception e) {
        Log.e("BACKGROUND_PROC", e.getMessage());
      }
      runOnUiThread(returnRes);
  }//end of getOrders()

private Runnable returnRes = new Runnable() {

    public void run() {
      			
        //Show HTML Table for exchange rates
        exchangeRateWebview.loadData(generateExchangeRatesPage(exchangeRateList), "text/html", "UTF-8");
        
        //display HTML Table for fixed deposit rates
        fixedDepositWebview.loadData(generateFixedDepositRatesPage(fixedRatesList), "text/html", "UTF-8");
        m_ProgressDialog.dismiss();
    }
};//end of runnable  returnRes

	/*
	 * Generate Rudimentary HTML Table
	 */
	public String generateExchangeRatesPage(ArrayList<ExchangeRateDataType> rates) {
	  StringBuffer bufResult=new StringBuffer("<html><body><table border = \"1\">");
	  bufResult.append("<tr><th>Currency</th>"+
	                    "<th>Buying Rate</th><th>Selling Rate</th></tr>");
	  for (ExchangeRateDataType exchangeRate : rates) {
		    bufResult.append("<tr><td align=\"center\">");
		    bufResult.append(exchangeRate.getCurrencyName());
		    bufResult.append("</td><td align=\"center\">");
		    bufResult.append(exchangeRate.getBuyingRate());
		    bufResult.append("</td><td align=\"center\">");
		    bufResult.append(exchangeRate.getSellingRate());
		    bufResult.append("</td></tr>");
		  }
		  bufResult.append("</table></body></html>");
		  return(bufResult.toString());
	}//end of method
	
	/*
	 * Generate Rudimentary HTML Table
	 */
	public String generateFixedDepositRatesPage(ArrayList<FixedDepositDataType> rates) {
	  StringBuffer bufResult=new StringBuffer("<html><body><table border = \"1\">");
	  bufResult.append("<tr><th>Range KES</th>"+
	                    "<th>1 Month p.a</th><th>3 Month p.a</th><th>6 Month p.a</th><th>1 Year p.a</th></tr>");
	  for (FixedDepositDataType fixedDepositRate : rates) {
		    bufResult.append("<tr><td align=\"center\">");
		    bufResult.append(fixedDepositRate.getRangeKES());
		    bufResult.append("</td><td align=\"center\">");
		    bufResult.append(fixedDepositRate.getOneMonthRate());
		    bufResult.append("</td><td align=\"center\">");
		    bufResult.append(fixedDepositRate.getThreeMonthRate());
		    bufResult.append("</td><td align=\"center\">");
		    bufResult.append(fixedDepositRate.getSixMonth());
		    bufResult.append("</td><td align=\"center\">");
		    bufResult.append(fixedDepositRate.getOneYear());
		    bufResult.append("</td></tr>");
		  }
		  bufResult.append("</table></body></html>");
		  return(bufResult.toString());
	}//end of method
    
}//end class
