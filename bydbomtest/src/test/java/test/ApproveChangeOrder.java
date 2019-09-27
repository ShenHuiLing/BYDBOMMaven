package test;

import org.testng.annotations.Test;

import base.BTest;
import common.BCommonFunction;
import common.ColumnStyle;
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

public class ApproveChangeOrder extends BTest {
  @Test()
  public void ApproveCO() throws IOException {
	  try {
		  //start BOM
		  super.StartBOM(EnvJsonFile.BASICFILE, "local");
		  Thread.sleep(20000);
		  
		  //login BOM
		  super.LoginBOMAsApprover();
		  Thread.sleep(25000);
		  
		  Page page=new Page(super.driver);
		  
		  //open pending task window
		  logger.info("open pending task window");
		  page.mainMenu.hoverMenu("个人中心");
		  Thread.sleep(2000);
		  page.mainMenu.clickMenu("待处理任务");
		  Thread.sleep(20000);
		  
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  String changeOrder=super.bcf.getProperty("ChangeOrder");
		  String taskName;
		  
		  //input the change order number and fire a search
		  String labelId=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "表单");
		  page.text.openTextBox(TextStyle.IDININPUT, labelId, 0);
		  Thread.sleep(2000);
		  page.text.inputText("orderName", changeOrder);
		  Thread.sleep(2000);
		  page.button.clickButton("查询");
		  Thread.sleep(5000);
		  
		  //click the change order link and keep approving the order till the approver completes his task
		  logger.info("start to approve the change order");
		  while(page.link.isLinkExist(changeOrder)) {
			  taskName=page.otherElements.getTaskName(changeOrder);
			  logger.info("open the change order for task: " + taskName);
			  page.link.clickLinkByText(changeOrder);
			  Thread.sleep(30000);
			  super.approveProcess(taskName);
			  
		  }
		  
		  
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
