package base;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import common.TableStyle;


/**
 * elements 'button', 'tab'
 * @author alans
 *
 */

public class BButton {
	protected List<WebElement> elementList;
	protected Actions action;
	protected WebDriver driver;
	
	public BButton(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
		action=new Actions(this.driver);
	}

	/**
	 * cover: product spectrum page, VPPD page
	 * click the buttons by the button caption, it can cover query section, functional section
	 * @param button
	 */
	public void clickButton(String button) {
		elementList=this.driver.findElements(By.xpath("//span[contains(@id,'button-')]"));
		int i;
		for(i=0;i<elementList.size();i++)
				  {
				  	if(elementList.get(i).getText().contains(button))
				  	{
				  		elementList.get(i).click();
				  		break;
				  	}
				  }
	}
	
	/**
	 * when there are several buttons with the same name, need to pass along with the index to indicate which one should be clicked
	 * @param button: button name
	 * @param index: the index which one should be clicked
	 */
	public void clickButton(String button, int index) {
		elementList=this.driver.findElements(By.xpath("//span[contains(@id,'btnInnerEl')]"));
		List<WebElement> elementList2=new ArrayList<WebElement>();
		int i;
		for(i=0;i<elementList.size();i++)
		{
			if(elementList.get(i).getText().equals((button)))
			{
				elementList2.add(elementList.get(i));
				
			}
		}
		elementList2.get(index).click();
	}
	
	/**
	 * click the child button by the specific button caption, 
	 * it cover the buttons which have child buttons in functional section
	 * @param String childButton
	 * @return void
	 */
	public void clickChildButton(String childButton) {
		elementList=this.driver.findElements(By.xpath("//*[contains(@id,'menuitem-')]"));
		int i;
		for(i=0;i<elementList.size();i++)
				  {
				  	if(elementList.get(i).getText().contains(childButton))
				  	{
						elementList.get(i).click();
				  		break;
				  	}
				  }
	}
	
	/**
	 * click magnifying glass icon
	 * @param TableStyle
	 * WORKFLOWTASKOWNERTRIGGERFIELD: the magnifying glass icon in approval selector process
	 * GANTTRIGGERFIELD: the magnifying glass icon in eBom
	 * materialName2: the magnifying glass icon in material library
	 * @param tableId: the ID of the table which contains the magnifying glass icon
	 * @param row: the row number to locate the magnifying glass icon
	 * @param col: the column number magnifying glass icon
	 */
	public void clickMagnifyingGlass(TableStyle ts, String tableId, int row, int col) {
		String xPath="";
		if(ts==TableStyle.WORKFLOWTASKOWNERTRIGGERFIELD) {
			xPath="//table[contains(@id, 'workflowTaskOwnerTriggerField') and contains(@id, '" + tableId + "') and contains(@id, 'triggerWrap')]/tbody/tr[" + row + "]/td[" + col + "]/div"; 
		}
		else if(ts==TableStyle.GANGTRIGGERFIELD){
			xPath="//table[contains(@id, 'ganttriggerfield') and contains(@id, '" + tableId + "') and contains(@id, 'triggerWrap')]/tbody/tr[" + row + "]/td[" + col + "]/div"; 
		}
		else if(ts==TableStyle.materialName2) {
			xPath="//table[contains(@id, 'materialName2') and contains(@id, 'triggerWrap')]/tbody/tr[" + row + "]/td[" + col + "]/div"; 
		}
		else if(ts==TableStyle.USERTRIGGERFIELD){
			xPath="//table[contains(@id, 'userTriggerField') and contains(@id, '" + tableId + "') and contains(@id, 'triggerWrap')]/tbody/tr[" + row + "]/td[" + col + "]/div"; 
		}
		else if(ts==TableStyle.GANTGRIDCOMBOBOX) {
			xPath="//table[contains(@id, 'gantgridcombobox') and contains(@id, '" + tableId + "') and contains(@id, 'triggerWrap')]/tbody/tr[" + row + "]/td[" + col + "]/div"; 
		}
		WebElement element=this.driver.findElement(By.xpath(xPath));
		element.click();
	}
	
	
	
	public void clickCloseButton(int index) {
		String xPath="//img[contains(@id, 'tool') and contains(@class, 'close')]";
		elementList=this.driver.findElements(By.xpath(xPath));
		elementList.get(index).click();
	}
	
	/**
	 * click the query button in approver selector to look up the candidate
	 */
	public void clickQueryApproverButton() {
		String xPath="//span[contains(@id,'gantsearchform') and contains(@id, 'header_hd-textEl')]";
		elementList=this.driver.findElements(By.xpath(xPath));
		String id="";
		int i;
		for(i=0;i<elementList.size();i++)
			{
				if(elementList.get(i).getText().equals("Î´Ñ¡ÔñÓÃ»§")){
					id=elementList.get(i).getAttribute("id");
					break;
				}
			}
		
		xPath="//span[@id='" + id + "']/../following-sibling::a[1]/span/span/span";
		WebElement element=this.driver.findElement(By.xpath(xPath));
		element.click();
	}

	public void clickMaximizeButton() {
		String xPath="//img[contains(@id,'tool') and contains(@class, 'x-tool-maximize')]";
		WebElement element=this.driver.findElement(By.xpath(xPath));
		element.click();
	}
}

