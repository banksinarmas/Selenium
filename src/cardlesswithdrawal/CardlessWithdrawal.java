package cardlesswithdrawal;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import components.CardlessWithdrawal_component;
import framework.BrowserSetup;

public class CardlessWithdrawal extends BrowserSetup {

	private String fromAccountType,fromAccount,phoneNo,amount,desc;
	
	private CardlessWithdrawal_component cardlessWithdrawal;
	
	public CardlessWithdrawal() throws IOException {
		
		this(
				DEFAULT_PROPERTIES.getProperty("DEF_USERNAME"),
				DEFAULT_PROPERTIES.getProperty("DEF_FROM_ACCOUNT_TYPE"),
				DEFAULT_PROPERTIES.getProperty("DEF_CARDLESS_AMOUNT"),
				DEFAULT_PROPERTIES.getProperty("DEF_CARDLESS_DESC")
				);
		
	}
	
	public CardlessWithdrawal(String fromAccountType,String amount,String desc) throws IOException {
		
		this(DEFAULT_PROPERTIES.getProperty("DEF_AUTOMATION_USERNAME"),fromAccountType,amount,desc);
	}
	
	public CardlessWithdrawal(String username,String fromAccountType,String amount,String desc) throws IOException {	
		BrowserSetup.setUser(username);
		this.fromAccountType=fromAccountType;
		this.fromAccount=user_prop.getProperty(this.fromAccountType);
		this.phoneNo=user_prop.getProperty("TO_PHONE_NO");
		this.amount=amount;
		this.desc=desc;
		
		
	}

	@BeforeClass
	private void loadComponents() {
		
		this.cardlessWithdrawal= new CardlessWithdrawal_component(driver);
	}
	
	@Test
	private void Test01_Cardless_Withdrawal_Menu() {
		
		cardlessWithdrawal.menu();
	}

	@Test(dependsOnMethods="Test01_Cardless_Withdrawal_Menu")
	private void Test02_Select_Account() {
		
		cardlessWithdrawal.selectPayee(fromAccount, phoneNo, amount, desc);
	}
	
	@Test(dependsOnMethods="Test02_Select_Account")
	private void Test03_Confirm() {	
		
		cardlessWithdrawal.confirm(phoneNo);
	}
	
	@Test(dependsOnMethods="Test03_Confirm")
	private void Test04_Cardless_Withdrawal_Result() {
		cardlessWithdrawal.result(fromAccountType,phoneNo);
	}
}
