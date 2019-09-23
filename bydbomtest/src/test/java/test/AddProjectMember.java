package test;

import org.testng.annotations.Test;

import base.BTest;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.TableStyle;
import common.TextStyle;
import page.Page;

import org.testng.annotations.BeforeTest;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class AddProjectMember extends BTest{
  @Test
  public void addProjectMember() throws IOException {
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(10000);
		  
		  //login BOM
		  String username=super.LoginBOM();
		  Thread.sleep(10000);
		  
		  Page page=new Page(super.driver);
		  
		  //open project member window
		  logger.info("open project member");
		  page.mainMenu.hoverMenu("基础数据管理");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("项目成员管理");
		  Thread.sleep(3000);
		  
		  //select vehicle mode code
		  logger.info("select vehicle mode code and query the project member records");
		  String labelId=page.otherElements.getLabelId(LabelStyle.COMBO,"车型型号");
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  String prjectCode=super.bcf.getProperty("ProjectCode");
		  page.option.expandDropdownList(DropDownListStyle.COMBO,labelId);
		  Thread.sleep(5000);
		  page.option.selectOption(prjectCode);
		  Thread.sleep(5000);
		  page.button.clickButton("查询");
		  Thread.sleep(5000);
		  
		  //add a member
		  logger.info("start editting");
		  page.button.clickButton("进入编辑");
		  Thread.sleep(1000);
		  
		  this.addMemberByRole(page, "产品工程师", username);
		  this.addMemberByRole(page, "配置工程师", username);
		  
	  }catch(Exception e) {
		  super.TakeSnap();
		  logger.error(e.getMessage());
		  e.printStackTrace();
		  Assert.assertEquals(false, true);
	  }
	  
  }
  
  private void addMemberByRole(Page page, String role, String username) throws Exception {
	  
	  try {
		  logger.info("add a new member");
		  page.button.clickButton("添加");
		  Thread.sleep(1000);
		  
		  String projectMemberTableId;
		  
		  //set the role as "product engineer" for the new member
		  logger.info("select the role");
		  projectMemberTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW, 0);
		  //select the base belong to
		  page.text.openTextBox(projectMemberTableId, 1, 4);
		  Thread.sleep(1000);
		  page.option.expandDropdownList();
		  Thread.sleep(1000);
		  page.text.inputText("roleCode",1,role);
		  Thread.sleep(1000);
		  page.option.selectOption(role);
		  Thread.sleep(2000);
		  
		  //select the user
		  String PopUpTableId;
		  String MagnifyingGlassTableId;
		  
		  logger.info("assign the user to the project role");
		  page.text.openTextBox(projectMemberTableId, 1, 7);
		  Thread.sleep(1000);
		  MagnifyingGlassTableId=page.otherElements.getTableId(TableStyle.USERTRIGGERFIELD, 1);
		  page.button.clickMagnifyingGlass(TableStyle.USERTRIGGERFIELD, MagnifyingGlassTableId,1,2);
		  Thread.sleep(1000);
		  String Id;
		  Id=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "人员工号");
		  logger.info("input userId and query the user");
		  page.text.openTextBox(TextStyle.IDININPUT, Id, 1);
		  Thread.sleep(1000);
		  page.text.inputText(TextStyle.TEXTFIELD,username);
		  Thread.sleep(1000);
		  page.button.clickButton("查询",1);
		  Thread.sleep(10000);
		  logger.info("select the user which is found");
		  PopUpTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,1);
		  page.option.clickCheckBox(PopUpTableId, 1,1);
		  Thread.sleep(1000);
		  page.button.clickButton("确定");
		  Thread.sleep(1000);
		  
		  //save the project member
		  logger.info("save the project member");
		  page.button.clickButton("保存");
		  Thread.sleep(5000);
	  }catch(Exception e) {
		  e.printStackTrace();
		  throw e;
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
