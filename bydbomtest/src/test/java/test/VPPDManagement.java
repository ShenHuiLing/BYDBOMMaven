package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ColumnStyle;
import common.EnvJsonFile;
import common.ListViewStyle;
import common.TextStyle;
import page.MainPage;
import page.VPPDPage;

import org.testng.annotations.BeforeTest;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

/* Test: VPPDά��
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
		  
		  //open product structure window
		  logger.info("open product structure management window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("��Ʒ����");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("��Ʒ�ṹģ��");
		  Thread.sleep(10000);
		  
		  //select version to lookup VPPD
		  logger.info("query the latest VPPD");
		  VPPDPage vppdPage=new VPPDPage(super.driver);
		  vppdPage.option.openComboxFromQuerySection("�汾");
		  Thread.sleep(1000);
		  vppdPage.option.selectLastOption();
		  Thread.sleep(1000);
		  vppdPage.button.clickButton("��ѯ");
		  Thread.sleep(5000);
		  
		  try {
			  logger.info("start editting VPPD");
		  vppdPage.button.clickButton("����༭");
		  Thread.sleep(1000);
		  }
		  catch(Exception e) {
			  //if there is no draft version, need to click prompt button then start editting
			  logger.info("As current version has published already, upgrade a new draft version");
			  vppdPage.button.clickButton("����");
			  Thread.sleep(5000);
			  vppdPage.option.openComboxFromQuerySection("�汾");
			  Thread.sleep(1000);
			  vppdPage.option.selectLastOption();
			  Thread.sleep(1000);
			  vppdPage.button.clickButton("��ѯ");
			  Thread.sleep(5000);
			  logger.info("start editting VPPD");
			  vppdPage.button.clickButton("����༭");
			  Thread.sleep(1000);
		  }
		  
		  vppdPage.option.clickCheckBox(0,ListViewStyle.GRIDVIEW);
		  
		  //add a node
		  logger.info("add a new node");
		  vppdPage.button.clickButton("����");
		  Thread.sleep(1000);
		  vppdPage.button.clickChildButton("�����ӽڵ�");
		  Thread.sleep(1000);
		  
		  //select the newly added node to edit its attributes
		  vppdPage.option.clickCheckBox(1, ListViewStyle.GRIDVIEW);
		  Thread.sleep(1000);
		  
		  //add the code
		  logger.info("input the code");
		  int columnId=Integer.parseInt(vppdPage.otherElements.getColumnId(ColumnStyle.GRIDCOLUMN,"����"));
		  vppdPage.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId), 1);
		  Thread.sleep(1000);
		  vppdPage.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
		  
		  //add the simple code
		  logger.info("input the brief code");
		  vppdPage.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId+1), 1);
		  Thread.sleep(1000);
		  vppdPage.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
		  
		  //add the function position code
		  logger.info("input the functional position code");
		  vppdPage.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId+2), 1);
		  Thread.sleep(1000);
		  vppdPage.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
		  
		  //add the Chinese description
		  logger.info("input the Chinese description");
		  vppdPage.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId-1), 1);
		  Thread.sleep(1000);
		  vppdPage.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
		  
		  //add the English description
		  logger.info("input the English description");
		  vppdPage.text.openTextBox(TextStyle.IDINTD, String.valueOf(columnId+3), 1);
		  Thread.sleep(1000);
		  vppdPage.text.inputText(TextStyle.TEXTFIELD,super.bcf.getTimeStamp());
		  
		  //save the VPPD
		  logger.info("save the VPPD");
		  vppdPage.button.clickButton("����");
		  Thread.sleep(1000);
		  
		  Assert.assertEquals(vppdPage.otherElements.isEditFlagDisappeared(ListViewStyle.GRIDVIEW), true);
		  
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
