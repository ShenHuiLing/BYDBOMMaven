package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ChangeOrderType;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
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

public class PSChangePart extends BTest{
  @Test
  public void PSpublishPartChange() throws IOException {
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(15000);
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(20000);
		  
		  //open PS window
		  logger.info("open PS management window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("�������");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("���󱨸����");
		  Thread.sleep(10000);
		  
		  PSPage psPage=new PSPage(super.driver);
		  
		  //create a new PS
		  logger.info("create a new PS");
		  psPage.button.clickButton("����");
		  Thread.sleep(1000);
		  String changeOrder=psPage.text.getChangeOrderNumber();
		  logger.info("new PS number: " + changeOrder);
		  
		  String labelId;
		  String projectCode;
		  String partNum;
		  String basicCarCode;
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  projectCode=super.bcf.getProperty("ProjectCode");
		  partNum=super.bcf.getProperty("PartNum");
		  basicCarCode=super.bcf.getProperty("BasicCar");
		  logger.info("vehicle mode code: " + projectCode);
		  logger.info("part number: " + projectCode);
		  logger.info("basic car code: " + basicCarCode);
		  
		//select the basic car code
		  logger.info("select basic car code");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTGRIDCOMBOBOX,"��������",0);
		  psPage.button.clickMagnifyingGlass(TableStyle.GANTGRIDCOMBOBOX, labelId, 1, 2);
		  Thread.sleep(1000);
		  psPage.text.inputText("nodeCode", basicCarCode);
		  Thread.sleep(1000);
		  
		  psPage.button.clickButton("��ѯ", 1);
		  Thread.sleep(1000);
		  
		  String tableId;
		  tableId=psPage.otherElements.getTableId(TableStyle.GRIDVIEW, 1);
		  psPage.otherElements.clickRowByText(TableStyle.GRIDVIEW, tableId, "5", basicCarCode);
		  Thread.sleep(2000);
		  psPage.button.clickButton("ѡ��");
		  Thread.sleep(1000);
		  
		  //select the change type
		  logger.info("select the change type");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"�������",1);
		  psPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  psPage.option.selectOption("�㲿��/���/BOM/���ñ��");
		  Thread.sleep(1000);
		  
		  //select the change content
		  logger.info("select the change content");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"�������",1);
		  psPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  psPage.option.selectOption("�㲿��/���/BOM/���ñ��");
		  Thread.sleep(1000);
		  
		  //select the change source
		  logger.info("select the change source");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"�����Դ",0);
		  psPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  psPage.option.selectOption("��˾����");
		  Thread.sleep(1000);
		  
		  //select the change phase
		  logger.info("select the change phase");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"����׶�",0);
		  psPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  psPage.option.selectOption("����ǰ");
		  Thread.sleep(1000);
		  
		  //input the change brief
		  logger.info("input the change brief");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.TEXTFIELD, "�������");
		  psPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
		  Thread.sleep(1000);
		  psPage.text.inputText("changeExt.changeTheme","PartChange_publish_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change reason
		  logger.info("input the change reason");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "���ԭ��");
		  psPage.text.openTextBox(TextStyle.TEXTAREAFIELD, labelId, 1);
		  Thread.sleep(1000);
		  psPage.text.inputText(TextStyle.TEXTAREAFIELD,"changeReason","PartChange_publish_reason_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //input the change method
		  logger.info("input the change method");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.TEXTAREAFIELD, "�����ʩ");
		  psPage.text.openTextBox(TextStyle.TEXTAREAFIELD, labelId, 1);
		  Thread.sleep(1000);
		  psPage.text.inputText(TextStyle.TEXTAREAFIELD,"changeExt.changeMeasures","PartChange_publish_method_" + changeOrder);
		  Thread.sleep(1000);
		  
		  //save the PS
		  logger.info("save the PS");
		  psPage.button.clickButton("����");
		  Thread.sleep(5000);
		  
		  //add the change content
		  logger.info("open part change tab");
		  psPage.tab.clickTab("�㲿�����");
		  Thread.sleep(10000);
		  psPage.button.clickButton("����");
		  Thread.sleep(3000);
		  
		  super.selectPartFromPartSelector(partNum, 3, false);
		  Thread.sleep(5000);
		  //click maintain link
		  logger.info("click maintain link");
		  psPage.link.clickLinkByText("ά��");
		  Thread.sleep(1000);
		  
		  //fill the change contents
		  logger.info("select change type");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"����������",0);
		  psPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
		  Thread.sleep(2000);
		  psPage.option.selectOption("ɾ��");
		  Thread.sleep(1000);
		  
		  logger.info("fill the quantity change");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"�����仯",0);
		  psPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
		  Thread.sleep(1000);
		  psPage.text.inputText(TextStyle.TEXTFIELD,"entity.countChange",super.bcf.getTimeStamp().substring(4));
		  Thread.sleep(1000);
		  
		  logger.info("fill the affected project and configuration");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"����Ӱ����Ŀ������",0);
		  psPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
		  Thread.sleep(1000);
		  psPage.text.inputText(TextStyle.TEXTFIELD,"entity.affectProjects","PartChange_affectedProject_" + changeOrder);
		  Thread.sleep(1000);
		  
		  logger.info("fill the supplier");
		  labelId=psPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"��Ӧ��",0);
		  psPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
		  Thread.sleep(1000);
		  psPage.text.inputText(TextStyle.TEXTFIELD,"entity.supplier","PartChange_supplier_" + changeOrder);
		  Thread.sleep(1000);
		  
		  logger.info("click the save button");
		  psPage.button.clickButton("����");
		  Thread.sleep(10000);
		  
		  //start approval process
		  logger.info("start approval process");
		  super.startApprovalProcess(ChangeOrderType.PART);
		  
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
