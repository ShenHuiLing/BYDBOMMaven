package base;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class  BPage {
	protected List<WebElement> elementList;
	protected Actions action;
	protected WebDriver driver;
	public BMainMenu mainMenu;
	public BTab tab;
	public BText text;
	public BButton button;
	public BOption option;
	public BLink link;
	public BOtherElements otherElements;
	
	public BPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
		action=new Actions(this.driver);
		mainMenu=new BMainMenu(driver);
		tab=new BTab(driver);
		text=new BText(driver);
		button=new BButton(driver);
		option=new BOption(driver);
		link=new BLink(driver);
		otherElements=new BOtherElements(driver);
	}
}

