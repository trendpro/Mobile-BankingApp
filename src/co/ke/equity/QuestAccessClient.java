/*
 * This class handles all the communication between the client and the EA Server.
 */
package co.ke.equity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;

/*
 * This is QuestAccess client responsible for making requests and receiving responses from QuestAccess Server. Other clasess 
 * which need to communicate with the server do so through this class.
 * 
 * It uses singleton Design Pattern.
 */
public class QuestAccessClient {
	
	//Instance Variables
	public static final QuestAccessClient client = new QuestAccessClient();
	
	//use http://10.0.2.2/ to connect to your localhost i.e. http://localhost/
    private static String loginURL = "http://10.0.2.2/eserver/request.php";
    private JSONParser jsonParser = new JSONParser();
    
    //JSON Response node names
    private static String TAG_SUCCESS = "success";

    private static String TAG_GET_EXCHANGE_RATES ="get_exchange_rates";
    private static String TAG_GET_FIXED_DEPOSIT_RATES = "get_fixed_deposit_rates";
    private static String TAG_GET_MY_PORTFOLIO = "get_my_portfolio";
    private static String TAG_GET_MY_WATCHLIST = "get_my_watchlist";
    private static String TAG_GET_TOP_LOSERS = "get_top_losers";
    private static String TAG_GET_TOP_GAINERS = "get_top_gainers";
    private static String TAG_ADD_MY_PORTFOLIO_ENTRY = "add_my_portfolio_entry";
    private static String TAG_ADD_MY_WATCHLIST_ENTRY = "add_my_watchlist_entry";
    private static String TAG_REMOVE_MY_PORTFOLIO_ENTRY = "remove_my_portfolio_entry";
    private static String TAG_REMOVE_MY_WATCHLIST_ENTRY = "remove_my_watchlist_entry";
    private static String TAG_FETCH_AD ="fetch_ad";
    
    
	/*
	 * Implement Singleton design pattern
	 */
    public static QuestAccessClient getQuestAccessInstance()
    {
        return client;
    }
    
