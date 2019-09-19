package test;

import org.testng.annotations.Test;

import base.BTest;
import common.ChangeOrderCode;
import common.ChangeOrderType;
import common.ColumnStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.TableStyle;
import common.TextStyle;
import page.MainPage;
import page.VCOPage;

import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class VCOPublishVPPD extends BTest {
  @Test
  public void SubmitVCO() throws IOException {
	  try
	  {
		  logger.info("read the VCO skip flag to see if there is already existing VCO in process");
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  String VCOSkipFlag=super.bcf.getProperty("VCOSkipFlag");
		  if(VCOSkipFlag.contains("true")) {
			  logger.info("there is already existing VCO in process, so don't need to create a new VCO");
			  String existingVCO=super.bcf.getProperty("ChangeOrder");
			  logger.info("Existing VCO is: " + existingVCO);
		  }else {
			  //start BOM
			  super.StartBOM(EnvJsonFile.BASICFILE, "local");
			  Thread.sleep(15000);
			
			  //login BOM
			  super.LoginBOM();
			  Thread.sleep(20000);
			  
			  //open VCO window
			  logger.info("open VCO management window");
			  MainPage mainPage=new MainPage(super.driver);
			  mainPage.mainMenu.hoverMenu("变更管理");
			  Thread.sleep(2000);
			  mainPage.mainMenu.clickMenu("产品结构模板变更管理");
			  Thread.sleep(10000);
			  
			  //create a new VCO
			  logger.info("create a new VCO");
			  VCOPage vcoPage=new VCOPage(super.driver);
			  vcoPage.button.clickButton("新增");
			  Thread.sleep(2000);
			  String labelId=vcoPage.otherElements.getLabelId(LabelStyle.TEXTFIELD, "VCO编号",1);
			  String changeOrder=vcoPage.text.getValueFromTextBox(labelId, "changeCode", 0);
			  System.out.println(changeOrder);
			  
			  logger.info("save the newly added VCO");
			  vcoPage.button.clickButton("保存");
			  Thread.sleep(10000);
			  
			  //add the change content
			  logger.info("assign the change content");
			  vcoPage.tab.clickTab("变更内容");
			  Thread.sleep(1000);
			  vcoPage.button.clickButton("整版关联");
			  Thread.sleep(5000);
			  
			  //start approval process
			  super.startApprovalProcess(ChangeOrderType.VPPD);
			  
			  logger.info("save the change order number in test data file");
			  Map<String, String> testData=new HashMap<String, String>();
			  testData.put("ChangeOrder",changeOrder);
			  super.bcf.writeJasonFile(EnvJsonFile.TESTDATA, testData);
			  
			  super.close();
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
		  
  }

}
