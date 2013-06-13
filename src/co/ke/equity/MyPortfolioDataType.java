package co.ke.equity;

public class MyPortfolioDataType {
	//Instance Variables
	String companyName = null;
	String recordID = null;
	int noOfShares = 0;
	double sharePrice = 0.0;
	double approxWorth = 0.0;
	
	public MyPortfolioDataType(String recID, String cn,int nOS,double sp,double aw)
	{
		companyName = cn;
		noOfShares = nOS;
		sharePrice = sp;
		approxWorth =  aw;
	}
	
	public String getRecordID()
	{
		return recordID;
	}
	
	public String getCompanyName()
	{
		return companyName;
	}
	
	public int getNoOfShares()
	{
		return noOfShares;
	}
	
	public double getSharePrice()
	{
		return sharePrice;
	}
	
	public double getApproxWorth()
	{
		return approxWorth;
	}

}//end of class
