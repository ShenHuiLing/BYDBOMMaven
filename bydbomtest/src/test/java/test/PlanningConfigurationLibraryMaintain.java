package test;

import org.testng.annotations.Test;

import base.BTest;
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
 * 1. add new planning configuration in the library
 * 2. save the planning configuration
 * 3. take the planning configuration effectiveness
 * @author alans
 *
 */

public class PlanningConfigurationLibraryMaintain extends BTest{
  @Test
  public void AddNewPlanningConfiguration() throws IOException{
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(10000);
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(10000);
		  
		  //open planning configuration window
		  logger.info("open planning configuration management window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("配置管理");
		  Thread.sleep(2000);
		  mainPage.mainMenu.hoverMenu("规划配置");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("规划配置描述库");
		  Thread.sleep(5000);
		  
		  PlanningConfigurationPage planConfigPage=new PlanningConfigurationPage(super.driver);
		  
		  //start editing
		  logger.info("start editing");
		  planConfigPage.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  
		  //add configuration group
		  logger.info("add configuration group");
		  planConfigPage.button.clickButton("新增");
		  Thread.sleep(1000);
		  planConfigPage.button.clickChildButton("新增根节点");
		  Thread.sleep(1000);
		  
		  String tableId;
		  tableId=planConfigPage.otherElements.getTableId(TableStyle.TREEVIEW, 0);
		  
		  planConfigPage.text.openTextBox(tableId, 1, 3);
		  Thread.sleep(1000);
		  
		  planConfigPage.text.inputText("featureName", "PCG_" + bcf.getTimeStamp());
		  Thread.sleep(2000);
		  
		  logger.info("save the configuration group");
		  planConfigPage.button.clickButton("保存");
		  Thread.sleep(1000);
		  
		  logger.info("take the configuration group effectiveness");
		  planConfigPage.button.clickButton("生效");
		  Thread.sleep(1000);
		  
		  planConfigPage.button.clickButton("是");
		  Thread.sleep(1000);
		  
		  //add configuration family
		  logger.info("add the configuration family");
		  planConfigPage.button.clickButton("新增");
		  Thread.sleep(1000);
		  planConfigPage.button.clickChildButton("新增子节点");
		  Thread.sleep(1000);
		  
		  planConfigPage.text.openTextBox(tableId, 2, 3);
		  Thread.sleep(1000);
		  
		  planConfigPage.text.inputText("featureName", "PCF_"+bcf.getTimeStamp());
		  Thread.sleep(2000);
		  
		  logger.info("save the configuration family");
		  planConfigPage.button.clickButton("保存");
		  Thread.sleep(1000);
		  
		  logger.info("take the configuration family effectiveness");
		  planConfigPage.button.clickButton("生效");
		  Thread.sleep(1000);
		  
		  planConfigPage.button.clickButton("是");
		  Thread.sleep(1000);
		  
		  //add configuration value 
		  logger.info("add the configuration value");
		  planConfigPage.button.clickButton("新增");
		  Thread.sleep(1000);
		  planConfigPage.button.clickChildButton("新增子节点");
		  Thread.sleep(1000);
		  
		  planConfigPage.text.openTextBox(tableId, 3, 3);
		  Thread.sleep(1000);
		  
		  String planningConfig="PCV_"+bcf.getTimeStamp();
		  planConfigPage.text.inputText("featureName", planningConfig);
		  Thread.sleep(2000);
		  
		  logger.info("save the configuration value");
		  planConfigPage.button.clickButton("保存");
		  Thread.sleep(1000);
		  
		  logger.info("take the configuration value effectiveness");
		  planConfigPage.button.clickButton("生效");
		  Thread.sleep(1000);
		  
		  planConfigPage.button.clickButton("是");
		  Thread.sleep(1000);
		  
		  //save the change order number in test data file
		  logger.info("save the planning configuration value in test data file");
		  logger.info("planning configuration value: " + planningConfig);
		  Map<String, String> testData=new HashMap<String, String>();
		  testData.put("PlanningConfig",planningConfig);
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
