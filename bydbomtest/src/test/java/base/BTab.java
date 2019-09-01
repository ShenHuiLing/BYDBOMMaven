package base;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;


/**
 * cover the Tab on the change order page
 * @author alans
 *
 */
public class BTab {
	protected List<WebElement> elementList;
	protected Actions action;
	protected WebDriver driver;
	
	public BTab(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
		action=new Actions(this.driver);
	}
	
	/**
     * click the tab
     * @param tabName
     */
	public void clickTab(String tabName) {
		elementList=this.driver.findElements(By.xpath("//span[contains(@id,'tab') and contains(@id, 'btnInner') and contains(@class, 'tab')]"));
		int i;
		for(i=0;i<elementList.size();i++)
		{			
	        if(elementList.get(i).getText().contains(tabName))
	        {
	        	elementList.get(i).click();
	        	break;
	        }
		}
	}
}

