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
		  mainPage.mainMenu.hoverMenu("���ù���");
		  Thread.sleep(2000);
		  mainPage.mainMenu.hoverMenu("�滮����");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("�滮���������嵥");
		  Thread.sleep(10000);
		  
		  ConfigurationPage planConfigPage=new ConfigurationPage(super.driver);
		  
		  //select planning configuration car
		  logger.info("select planning configuration car");
		  String planningConfigurationCarName;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  planningConfigurationCarName=super.bcf.getProperty("PlanningConfigurationCarName");
		  String labelId=planConfigPage.otherElements.getLabelId(LabelStyle.GANTCOMBOBOX,"�滮����");
		  planConfigPage.option.expandDropdownList(DropDownListStyle.GANTCOMBOBOX,labelId);
		  Thread.sleep(20000);
		  planConfigPage.option.selectOption(planningConfigurationCarName);
		  Thread.sleep(10000);
		  
		  //associate configuration to the list
		  logger.info("associate configuration to the list");
		  planConfigPage.button.clickButton("�����滮��������");
		  Thread.sleep(10000);
		  
		  //input the planning configuration for search
		  String planningConfiguration=super.bcf.getProperty("PlanningConfig");
		  planConfigPage.text.inputText("level3", 1, planningConfiguration);
		  Thread.sleep(1000);
		  planConfigPage.button.clickButton("��ѯ", 1);
		  Thread.sleep(5000);
		  
		  //check the planning configuration which was found
		  String PopUpTableId=planConfigPage.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		  planConfigPage.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  planConfigPage.button.clickButton(">>");
		  Thread.sleep(1000);
		  
		  //planConfigPage.otherElements.resizePopupWindow("gantang.cfgmgmt.planconfig.planconfigdesc.PlanConfigDescSelectorView");
		  planConfigPage.button.clickButton("ȷ��");
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