	/*
	 * Returns latest Exchange rates from QuestAccess Server
	 */
	public List<ExchangeRateDataType> getLatestExchangeRates()
	{
		List<ExchangeRateDataType> list = new ArrayList<ExchangeRateDataType>();
		
		//fetch items from network		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_GET_EXCHANGE_RATES));
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		   
		   
    	 
    	//check for server response
         if (json.getString(TAG_SUCCESS)!= null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
            	 
                 //server succcessfully fetched exchange rates
            	 JSONArray arr= json.getJSONArray("JSONArr");
            	 
            	 //loop through all elements
            	 for(int i =0; i < arr.length();i++)
            	 {
            		 JSONObject jobj = arr.getJSONObject(i);
            		 
            		 String cur = jobj.getString("Currency");
      			   	 String sel = jobj.getString("Selling_Rate");
      			   	 String buy = jobj.getString("Buying_Rate");
      			   
      			   //add entry to list
      			   list.add(new ExchangeRateDataType(cur, sel, buy));
            	 }
                
             }else
             {
                 //Server could not fetch exchange rates
            	 //put some default vales in the list to indicate this error condition
            	 list.add(new ExchangeRateDataType("Server Error", "00", "oo"));
             }
         }
     } catch (Exception e) {
    	//put some default vales in the list to indicate this error condition
    	 list.add(new ExchangeRateDataType("Network Error", "00", "oo"));
     }
		
		return list;
	}
	
	
	/*
	 * Returns the latest fixed deposit rates from QuestAccess Server
	 */
	public List<FixedDepositDataType> getLatestFixedDepositRates()
	{
		List<FixedDepositDataType> list = new ArrayList<FixedDepositDataType>();
		
		//Fetch data from network
		
        try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_GET_FIXED_DEPOSIT_RATES));
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    	 
		 //check for server response
         if (json.getString(TAG_SUCCESS) != null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
                 //server successfully fetched fixed deposit rates
            	 JSONArray arr= json.getJSONArray("JSONArr");
            	 
            	 //loop through all elements
            	 for(int i =0; i < arr.length();i++)
            	 {
            		 JSONObject jobj = arr.getJSONObject(i);
            		 
            		 String rangeFrom = jobj.getString("Range_From");
            		 String rangeTo = jobj.getString("Range_To");
            		 String oneMonth = jobj.getString("One_Month_Pa");
            		 String threeMonth = jobj.getString("Three_Month_Pa");
            		 String sixMonth = jobj.getString("Six_Month_Pa");
            		 String oneYear = jobj.getString("One_Year_Pa");
      			   	 
      			   //add entry to list
            		 list.add(new FixedDepositDataType(rangeFrom+"-" + rangeTo, oneMonth, threeMonth, sixMonth, oneYear));
            	 }
                 
                
             }else
             {
                 //Server could not fetch fixed deposit rates
            	 //put some default vales in the list to indicate this error condition
            	 list.add(new FixedDepositDataType("Server Error:", "00", "00", "00", "00"));
             }
         }
     } catch (Exception e) {
    	//put some default vales in the list to indicate this error condition
    	 list.add(new FixedDepositDataType("Network Error:", "00", "00", "00", "00"));
    	 
     }
        
		return list;
	}
	
	/*
	 * Returns Most Updated user portfolio data from Quest Access Server
	 */
	public List<MyPortfolioDataType> getMyPortfolio(String email)
	{
		List<MyPortfolioDataType> list = new ArrayList<MyPortfolioDataType>();
		
		//Fetch data from network server
       try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_GET_MY_PORTFOLIO));
		    params.add(new BasicNameValuePair("email", email));
		    
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		    
   	 
		 //check for server response
        if (json.getString(TAG_SUCCESS) != null) {
           
            String res = json.getString(TAG_SUCCESS);
            if(Integer.parseInt(res) == 1){
            	
             //server successfully fetched my portfolio data
           	 JSONArray arr= json.getJSONArray("JSONArr");
           	 
           	 //loop through all elements
           	 for(int i =0; i < arr.length();i++)
           	 {
           		 JSONObject jobj = arr.getJSONObject(i);
           		 
           		 String companyName = jobj.getString("Company_Name");
           		 int noOfShares = Integer.parseInt(jobj.getString("no_of_shares"));
           		 double shareprice = Double.parseDouble(jobj.getString("share_price"));
           		 double total = Double.parseDouble(jobj.getString("total"));
           		 String portfolioID = jobj.getString("portfolio_ID");
   
           		 //Add entry to list
           		 list.add(new MyPortfolioDataType(portfolioID,companyName, noOfShares, shareprice, total));
           	 }
               
            }else
            {
                //Server could not fetch my portfolio data
            	list.add(new MyPortfolioDataType("Server Error","Server Error!", 00, 00, 00));
            }
        }
    } catch (Exception e) {
    	//network error
    	list.add(new MyPortfolioDataType("Network Error","Network Error!", 00, 00, 00));
    }
       
       return list;
		
	}
	
	/*
	 * Returns a Users stock wactchlist from QuestAccess
	 */
	@SuppressLint("ParserError")
	public List<ShareListingDataType> getMyWatchList(String email)
	{
		List<ShareListingDataType> list = new ArrayList<ShareListingDataType>();
		
		//Fetch data from database
        try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_GET_MY_WATCHLIST));
		    params.add(new BasicNameValuePair("email", email));
		    
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    	 
		 //check for server response
         if (json.getString(TAG_SUCCESS) != null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
                 //server successfully fetched my shares watchlist
            	 JSONArray arr= json.getJSONArray("JSONArr");
            	 
            	 //loop through all elements
            	 for(int i =0; i < arr.length();i++)
            	 {
            		 JSONObject jobj = arr.getJSONObject(i);
            		 
            		 String companyName = jobj.getString("Company_Name");
            		 double ltp = Double.parseDouble(jobj.getString("Last_Traded_Price"));
            		 double pp = Double.parseDouble(jobj.getString("Previous_Price"));
            		 double change = Double.parseDouble(jobj.getString("Change"));
            		 String watchlistID = jobj.getString("Watchlist_ID");
    
            		 //Add entry to list
            		 list.add(new ShareListingDataType(watchlistID,companyName, ltp, pp,change));
            	 }
                 
                
             }else
             {
                 //Server could not fetch my shares watchlist
            	 list.add(new ShareListingDataType("Server Error:","Server Error:", 0.00, 0.00,0.00));
             }
         }
         else
         {
             //Server could not fetch my shares watchlist
        	 list.add(new ShareListingDataType("Server Error:","Server Error2:", 0.00, 0.00,0.00));
         }
     } catch (Exception e) {
    	//network error
    	 Log.d("Json ERRor....", e.getMessage());
    	 list.add(new ShareListingDataType("Network Error:","Network Error", 0.00, 0.00,0.00));
     }
		return list;
	}
	
	/*
	 * Returns the most  Updated list of stock top gaines from QuestAccess Server
	 */
	public List<ShareListingDataType> getTopGainers()
	{
		List<ShareListingDataType> list = new ArrayList<ShareListingDataType>();
		
		//Fetch data from QuestAccess Servers
        try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_GET_TOP_GAINERS));
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    	 
		 //check for server response
         if (json.getString(TAG_SUCCESS) != null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
                 //server successfully fetched top gainers from the Sharelistings table
            	 JSONArray arr= json.getJSONArray("JSONArr");
            	 
            	 //loop through all elements
            	 for(int i =0; i < arr.length();i++)
            	 {
            		 JSONObject jobj = arr.getJSONObject(i);
            		 
            		 String companyName = jobj.getString("Company_Name");
            		 double ltp = Double.parseDouble(jobj.getString("Last_Traded_Price"));
            		 double pp = Double.parseDouble(jobj.getString("Previous_Price"));
            		 double change = Double.parseDouble(jobj.getString("Change"));
            		 String sharelistingID = jobj.getString("Share_Listing_ID");
    
            		 //Add entry to list
            		 list.add(new ShareListingDataType(sharelistingID,companyName, ltp, pp,change));
            	 }
                
             }else
             {
                 //Server could not fetch top gainers from sharelisting table
            	 list.add(new ShareListingDataType("Server Error:","Server Error:", 0.00, 0.00,0.00));
             }
         }
     } catch (Exception e) {
    	//Network
    	 list.add(new ShareListingDataType("Network Error:","Network Error:", 0.00, 0.00,0.00));
     }
		
		return list;
	}
	
	/*
	 * Returns the most latest list of stock top losers from QuestAccess Server
	 */
	public List<ShareListingDataType> getTopLosers()
	{
		List<ShareListingDataType> list = new ArrayList<ShareListingDataType>();
		
		//Fetch data from the database
        try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_GET_TOP_LOSERS));
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    	 
		 //check for server response
         if (json.getString(TAG_SUCCESS) != null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
                 //server successfully fetched losers from the Sharelisting table
            	 JSONArray arr= json.getJSONArray("JSONArr");
            	 
            	 //loop through all elements
            	 for(int i =0; i < arr.length();i++)
            	 {
            		 JSONObject jobj = arr.getJSONObject(i);
            		 
            		 String companyName = jobj.getString("Company_Name");
            		 double ltp = Double.parseDouble(jobj.getString("Last_Traded_Price"));
            		 double pp = Double.parseDouble(jobj.getString("Previous_Price"));
            		 double change = Double.parseDouble(jobj.getString("Change"));
            		 String sharelistingID = jobj.getString("Share_Listing_ID");
    
            		 //Add entry to list
            		 list.add(new ShareListingDataType(sharelistingID,companyName, ltp, pp,change));
            	 }
                 
                
             }else
             {
                 //Server could not fetch top losers from the sharelisting table
            	 list.add(new ShareListingDataType("Server Error:","Server Error:", 0.00, 0.00,0.00));
             }
         }
     } catch (Exception e) {
    	//Network error
    	list.add(new ShareListingDataType("Connection Error:","Connection Error:", 0.00, 0.00,0.00));
     }
		
		return list;
	}
	
	/*
	 * Fetches a random but targeted Advertisement from QuestAccess Server
	 */
	public String[] fetchRandomAdvert(String username)
	{
		String ads[] = new String[2];
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_FETCH_AD));
		    params.add(new BasicNameValuePair("username", username));
		    
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    	 
		 //check for server response
         if (json.getString(TAG_SUCCESS) != null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
                 //server successfully fetched a random but targeted advert
            	 ads[0] = json.getString("advert");
            	 ads[1] = json.getString("ad_url");
                 
                
             }else
             {
                 //Server could not fetch an advert
            	 ads[0] ="Server Error!";
            	 ads[1] ="no_url";
             }
         }
     } catch (Exception e) {
    	//network error
    	 ads[0] ="Network Error!";
    	 ads[1] ="no_url";
    	 Log.d("Advert Error", e.getMessage());
     }
		return ads;
	}

	
	/*
	 * Generates a message digest from user credentials
	 */
	public static byte[] makePasswordDigest(String user, char password[]) throws NoSuchAlgorithmException, NoSuchProviderException 
	{
        MessageDigest md = MessageDigest.getInstance("SHA-512","SUN");
        md.update(user.getBytes());
        md.update(makeBytes(password));
        
        return md.digest();
	}
	
	/*
	 * Generates a byte Array from a char Array
	 */
	public static byte[] makeBytes(char pass[]) 
	{
        byte[] bytes = new byte[pass.length];
        for (int i = 0; i != pass.length; i++)
        {
            bytes[i] = (byte)pass[i];
        }
        
        return bytes;
	}
	
	/*
	 * Adds a share listing to my watchlist.
	 */
	public boolean addSharelistingToMyWatchList(String shareListingID,String username)
	{
		//variable declaration
		boolean isAddingSuccessful = false;
		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_ADD_MY_WATCHLIST_ENTRY));
		    params.add(new BasicNameValuePair("share_listing_id", shareListingID));
		    params.add(new BasicNameValuePair("email", username));
		    
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    	 
		 //check for server response
         if (json.getString(TAG_SUCCESS) != null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
                 //server successfully added a share listing to my watchlist
                 
                isAddingSuccessful = true;
             }else
             {
                 //Server could not add the share listing to my watchlist
            	 isAddingSuccessful = false;
             }
         }
     } catch (Exception e) {
    	 //network error
    	 isAddingSuccessful = false;
     }
		
		return isAddingSuccessful;
	}
	
	/*
	 * Removes a share listing from my watchlist
	 */
	public boolean removeShareListingFromMyWatchList(String recordID,String username)
	{
		//Instance variables
		boolean isRemovingSuccessful = false;
		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_REMOVE_MY_WATCHLIST_ENTRY));
		    params.add(new BasicNameValuePair("watchlist_ID", recordID));
		    params.add(new BasicNameValuePair("username", username));
		    
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    	 
		 //check for server response
         if (json.getString(TAG_SUCCESS) != null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
                 //server successfully removed share listing from users watchlist
            	 isRemovingSuccessful = true;
                
             }else
             {
                 //Server could not remove the share listing from users watchlist
            	 isRemovingSuccessful = false;
             }
         }
     } catch (Exception e) {
    	 //network error
    	 isRemovingSuccessful = false;
     }
		
		return isRemovingSuccessful;
	}
	
	/*
	 * Adds a new company listing to my portfolio.
	 */
	public boolean addCompanyToMyPortfolio(String recordID,String compnayName, int noOfShares,String username)
	{
		//variable declaration
		boolean isAddingSuccessful = false;
		String s = noOfShares +"";
		String shares = s.trim();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_ADD_MY_PORTFOLIO_ENTRY));
		    params.add(new BasicNameValuePair("share_listing_id", recordID));
		    params.add(new BasicNameValuePair("company_name", compnayName));
		    params.add(new BasicNameValuePair("no_of_shares", shares));
		    params.add(new BasicNameValuePair("email", username));
		    
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    	 
		 //check for server response
         if (json.getString(TAG_SUCCESS) != null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
                 
            	 isAddingSuccessful = true;
             }else
             {
                 //Server could not add a company listing to my watchlist
            	 isAddingSuccessful = false;
             }
         }
     } catch (Exception e) {
    	//network error
    	 isAddingSuccessful = false;
     }
		
		return isAddingSuccessful;
	}
	
	public boolean removeCompanyFromMyPortfolio(String recID, String username)
	{
		//variable declaration
		boolean isRemovingSuccessful = false;
		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("tag", TAG_REMOVE_MY_PORTFOLIO_ENTRY));
		    params.add(new BasicNameValuePair("portfolio_ID", recID));
		    params.add(new BasicNameValuePair("username", username));
		    
		    JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
    	 
		 //check for server response
         if (json.getString(TAG_SUCCESS) != null) {
            
             String res = json.getString(TAG_SUCCESS);
             if(Integer.parseInt(res) == 1){
                 //server successfully removed an entry from my portfolio
            	 isRemovingSuccessful = true;
                
             }else
             {
            	 isRemovingSuccessful = false;
             }
         }
     } catch (Exception e) {
    	 //network error
    	 isRemovingSuccessful = false;
     }
		
		return isRemovingSuccessful;
	}
	
	
}//end of class
