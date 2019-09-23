package test;

import org.testng.annotations.Test;

import base.BTest;
import common.EnvJsonFile;
import common.TableStyle;
import page.Page;

import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

/**
 * 1. add a new planning configuration car
 * 2. select basic care type and configuration car type
 * 3. save
 * @author alans
 *
 */
public class PlanningConfigurationCarMaintain extends BTest{
  @Test
  public void AddNewPlanningConfigurationCar() throws IOException{
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(10000);
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(10000);
		  
		  Page page=new Page(super.driver);
		  
		  //open planning configuration car window
		  logger.info("open planning configuration management window");
		  page.mainMenu.hoverMenu("���ù���");
		  Thread.sleep(2000);
		  page.mainMenu.hoverMenu("�滮����");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("�滮���ó���");
		  Thread.sleep(5000);
		  
		  
		  //start editing
		  logger.info("start editing");
		  page.button.clickButton("����༭");
		  Thread.sleep(1000);
		  
		  page.button.clickButton("���");
		  Thread.sleep(1000);
		  
		  String projectCode;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  projectCode=super.bcf.getProperty("ProjectCode");
		  
		  String tableId;
		  tableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 0);
		  System.out.println(tableId);
		  
		  //select vehicle code
		  logger.info("select the vehicle code");
		  page.text.openTextBox(tableId, 1, 4);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption(projectCode);
		  Thread.sleep(1000);
		  
		  //fill planning configuration car name
		  logger.info("fill planning configuration car name");
		  page.text.openTextBox(tableId, 1, 5);
		  Thread.sleep(1000);
		  String planningConfigurationCarName="PVN_"+bcf.getTimeStamp();
		  page.text.inputText("planVehicleName", 1, planningConfigurationCarName);
		  Thread.sleep(1000);
		  
		  //select basic car code
		  logger.info("select basic car code");
		  page.text.openTextBox(tableId, 1, 6);
		  Thread.sleep(2000);
		  page.option.expandDropdownList();
		  Thread.sleep(2000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.SelectAllCheckboxOption();
		  Thread.sleep(1000);
		  
		  //select configuration car
		  logger.info("select configuration car");
		  page.text.openTextBox(tableId, 1, 7);
		  Thread.sleep(2000);
		  page.option.expandDropdownList();
		  Thread.sleep(2000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.SelectAllCheckboxOption();
		  Thread.sleep(1000);
		  
		  logger.info("save the planning configuration car");
		  page.button.clickButton("����");
		  Thread.sleep(2000);
		  
		  logger.info("save the planning configuration car name in test data for further usage");
		  Map<String, String> testData=new HashMap<String, String>();
		  testData.put("PlanningConfigurationCarName", planningConfigurationCarName);
		  super.bcf.writeJasonFile(EnvJsonFile.TESTDATA, testData);
		  
	  }catch(Exception e) {
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
