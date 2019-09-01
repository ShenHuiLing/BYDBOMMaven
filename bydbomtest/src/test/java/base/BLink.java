package base;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BLink {
	protected List<WebElement> elementList;
	protected Actions action;
	protected WebDriver driver;
	
	public BLink(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
		action=new Actions(this.driver);
	}
	
	/**
	 * cover: change order page
	 * get the link count
	 * @param StringlinkText
	 * @return int how many links matching with the text
	 */
	public int getLinkCount(String linkText) {
		int linkCount=0;
		String xPath="//a[contains(@onclick, '" + linkText + "')]";
		elementList=driver.findElements(By.xpath(xPath));
		linkCount=elementList.size();
		if(linkCount!=0) {
			if(elementList.get(0).getText().equals("½áÊø")) {
				linkCount=0;
			}
				
		}
		
		return linkCount;
	}
	
	/**
	 * cover: pending task page
	 * click the link by the specific text
	 * @param text
	 */
	public void clickLinkByText(String text) throws Exception {
		try {
			this.driver.findElement(By.linkText(text)).click();
		}
		catch(Exception e){
			throw e;
		}
	}
	
	public boolean isLinkExist(String text) {
		boolean result=true;
		try {
			this.driver.findElement(By.linkText(text));
		}
		catch(Exception e){
			result=false;
		}
		return result;
	}
}
