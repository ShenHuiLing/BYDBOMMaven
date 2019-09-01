package test;

import org.testng.annotations.Test;

import base.BTest;
import common.EnvJsonFile;
import common.TableStyle;
import page.MainPage;
import page.PlanningConfigurationPage;

import org.testng.annotations.BeforeTest;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class PlanningConfigurationTableMaintain extends BTest{
  @Test
  public void SetConfigurationOptionForCar() throws IOException{
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
		  mainPage.mainMenu.clickMenu("规划配置表");
		  Thread.sleep(5000);
		  
		  PlanningConfigurationPage planConfigPage=new PlanningConfigurationPage(super.driver);
		  
		  //select planning configuration car
		  String planningConfigurationCar;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  planningConfigurationCar=super.bcf.getProperty("PlanningConfigurationCarName");
		  String MagnifyingGlassTableId=planConfigPage.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 0);
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
		  
		  //start editing
		  logger.info("start editing configuration option");
		  planConfigPage.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  
		  String mainDataTableId=planConfigPage.otherElements.getTableId(TableStyle.GRIDVIEW,0);
		  logger.info("select configuraiton option");
		  planConfigPage.text.openTextBox(mainDataTableId, 1, 8);
		  Thread.sleep(1000);
		  planConfigPage.option.expandDropdownList();
		  Thread.sleep(1000);
		  planConfigPage.option.selectOption("●");
		  Thread.sleep(1000);
		  planConfigPage.text.openTextBox(mainDataTableId, 1, 9);
		  Thread.sleep(1000);
		  
		  //save configuration table
		  logger.info("save configuration table");
		  planConfigPage.button.clickButton("保存");
		  Thread.sleep(2000);
		  
	  } catch (Exception e) {
			// TODO Auto-generated catch block
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
