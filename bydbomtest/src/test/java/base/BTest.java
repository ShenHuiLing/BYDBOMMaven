package base;



import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.common.io.Files;

import common.BCommonFunction;
import common.ChangeOrderType;
import common.CheckBoxStyle;
import common.ColumnStyle;
import common.DropDownListStyle;
import common.EnvJsonFile;
import common.LabelStyle;
import common.ListViewStyle;
import common.TableStyle;
import common.TextStyle;
import page.ApprovalPage;
import page.LoginPage;




public class BTest {
	protected WebDriver driver;
	protected BCommonFunction bcf;
	protected static Logger logger=LogManager.getLogger();
	
	public BTest() {
		this.bcf=new BCommonFunction();
	}
	
	public void StartBOM(EnvJsonFile enj, String Env) throws Exception {
		bcf.readJasonFile(enj);
		String envURL=bcf.getProperty(Env);
		String driverPath=bcf.getProperty("chormedriver");
		
		
		try {
			  ChromeDriverService service = new ChromeDriverService.Builder()
					  .usingDriverExecutable(new File(driverPath))
					  .usingAnyFreePort()
					  .build();
			  service.start();
			  logger.info("Start BOM");
			  driver=new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
			  
			  driver.get(envURL);
			  driver.manage().window().maximize();
			  driver.manage().window().fullscreen();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public String LoginBOM() throws Exception {
		 try {
			  	  logger.info("login BOM");
				  LoginPage lPage=new LoginPage(this.driver);
				  bcf.readJasonFile(EnvJsonFile.TESTFILE);
				  String username=bcf.getProperty("username");
				  String pwd=bcf.getProperty("pwd");
				  lPage.InputUserName(username);
				  lPage.InputPwd(pwd);
				  Thread.sleep(1000);
				  lPage.clickLoginButton();
				  return username;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public void LoginBOMAsApprover() throws Exception {
		 try {
				  LoginPage lPage=new LoginPage(this.driver);
				  bcf.readJasonFile(EnvJsonFile.TESTFILE);
				  String username=bcf.getProperty("approver");
				  String pwd=bcf.getProperty("approverpwd");
				  lPage.InputUserName(username);
				  lPage.InputPwd(pwd);
				  Thread.sleep(1000);
				  lPage.clickLoginButton();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * select approver process
	 * @param: approver
	 * @param: tableId, this is the table Id for approver list
	 */
	public void selectApprover (String approver, String tableId) throws Exception  {
		try {
			  ApprovalPage approvalPage=new ApprovalPage(this.driver);
			  //judge if there are candidate approvers to be selected, if column count greater than 2 then continue to select approver
			  int approverColCount=approvalPage.otherElements.getTableColCount(TableStyle.GRIDVIEW, tableId);
			  if(approverColCount>2) {
				  int approverCount=approvalPage.otherElements.getTableRowCount(TableStyle.GRIDVIEW, tableId);
				  logger.info("total approval nodes: " + approverCount);
				  
				  for (int i=0;i<approverCount;i++) {
					  
					  String taskName=approvalPage.text.getValueFromTextBox(TableStyle.GRIDVIEW, tableId, i+1, 2);
					  logger.info("task name: " + taskName);
					  //check if the task is for OTS verification or trial assemble
					  //if yes, uncheck the approval node
					  if(taskName.equals("OTS�Ƿ�ͨ��") || taskName.equals("��װ�Ƿ�ͨ��")) {
						  approvalPage.option.unCheckCheckBoxByTable(tableId, i+1, 1);
						  Thread.sleep(1000);
					  }
					  
					  //check if the task is cost engine department confirmation
					  //if yes, check all the approval node
					  if(taskName.equals("�ɱ����̲�ȷ��") || taskName.equals("��Ʒ�ܼ���׼2") 
							  || taskName.equals("Ժ������") || taskName.equals("COO��׼")) {
						  approvalPage.option.unCheckCheckBoxByTable(tableId, i+1, 1);
						  Thread.sleep(1000);
					  }
					  
					  //check if there is already approver as expected
					  String tempApprover=approvalPage.text.getValueFromTextBox(TableStyle.GRIDVIEW, tableId, i+1, 3);
					  //if the existing approver is not matching with the approver data, need to re-choose approver
					  //if the node is an optional approver, will skip
					  if(!tempApprover.equals(approver) && !tempApprover.contains("������˽ڵ��Ƿ��������")) {
						  approvalPage.text.openTextBox(tableId, i+1, 3);
						  Thread.sleep(1000);
						  
						  //remove all existing approver
						  approvalPage.option.checkAllSelectedApprover();
						  approvalPage.button.clickButton("<<");
						  Thread.sleep(1000);
						  
						  //input login name in query box
						  approvalPage.text.inputText("userLoginName",approver);
						  Thread.sleep(1000);
						  //switch the query range to "all"
						  String labelId=approvalPage.otherElements.getLabelId(LabelStyle.COMBO, "������Ա��Χ");
						  approvalPage.option.expandDropdownList(DropDownListStyle.COMBO,labelId);
						  approvalPage.option.selectOption("ȫ��");
						  //click query button
						  approvalPage.button.clickQueryApproverButton();
						  Thread.sleep(1000);
						 
						  approvalPage.option.clickCheckBoxOption(CheckBoxStyle.ROWCHECKER,approver);
						  Thread.sleep(1000);
						  
						  approvalPage.button.clickButton(">>");
						  Thread.sleep(1000);
						  approvalPage.button.clickButton("ȷ��");
						  Thread.sleep(1000);
					  }
				  }
				
			  }
			  
			
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	/**
	 * start approveal process, including switch to approval tab, select approver and start approval
	 */
	public void startApprovalProcess(ChangeOrderType cot) throws Exception {
		try {
				ApprovalPage approvalPage=new ApprovalPage(this.driver);
				//click the approval tab
				logger.info("switch to approval tab");
				approvalPage.tab.clickTab("��������");
				Thread.sleep(2000);
				
				//get the approver from the test file and pass this parameter to selectApprover function
				bcf.readJasonFile(EnvJsonFile.TESTFILE);
				String approver=bcf.getProperty("approver");
				
				//get the approval table ID and pass this parameter to selectApprover function
				int index=2;
				if(cot==ChangeOrderType.BOM)
					index=2;
				else if(cot==ChangeOrderType.VPPD)
					index=2;
				else if(cot==ChangeOrderType.PART)
					index=3;
				else if(cot==ChangeOrderType.PLANNINGCONFIGURATION)
					index=2;
				
				String tableId=approvalPage.otherElements.getTableId(TableStyle.GRIDVIEW, index);
				
				this.selectApprover(approver, tableId);
				
				logger.info("start approval process");
				approvalPage.button.clickButton("������������");
				Thread.sleep(5000);
				
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	/**
	 * approve process, including switch to approval tab, check the approval check box
	 * re-select all following approvers and click approval confirmation button
	 */
	public void approveProcess(String taskName) throws Exception {
		try {
				ApprovalPage approvalPage=new ApprovalPage(this.driver);
				
				//if taskName is the cost estimation, need to fill the cost estimation value
				if(taskName.equalsIgnoreCase("�ɱ�����")) {
					logger.info("as current approval node is cost estimation, it needs to fill the estimated cost information");
					approvalPage.tab.clickTab("�㲿�����");
					Thread.sleep(5000);
					logger.info("click view link");
					approvalPage.link.clickLinkByText("�鿴");
					Thread.sleep(5000);
					
					String labelId;
					logger.info("fill the estimated unit price");
					labelId=approvalPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"���������",0);
					approvalPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
					Thread.sleep(1000);
					approvalPage.text.inputText(TextStyle.TEXTFIELD,"entity.targetChangePrice",this.bcf.getTimeStamp().substring(4));
					Thread.sleep(1000);
					
					logger.info("fill the estimated total price");
					labelId=approvalPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"�������",0);
					approvalPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
					Thread.sleep(1000);
					approvalPage.text.inputText(TextStyle.TEXTFIELD,"entity.targetChangeCost",this.bcf.getTimeStamp().substring(4));
					Thread.sleep(1000);
					
					logger.info("fill the estimated price change comment");
					labelId=approvalPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"����ɱ��仯˵��",0);
					approvalPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
					Thread.sleep(1000);
					approvalPage.text.inputText(TextStyle.TEXTFIELD,"entity.targetRemark",this.bcf.getTimeStamp().substring(4));
					Thread.sleep(1000);
					
					logger.info("save the estimated cost information");
					approvalPage.button.clickButton("����");
					Thread.sleep(20000);
				}
				else if(taskName.equalsIgnoreCase("�ɹ��ɱ�ά��")) {
					logger.info("as current approval node is cost for purchase, it needs to fill the negotiation price information");
					approvalPage.tab.clickTab("�㲿�����");
					Thread.sleep(5000);
					logger.info("click view link");
					approvalPage.link.clickLinkByText("�鿴");
					Thread.sleep(5000);
					
					String labelId;
					logger.info("fill the supplier name");
					labelId=approvalPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"��Ӧ������1",0);
					approvalPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
					Thread.sleep(1000);
					approvalPage.text.inputText(TextStyle.TEXTFIELD,"entity.supplyName",this.bcf.getTimeStamp().substring(4));
					Thread.sleep(1000);
					
					logger.info("fill the negotiation unit price");
					labelId=approvalPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"��ۡ�����1",0);
					approvalPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
					Thread.sleep(1000);
					approvalPage.text.inputText(TextStyle.TEXTFIELD,"entity.actualChangePrice",this.bcf.getTimeStamp().substring(4));
					Thread.sleep(1000);
					
					logger.info("fill the negotiation total price");
					labelId=approvalPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"��۷���1",0);
					approvalPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
					Thread.sleep(1000);
					approvalPage.text.inputText(TextStyle.TEXTFIELD,"entity.actualChangeCost",this.bcf.getTimeStamp().substring(4));
					Thread.sleep(1000);
					
					logger.info("fill the negotiation change price comment");
					labelId=approvalPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"��۳ɱ��仯˵��1",0);
					approvalPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
					Thread.sleep(1000);
					approvalPage.text.inputText(TextStyle.TEXTFIELD,"entity.actualRemark",this.bcf.getTimeStamp().substring(4));
					Thread.sleep(1000);
					
					logger.info("fill the cost balance method");
					labelId=approvalPage.otherElements.getLabelId(LabelStyle.CHECKBOXFIELD,"����֧��",0);
					approvalPage.option.clickCheckBoxById(labelId);
					Thread.sleep(1000);
					
					logger.info("fill the unconsumed material review");
					labelId=approvalPage.otherElements.getLabelId(LabelStyle.TEXTFIELD,"������������",0);
					approvalPage.text.openTextBox(TextStyle.IDININPUT, labelId, 1);
					Thread.sleep(1000);
					approvalPage.text.inputText(TextStyle.TEXTFIELD,"entity.sluggishMaterial",this.bcf.getTimeStamp().substring(4));
					Thread.sleep(1000);
					
					logger.info("save the negotiation price information");
					approvalPage.button.clickButton("����");
					Thread.sleep(20000);
					
				}else if(taskName.equalsIgnoreCase("Ʒ�ʻ�ǩ")) {
					logger.info("as quality approver, it needs to asses the serious index");
					  //select the change type
					  logger.info("select the serious index");
					  String labelId=approvalPage.otherElements.getLabelId(LabelStyle.GANTCODETYPECOMBOBOX,"�Ƿ�����ȵ��±��",0);
					  approvalPage.option.expandDropdownList(DropDownListStyle.GANTCODETYPECOMBOBOX,labelId);
					  Thread.sleep(2000);
					  approvalPage.option.selectOption("��");
					  Thread.sleep(1000);
					approvalPage.button.clickButton("����");
					Thread.sleep(10000);
					
				}
				
				
				//click the approval tab
				approvalPage.tab.clickTab("��������");
				Thread.sleep(10000);
				
				//check the approval check box
				approvalPage.option.selectApprovalOption();
				//get the approver from the test file and pass this parameter to selectApprover function
				bcf.readJasonFile(EnvJsonFile.TESTFILE);
				String approver=bcf.getProperty("approver");
			
				//get the approval table ID and pass this parameter to selectApprover function
				String tableId=approvalPage.otherElements.getApproverTableId();
				selectApprover(approver, tableId);
				
				//click the approve button
				approvalPage.button.clickButton("ִ�в���");
				Thread.sleep(1000);
				approvalPage.button.clickButton("��");
				Thread.sleep(10000);
				
		}catch (Exception e){
			throw e;
		}
		  
	}
	
	/**
	 * select the part part from part selector, this method covers anywhere need to select part from selector,
	 * like EBOM, PS part change order, etc.
	 * @param partNum: the part number need to find out
	 * @param resultTableIndex: the result table, need to indicate which table should be used
	 * @param isNeedClose: in some test case, the selector needs to be closed manually, some don't. True--need to close, false--don't
	 * @throws Exception
	 */
	public void selectPartFromPartSelector(String partNum, int resultTableIndex, boolean isNeedClose) throws Exception 
	{
		try {
				BPage page=new BPage(this.driver);
				//from the part selector, select the part
			  logger.info("select the part from the part selector");
			  logger.info("input the part num in the query criteria field");
			  String Id=page.otherElements.getLabelId(LabelStyle.TEXTFIELD, "�����");
			  page.text.openTextBox(TextStyle.IDININPUT, Id, 1);
			  page.text.inputText(TextStyle.TEXTFIELD,partNum);
			  Thread.sleep(1000);
			  logger.info("click query button");
			  page.button.clickButton("��ѯ",1);
			  Thread.sleep(1000);
			  logger.info("tik the check box of the part from the result table");
			  String PopUpTableId=page.otherElements.getTableId(TableStyle.GRIDVIEW,resultTableIndex);
			  page.option.clickCheckBox(PopUpTableId, 1,1);
			  Thread.sleep(1000);
			  logger.info("select the result");
			  page.button.clickButton("ѡ��");
			  Thread.sleep(1000);
			
			  if(isNeedClose)
			  {
				  page.button.clickCloseButton(1);
				  Thread.sleep(1000);
			  }
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void TakeSnap() throws IOException
	{
		String filePath=this.bcf.getProjectPath();
		filePath=filePath + "\\test-output\\" + bcf.getCurrentTime() + ".jpg"; 
		File scrFile=((RemoteWebDriver)driver).getScreenshotAs(OutputType.FILE);
		Files.copy(scrFile, new File(filePath));
		logger.info("screenshot was caught here: " + filePath);
	}
	
	public void close()
	{
		this.driver.quit();
	}
	
	
}

