package base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import common.CheckBoxStyle;
import common.DropDownListStyle;
import common.ListViewStyle;


/**
 * cover: checkbox, dropdown list
 * @author alans
 *
 */
public class BOption {
	protected List<WebElement> elementList;
	protected Actions action;
	protected WebDriver driver;
	
	public BOption(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
		action=new Actions(this.driver);
	}
	
	/**
	 * cover: change order page
	 * click the checkbox option on change order page
	 * @param option: which option will be checked
	 */
	public void clickCheckBoxOption(CheckBoxStyle cbs,String option) {
		String xPath="";
		if(cbs==CheckBoxStyle.ROWCHECKER) {
			xPath="//td[contains(@class, 'headerId-gridcolumn') and contains(@class, 'checker')]/following-sibling::td[1]/div[not(contains(@class, 'number'))]";
		}else if(cbs==CheckBoxStyle.INPUTCHECKBOX) {
			xPath="//input[contains(@type, 'checkbox')]";
		}
		elementList=this.driver.findElements(By.xpath(xPath));
		
		int i;
		for(i=0;i<elementList.size();i++)
			{	
			    if(elementList.get(i).getText().contains(option)){
			    	elementList.get(i).click();
				    break;
			    }   
			}	
	}
	
	/**
	 * cover: product spectrum page, vppd page, eBom page
	 * click the checkBox in tree list view 
	 * @param node
	 */
	public void clickCheckBox(int node, ListViewStyle lvs) {
		if(lvs==ListViewStyle.TREEVIEW) {
			elementList=this.driver.findElements(By.xpath("//*[contains(@id,'treeview-')]/td[1]/div/div"));
			if(elementList.size()>0)
			{
				elementList.get(node).click();
			}
		}
		else if(lvs==ListViewStyle.GRIDVIEW){
			elementList=this.driver.findElements(By.xpath("//*[contains(@id,'gridview-')]/td[1]/div/div"));
			if(elementList.size()>0)
			{
				elementList.get(node).click();
			}
			
		}
	}
	
	/**
	 * find the check box basing on the table structure
	 * @param id: table id, indicate which table should be used
	 * @param row: indicate which row should be used
	 * @param col: indicate which col should be clicked
	 */
	public void clickCheckBox(String id, int row, int col) {
		String xPath="//table[contains(@id, '" + id + "')]/tbody/tr[" + row + "]/td[" + col + "]/div/div";
		WebElement element=this.driver.findElement(By.xpath(xPath));
		element.click();
	}
	
	/**
	 * input element which is check box role and have id. 
	 * This method is used in part change content page
	 * @param id: the id of the check box 
	 */
	public void clickCheckBoxById(String id) {
		String xPath="//input[contains(@id, '" + id + "') and contains(@role, 'checkbox')]";
		WebElement element=this.driver.findElement(By.xpath(xPath));
		element.click();
	}
	
	/**
	 * this method is used for approver selection, where there are some nodes not required,
	 * uncheck the check box
	 * @param tableId: the table which contain the check box element
	 * @param row: the row number
	 * @param col: the column number
	 */
	public void unCheckCheckBoxByTable(String tableId, int row, int col) {
		String xPath="//table[contains(@id, '" + tableId + "')]/tbody/tr[" + row + "]/td[" + col + "]/div/img";
		WebElement element=this.driver.findElement(By.xpath(xPath));
		if(element.getAttribute("class").contains("checked"))
			element.click();
	}
	
	/**
	 * this method is used for approver selection, when there are some nodes required
	 * check the check box
	 * @param tableId
	 * @param row
	 * @param col
	 */
	public void checkCheckBoxByTable(String tableId, int row, int col) {
		String xPath="//table[contains(@id, '" + tableId + "')]/tbody/tr[" + row + "]/td[" + col + "]/div/img";
		WebElement element=this.driver.findElement(By.xpath(xPath));
		if(!element.getAttribute("class").contains("checked"))
			element.click();
	}
	
	/**
	 * cover: product spectrum page
	 * expand dropdown list
	 */
	public void expandDropdownList()
	{
		WebElement element=this.driver.findElement(By.xpath("//*[contains(@id, 'combo') and contains(@id, '-inputEl') and contains(@class, 'focus')]"));
		element.click();
	}
	
