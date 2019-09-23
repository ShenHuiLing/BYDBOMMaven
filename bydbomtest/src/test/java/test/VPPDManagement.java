package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ColumnStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.ListViewStyle;
import common.TableStyle;
import common.TextStyle;
import page.Page;


import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

/* Test: VPPD维护
 * 1. start BOM
 * 2. login BOM
 * 3. open VPPD window
 * 4. add node step by step
 */
public class VPPDManagement extends BTest {
  @Test
  public void AddVPPD() throws IOException {
	  try {
		  
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(15000);
		
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(20000);
		  
		  Page page=new Page(super.driver);
		  
		  //open product structure window
		  logger.info("open product structure management window");
		  page.mainMenu.hoverMenu("产品管理");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("产品结构模板");
		  Thread.sleep(10000);
		  
		  
		  //select version to lookup VPPD
		  logger.info("query the latest VPPD");
		  page.option.openComboxFromQuerySection("版本");
		  Thread.sleep(5000);
		  page.option.selectLastOption();
		  Thread.sleep(5000);
		  page.button.clickButton("查询");
		  Thread.sleep(5000);
		  
		  String tableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 0);
		  
		  String existingStatus=page.text.getValueFromTextBox(TableStyle.GRIDVIEW, tableId, 1, 11);
		  
		  String VCOSkipFlag="false";
		  String existingVCO="";
		  
		  if(existingStatus.contains("当前")) {
			//if there is no draft version, need to click prompt button then start editing
			  logger.info("As current version has published already, upgrade a new draft version");
			  page.button.clickButton("升版");
			  Thread.sleep(20000);
			  page.option.openComboxFromQuerySection("版本");
			  Thread.sleep(5000);
			  page.option.selectLastOption();
			  Thread.sleep(5000);
			  page.button.clickButton("查询");
			  Thread.sleep(5000);  
		  }
		  
		  existingVCO=page.text.getValueFromTextBox(TableStyle.GRIDVIEW, tableId, 1, 12);
		  Thread.sleep(1000);
		  
		  if(!existingVCO.trim().isEmpty()) {
			  logger.info("as there is alredy change order associated, need to release from the existing change order");
			  logger.info("open the existing change order");
			  page.link.clickLinkByText(existingVCO);
			  Thread.sleep(5000);
			  
			  String lableId=page.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX, "VCO状态");
			  String changeStatus=page.text.getValueFromTextBox(lableId, "changeStatus", 0);
			  
			  if(changeStatus.contains("审批中")) {
				  //record a flag to identify the VPPD has been associated in change order and the order is in approval process
				  logger.info("there is already a VCO in process");
				  VCOSkipFlag="true";
			  }else if(changeStatus.contains("草稿")){
				//remove the change content
				  logger.info("remove the change content");
				  page.tab.clickTab("变更内容");
				  Thread.sleep(1000);
				  page.button.clickButton("取消关联");
				  Thread.sleep(5000);
			  }
			  page.button.clickCloseButton(1);
			  Thread.sleep(1000); 
		  }
		  
		  if(VCOSkipFlag.contains("false")) {
			  logger.info("start editting VPPD");
			  page.button.clickButton("进入编辑");
			  Thread.sleep(1000);
			  
			  page.option.clickCheckBox(0,ListViewStyle.GRIDVIEW);
			  
			  //add a node
			  logger.info("add a new node");
			  page.button.clickButton("新增");
			  Thread.sleep(1000);
			  page.button.clickChildButton("新增子节点");
			  Thread.sleep(1000);
			  
			  //select the newly added node to edit its attributes
			  page.option.clickCheckBox(1, ListViewStyle.GRIDVIEW);
			  Thread.sleep(1000);
			  
			  //add the code
			  logger.info("input the code");
			  int columnId=Integer.parseInt(page.otherElements.getColumnId(ColumnStyle.GRIDCOLUMN,"编码"));
			  page.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId), 1);
			  Thread.sleep(1000);
			  page.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
			  
			  //add the simple code
			  logger.info("input the brief code");
			  page.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId+1), 1);
			  Thread.sleep(1000);
			  page.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
			  
			  //add the function position code
			  logger.info("input the functional position code");
			  page.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId+2), 1);
			  Thread.sleep(1000);
			  page.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
			  
			  //add the Chinese description
			  logger.info("input the Chinese description");
			  page.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId-1), 1);
			  Thread.sleep(1000);
			  page.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
			  
			  //add the English description
			  logger.info("input the English description");
			  page.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId+3), 1);
			  Thread.sleep(1000);
			  page.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
			  
			  //save the VPPD
			  logger.info("save the VPPD");
			  page.button.clickButton("保存");
			  Thread.sleep(5000);
		  }
			  //save the VCO number and VCO skip flag to see if the current version has already associated in change order
			  //if yes, let the VCO publish VPPD case to skip creating a new VCO
			  logger.info("save the existing change order number and VCO skip flag in test data file");
			  Map<String, String> testData=new HashMap<String, String>();
			  testData.put("ChangeOrder",existingVCO);
			  testData.put("VCOSkipFlag", VCOSkipFlag);
			  super.bcf.writeJasonFile(EnvJsonFile.TESTDATA, testData);
		  
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
