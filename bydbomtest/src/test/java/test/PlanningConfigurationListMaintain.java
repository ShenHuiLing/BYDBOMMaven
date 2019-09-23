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
import page.Page;



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
		  
		  Page page=new Page(super.driver);
		  
		  //open planning configuration car window
		  logger.info("open planning configuration list window");
		  page.mainMenu.hoverMenu("配置管理");
		  Thread.sleep(2000);
		  page.mainMenu.hoverMenu("规划配置");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("规划配置描述清单");
		  Thread.sleep(20000);
		  
		  //select planning configuration car
		  logger.info("select planning configuration car");
		  String planningConfigurationCarName;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  planningConfigurationCarName=super.bcf.getProperty("PlanningConfigurationCarName");
		  String labelId=page.otherElements.getLabelId(LabelStyle.GANTCOMBOBOX,"规划车型");
		  page.option.expandDropdownList(DropDownListStyle.GANTCOMBOBOX,labelId);
		  Thread.sleep(20000);
		  page.option.selectOption(planningConfigurationCarName);
		  Thread.sleep(10000);
		  
		  //associate configuration to the list
		  logger.info("associate configuration to the list");
		  page.button.clickButton("关联规划配置描述");
		  Thread.sleep(10000);
		  
		  //input the planning configuration for search
		  String planningConfiguration=super.bcf.getProperty("PlanningConfig");
		  page.text.inputText("level3", 1, planningConfiguration);
		  Thread.sleep(1000);
		  page.button.clickButton("查询", 1);
		  Thread.sleep(5000);
		  
		  //check the planning configuration which was found
		  String PopUpTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		  page.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  page.button.clickButton(">>");
		  Thread.sleep(1000);
		  
		  //page.otherElements.resizePopupWindow("gantang.cfgmgmt.planconfig.planconfigdesc.PlanConfigDescSelectorView");
		  page.button.clickButton("确定");
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
