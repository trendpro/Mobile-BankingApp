package co.ke.equity;

public class UserSignUpDataType {
	
	//Instance Variables
	String name;
	String email;
	String password;
	String confirmPassword;
	String dateOfBirth;
	String gender;
	
	//constructor
	public UserSignUpDataType(String name, String email, String pass,String confirmPass,String age, String gender)
	{
		this.name = name;
		this.email = email;
		this.password =  pass;
		this.confirmPassword = confirmPass;
		this.dateOfBirth=age;
		this.gender = gender;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPasswprd()
	{
		return password;
	}
	
	public String getConfirmPassword()
	{
		return confirmPassword;
	}
	
	public String getDateOfBirth()
	{
		return dateOfBirth;
	}
	
	public String getGender()
	{
		return gender;
	}

}//end of class