	/**
	 * cover: eBOM page, BOMpublish Page
	 * expand the dropdown list per label id
	 * @param labelId
	 */
	public void expandDropdownList(DropDownListStyle ddls, String labelId) {
		String temp="";
		if(ddls==DropDownListStyle.COMBO)
			temp="combo-" + labelId + "-inputEl";
		else if(ddls==DropDownListStyle.GANTCOMBOBOX)
			temp="gantcombobox-" + labelId + "-inputEl";
		else if(ddls==DropDownListStyle.GANTCODETYPECOMBOBOX)
			temp="gantcodetypecombobox-" + labelId + "-inputEl";
		
		WebElement element=this.driver.findElement(By.xpath("//input[contains(@id,'" + temp + "')]"));
		element.click();
	}
	
	/**
	 * cover: VPPD page
	 * open the dropdown list with the specific label
	 * @param label
	 */
	public void openComboxFromQuerySection(String label) {
		elementList=this.driver.findElements(By.xpath("//label[contains(@id, 'combo')]"));
		int i;
		for(i=0;i<elementList.size();i++)
				  {
				  	if(elementList.get(i).getText().contains(label))
				  	{
				  		String id=elementList.get(i).getAttribute("id");
				  		id=id.replaceAll("label", "input");
				  		WebElement element=this.driver.findElement(By.xpath("//input[contains(@id,'" + id + "')]"));
				  		element.click();
				  		break;
				  	}
				  	
				  }
	}
	
	/**
	 * cover: product spectrum page, eBOM page
	 * select the option from the drop down list which is currently on focus
	 * @param option
	 */
	public void selectOption(String option) {
		String xPath="//li[contains(@role, 'option') and contains(@class, 'x-boundlist-item')]";
		elementList=this.driver.findElements(By.xpath(xPath));
		//System.out.println(elementList.size());
		int i;
		for(i=0;i<elementList.size();i++)
				  {
				  	if(elementList.get(i).getText().contains(option))
				  	{
						elementList.get(i).click();
				  		break;
				  	}
				  	
				  }
	}
	
	/**
	 * cover: Product spectrum page
	 * select all the checkbox option from the dropdown list
	 */
	public void SelectAllCheckboxOption() {
		String xPath="//input[contains(@type, 'checkbox')]";
		elementList=this.driver.findElements(By.xpath(xPath));
		int i;
		for(i=0;i<elementList.size();i++)
				  {
					if(!elementList.get(i).isSelected())
						elementList.get(i).click();
				  }
	}
	
	/**
	 * cover: VPPD page
	 * select last option
	 */
	public void selectLastOption(){
		WebElement element=driver.findElement(By.xpath("//ul[contains(@class, 'x-list-plain')]/li[last()]"));
		element.click();
	}
	
	/**
	 * click the check box in approver selector to select all the chosen approvers
	 */
	public void checkAllSelectedApprover() {
		String xPath="//span[contains(@id,'gantsearchform') and contains(@id, 'header_hd-textEl')]";
		elementList=this.driver.findElements(By.xpath(xPath));
		String id="";
		int i;
		for(i=0;i<elementList.size();i++)
			{
				if(elementList.get(i).getText().equals("已选择用户")){
					id=elementList.get(i).getAttribute("id");
					break;
				}
			}
		
		xPath="//span[@id='" + id + "']/../../../../../../../../following-sibling::div[1]/div/div/div/div/div/span";
		WebElement element=this.driver.findElement(By.xpath(xPath));
		element.click();
	}
	
	/**
	 * click the checkbox to select approval option
	 */
	public void selectApprovalOption()
	{
		String xPath="//span[contains(@id, 'panel') and contains(@id, 'header_hd-textEl')]";
		elementList=this.driver.findElements(By.xpath(xPath));
		String id="";
		int i;
		for(i=0;i<elementList.size();i++)
			{
				if(elementList.get(i).getText().equals("审批操作")){
					id=elementList.get(i).getAttribute("id");
					break;
				}
			}
		
		xPath="//span[contains(@id, '" + id + "')]/../../../../../following-sibling::div[1]";
		WebElement element=this.driver.findElement(By.xpath(xPath));
		element.click();
	}
}
