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
import page.MainPage;
import page.PendingTaskPage;

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
		  Thread.sleep(15000);
		  
		  //login BOM
		  super.LoginBOMAsApprover();
		  Thread.sleep(20000);
		  
		  //open pending task window
		  logger.info("open pending task window");
		  MainPage mainPage=new MainPage(super.driver);
		  mainPage.mainMenu.hoverMenu("个人中心");
		  Thread.sleep(2000);
		  mainPage.mainMenu.clickMenu("待处理任务");
		  Thread.sleep(10000);

		  //click the change order link and keep approving the order till the approver completes his task
		  PendingTaskPage pendingTaskPage=new PendingTaskPage(super.driver);
		  super.bcf.readJasonFile(EnvJsonFile.TESTDATA);
		  String changeOrder=super.bcf.getProperty("ChangeOrder");
		  String taskName;
		  
		  logger.info("start to approve the change order");
		  while(pendingTaskPage.link.isLinkExist(changeOrder)) {
			  taskName=pendingTaskPage.otherElements.getTaskName(changeOrder);
			  logger.info("open the change order for task: " + taskName);
			  pendingTaskPage.link.clickLinkByText(changeOrder);
			  Thread.sleep(10000);
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
