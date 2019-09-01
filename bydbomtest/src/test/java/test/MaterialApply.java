package test;

import org.testng.annotations.Test;

import base.BTest;
import common.EnvJsonFile;
import common.LabelStyle;
import common.ListViewStyle;
import common.TableStyle;
import common.TextStyle;
import page.MainPage;
import page.MaterialPage;

import org.testng.annotations.BeforeTest;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class MaterialApply extends BTest{
  @Test
  public void ApplyMaterialNum() throws IOException {
	  try {
		  
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(10000);
		  
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(10000);
		  
		  //open material application window
		  logger.info("open part material application window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("物料管理");
		  Thread.sleep(1000);
		  mainPage.mainMenu.hoverMenu("零件物料管理");
		  Thread.sleep(1000);
		  mainPage.mainMenu.clickMenu("零件物料申请");
		  Thread.sleep(5000);
		  
		  MaterialPage materialPage=new MaterialPage(super.driver);
		  //create a new material application form
		  logger.info("start editting");
		  materialPage.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  logger.info("add a new part");
		  materialPage.button.clickButton("添加零件");
		  Thread.sleep(1000);
		  
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  
		  String materialApplicationTableId;
		  String magnifyingGlassTableId;
		  String PopUpTableId;
		  materialApplicationTableId=materialPage.otherElements.getTableId(TableStyle.GRIDVIEW, 0);
		  
		  //select part
		  logger.info("from the test data, get the part for using and query the part in the part selector");
		  String Id;
		  Id=materialPage.otherElements.getLabelId(LabelStyle.TEXTFIELD, "零件号");
		  materialPage.text.openTextBox(TextStyle.IDININPUT, Id, 1);
		  String partNum=super.bcf.getProperty("PartNum");
		  materialPage.text.inputText(TextStyle.TEXTFIELD, partNum);
		  Thread.sleep(1000);
		  materialPage.button.clickButton("查询",1);
		  Thread.sleep(1000);
		  PopUpTableId=materialPage.otherElements.getTableId(TableStyle.GRIDVIEW,2);
		  materialPage.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  materialPage.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  materialApplicationTableId=materialPage.otherElements.getTableId(TableStyle.GRIDVIEW, 0);
		  
		  //select the base belong to
		  logger.info("select thr plant");
		  materialPage.text.openTextBox(materialApplicationTableId, 1, 7);
		  Thread.sleep(1000);
		  materialPage.option.expandDropdownList();
		  Thread.sleep(1000);
		  materialPage.option.selectOption("长沙");
		  Thread.sleep(2000);
		  
		  materialApplicationTableId=materialPage.otherElements.getTableId(TableStyle.GRIDVIEW, 1);
		  
		  //select material group
		  logger.info("select material group");
		  materialPage.text.openTextBox(materialApplicationTableId, 1, 1);
		  Thread.sleep(2000);
		  materialPage.option.expandDropdownList();
		  Thread.sleep(2000);
		  materialPage.option.selectOption("(汽)标准件");
		  Thread.sleep(2000);
		  
		  //select manufacture code
		  logger.info("select manufacture code");
		  materialPage.text.openTextBox(materialApplicationTableId, 1, 2);
		  Thread.sleep(2000);
		  materialPage.option.expandDropdownList();
		  Thread.sleep(2000);
		  materialPage.option.selectOption("内部供应商");
		  Thread.sleep(2000);
		  
		  //select tax code
		  logger.info("select tax code");
		  materialPage.text.openTextBox(materialApplicationTableId, 1, 3);
		  Thread.sleep(1000);
		  magnifyingGlassTableId=materialPage.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 1);
		  materialPage.button.clickMagnifyingGlass(TableStyle.GANGTRIGGERFIELD, magnifyingGlassTableId, 1, 2);
		  Thread.sleep(2000);
		  Id=materialPage.otherElements.getLabelId(LabelStyle.TEXTFIELD, "税收编码");
		  materialPage.text.openTextBox(TextStyle.IDININPUT, Id, 1);
		  Thread.sleep(1000);
		  materialPage.text.inputText(TextStyle.TEXTFIELD, "122333");
		  Thread.sleep(1000);
		  materialPage.button.clickButton("查询",1);
		  Thread.sleep(1000);
		  PopUpTableId=materialPage.otherElements.getTableId(TableStyle.GRIDVIEW,2);
		  materialPage.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  materialPage.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  //save the material application order
		  logger.info("save the part material application order");
		  materialPage.button.clickButton("保存");
		  Thread.sleep(5000);
		  Assert.assertEquals(materialPage.otherElements.isEditFlagDisappeared(ListViewStyle.GRIDVIEW), true);
		  
		  //update the material application record in DB and assign a material number to the part
		  logger.info("as there is no PNAS interface in test environment, directly update the material code in DB");
		  super.bcf.connectDB(EnvJsonFile.BASICFILE, "local");
		  String sql="update CUST.CUST_MATERIAL_INFO\n" + 
		  		"set \n" + 
		  		"CF_STATUS='APPLY_COMPLETE',\n" + 
		  		"CF_NO='P'||(select to_char(sysdate,'yyyymmddhh24miss') from dual),\n" + 
		  		"MAT_NO=(select to_char(sysdate,'yyyymmddhh24miss') from dual),\n" + 
		  		"mat_desc='mat_desc' || (select to_char(sysdate,'yyyymmddhh24miss') from dual),\n" + 
		  		"mat_endesc='mat_endesc' || (select to_char(sysdate,'yyyymmddhh24miss') from dual),\n" + 
		  		"ACTIVE_STATUS='CURRENT'\n" + 
		  		"where material_id=\n" + 
		  		"(\n" + 
		  		"select cmi.material_id\n" + 
		  		"from CUST.CUST_MATERIAL_INFO cmi, mstdata.md_material mm\n" + 
		  		"where cmi.material_id=mm.md_material_id\n" + 
		  		"and mm.material_num='" + partNum + "'\n" + 
		  		")";
		  super.bcf.updateData(sql);
		  super.bcf.closeDB();
		  
		  
		  
			  	  
	} catch (Exception e) {
		// TODO Auto-generated catch block
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
