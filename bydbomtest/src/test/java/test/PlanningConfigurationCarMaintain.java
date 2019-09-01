package test;

import org.testng.annotations.Test;

import base.BTest;
import common.CheckBoxStyle;
import common.EnvJsonFile;
import common.TableStyle;
import page.MainPage;
import page.PlanningConfigurationPage;

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
		  
		  //open planning configuration car window
		  logger.info("open planning configuration management window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("配置管理");
		  Thread.sleep(2000);
		  mainPage.mainMenu.hoverMenu("规划配置");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("规划配置车型");
		  Thread.sleep(5000);
		  
		  PlanningConfigurationPage planConfigPage=new PlanningConfigurationPage(super.driver);
		  
		  //start editing
		  logger.info("start editing");
		  planConfigPage.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  
		  planConfigPage.button.clickButton("添加");
		  Thread.sleep(1000);
		  
		  String projectCode;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  projectCode=super.bcf.getProperty("ProjectCode");
		  
		  String tableId;
		  tableId=planConfigPage.otherElements.getTableId(TableStyle.GRIDVIEW, 0);
		  System.out.println(tableId);
		  
		  //select vehicle code
		  logger.info("select the vehicle code");
		  planConfigPage.text.openTextBox(tableId, 1, 4);
		  Thread.sleep(1000);
		  planConfigPage.option.expandDropdownList();
		  Thread.sleep(1000);
		  planConfigPage.option.selectOption(projectCode);
		  Thread.sleep(1000);
		  
		  //fill planning configuration car name
		  logger.info("fill planning configuration car name");
		  planConfigPage.text.openTextBox(tableId, 1, 5);
		  Thread.sleep(1000);
		  String planningConfigurationCarName="PVN_"+bcf.getTimeStamp();
		  planConfigPage.text.inputText("planVehicleName", 1, planningConfigurationCarName);
		  Thread.sleep(1000);
		  
		  //select basic car code
		  logger.info("select basic car code");
		  planConfigPage.text.openTextBox(tableId, 1, 6);
		  Thread.sleep(2000);
		  planConfigPage.option.expandDropdownList();
		  Thread.sleep(2000);
		  planConfigPage.option.expandDropdownList();
		  Thread.sleep(1000);
		  planConfigPage.option.SelectAllCheckboxOption();
		  Thread.sleep(1000);
		  
		  //select configuration car
		  logger.info("select configuration car");
		  planConfigPage.text.openTextBox(tableId, 1, 7);
		  Thread.sleep(2000);
		  planConfigPage.option.expandDropdownList();
		  Thread.sleep(2000);
		  planConfigPage.option.expandDropdownList();
		  Thread.sleep(1000);
		  planConfigPage.option.SelectAllCheckboxOption();
		  Thread.sleep(1000);
		  
		  logger.info("save the planning configuration car");
		  planConfigPage.button.clickButton("保存");
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
