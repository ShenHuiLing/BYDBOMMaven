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

public class PSPublishEngineConfigurationTable extends BTest{
  @Test
  public void PSpublishEngineConfigurationTable() throws IOException {
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(15000);
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(20000);
		  
		  Page page=new Page(super.driver);
		  
		  //open PS window
		  logger.info("open PS management window");
		  
		  page.mainMenu.hoverMenu("变更管理");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("评审报告管理");
		  Thread.sleep(10000);
		  
		  String labelId;
		  String projectCode;
		  String basicCarCode;
		  
		  //create a new PS
		  logger.info("create a new PS");
		  page.button.clickButton("新增");
		  Thread.sleep(5000);
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "PS编号", 1);
		  String changeOrder=page.text.getValueFromTextBox(labelId, "changeCode", 0);
		  logger.info("new PS number: " + changeOrder);
		  
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  projectCode=super.bcf.getProperty("ProjectCode");
		  basicCarCode=super.bcf.getProperty("BasicCar");
		  logger.info("vehicle mode code: " + projectCode);
		  logger.info("part number: " + projectCode);
		  logger.info("basic car code: " + basicCarCode);
		  
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
		  logger.info("select the change type");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"变更类型",1);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("零部件/软件/BOM/配置变更");
		  Thread.sleep(1000);
		  
		  //select the change content
		  logger.info("select the change content");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"变更内容",1);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("仅工程配置表发布");
		  Thread.sleep(1000);
		  
		  //select the change source
		  logger.info("select the change source");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"变更来源",0);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("公司定义");
		  Thread.sleep(1000);
		  
		  //select the change phase
		  logger.info("select the change phase");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"评审阶段",0);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("试制前");
		  Thread.sleep(1000);
		  
		  //input the change brief
		  logger.info("input the change brief");
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "变更主题");
		  page.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
		  Thread.sleep(1000);
		  page.text.inputText("changeExt.changeTheme","PartChange_publish_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change reason
		  logger.info("input the change reason");
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "变更原因");
		  page.text.openTextBox(TextStyle.TEXTAREAFIELD, labelId, 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTAREAFIELD,"changeReason","PartChange_publish_reason_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change method
		  logger.info("input the change method");
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "变更措施");
		  page.text.openTextBox(TextStyle.TEXTAREAFIELD, labelId, 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTAREAFIELD,"changeExt.changeMeasures","PartChange_publish_method_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //save the PS
		  logger.info("save the PS");
		  page.button.clickButton("保存");
		  Thread.sleep(5000);
		  
		  //add the change content
		  logger.info("open part change tab");
		  page.tab.clickTab("配置表变更");
		  Thread.sleep(10000);
		  labelId=page.otherElements.getLabelId(LabelStyle.mstdata_varibalevehiclecombobox, "配置车型");
		  System.out.println(labelId);
		  page.text.openTextBox(TextStyle.mstdata_varibalevehiclecombobox, labelId, 0);
		  Thread.sleep(3000);
		  page.option.SelectAllCheckboxOption();
		  Thread.sleep(3000);
		  page.button.clickButton("关联变更");
		  Thread.sleep(10000);
		  
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
