package co.ke.equity;

public class ExchangeRateDataType {
	//Instance Variables
	String currencyName= null;
	String buyingRate = null;
	String sellingRate = null;
	
	public ExchangeRateDataType(String curName,String selRate,String buyRate)
	{
		currencyName = curName;
		buyingRate = buyRate;
		sellingRate = selRate;
	}
	
	public String getCurrencyName()
	{
		return  currencyName;
	}
	
	public String getBuyingRate()
	{
		return buyingRate;
	}
	
	public String getSellingRate()
	{
		return sellingRate;
	}

}
