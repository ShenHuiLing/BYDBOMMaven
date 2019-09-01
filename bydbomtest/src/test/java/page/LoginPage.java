package page;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import base.BPage;

public class LoginPage extends BPage {
	@FindBy(xpath = "//*[@id=\"username-inputEl\"]")
	protected WebElement txtUsername;
		
	@FindBy(xpath = "//*[@id=\"password-inputEl\"]")
	protected WebElement txtPwd;
	
	@FindBy(xpath = "//*[@id=\"button-1020-btnInnerEl\"]")
	protected WebElement btnLogin;
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void InputUserName(String userName)
	{
		this.txtUsername.sendKeys(userName);
	}
	
	public void InputPwd(String pwd)
	{
		this.txtPwd.sendKeys(pwd);
	}
	
	public void clickLoginButton()
	{
		this.btnLogin.click();
	}

}
