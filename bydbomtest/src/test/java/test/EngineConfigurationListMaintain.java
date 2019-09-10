package test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.BTest;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.TableStyle;
import page.MainPage;
import page.ConfigurationPage;


public class EngineConfigurationListMaintain extends BTest {
  @Test
  public void AssociateConfigurationToList() throws IOException{
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(10000);
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(10000);
		  
		  //open planning configuration car window
		  logger.info("open engine configuration list window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("配置管理");
		  Thread.sleep(2000);
		  mainPage.mainMenu.hoverMenu("工程配置");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("产品特征清单");
		  Thread.sleep(5000);
		  
		  ConfigurationPage ConfigPage=new ConfigurationPage(super.driver);
		  
		  //select planning configuration car
		  logger.info("select planning configuration car");
		  String projectCode;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  projectCode=super.bcf.getProperty("ProjectCode");
		  String labelId=ConfigPage.otherElements.getLabelId(LabelStyle.GANTCOMBOBOX,"车型型号");
		  ConfigPage.option.expandDropdownList(DropDownListStyle.GANTCOMBOBOX,labelId);
		  Thread.sleep(5000);
		  ConfigPage.option.selectOption(projectCode);
		  Thread.sleep(5000);
		  
		  //associate configuration to the list
		  logger.info("associate configuration to the list");
		  ConfigPage.button.clickButton("关联特征值");
		  Thread.sleep(10000);
		  
		  //input the engine configuration for search
		  String engineConfiguration=super.bcf.getProperty("EngineConfig");
		  ConfigPage.text.inputText("featureCode", 1, engineConfiguration);
		  Thread.sleep(1000);
		  ConfigPage.button.clickButton("查询", 1);
		  Thread.sleep(5000);
		  
		  //check the engine configuration which was found
		  String PopUpTableId=ConfigPage.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		  ConfigPage.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  ConfigPage.button.clickButton(">>");
		  Thread.sleep(1000);
		  
		  //ConfigPage.otherElements.resizePopupWindow("gantang.cfgmgmt.planconfig.planconfigdesc.PlanConfigDescSelectorView");
		  ConfigPage.button.clickButton("确定");
		  Thread.sleep(2000);
		  
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
	  //super.close();
  }

}
