package test;


import org.testng.annotations.Test;

import base.BTest;
import common.ColumnStyle;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.ListViewStyle;
import common.TableStyle;
import common.TextStyle;
import page.Page;

import org.testng.annotations.BeforeTest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

/**
 * apply a new part
 * 1. create a part application form
 * 2. fill all the mandatory fields
 * 3. save the form
 * 4. publish the form
 * @author alans
 *
 */
public class PartApply extends BTest{
  @Test
  public void partApplyProcess() throws IOException {
	  try {
		//start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(15000);
		  
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(15000);
		  
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  String basicCarCode=super.bcf.getProperty("BasicCar");
		  
		  Page page=new Page(super.driver);
		  
		  //open product spectrum window
		  logger.info("open part application management window");
		  page.mainMenu.hoverMenu("零部件管理");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("零件申请单管理");
		  Thread.sleep(10000);
		  
		  //create a new part application order
		  logger.info("create a new part application form");
		  page.button.clickButton("新增");
		  Thread.sleep(2000);
		  
		  logger.info("set the part application type as structure part");
		  String labelId=page.otherElements.getLabelId(LabelStyle.COMBO, "申请单类型");
		  page.option.expandDropdownList(DropDownListStyle.COMBO,labelId);
		  Thread.sleep(1000);
		  page.option.selectOption("结构件");
		  Thread.sleep(1000);
		  logger.info("save the new part application");
		  page.button.clickButton("保存");
		  Thread.sleep(10000);
		  
		  logger.info("start editing the fields in the part application");
		  page.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  
		  logger.info("create a new aggregate");
		  page.button.clickButton("新增",1);
		  Thread.sleep(1000);
		  page.button.clickChildButton("新增-总成件");
		  Thread.sleep(5000);
		  
		  //get the part application table Id
		  String partApplicationTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 1);
		  //define the table Id for pop-up selector, like part name selector, VPPD selector, etc.
		  String popUpSelectorTableId;
		  //define the magnifying glass table Id for those field contains magnifying glass button
		  String magnifyingGlassTableId;
		  
		  
		  //select Chinese name for the part
		  logger.info("seletct the Chinese name for the new part");
		  page.text.openTextBox(partApplicationTableId, 1, 7);
		  Thread.sleep(1000);
		  page.button.clickMagnifyingGlass(TableStyle.materialName2, "", 1, 2);
		  Thread.sleep(10000);
		  popUpSelectorTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 2);
		  page.option.clickCheckBox(popUpSelectorTableId, 1, 1);
		  Thread.sleep(1000);
		  page.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  
		  //select car series code
		  logger.info("select the vehicle mode code for the new part");
		  page.text.openTextBox(partApplicationTableId, 1, 8);
		  Thread.sleep(1000);
		  magnifyingGlassTableId=page.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 0);
		  Thread.sleep(1000);
		  page.button.clickMagnifyingGlass(TableStyle.GANGTRIGGERFIELD, magnifyingGlassTableId, 1, 2);
		  Thread.sleep(1000);
		  popUpSelectorTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 2);
		  page.option.clickCheckBox(popUpSelectorTableId, 1, 1);
		  Thread.sleep(1000);
		  page.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  //add the change code
		  logger.info("Fill the change code as part of the part code");
		  page.text.openTextBox(partApplicationTableId, 1, 13);
		  Thread.sleep(1000);
		  System.out.println(super.bcf.getTimeStamp());
		  page.text.inputText(TextStyle.TEXTFIELD, super.bcf.getTimeStamp().substring(7));
		  Thread.sleep(1000);
		  
		  //select the functional position code
		  if(page.text.isTextBoxEmpty(partApplicationTableId, 1, 18)){
			  logger.info("select the functional position code");
			  page.text.openTextBox(partApplicationTableId, 1, 18);
			  Thread.sleep(1000);
			  magnifyingGlassTableId=page.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 1);
			  Thread.sleep(1000);
			  page.button.clickMagnifyingGlass(TableStyle.GANGTRIGGERFIELD, magnifyingGlassTableId, 1, 2);
			  Thread.sleep(1000);
			  popUpSelectorTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 2);
			  page.option.clickCheckBox(popUpSelectorTableId, 2, 1);
			  Thread.sleep(1000);
			  page.button.clickButton("选择");
			  Thread.sleep(2000);
		  }
		  
		  //select suggested source
		  logger.info("Select the suggested source");
		  page.text.openTextBox(partApplicationTableId, 1, 22);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("采购");
		  Thread.sleep(1000);
		  page.text.openTextBox(partApplicationTableId, 1, 23);
		  Thread.sleep(1000);
		  
		  //save
		  logger.info("save the part information");
		  page.button.clickButton("保存");
		  Thread.sleep(5000);
		  
		  //check if the unsaved icon (green plus) disappears, if yes, the data has been saved
		  assertEquals(page.otherElements.isEditFlagDisappeared(ListViewStyle.GRIDVIEW), true);
		  Thread.sleep(1000);
		  
		  //fetch the part number and save the number into json file, it can be used for further testing, like eBOM
		  logger.info("save the part code in test data file for other scripts using");
		  String partNum=page.text.getValueFromTextBox(TableStyle.GRIDVIEW, partApplicationTableId, 1, 6);
		  Map<String, String> testData=new HashMap<String, String>();
		  testData.put("PartNum",partNum);
		  super.bcf.writeJasonFile(EnvJsonFile.TESTDATA, testData);
		  logger.info("part number: " + partNum);
		  
		  //close the part application order window and publish the part application form
		  logger.info("close the part application");
		  String title=page.otherElements.getWindowTitle();
		  System.out.println(title);
		  page.button.clickCloseButton(1);
		  Thread.sleep(1000);
		  
		  //check the part application order and publish it
		  logger.info("publish the part application form");
		  partApplicationTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 0);
		  String columnId=page.otherElements.getColumnId(ColumnStyle.GANTLINKCOLUMN, "申请单号");
		  int linkCount=page.link.getLinkCount(columnId);
		  for(int i=0;i<linkCount;i++) {
			  if(title.contains(page.text.getValueFromTextBox(TableStyle.GRIDVIEW, partApplicationTableId, i+1, 3))) {
				  page.option.clickCheckBox(partApplicationTableId, i+1, 1);
				  Thread.sleep(1000);
				  page.button.clickButton("发布");
				  Thread.sleep(1000);
				  break;
			  }
		  }
		  
		  
	  }
	  catch(Exception e) {
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
