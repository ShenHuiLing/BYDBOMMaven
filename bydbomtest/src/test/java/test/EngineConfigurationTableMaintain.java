package test;

import org.testng.annotations.Test;

import base.BTest;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.TableStyle;
import page.Page;
import page.ConfigurationPage;

import org.testng.annotations.BeforeTest;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class EngineConfigurationTableMaintain extends BTest{
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
		  logger.info("open engine configuration management window");
		  page.mainMenu.hoverMenu("配置管理");
		  Thread.sleep(2000);
		  page.mainMenu.hoverMenu("工程配置");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("工程配置表");
		  Thread.sleep(5000);
		  
		  ConfigurationPage configPage=new ConfigurationPage(super.driver);
		  
		  //select planning configuration car
		  logger.info("select vehicle mode code");
		  String labelId=configPage.otherElements.getLabelId(LabelStyle.COMBO,"车型型号");
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  String projectCode=super.bcf.getProperty("ProjectCode");
		  configPage.option.expandDropdownList(DropDownListStyle.COMBO,labelId);
		  Thread.sleep(2000);
		  configPage.option.selectOption(projectCode);
		  Thread.sleep(5000);
		  
		  //start editing
		  logger.info("start editing configuration option");
		  configPage.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  
		  String mainDataTableId=configPage.otherElements.getTableId(TableStyle.GRIDVIEW,0);
		  String configDataTableId=configPage.getTableId(mainDataTableId);

		  logger.info("select configuraiton option");
		  configPage.text.openTextBox(configDataTableId, 1, 8);
		  Thread.sleep(1000);
		  configPage.option.expandDropdownList();
		  Thread.sleep(1000);
		  configPage.option.selectOption("●");
		  Thread.sleep(1000);
		  configPage.text.openTextBox(configDataTableId, 1, 7);
		  Thread.sleep(1000);
		  
		  //save configuration table
		  logger.info("save configuration table");
		  configPage.button.clickButton("保存");
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
