package test;

import org.testng.annotations.Test;

import base.BTest;
import common.EnvJsonFile;
import common.TableStyle;
import page.Page;
import page.ConfigurationPage;

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

public class EngineConfigurationLibraryMaintain extends BTest{
  @Test
  public void AddNewEngineConfiguration() throws IOException{
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(10000);
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(10000);
		  
		  Page page=new Page(super.driver);
		  
		  //open planning configuration window
		  logger.info("open engine configuration management window");
		  page.mainMenu.hoverMenu("基础数据管理");
		  Thread.sleep(2000);
		  page.mainMenu.hoverMenu("配置基础数据");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("工程特征库管理");
		  Thread.sleep(5000);
		  
		  //start editing
		  logger.info("start editing");
		  page.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  
		  //add configuration family
		  logger.info("add configuration family");
		  page.button.clickButton("新增");
		  Thread.sleep(1000);
		  page.button.clickChildButton("新增特征族");
		  Thread.sleep(1000);
		  
		  String tableId;
		  tableId=page.otherElements.getTableId(TableStyle.TREEVIEW, 0);
		  
		  page.text.openTextBox(tableId, 1, 3);
		  Thread.sleep(1000);
		  //fill feature code
		  page.text.inputText("featureCode",1, "ECF_" + bcf.getTimeStamp());
		  Thread.sleep(2000);
		  //fill feature family name
		  page.text.openTextBox(tableId, 1, 4);
		  Thread.sleep(1000);
		  page.text.inputText("featureName",1, "ECF_" + bcf.getTimeStamp());
		  
		  logger.info("save the configuration family");
		  page.button.clickButton("保存");
		  Thread.sleep(1000);
		  
		  logger.info("take the configuration family effectiveness");
		  page.button.clickButton("生效");
		  Thread.sleep(1000);
		  
		  page.button.clickButton("是");
		  Thread.sleep(1000);
		  
		  //add configuration value
		  logger.info("add the configuration value");
		  page.button.clickButton("新增");
		  Thread.sleep(1000);
		  page.button.clickChildButton("新增特征值");
		  Thread.sleep(1000);
		  
		  page.text.openTextBox(tableId, 2, 3);
		  Thread.sleep(1000);
		  
		  //fill feature code
		  String engineConfigCode="ECV_"+bcf.getTimeStamp();
		  page.text.inputText("featureCode", 1, engineConfigCode);
		  Thread.sleep(2000);
		  //fill feature name
		  page.text.openTextBox(tableId, 2, 4);
		  Thread.sleep(1000);
		  page.text.inputText("featureName", 1, "ECV_" + bcf.getTimeStamp());
		  
		  
		  logger.info("save the configuration value");
		  page.button.clickButton("保存");
		  Thread.sleep(1000);
		  
		  logger.info("take the configuration value effectiveness");
		  page.button.clickButton("生效");
		  Thread.sleep(1000);
		  
		  page.button.clickButton("是");
		  Thread.sleep(1000);
		  
		  //save the change order number in test data file
		  logger.info("save the engine configuration value in test data file");
		  logger.info("engine configuration value: " + engineConfigCode);
		  Map<String, String> testData=new HashMap<String, String>();
		  testData.put("EngineConfig",engineConfigCode);
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
