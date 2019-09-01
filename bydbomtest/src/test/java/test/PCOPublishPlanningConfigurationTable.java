package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ChangeOrderType;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.TableStyle;
import page.MainPage;
import page.PlanningConfigurationPage;

import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class PCOPublishPlanningConfigurationTable extends BTest{
  @Test
  public void CreatePCOToPublishPlanningConfiguration() throws IOException{
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(15000);
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(25000);
		  
		  //open PCO window
		  logger.info("open PCO management window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("变更管理");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("规划配置变更管理");
		  Thread.sleep(10000);
		  
		  PlanningConfigurationPage planConfigPage=new PlanningConfigurationPage(super.driver);
		  
		  //create a new PCO
		  logger.info("create a new PCO");
		  planConfigPage.button.clickButton("新增");
		  Thread.sleep(1000);
		  String changeOrder=planConfigPage.text.getChangeOrderNumber();
		  logger.info("new PCO number: " + changeOrder);
		  
		  //select planning configuration car
		  logger.info("select planning configuration car");
		  String planningConfigurationCar;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  planningConfigurationCar=super.bcf.getProperty("PlanningConfigurationCarName");
		  String MagnifyingGlassTableId=planConfigPage.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 1);
		  planConfigPage.button.clickMagnifyingGlass(TableStyle.GANGTRIGGERFIELD, MagnifyingGlassTableId,1,2);
		  Thread.sleep(3000);
		  //input planning configuration car for search
		  planConfigPage.text.inputText("planVehicleName", planningConfigurationCar);
		  Thread.sleep(1000);
		  planConfigPage.button.clickButton("查询", 1);
		  Thread.sleep(1000);
		  String PopUpTableId=planConfigPage.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		  planConfigPage.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  planConfigPage.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  String labelId=planConfigPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"变更版本",1);
		  planConfigPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  planConfigPage.option.selectOption("A版");
		  Thread.sleep(1000);
		  
		  //save the PS
		  logger.info("save the PCO");
		  planConfigPage.button.clickButton("保存");
		  Thread.sleep(5000);
		  
		  //add the change content
		  logger.info("add the change content");
		  planConfigPage.tab.clickTab("变更内容");
		  Thread.sleep(10000);
		  planConfigPage.button.clickButton("关联变更");
		  Thread.sleep(15000);
		  
		  //start approval process
		  logger.info("start approval process");
		  super.startApprovalProcess(ChangeOrderType.PLANNINGCONFIGURATION);
		  
		  //save the change order number in test data file
		  logger.info("save the change order number in test data file");
		  logger.info("change order#: " + changeOrder);
		  Map<String, String> testData=new HashMap<String, String>();
		  testData.put("ChangeOrder",changeOrder);
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
