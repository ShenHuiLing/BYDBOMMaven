package test;


import org.testng.annotations.Test;

import base.BTest;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.ListViewStyle;
import common.TableStyle;
import common.TextStyle;
import page.Page;

import org.testng.annotations.BeforeTest;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

/**
 * EBOM maintain
 * 1. add part
 * 2. input the mandatory fields including quantity, structure code, assemble workshop, suggested source, development strategy 
 * 3. save
 * @author alans
 *
 */
public class EBOMManagement extends BTest {
  @Test
  public void EBOMAddPart() throws IOException {
	  try {
		  
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(15000);
		  
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(20000);
		  
		  Page page=new Page(super.driver);
		  
		  //open EBOM window
		  logger.info("open EBOM window");
		  page.mainMenu.hoverMenu("BOM����");
		  Thread.sleep(2000);
		  page.mainMenu.hoverMenu("����BOM����");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("����BOM����");
		  Thread.sleep(10000);
		  
		  page.button.clickMaximizeButton();
		  Thread.sleep(1000);
		  
		  //select project code and query the bom
		  logger.info("select vehicle mode code");
		  String labelId=page.otherElements.getLabelId(LabelStyle.COMBO,"�����ͺ�");
		  System.out.println(labelId);
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  String prjectCode=super.bcf.getProperty("ProjectCode");
		  page.option.expandDropdownList(DropDownListStyle.COMBO,labelId);
		  Thread.sleep(2000);
		  page.text.inputText("partId", prjectCode);
		  Thread.sleep(2000);
		  page.option.selectOption(prjectCode);
		  Thread.sleep(1000);
		  page.button.clickButton("��ѯ");
		  Thread.sleep(10000);
		  
		  page.option.clickCheckBox(0,ListViewStyle.GRIDVIEW);
		  Thread.sleep(1000);
		  
		  //add a part
		  logger.info("start editting");
		  page.button.clickButton("����༭");
		  Thread.sleep(1000);
		  logger.info("add a new node");
		  page.button.clickButton("����");
		  Thread.sleep(1000);
		  page.button.clickChildButton("�����ӽڵ�");
		  Thread.sleep(1000);
		  
		  //from the part selector, choose a part
		  String partNum=super.bcf.getProperty("PartNum");
		  super.selectPartFromPartSelector(partNum, 2, true);
		  /*
		  String Id;
		  Id=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "�����");
		  page.text.openTextBox(TextStyle.IDININPUT, Id, 1);
		  page.text.inputText(TextStyle.TEXTFIELD,super.bcf.getProperty("PartNum"));
		  Thread.sleep(1000);
		  page.button.clickButton("��ѯ",1);
		  Thread.sleep(1000);
		  String PopUpTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,2);
		  page.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  page.button.clickButton("ѡ��");
		  Thread.sleep(1000);
		  page.button.clickCloseButton(1);
		  Thread.sleep(1000);
		  */
		  String mainDataTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		 
		  //input quantity
		  logger.info("input quantity");
		  Thread.sleep(1000);
		  page.text.openTextBox(mainDataTableId, 2, 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.NUMBERFIELD, super.bcf.getTimeStamp().substring(4));
		  Thread.sleep(1000);
		  
		  String PopUpTableId;
		  //check if there is functional position code. if no, select functional position code
		  if(page.text.isTextBoxEmpty(mainDataTableId, 2, 27)) {
			  logger.info("select functional position code");
			  page.text.openTextBox(mainDataTableId, 2, 27);
			  Thread.sleep(1000);
			  String MagnifyingGlassTableId=page.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 2);
			  page.button.clickMagnifyingGlass(TableStyle.GANGTRIGGERFIELD, MagnifyingGlassTableId,1,2);
			  Thread.sleep(3000);
			  PopUpTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,2);
			  page.option.clickCheckBox(PopUpTableId, 2,1);
			  Thread.sleep(1000);
			  page.button.clickButton("ѡ��");
			  Thread.sleep(2000);
		  }
		  
		  
		  
		  //select the assemble workshop
		  logger.info("select assemble workshop");
		  page.text.openTextBox(mainDataTableId, 2, 2);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("��װ����");
		  Thread.sleep(1000);
		  
		  
		  //check if there is suggested source. If no, add it
		  if(page.text.isTextBoxEmpty(mainDataTableId, 2, 6)) {
			  logger.info("select suggested source");
			  page.text.openTextBox(mainDataTableId, 2, 6);
			  Thread.sleep(1000);
			  page.option.expandDropdownList();
			  Thread.sleep(1000);
			  page.option.selectOption("����-Ϳ");
			  Thread.sleep(1000);
		  }
		  
		  
		  //select development strategy
		  logger.info("select development strategy");
		  page.text.openTextBox(mainDataTableId, 2, 7);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("�¿���");
		  Thread.sleep(2000);
		  page.text.openTextBox(mainDataTableId, 2, 12);
		  
		  //save
		  logger.info("save");
		  page.button.clickButton("����");
		  Thread.sleep(2000);
			  	  
	} catch (Exception e) {
		super.TakeSnap();
		logger.error(e.getMessage());
		// TODO Auto-generated catch block
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
