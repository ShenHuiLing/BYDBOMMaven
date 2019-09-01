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
import page.PlanningConfigurationPage;


public class PlanningConfigurationListMaintain extends BTest {
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
		  logger.info("open planning configuration list window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("配置管理");
		  Thread.sleep(2000);
		  mainPage.mainMenu.hoverMenu("规划配置");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("规划配置描述清单");
		  Thread.sleep(5000);
		  
		  PlanningConfigurationPage planConfigPage=new PlanningConfigurationPage(super.driver);
		  
		  //select planning configuration car
		  logger.info("select planning configuration car");
		  String planningConfigurationCarName;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  planningConfigurationCarName=super.bcf.getProperty("PlanningConfigurationCarName");
		  String labelId=planConfigPage.otherElements.getLabelId(LabelStyle.GANTCOMBOBOX,"规划车型");
		  planConfigPage.option.expandDropdownList(DropDownListStyle.GANTCOMBOBOX,labelId);
		  Thread.sleep(2000);
		  planConfigPage.option.selectOption(planningConfigurationCarName);
		  Thread.sleep(2000);
		  
		  //associate configuration to the list
		  logger.info("associate configuration to the list");
		  planConfigPage.button.clickButton("关联规划配置描述");
		  Thread.sleep(2000);
		  
		  //input the planning configuration for search
		  String planningConfiguration=super.bcf.getProperty("PlanningConfig");
		  planConfigPage.text.inputText("level3", 1, planningConfiguration);
		  Thread.sleep(1000);
		  planConfigPage.button.clickButton("查询", 1);
		  Thread.sleep(1000);
		  
		  //check the planning configuration which was found
		  String PopUpTableId=planConfigPage.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		  planConfigPage.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  planConfigPage.button.clickButton(">>");
		  Thread.sleep(1000);
		  
		  //planConfigPage.otherElements.resizePopupWindow("gantang.cfgmgmt.planconfig.planconfigdesc.PlanConfigDescSelectorView");
		  planConfigPage.button.clickButton("确定");
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
	  super.close();
  }

}
