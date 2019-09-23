package test;

import org.testng.annotations.Test;

import base.BTest;
import common.EnvJsonFile;
import common.ListViewStyle;
import common.TextStyle;
import page.Page;

import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

/* Test: 产品型谱维护功能
 * 1. start BOM
 * 2. login BOM
 * 3. open product spectrum window
 * 4. add node step by step
 */
public class ProductSpectrum extends BTest {
  @Test
  public void ProductSpectrumManagement() throws IOException {
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(5000);
		  
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(10000);
		  
		  Page page=new Page(super.driver);
		  
		  //open product spectrum window
		  logger.info("open the product spectrum window");
		  page.mainMenu.hoverMenu("产品管理");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("产品型谱管理");
		  Thread.sleep(5000);
		  
		  //edit product spectrum
		  logger.info("select the root node and start to editting");
		  page.option.clickCheckBox(0,ListViewStyle.TREEVIEW);
		  page.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  
		  //add car series
		  logger.info("add a new node for car series");
		  page.button.clickButton("新增");
		  Thread.sleep(1000);
		  page.button.clickChildButton("新增子节点");
		  Thread.sleep(1000);
		  
		  logger.info("fill car series code");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeCode", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-code-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill car series name");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeName", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-name-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill car series description");
		  page.text.openTextBox(TextStyle.IDINTR, "description", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-description-"+super.bcf.getTimeStamp());
		  
		  //add car type
		  logger.info("add a new node for car type");
		  page.button.clickButton("新增");
		  Thread.sleep(1000);
		  page.button.clickChildButton("新增子节点");
		  Thread.sleep(1000);
		  
		  logger.info("fill car type code");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeCode", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp().substring(4));
		  
		  logger.info("fill car type name");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeName", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-name-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill car type description");
		  page.text.openTextBox(TextStyle.IDINTR, "description", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-description-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill car type fuel type");
		  page.text.openTextBox(TextStyle.IDINTR, "fuelType", 1);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("燃油");
		  Thread.sleep(1000);
		  
		  logger.info("fill status of car type");
		  page.text.openTextBox(TextStyle.IDINTR, "status", 1);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("在研");
		  
		  //page.text.openTextBox(TextStyle.IDINTR, "upgrading", 1);
		  Thread.sleep(1000);
		  //page.text.inputText(TextStyle.TEXTFIELD,"AT-upgrading-"+super.bcf.getTimeStamp());
		  
		  //add expected go-live year
		  logger.info("fill go-live year");
		  page.button.clickButton("新增");
		  Thread.sleep(1000);
		  page.button.clickChildButton("新增子节点");
		  Thread.sleep(1000);
		  
		  logger.info("fill code for go-live year");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeCode", 1);
		  String projectCode="AT-CODE-"+super.bcf.getTimeStamp();
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,projectCode);
		  
		  logger.info("fill name for go-live year");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeName", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-name-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill description for go-live year");
		  page.text.openTextBox(TextStyle.IDINTR, "description", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-description-"+super.bcf.getTimeStamp());
		  
		  /*
		  page.text.openTextBox(TextStyle.IDINTR, "status", 1);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("规划");
		  */
		  Thread.sleep(1000);
		  
		  logger.info("fill manufacture plant for go-live year");
		  page.text.openTextBox(TextStyle.IDINTR, "firstPlant", 1);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.SelectAllCheckboxOption();
		  Thread.sleep(1000);
		  
		  /*
		  page.button.clickButton("保存");
		  Thread.sleep(2000);
		  page.button.clickButton("初始化超级BOM");
		  Thread.sleep(2000);
		  
		  Map<String, String> testData=new HashMap<String, String>();
		  testData.put("ProjectCode",projectCode);
		  super.bcf.writeJasonFile(EnvJsonFile.TESTDATA, testData);
		  */
		  
		  //add basic car
		  logger.info("add basic car");
		  page.button.clickButton("新增");
		  Thread.sleep(1000);
		  page.button.clickChildButton("新增子节点");
		  Thread.sleep(1000);
		  
		  logger.info("fill code for basic car");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeCode", 1);
		  Thread.sleep(1000);
		  String basicCarCode=super.bcf.getTimeStamp().substring(4);
		  page.text.inputText(TextStyle.TEXTFIELD,basicCarCode);
		  
		  logger.info("fill name for basic car");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeName", 1);
		  Thread.sleep(1000);
		  
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-name-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill description for basic car");
		  page.text.openTextBox(TextStyle.IDINTR, "description", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-description-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill market for basic car");
		  page.text.openTextBox(TextStyle.IDINTR, "saleMarket", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-saleMarket-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill power configuration for basic car");
		  page.text.openTextBox(TextStyle.IDINTR, "dynamicConfig", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-dynamicConfig-"+super.bcf.getTimeStamp());
		  Thread.sleep(1000);
		  
		  logger.info("fill status of basic car");
		  page.text.openTextBox(TextStyle.IDINTR, "status", 1);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("在研");
		  /*
		  page.text.openTextBox(TextStyle.IDINTR, "status", 1);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("规划");
		  */
		  Thread.sleep(1000);

		  //add configuration car
		  logger.info("add configuration car");
		  page.button.clickButton("新增");
		  Thread.sleep(1000);
		  page.button.clickChildButton("新增子节点");
		  Thread.sleep(1000);
		  
		  logger.info("fill code for configuration car");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeCode", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-code-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill name for configuraton car");
		  page.text.openTextBox(TextStyle.IDINTR, "nodeName", 1);
		  Thread.sleep(1000);
		  String configurationCarName="AT-name-"+super.bcf.getTimeStamp();
		  page.text.inputText(TextStyle.TEXTFIELD,configurationCarName);
		  
		  logger.info("fill description configuration car");
		  page.text.openTextBox(TextStyle.IDINTR, "description", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-description-"+super.bcf.getTimeStamp());
		  Thread.sleep(1000);
		  
		  logger.info("fill configuration level for configuration car");
		  page.text.openTextBox(TextStyle.IDINTR, "configLevel", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-configLevel-"+super.bcf.getTimeStamp());
		  
		  logger.info("fill announcement code for configuration car");
		  page.text.openTextBox(TextStyle.IDINTR, "announcementCode", 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,"AT-announcementCode-"+super.bcf.getTimeStamp());
		  
		  logger.info("save the change");
		  page.button.clickButton("保存");
		  Thread.sleep(2000);
		  
		  //click the go-live year node and intial super BOM header and configuration table header
		  logger.info("initial super BOM");
		  page.otherElements.clickRowByText(ListViewStyle.TREEVIEW, "4", projectCode);
		  Thread.sleep(1000);
		  
		  page.button.clickButton("初始化超级BOM");
		  Thread.sleep(5000);
		  
		  logger.info("initial engine configuration table header");
		 
		  page.button.clickButton("初始化工程配置表");
		  Thread.sleep(5000);
		  
		  logger.info("save the vehicle mode for other test to use");
		  Map<String, String> testData=new HashMap<String, String>();
		  testData.put("ProjectCode",projectCode);
		  testData.put("BasicCar", basicCarCode);
		  testData.put("ConfigurationCar", configurationCarName);
		  super.bcf.writeJasonFile(EnvJsonFile.TESTDATA, testData);
		  		  
	  }
	  catch(Exception e) {
		  super.TakeSnap();
		  logger.error(e.getMessage());
		  e.printStackTrace();
		  Assert.assertEquals(false, true);
	  }
	  
  }
  @BeforeTest
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
	  super.close();
  }

}
