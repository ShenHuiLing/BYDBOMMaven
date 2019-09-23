package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ChangeOrderType;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.TableStyle;
import page.Page;

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
		  
		  Page page=new Page(super.driver);
		  
		  //open PCO window
		  logger.info("open PCO management window");
		  page.mainMenu.hoverMenu("�������");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("�滮���ñ������");
		  Thread.sleep(10000);
		  
		  String labelId;
		  
		  //create a new PCO
		  logger.info("create a new PCO");
		  page.button.clickButton("����");
		  Thread.sleep(5000);
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "PCO���", 1);
		  String changeOrder=page.text.getValueFromTextBox(labelId, "changeCode", 0);
		  logger.info("new PS number: " + changeOrder);
		  
		  //select planning configuration car
		  logger.info("select planning configuration car");
		  String planningConfigurationCar;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  planningConfigurationCar=super.bcf.getProperty("PlanningConfigurationCarName");
		  String MagnifyingGlassTableId=page.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 1);
		  page.button.clickMagnifyingGlass(TableStyle.GANGTRIGGERFIELD, MagnifyingGlassTableId,1,2);
		  Thread.sleep(3000);
		  //input planning configuration car for search
		  page.text.inputText("planVehicleName", planningConfigurationCar);
		  Thread.sleep(1000);
		  page.button.clickButton("��ѯ", 1);
		  Thread.sleep(1000);
		  String PopUpTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		  page.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  page.button.clickButton("ѡ��");
		  Thread.sleep(1000);
		  
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"����汾",1);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("A��");
		  Thread.sleep(1000);
		  
		  //save the PS
		  logger.info("save the PCO");
		  page.button.clickButton("����");
		  Thread.sleep(5000);
		  
		  //add the change content
		  logger.info("add the change content");
		  page.tab.clickTab("�������");
		  Thread.sleep(10000);
		  page.button.clickButton("�������");
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
