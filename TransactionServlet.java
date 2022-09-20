import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;  
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class TransactionServlet extends HttpServlet{
public void service(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
res.setContentType("text/html");
String usr1=req.getParameter("usr1");
String usr2=req.getParameter("usr2");
String s=req.getParameter("ac1");
String p=req.getParameter("ac2");
String b=req.getParameter("balance");
int amount=Integer.parseInt(b);
PrintWriter out=res.getWriter();
DateTimeFormatter date=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
LocalDateTime now=LocalDateTime.now();
String state=date.format(now);
String su="Success";
String fa="Failed";
out.println("<html><body>");
try{
Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb?useSSL=false","root","Kande@123");
Statement stm = con.createStatement();
ResultSet rs=stm.executeQuery("select balance from customer where accountno='"+s+"'");
if(rs.next()){
String n = rs.getString(1);

int balance1 = Integer.parseInt(n);
if(balance1 - amount > 0){

    int x=stm.executeUpdate("update customer set balance=balance+'"+amount+"' where accountno='"+p+"'");
   int y=stm.executeUpdate("update customer set balance=balance-'"+amount+"' where accountno='"+s+"'");
   out.println("<br><br><br><br><center><img src=https://media.tenor.com/images/cbae2dfd31aa5ec2fcb7f46b65e1550f/tenor.gif alt=Transaction successful width=400 height=400><br>");
   out.println("<b>TRANSACTION SUCCESSFUL</b></center>");
   String q1="insert into history values('"+usr1+"','"+usr2+"','"+amount+"','"+state+"','"+su+"')";
   PreparedStatement stm1=con.prepareStatement(q1);
   int z=stm1.executeUpdate();
}
else{
	out.println("<center><br><br><br><br><img src=https://dpsnagpur.edunexttech.com/images/payment_failure.png alt=Transaction Unsuccessful width=400 height=400><br>");
  
   String q2="insert into history values('"+usr1+"','"+usr2+"','"+amount+"','"+state+"','"+fa+"')";
   PreparedStatement stm1=con.prepareStatement(q2);
   int z=stm1.executeUpdate();
}	
con.close();
}}
catch(Exception e){
System.out.println(e.getMessage());
}
}
}