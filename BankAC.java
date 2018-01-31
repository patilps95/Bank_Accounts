
import java.io.*;
import java.util.*;

class Tran {
	String status;
	int amt;
	Tran(){
		status=new String();
		amt=0;
	}
	void getTran(String stat,int amt2){
		status = stat;
		amt = amt2;
	}
	void showTran(){
		System.out.print("\t\t|"+status+"|\t "+amt+" /-|\n");
	}
}

class Account extends Tran{
	String name, type;     
	int bal;
	protected int acno;
	LinkedList <Tran> tran;

	Account(){
		name = new String();
		type = new String();
		bal = 500;
		tran=new LinkedList<Tran>();
	}

	void setData(int no){
		Console con = System.console();
		Scanner a = new Scanner(System.in);
		System.out.print("\n\tEnter Name: ");
		name = a.nextLine();
		type = getType();
		acno = no;
		display();
	}

	String getType(){
		Scanner a = new Scanner(System.in);
		int opt=0;
		System.out.println("\n\t\t***Rules:\n\t\tA. Min Bal For Saving Account is Rs 0 /-\n\t\tB. Min Bal For Current Account is Rs 500 /-\n\t\tC. Current Account Holders get Checkbook\n\t\tD. But Checkbook charges applied Rs 10/- per month");
		System.out.print("\n\tChoose Type:\n\t1.Saving\n\t2.Current \n\tOpt: ");
		opt = a.nextInt();
		if(opt==1)
			return "Saving";
		else
			return "Current";
	}

	void withDraw(int amt1){
		Scanner obj = new Scanner(System.in);
		int j=0;
		if(type=="Current"){
			if(bal-amt1<500 && bal-amt1>=0)
			{
				System.out.println("\t===unsufficient account balance===\n\t===are you want to proceed===\n\t===charges may applied===\n\t1.Yes\t2.No");
				try{
					j = obj.nextInt();
				}
				catch(Exception e){
					System.out.println("\t===Transaction Failed\n\t===You Entered Something wrong");
					return;
				}
				if(j==2)
					return;
				else if(j==1)
					deposite("Low Balance",-50,true);
				else
					return;
			}
			else if(amt1==0){
				System.out.println("\t\t===Transaction Failed\n\t\t===You Entered Something wrong");
				return;
			}
			else if(bal-amt1<0){
				System.out.println("\t\t===Sorry your A/C balance becoming less than 0===");
				return;
			}
		}
		else if(type=="Saving"){
			if(bal-amt1<0)
			{
				System.out.println("\t\t===Transaction Failed\n\t\t===unsufficient account balance");
				return;
			}
			else if(amt1==0){
				System.out.println("\t\t===Transaction Failed\n\t\t===You Entered Something wrong");
				return;
			}
		}
			Tran a = new Tran();
			bal = bal - amt1;
			if(amt1>0)
			a.getTran("Withdrawal ",amt1);
			else
				a.getTran("Interest   ",-amt1);
			tran.add(a);
	}

	void deposite(String s,int amt1,boolean state){
		if(amt1==0){
			System.out.println("\t\t===Transaction Failed\n\t\t===You Entered Something wrong");
			return;
		}
		bal = bal + amt1;
		Tran a = new Tran();
		if(amt1>0 && state==true)
			a.getTran("Deposite   ",amt1);
		else if(s!=null)
			a.getTran("Low balance",-amt1);
		else
			a.getTran("Charges    ",-amt1);
		tran.add(a);
	}

	int showBalance(){
		return bal;
	}

	int showAcNo(){
		return acno;
	}
	
	String showType(){
		return type;
	}
	
	public String toString(){
		String s = "\t\t===================\n";
		s = s + "\t\tAccount Data\n\t\tName: "+name+"\n\t\tAC no: "+acno+"\n\t\tbalance: "+bal+"\n\t\tType: "+type;
		s = s + "\n\t\t===================\n";
		return s;
	}

	void display(){
		System.out.println(toString());
	}

	void showTran1(){
		System.out.println("\t\t@Transactions:\n");
		Tran [] a = new Tran[tran.size()];
		tran.toArray(a);
		for(int i=0;i<tran.size();i++)
			a[i].showTran();
	}

