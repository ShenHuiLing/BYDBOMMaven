package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ChangeOrderType;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.TableStyle;
import common.TextStyle;
import page.Page;

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
		  
		  Page page=new Page(super.driver);
		  
		  //open PS window
		  logger.info("open PS management window");
		  page.mainMenu.hoverMenu("变更管理");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("评审报告管理");
		  Thread.sleep(5000);
		  
		  String labelId;
		  String partNum;
		  String basicCarCode;
		  
		  //create a new PS
		  logger.info("create a new PS");
		  page.button.clickButton("新增");
		  Thread.sleep(5000);
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "PS编号", 1);
		  String changeOrder=page.text.getValueFromTextBox(labelId, "changeCode", 0);
		  logger.info("new PS number: " + changeOrder);
		  
		 
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  partNum=super.bcf.getProperty("PartNum");
		  basicCarCode=super.bcf.getProperty("BasicCar");
		  
		  //select the basic car code
		  logger.info("select basic car code");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTGRIDCOMBOBOX,"基础车型",0);
		  page.button.clickMagnifyingGlass(TableStyle.GANTGRIDCOMBOBOX, labelId, 1, 2);
		  Thread.sleep(1000);
		  page.text.inputText("nodeCode", basicCarCode);
		  Thread.sleep(1000);
		  
		  page.button.clickButton("查询", 1);
		  Thread.sleep(1000);
		  
		  String tableId;
		  tableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 1);
		  page.otherElements.clickRowByText(TableStyle.GRIDVIEW, tableId, "5", basicCarCode);
		  Thread.sleep(2000);
		  page.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  //select the change type
		  logger.info("select change type as only BOM change");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"变更类型",1);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("仅BOM变更");
		  Thread.sleep(1000);
		  
		  //select the change source
		  logger.info("select change source");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"变更来源",0);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("公司定义");
		  Thread.sleep(1000);
		  
		  //select the stage
		  logger.info("select stage");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"评审阶段",0);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("试制前");
		  Thread.sleep(1000);
		  
		  String Id;
		  //input the change brief
		  logger.info("input change brief");
		  Id=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "变更主题");
		  page.text.openTextBox(TextStyle.IDININPUT, Id, 1);
		  Thread.sleep(1000);
		  page.text.inputText("changeExt.changeTheme","EBOM_publish_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change reason
		  logger.info("select change reason");
		  Id=page.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "变更原因");
		  page.text.openTextBox(TextStyle.TEXTAREAFIELD, Id, 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTAREAFIELD,"changeReason","EBOM_publish_reason_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change method
		  logger.info("select change method");
		  Id=page.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "变更措施");
		  page.text.openTextBox(TextStyle.TEXTAREAFIELD, Id, 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTAREAFIELD,"changeExt.changeMeasures","EBOM_publish_method_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //save the PS
		  logger.info("save the change order");
		  page.button.clickButton("保存");
		  Thread.sleep(10000);
		  
		  //add the change content
		  logger.info("assign BOM line to the change order");
		  page.tab.clickTab("仅BOM变更");
		  Thread.sleep(5000);
		  page.button.clickButton("关联");
		  Thread.sleep(5000);
		  
		  //query the BOM
		  page.button.clickButton("查询",1);
		  Thread.sleep(5000);
		  //input the part number and filter out the part
		  String bomLocatorTableId;
		  bomLocatorTableId=page.otherElements.getTableId(TableStyle.COLUMNFILTER, 1);
		  //page.text.openTextBox(bomLocatorTableId, 1, 1);
		  //Thread.sleep(1000);
		  page.text.inputText(bomLocatorTableId, partNum);
		  Thread.sleep(1000);
		  //use the open text box in table method to click the magnification glass as they are the same kind of element "div"
		  page.text.openTextBox(bomLocatorTableId, 1, 2);
		  Thread.sleep(1000);
		  
		  String mainDataTableId;
		  //choose the part which is found
		  mainDataTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 2);
		  System.out.println(mainDataTableId);
		  page.option.clickCheckBox(mainDataTableId, 2, 1);
		  Thread.sleep(1000);
		  
		  page.button.clickButton("选择");
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
