package bank_system;
import java.sql.*;
import java.io.*;
public class myjdbcproj {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Connection con=null;
		Statement stmt=null;
		int row,ch,c1;
		try {
			String url="jdbc:mysql://localhost:3306/test2";
			String uid="root";
			String pwd="";
			
				con=DriverManager.getConnection(url,uid,pwd);
				stmt=con.createStatement();
				System.out.println("\\n\\n***** Banking Management System*****");
				BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
				do {
					System.out.println("\n1: Display the customer details");
					System.out.println("2: Insert customer's details");
					System.out.println("3: delete customer's details");
					System.out.println("4: update customer's details");
					System.out.println("5: show account details of a customer");
					System.out.println("6: show loan details of a customer");
					System.out.println("7: Deposit Money to an Account");
					System.out.println("8: Withdraw Money from an Account");
					System.out.println("9: EXIT\n");
					System.out.println("Enter Your Choice");
					ch=Integer.parseInt(br.readLine());
					switch(ch) {
					case 1://display record
						int c=0;
						String sql="select * from customer";
						ResultSet result=stmt.executeQuery(sql);
						while(result.next()) {
							System.out.print(result.getString("cust_no")+"\t\t");
							System.out.print(result.getString("name")+"\t\t");
							System.out.print(result.getString("phone_no")+"\t\t");
							System.out.print(result.getString("city")+"\t\t\n");
							c++;
						}
						System.out.println(" ");
						System.out.println(c+" rows selected\n\n");
						break;
					case 2://insert record
						System.out.println("Enter the value of cust_no of the customer");
						String custno=br.readLine();
						System.out.println("Enter the value of name of the customer");
						String name=br.readLine();
						System.out.println("Enter the value of phone_no of the customer");
						Long phone=Long.parseLong(br.readLine());
						System.out.println("Enter the value of city of the customer");
						String city=br.readLine();
						String insert="insert into customer (cust_no,name,phone_no,city) values('"+custno+"','"+name+"','"+phone+"','"+city+"')";
						stmt.executeUpdate(insert);
						System.out.println("Row has been inserted");
						break;
					case 3://delete record
						System.out.println("Enter the cust_no you want to delete");
						String cust=br.readLine();
						String delete="delete from customer where cust_no='"+cust+"'";
						stmt.executeUpdate(delete);
						System.out.println("\nRow have been deleted\n");
						break;
					case 4://update record
						System.out.println("Enter the value of cust_no you want to update");
						String cno=br.readLine();
						System.out.println("Enter 1: For Name 2: For Phone no 3: For City to update:");
						System.out.println("Enter Choice\n");
						c1=Integer.parseInt(br.readLine());
						switch(c1) {
							case 1://for name
								System.out.println("Enter name you want to update of the customer");
								String cname=br.readLine();
								String update="update customer set name='"+cname+"' where cust_no='"+cno+"'";
								stmt.executeUpdate(update);
								System.out.println("name updated");
								break;
							case 2: //phone no
								System.out.println("Enter phone no you want to update of the customer");
								Long phoneno=Long.parseLong(br.readLine());
								String update1="update customer set phone_no='"+phoneno+"' where cust_no='"+cno+"'";
								stmt.executeUpdate(update1);
								System.out.println("phoneno updated");
								break;
							case 3://city
								System.out.println("Enter city you want to update of the customer");
								String c_city=br.readLine();
								String update2="update customer set city='"+c_city+"' where cust_no='"+cno+"'";
								stmt.executeUpdate(update2);
								System.out.println("city updated");
								break;
						}
						System.out.println("\nRow updated\n");
						break;
					case 5://account details
						System.out.println("Enter the cust_no of the customer");
						String cno1=br.readLine();
						String show="select cust_no,name,phone_no,city,account_no,type,balance,branch_code,branch_name,branch_city from customer natural join depositor natural join account natural join branch where cust_no='"+cno1+"'";
						ResultSet res=stmt.executeQuery(show);
						System.out.println("\ncust_no\t\tname\t\t\tphone_no\t\tcity\t\taccount_no\ttype\t\tbalance\t\tbranch_code\tbranch_name\t\tbranch_city");
						while(res.next()) {
							for(int i=1;i<11;i++) {
								System.out.print(res.getString(i)+"\t\t");
							}
						}
						System.out.println("\nRow Executed !!");
						break;
					case 6://loan details
						System.out.println("Enter the cust_no of the customer");
						String cno2=br.readLine();
						String loan="select cust_no,name,phone_no,city,loan_no,amount,branch_code,branch_name,branch_city from customer natural join loan natural join branch where cust_no='"+cno2+"'";
						ResultSet res1=stmt.executeQuery(loan);
						System.out.println("\ncust_no\t\tname\t\t\tphone_no\t\tcity\t\tloan_no\t\tamount\t\tbranch_code\tbranch_name\t\tbranch_city");
						while(res1.next()) {
							for(int i=1;i<10;i++) {
								System.out.print(res1.getString(i)+"\t\t");
							}
						}
						System.out.println("\nRow executed !!");
						break;
					case 7://deposit money
						System.out.println("Enter the account_no");
						String accno=br.readLine();
						System.out.println("Enter the amount you want to add");
						int amount=Integer.parseInt(br.readLine());
						String add="update account set balance=balance+'"+amount+"' where account_no='"+accno+"'";
						stmt.executeUpdate(add);
						System.out.println("Account balanced updated");
						String balance="select balance from account where account_no='"+accno+"'";
						ResultSet res2=stmt.executeQuery(balance);
						while(res2.next()) {
							System.out.println("Updated Balance:"+res2.getString("balance")+"\n");
						}
						break;
					case 8://withdraw money
						System.out.println("Enter the account_no");
						String accno1=br.readLine();
						System.out.println("Enter the amount you want to add");
						int amount1=Integer.parseInt(br.readLine());
						int bal=0;
						String cbal="select balance from account where account_no='"+accno1+"'";
						ResultSet res3=stmt.executeQuery(cbal);
						while(res3.next()) {
							System.out.println("previous balance:"+res3.getString("balance")+"\n");
							bal=Integer.parseInt(res3.getString("balance"));
						}
						if(bal>=amount1) {
							String sub="update account set balance=balance-'"+amount1+"' where account_no='"+accno1+"'";
							stmt.executeUpdate(sub);
							System.out.println("Account balanced updated");
							String balance1="select balance from account where account_no='"+accno1+"'";
							ResultSet res4=stmt.executeQuery(balance1);
							while(res4.next()) {
								System.out.println("Updated Balance:"+res4.getString("balance")+"\n");
							}
						}
						else {
							System.out.println("Insufficient Balance!!!\n");
						}
						break;
					case 9:
						stmt.close();
						con.close();
						System.exit(0);
						break;
					}
				}while(ch!=9);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}

//String url="jdbc:mysql://localhost:3306/test1";
//String username="root";
//String password="";
//try {
//		Connection con=DriverManager.getConnection(url,username,password);
//	//System.out.println("Connected to the database");
//		String sql="select * from course";
//		Statement statement =con.createStatement();
//		ResultSet result=statement.executeQuery(sql);
//		int count=0;
//		while(result.next()) {
//			String title=result.getString("title");
//			String dep=result.getString("department");
//			count++;
//			System.out.println("Course "+count+":"+title+" "+dep );
//		}
//		con.close();
//}
//catch(SQLException e){
//	System.out.println("Oops Error!");
//	e.printStackTrace();
//}
//}





//System.out.print(res.getString("cust_no")+"\t");
//System.out.print(res.getString("name")+"\t");
//System.out.print(res.getString("phone_no")+"\t");
//System.out.print(res.getString("city")+"\t");
//System.out.print(res.getString("account_type")+"\t");
//System.out.print(res.getString("type")+"\t");
//System.out.print(res.getString("balance")+"\t");
//System.out.print(res.getString("branch_code")+"\t");
//System.out.print(res.getString("branch_name")+"\t");
//System.out.print(res.getString("branch_city")+"\n");