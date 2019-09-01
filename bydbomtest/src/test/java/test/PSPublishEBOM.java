package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ChangeOrderType;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.ListViewStyle;
import common.TableStyle;
import common.TextStyle;
import page.MainPage;
import page.PSPage;

import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class PSPublishEBOM extends BTest{
  @Test
  public void publishEBOMWithECO() throws IOException {
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(10000);
		
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(10000);
		  
		  //open PS window
		  logger.info("open PS management window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("变更管理");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("评审报告管理");
		  Thread.sleep(5000);
		  
		  PSPage psPage=new PSPage(super.driver);
		  
		  //create a new PS
		  logger.info("create a new PS");
		  psPage.button.clickButton("新增");
		  Thread.sleep(1000);
		  String changeOrder=psPage.text.getChangeOrderNumber();
		  System.out.println(changeOrder);
		  
		  String labelId;
		  String prjectCode;
		  String partNum;
		  String basicCarCode;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  prjectCode=super.bcf.getProperty("ProjectCode");
		  partNum=super.bcf.getProperty("PartNum");
		  basicCarCode=super.bcf.getProperty("BasicCar");
		  
		  //select the basic car code
		  logger.info("select basic car code");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTGRIDCOMBOBOX,"基础车型",0);
		  psPage.button.clickMagnifyingGlass(TableStyle.GANTGRIDCOMBOBOX, labelId, 1, 2);
		  Thread.sleep(1000);
		  psPage.text.inputText("nodeCode", basicCarCode);
		  Thread.sleep(1000);
		  
		  psPage.button.clickButton("查询", 1);
		  Thread.sleep(1000);
		  
		  String tableId;
		  tableId=psPage.otherElements.getTableId(TableStyle.GRIDVIEW, 1);
		  psPage.otherElements.clickRowByText(TableStyle.GRIDVIEW, tableId, "5", basicCarCode);
		  Thread.sleep(2000);
		  psPage.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  //select the change type
		  logger.info("select change type as only BOM change");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"变更类型",1);
		  psPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  psPage.option.selectOption("仅BOM变更");
		  Thread.sleep(1000);
		  
		  //select the change source
		  logger.info("select change source");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"变更来源",0);
		  psPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  psPage.option.selectOption("公司定义");
		  Thread.sleep(1000);
		  
		  //select the stage
		  logger.info("select stage");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"评审阶段",0);
		  psPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  psPage.option.selectOption("试制前");
		  Thread.sleep(1000);
		  
		  String Id;
		  //input the change brief
		  logger.info("input change brief");
		  Id=psPage.otherElements.getLabelId(LabelStyle.TEXTFIELD, "变更主题");
		  psPage.text.openTextBox(TextStyle.IDININPUT, Id, 1);
		  Thread.sleep(1000);
		  psPage.text.inputText("changeExt.changeTheme","EBOM_publish_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change reason
		  logger.info("select change reason");
		  Id=psPage.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "变更原因");
		  psPage.text.openTextBox(TextStyle.TEXTAREAFIELD, Id, 1);
		  Thread.sleep(1000);
		  psPage.text.inputText(TextStyle.TEXTAREAFIELD,"changeReason","EBOM_publish_reason_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change method
		  logger.info("select change method");
		  Id=psPage.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "变更措施");
		  psPage.text.openTextBox(TextStyle.TEXTAREAFIELD, Id, 1);
		  Thread.sleep(1000);
		  psPage.text.inputText(TextStyle.TEXTAREAFIELD,"changeExt.changeMeasures","EBOM_publish_method_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //save the PS
		  logger.info("save the change order");
		  psPage.button.clickButton("保存");
		  Thread.sleep(3000);
		  
		  //add the change content
		  logger.info("assign BOM line to the change order");
		  psPage.tab.clickTab("仅BOM变更");
		  Thread.sleep(1000);
		  psPage.button.clickButton("关联");
		  Thread.sleep(3000);
		  
		  //query the BOM
		  psPage.button.clickButton("查询",1);
		  Thread.sleep(3000);
		  //input the part number and filter out the part
		  String bomLocatorTableId;
		  bomLocatorTableId=psPage.otherElements.getTableId(TableStyle.COLUMNFILTER, 1);
		  //psPage.text.openTextBox(bomLocatorTableId, 1, 1);
		  //Thread.sleep(1000);
		  psPage.text.inputText(bomLocatorTableId, partNum);
		  Thread.sleep(1000);
		  //use the open text box in table method to click the magnification glass as they are the same kind of element "div"
		  psPage.text.openTextBox(bomLocatorTableId, 1, 2);
		  Thread.sleep(1000);
		  
		  String mainDataTableId;
		  //choose the part which is found
		  mainDataTableId=psPage.otherElements.getTableId(TableStyle.GRIDVIEW, 2);
		  System.out.println(mainDataTableId);
		  psPage.option.clickCheckBox(mainDataTableId, 2, 1);
		  Thread.sleep(1000);
		  
		  psPage.button.clickButton("选择");
		  Thread.sleep(5000);
		 
		  //start approval process
		  super.startApprovalProcess(ChangeOrderType.BOM);
		  
		  logger.info("save the change order number: " + changeOrder);
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
