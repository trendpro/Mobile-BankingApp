package co.ke.equity;

public class ShareListingDataType {
	//Instance Variables
	String companyName = null;
	String recordID = null;
	double lastTradedPrice = 0.0;
	double previousPrice = 0.0;
	double percentageChange = 0.0;
	
	public ShareListingDataType(String recID, String comName, double ltp, double pp,double pc)
	{
		recordID = recID;
		companyName = comName;
		lastTradedPrice = ltp;
		previousPrice = pp;
		percentageChange = pc;
	}
	
	public String getRecordID()
	{
		return recordID;
	}
	
	public String getCompanyName()
	{
		return companyName;
	}
	
	public double getLastTradedPrice()
	{
		return lastTradedPrice;
	}
	
	public double getPreviousPrice()
	{
		return previousPrice;
	}
	
	public double getPercentageChange()
	{
		return percentageChange;
	}

}//end of class
