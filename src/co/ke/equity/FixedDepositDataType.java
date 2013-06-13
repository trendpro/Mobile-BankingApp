package co.ke.equity;

public class FixedDepositDataType {
	//Instance variables
	String rangeKES = null;
	String oneMonth = null;
	String threeMonth = null;
	String sixMonth = null;
	String oneYear = null;
	
	public FixedDepositDataType(String range,String oneMon, String threeMon, String sixMonth,String oneYear)
	{
		rangeKES = range;
		oneMonth = oneMon;
		threeMonth = threeMon;
		this.sixMonth = sixMonth;
		this.oneYear = oneYear;
	}
	
	public String getRangeKES()
	{
		return rangeKES;
	}
	
	public String getOneMonthRate()
	{
		return oneMonth;
	}
	
	public String getThreeMonthRate()
	{
		return threeMonth;
	}
	
	public String getSixMonth()
	{
		return sixMonth;
	}
	
	public String getOneYear()
	{
		return oneYear;
	}

}//end of clas