	void menu(){
		Scanner a = new Scanner(System.in);
		int opt,amt=0;
		while(true){
			System.out.print("\n\tOptions:\n\t1.Withdraw\n\t2.Deposite\n\t3.Balance\n\t4.Display Transactions\n\t5.logout\n\tOpt: ");
			opt = a.nextInt();
			if (opt == 5) {
				break;
			}
			switch (opt) {
				case 1:
					System.out.print("\n\tEnter Amount: ");
					try{
						amt = Integer.parseInt(a.next());
					}
					catch (Exception e) {
						amt = 0;
						withDraw(amt);
						break;
					}
					withDraw(amt);
					break;

				case 2:
					System.out.print("\n\tEnter Amount: ");
					try{
						amt = Integer.parseInt(a.next());
					}
					catch (Exception e) {
						amt = 0;
						deposite(null,amt,true);
						break;
					}
					deposite(null,amt,true);
					break;

				case 3:
					System.out.println("\t\t@Your Account Balance: "+showBalance());
					break;

				case 4:
					display();
					showTran1();
					break;
			}
		}
	}
}

class BankAC extends Account {
	LinkedList <Account> acs;

	BankAC(){
		acs = new LinkedList<Account>();
	}

	Account search(int no){
		Account [] tmp = new Account[acs.size()];
		acs.toArray(tmp);
		int i=0;
		for(i=0;i<acs.size();i++){
			if(tmp[i].acno==no)
				break;
		}
		if(i!=acs.size())
			return tmp[i];
		return null;
	}

	void display1(){
		Account [] tmp = new Account[acs.size()];
		acs.toArray(tmp);
		int i=0;
		for(i=0;i<acs.size();i++){
			System.out.println("\n\t"+i+". ");
			tmp[i].display();
		}
	}
	
	void intCharge(int times){
		Account [] tmp = new Account[acs.size()];
		acs.toArray(tmp);
		int i=0,amt3=0;
		String s = new String();
		for(i=0;i<acs.size();i++){
			amt3=tmp[i].showBalance();
			s = tmp[i].showType();
			if(s=="Saving")
				tmp[i].withDraw(-amt3*1*times);
			else
				tmp[i].deposite(null,-10*times,false);
		}
	}
	
	void mainMenu(){
		Scanner a = new Scanner(System.in);
		int opt=0;
		while(true){
			System.out.print("\n\tOptions:\n\t1.New Account\n\t2.Login\n\t3.Admin Login\n\t4.exit\n\tOpt: ");
			opt = a.nextInt();
			if (opt == 4) {
				break;
			}
			switch (opt) {
				case 1:
					Account ad = new Account();
					int tmp1;
					while(true){
						tmp1 = (int)(Math.random() * 100);
						if(search(tmp1)==null){
							break;
						}
					}
					ad.setData(tmp1);
					System.out.println("\t\t==Congratulations!...New Account Successfully Opened.==");
					System.out.println("\t\t==Remember Your Account No: "+tmp1+" ==");
					ad.menu();
					acs.add(ad);
					break;
					
				case 2:
					Account ad1 = new Account();
					System.out.print("\tEnter A/C No: ");
					ad1 = search(a.nextInt());
					if(ad1==null){
						System.out.println("\t\t====Sorry, Account not found====");
						break;
					}
					System.out.println("\t\t=========Welcome, "+ad1.name+"==========");
					if(ad1.bal<0)
						System.out.println("\t\t=========Your Balance is less than 0=======");
					ad1.menu();
					break;
					
				case 3:
					while(true){
						System.out.print("\n\tOptions:\n\t1.Display all accounts\n\t2.Time Jump\n\t3.logout\n\tOpt: ");
						opt = a.nextInt();
						if(opt==3)
							break;

						switch(opt){
							case 1:
								display1();
								break;

							case 2:
								System.out.print("\tEnter time in month passed\n\t: ");
								intCharge(a.nextInt());
								break;
						}
					}
			}
		}
	}
	public static void main(String[] args) {
		BankAC a= new BankAC();
		a.mainMenu();
	}
}
