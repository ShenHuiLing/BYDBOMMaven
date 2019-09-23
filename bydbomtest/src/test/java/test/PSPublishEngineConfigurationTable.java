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
		  
		  page.mainMenu.hoverMenu("�������");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("���󱨸����");
		  Thread.sleep(10000);
		  
		  String labelId;
		  String projectCode;
		  String basicCarCode;
		  
		  //create a new PS
		  logger.info("create a new PS");
		  page.button.clickButton("����");
		  Thread.sleep(5000);
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "PS���", 1);
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
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTGRIDCOMBOBOX,"��������",0);
		  page.button.clickMagnifyingGlass(TableStyle.GANTGRIDCOMBOBOX, labelId, 1, 2);
		  Thread.sleep(1000);
		  page.text.inputText("nodeCode", basicCarCode);
		  Thread.sleep(1000);
		  
		  page.button.clickButton("��ѯ", 1);
		  Thread.sleep(1000);
		  
		  String tableId;
		  tableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 1);
		  page.otherElements.clickRowByText(TableStyle.GRIDVIEW, tableId, "5", basicCarCode);
		  Thread.sleep(2000);
		  page.button.clickButton("ѡ��");
		  Thread.sleep(1000);
		  
		  //select the change type
		  logger.info("select the change type");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"�������",1);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("�㲿��/���/BOM/���ñ��");
		  Thread.sleep(1000);
		  
		  //select the change content
		  logger.info("select the change content");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"�������",1);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("���������ñ���");
		  Thread.sleep(1000);
		  
		  //select the change source
		  logger.info("select the change source");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"�����Դ",0);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("��˾����");
		  Thread.sleep(1000);
		  
		  //select the change phase
		  logger.info("select the change phase");
		  labelId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"����׶�",0);
		  page.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  page.option.selectOption("����ǰ");
		  Thread.sleep(1000);
		  
		  //input the change brief
		  logger.info("input the change brief");
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "�������");
		  page.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
		  Thread.sleep(1000);
		  page.text.inputText("changeExt.changeTheme","PartChange_publish_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change reason
		  logger.info("input the change reason");
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "���ԭ��");
		  page.text.openTextBox(TextStyle.TEXTAREAFIELD, labelId, 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTAREAFIELD,"changeReason","PartChange_publish_reason_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change method
		  logger.info("input the change method");
		  labelId=page.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "�����ʩ");
		  page.text.openTextBox(TextStyle.TEXTAREAFIELD, labelId, 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTAREAFIELD,"changeExt.changeMeasures","PartChange_publish_method_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //save the PS
		  logger.info("save the PS");
		  page.button.clickButton("����");
		  Thread.sleep(5000);
		  
		  //add the change content
		  logger.info("open part change tab");
		  page.tab.clickTab("���ñ���");
		  Thread.sleep(10000);
		  labelId=page.otherElements.getLabelId(LabelStyle.mstdata_varibalevehiclecombobox, "���ó���");
		  System.out.println(labelId);
		  page.text.openTextBox(TextStyle.mstdata_varibalevehiclecombobox, labelId, 0);
		  Thread.sleep(3000);
		  page.option.SelectAllCheckboxOption();
		  Thread.sleep(3000);
		  page.button.clickButton("�������");
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
