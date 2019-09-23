package test;

import org.testng.annotations.Test;

import base.BTest;
import common.EnvJsonFile;
import common.TableStyle;
import page.Page;

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
		  
		  Page page=new Page(super.driver);
		  
		  //open planning configuration window
		  logger.info("open planning configuration management window");
		  page.mainMenu.hoverMenu("配置管理");
		  Thread.sleep(2000);
		  page.mainMenu.hoverMenu("规划配置");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("规划配置表");
		  Thread.sleep(5000);
		  
		  //select planning configuration car
		  String planningConfigurationCar;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  planningConfigurationCar=super.bcf.getProperty("PlanningConfigurationCarName");
		  String MagnifyingGlassTableId=page.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 0);
		  page.button.clickMagnifyingGlass(TableStyle.GANGTRIGGERFIELD, MagnifyingGlassTableId,1,2);
		  Thread.sleep(3000);
		  //input planning configuration car for search
		  page.text.inputText("planVehicleName", planningConfigurationCar);
		  Thread.sleep(1000);
		  page.button.clickButton("查询", 1);
		  Thread.sleep(1000);
		  String PopUpTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		  page.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  page.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  //start editing
		  logger.info("start editing configuration option");
		  page.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  
		  String mainDataTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,0);
		  logger.info("select configuraiton option");
		  page.text.openTextBox(mainDataTableId, 1, 8);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("●");
		  Thread.sleep(1000);
		  page.text.openTextBox(mainDataTableId, 1, 9);
		  Thread.sleep(1000);
		  
		  //save configuration table
		  logger.info("save configuration table");
		  page.button.clickButton("保存");
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
