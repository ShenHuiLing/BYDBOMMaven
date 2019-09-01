package test;


import org.testng.annotations.Test;

import base.BTest;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.ListViewStyle;
import common.TableStyle;
import common.TextStyle;
import page.EBOMPage;
import page.MainPage;

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
		  
		  //open EBOM window
		  logger.info("open EBOM window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("BOM管理");
		  Thread.sleep(2000);
		  mainPage.mainMenu.hoverMenu("工程BOM管理");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("工程BOM管理");
		  Thread.sleep(10000);
		  
		  EBOMPage eBomPage=new EBOMPage(super.driver);
		  
		  eBomPage.button.clickMaximizeButton();
		  Thread.sleep(1000);
		  
		  //select project code and query the bom
		  logger.info("select vehicle mode code");
		  String labelId=eBomPage.otherElements.getLabelId(LabelStyle.COMBO,"车型型号");
		  System.out.println(labelId);
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  String prjectCode=super.bcf.getProperty("ProjectCode");
		  eBomPage.option.expandDropdownList(DropDownListStyle.COMBO,labelId);
		  Thread.sleep(2000);
		  eBomPage.text.inputText("partId", prjectCode);
		  Thread.sleep(2000);
		  eBomPage.option.selectOption(prjectCode);
		  Thread.sleep(1000);
		  eBomPage.button.clickButton("查询");
		  Thread.sleep(5000);
		  
		  eBomPage.option.clickCheckBox(0,ListViewStyle.GRIDVIEW);
		  
		  //add a part
		  logger.info("start editting");
		  eBomPage.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  logger.info("add a new node");
		  eBomPage.button.clickButton("新增");
		  Thread.sleep(1000);
		  eBomPage.button.clickChildButton("新增子节点");
		  Thread.sleep(1000);
		  
		  //from the part selector, choose a part
		  String partNum=super.bcf.getProperty("PartNum");
		  super.selectPartFromPartSelector(partNum, 2, true);
		  /*
		  String Id;
		  Id=eBomPage.otherElements.getLabelId(LabelStyle.TEXTFIELD, "零件号");
		  eBomPage.text.openTextBox(TextStyle.IDININPUT, Id, 1);
		  eBomPage.text.inputText(TextStyle.TEXTFIELD,super.bcf.getProperty("PartNum"));
		  Thread.sleep(1000);
		  eBomPage.button.clickButton("查询",1);
		  Thread.sleep(1000);
		  String PopUpTableId=eBomPage.otherElements.getTableId(TableStyle.GRIDVIEW,2);
		  eBomPage.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  eBomPage.button.clickButton("选择");
		  Thread.sleep(1000);
		  eBomPage.button.clickCloseButton(1);
		  Thread.sleep(1000);
		  */
		  String mainDataTableId=eBomPage.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		 
		  //input quantity
		  logger.info("input quantity");
		  Thread.sleep(1000);
		  eBomPage.text.openTextBox(mainDataTableId, 2, 1);
		  Thread.sleep(1000);
		  eBomPage.text.inputText(TextStyle.NUMBERFIELD, super.bcf.getTimeStamp().substring(4));
		  Thread.sleep(1000);
		  
		  String PopUpTableId;
		  //check if there is functional position code. if no, select functional position code
		  if(eBomPage.text.isTextBoxEmpty(mainDataTableId, 2, 27)) {
			  logger.info("select functional position code");
			  eBomPage.text.openTextBox(mainDataTableId, 2, 27);
			  Thread.sleep(1000);
			  String MagnifyingGlassTableId=eBomPage.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 2);
			  eBomPage.button.clickMagnifyingGlass(TableStyle.GANGTRIGGERFIELD, MagnifyingGlassTableId,1,2);
			  Thread.sleep(3000);
			  PopUpTableId=eBomPage.otherElements.getTableId(TableStyle.GRIDVIEW,2);
			  eBomPage.option.clickCheckBox(PopUpTableId, 2,1);
			  Thread.sleep(1000);
			  eBomPage.button.clickButton("选择");
			  Thread.sleep(2000);
		  }
		  
		  
		  
		  //select the assemble workshop
		  logger.info("select assemble workshop");
		  eBomPage.text.openTextBox(mainDataTableId, 2, 2);
		  Thread.sleep(1000);
		  eBomPage.option.expandDropdownList();
		  Thread.sleep(1000);
		  eBomPage.option.selectOption("总装车间");
		  Thread.sleep(1000);
		  
		  
		  //check if there is suggested source. If no, add it
		  if(eBomPage.text.isTextBoxEmpty(mainDataTableId, 2, 6)) {
			  logger.info("select suggested source");
			  eBomPage.text.openTextBox(mainDataTableId, 2, 6);
			  Thread.sleep(1000);
			  eBomPage.option.expandDropdownList();
			  Thread.sleep(1000);
			  eBomPage.option.selectOption("自制-涂");
			  Thread.sleep(1000);
		  }
		  
		  
		  //select development strategy
		  logger.info("select development strategy");
		  eBomPage.text.openTextBox(mainDataTableId, 2, 7);
		  Thread.sleep(1000);
		  eBomPage.option.expandDropdownList();
		  Thread.sleep(1000);
		  eBomPage.option.selectOption("新开发");
		  Thread.sleep(2000);
		  eBomPage.text.openTextBox(mainDataTableId, 2, 12);
		  
		  //save
		  logger.info("save");
		  eBomPage.button.clickButton("保存");
		  Thread.sleep(2000);
		  
		  Assert.assertEquals(eBomPage.otherElements.isEditFlagDisappeared(ListViewStyle.GRIDVIEW), true);
			  	  
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
