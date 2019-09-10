package page;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.BPage;

public class ConfigurationPage extends BPage{

	public ConfigurationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public String getTableId(String parentTableId) throws InterruptedException {
		String xPath="";
		xPath="//table[contains(@id, '" + parentTableId + "')]/tbody/tr/td/table";
		WebElement element=driver.findElement(By.xpath(xPath));
		element.click();
		Thread.sleep(1000);
		element=driver.findElement(By.xpath(xPath));
		return element.getAttribute("id");
	}
}
