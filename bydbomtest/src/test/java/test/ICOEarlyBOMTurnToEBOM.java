package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ChangeOrderType;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import page.Page;

import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class ICOEarlyBOMTurnToEBOM extends BTest{
  @Test
  public void earLyBOMTurnToEBOM() throws IOException {
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(10000);
		
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(10000);
		  
		  Page page=new Page(super.driver);
		  
		  //open ICO window
		  logger.info("open BOM data publish order window");
		  page.mainMenu.hoverMenu("变更管理");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("BOM数据发布管理");
		  Thread.sleep(10000);
		  
		  String labelId;
		  String prjectCode;
		  
		  //create a new ICO
		  logger.info("create a new order");
		  page.button.clickButton("新增");
		  Thread.sleep(1000);
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "ICO编号", 1);
		  String changeOrder=page.text.getValueFromTextBox(labelId, "changeCode", 0);
		  logger.info("new ICO number: " + changeOrder);

		  
		  //select vehicle mode code
		  logger.info("select vehicle mode code");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCOMBOBOX,"车型型号",1);
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  prjectCode=super.bcf.getProperty("ProjectCode");
		  page.option.expandDropdownList(DropDownListStyle.GANTCOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption(prjectCode);
		  Thread.sleep(1000);
		  
		  //select type
		  logger.info("select order type");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"类型",1);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("早期BOM转工程BOM");
		  Thread.sleep(1000);
		  
		  logger.info("save the order");
		  page.button.clickButton("保存");
		  Thread.sleep(3000);
		  
		  //add the change content
		  logger.info("switch early BOM to EBOM tab");
		  page.tab.clickTab("早期BOM转工程BOM");
		  Thread.sleep(5000);
		  logger.info("assign the early BOM to the order");
		  page.button.clickButton("关联");
		  Thread.sleep(5000);
		  
		  //start approval process
		  super.startApprovalProcess(ChangeOrderType.BOM);
		  
		  Map<String, String> testData=new HashMap<String, String>();
		  testData.put("ChangeOrder",changeOrder);
		  super.bcf.writeJasonFile(EnvJsonFile.TESTDATA, testData);
		  logger.info("save the change order: " + changeOrder);
		  
	  }catch(Exception e){
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
