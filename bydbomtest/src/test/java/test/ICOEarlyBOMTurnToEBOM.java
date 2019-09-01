package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ChangeOrderType;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import page.BOMPublishPage;
import page.MainPage;

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
		  
		  //open ICO window
		  logger.info("open BOM data publish order window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("变更管理");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("BOM数据发布管理");
		  Thread.sleep(2000);
		  
		  BOMPublishPage bomPublishPage=new BOMPublishPage(super.driver);
		  
		  //create a new ICO
		  logger.info("create a new order");
		  bomPublishPage.button.clickButton("新增");
		  Thread.sleep(1000);
		  String changeOrder=bomPublishPage.text.getChangeOrderNumber();
		  System.out.println(changeOrder);
		  
		  String labelId;
		  String prjectCode;
		  
		  //select vehicle mode code
		  logger.info("select vehicle mode code");
		  labelId=bomPublishPage.otherElements.getLabelId(LabelStyle.GANTCOMBOBOX,"车型型号",1);
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  prjectCode=super.bcf.getProperty("ProjectCode");
		  bomPublishPage.option.expandDropdownList(DropDownListStyle.GANTCOMBOBOX,labelId);
		  Thread.sleep(2000);
		  bomPublishPage.option.selectOption(prjectCode);
		  Thread.sleep(1000);
		  
		  //select type
		  logger.info("select order type");
		  labelId=bomPublishPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"类型",1);
		  bomPublishPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  bomPublishPage.option.selectOption("早期BOM转工程BOM");
		  Thread.sleep(1000);
		  
		  logger.info("save the order");
		  bomPublishPage.button.clickButton("保存");
		  Thread.sleep(3000);
		  
		  //add the change content
		  logger.info("switch early BOM to EBOM tab");
		  bomPublishPage.tab.clickTab("早期BOM转工程BOM");
		  Thread.sleep(1000);
		  logger.info("assign the early BOM to the order");
		  bomPublishPage.button.clickButton("关联");
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
