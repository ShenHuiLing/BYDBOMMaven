package test;

import org.testng.annotations.Test;

import base.BTest;
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

public class MaterialApply extends BTest{
  @Test
  public void ApplyMaterialNum() throws IOException {
	  try {
		  
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(20000);
		  
		  //login BOM
		  super.LoginBOM();
		  Thread.sleep(20000);
		  
		  Page page=new Page(super.driver);
		  
		  //open material application window
		  logger.info("open part material application window");
		  page.mainMenu.hoverMenu("物料管理");
		  Thread.sleep(1000);
		  page.mainMenu.hoverMenu("零件物料管理");
		  Thread.sleep(1000);
		  page.mainMenu.clickMenu("零件物料申请");
		  Thread.sleep(5000);
		  
		  //create a new material application form
		  logger.info("start editting");
		  page.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  logger.info("add a new part");
		  page.button.clickButton("添加零件");
		  Thread.sleep(1000);
		  
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  
		  String materialApplicationTableId;
		  String magnifyingGlassTableId;
		  String PopUpTableId;
		  materialApplicationTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 0);
		  
		  //select part
		  logger.info("from the test data, get the part for using and query the part in the part selector");
		  String Id;
		  Id=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "零件号");
		  page.text.openTextBox(TextStyle.IDININPUT, Id, 1);
		  String partNum=super.bcf.getProperty("PartNum");
		  page.text.inputText(TextStyle.TEXTFIELD, partNum);
		  Thread.sleep(1000);
		  page.button.clickButton("查询",1);
		  Thread.sleep(10000);
		  PopUpTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,2);
		  page.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  page.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  materialApplicationTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 0);
		  
		  //select the base belong to
		  logger.info("select thr plant");
		  page.text.openTextBox(materialApplicationTableId, 1, 7);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.option.selectOption("长沙");
		  Thread.sleep(2000);
		  
		  materialApplicationTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 1);
		  
		  //select material group
		  /*
		  logger.info("select material group");
		  page.text.openTextBox(materialApplicationTableId, 1, 1);
		  Thread.sleep(2000);
		  page.option.expandDropdownList();
		  Thread.sleep(2000);
		  page.option.selectOption("(汽)标准件");
		  Thread.sleep(2000);
		  */
		  
		  //select manufacture code
		  logger.info("select manufacture code");
		  page.text.openTextBox(materialApplicationTableId, 1, 1);
		  Thread.sleep(2000);
		  page.option.expandDropdownList();
		  Thread.sleep(2000);
		  page.option.selectOption("内部供应商");
		  Thread.sleep(2000);
		  
		  //select tax code
		  logger.info("select tax code");
		  page.text.openTextBox(materialApplicationTableId, 1, 2);
		  Thread.sleep(1000);
		  magnifyingGlassTableId=page.otherElements.getTableId(TableStyle.GANGTRIGGERFIELD, 1);
		  page.button.clickMagnifyingGlass(TableStyle.GANGTRIGGERFIELD, magnifyingGlassTableId, 1, 2);
		  Thread.sleep(2000);
		  Id=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "税收编码");
		  page.text.openTextBox(TextStyle.IDININPUT, Id, 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD, "122333");
		  Thread.sleep(1000);
		  page.button.clickButton("查询",1);
		  Thread.sleep(1000);
		  PopUpTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,2);
		  page.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  page.button.clickButton("选择");
		  Thread.sleep(1000);
		  
		  //save the material application order
		  logger.info("save the part material application order");
		  page.button.clickButton("保存");
		  Thread.sleep(5000);
		  
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
		  		"ACTIVE_STATUS='CURRENT',\n" +
		  		"IS_APPLY=1\n" +
		  		"where material_id=\n" + 
		  		"(\n" + 
		  		"select cmi.material_id\n" + 
		  		"from CUST.CUST_MATERIAL_INFO cmi, mstdata.md_material mm\n" + 
		  		"where cmi.material_id=mm.md_material_id\n" + 
		  		"and mm.material_num='" + partNum + "'\n" + 
		  		")";
		  super.bcf.updateData(sql);
		  Thread.sleep(5000);
		  super.bcf.closeDB();
		  Thread.sleep(5000);
		  
		  
			  	  
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
