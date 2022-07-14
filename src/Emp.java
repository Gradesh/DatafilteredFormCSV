import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Emp {
	
	
	public static void main(String[] args)throws Exception
	{
		 Class.forName("com.mysql.cj.jdbc.Driver");
  		
  		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/sample1","root","Monaleesa123@");
  		Statement stmt= con.createStatement();
	
	 BufferedReader lineReader = new BufferedReader(

             new FileReader("../AssignmentCSV/employee.csv"));
	
	
     String lineText = null;

     lineReader.readLine();
  
     while ((lineText = lineReader.readLine()) != null) 
     {



         String[] data = lineText.split(",");
         



         String EMPLOYEE_ID = data[0];
         String F_Name=data[1];
         String L_Name=data[2];
        String  EMAIL=data[3];
        
         String PHONE_NUMBER=data[4];
         String MobileNumber = PHONE_NUMBER.replaceAll("[,./]", "");
    //     System.out.println("MobileNumber"+MobileNumber);
         
         String HIRE_DATE=data[5];
         String JOB_ID=data[6];
         String SALARY=data[7];
         String COMMISSION_PCT=data[8];
         String MANAGER_ID=data[9];
         String DEPARTMENT_ID=data[10];
         
         LocalDate Date=LocalDate.now();
         DateTimeFormatter datestr=DateTimeFormatter.ofPattern("dd/MM/yyyy");
         String formatdate=Date.format(datestr);
         
      /*  System.out.println(EMPLOYEE_ID+"\t"+F_Name+"\t"+L_Name+"\t"+EMAIL+"\t"
         +MobileNumber+"\t"+HIRE_DATE+"\t"+JOB_ID+"\t"+SALARY+"\t"+COMMISSION_PCT+
         "\t"+MANAGER_ID+"\t"+DEPARTMENT_ID); */
         
        
         
         ArrayList<String> address = new ArrayList<>();
    address.add(EMAIL);
   
         
         try
			{ 
        	
        	 for (String i: address)
        	 {
        		 
        		 //if Email id data is valid then it will add all data as it is in the Employee Table
        		 
        		 if (isValid(i))
        		 {
        			
        			 String query1 = "INSERT INTO Employee " + "VALUES ('" + EMPLOYEE_ID + "','" + F_Name + "','" + L_Name + "','"+address+"','"
        			         +MobileNumber+"','"+HIRE_DATE+"','"+JOB_ID+"','"+SALARY+"','"+COMMISSION_PCT+"','"+MANAGER_ID+"','"+DEPARTMENT_ID+"',"+formatdate+")";
        			     
        			         

        			        stmt.executeUpdate(query1); 
        			    
        		//	 System.out.println(MobileNumber+""+address);
        			        System.out.println("Query Executed!! if Condition Data added Successfully");
        			       
        			       
        			        
        			        Statement stmt1 = con.createStatement();
        		            
        			     
        		            

        			              
        			                String success ="SELECT Employee.EMPLOYEE_ID , Employee.FIRST_NAME, Employee.EMAIL, \r\n"
        			                		+ "Employee_Job.JOB_Description,Department.Dept_Desc,Employee.MANAGER_ID,Employee.Created_date\r\n"
        			                		+ "FROM Employee\r\n"
        			                		+ "INNER JOIN Employee_job ON Employee.JOB_ID = Employee_Job.JOB_ID\r\n"
        			                		+ "INNER JOIN Department ON Employee.DEPARTMENT_ID = Department.Dept_ID;";
        			                 
        			                 ResultSet res= stmt1.executeQuery(success);
        			                 
        			                 BufferedWriter fileWriter = new BufferedWriter(
        			                         new FileWriter("../AssignmentCSV/Success.csv"));
   
        			                 // write header lines for column names       
        			                 fileWriter.write("EMPLOYEE_ID,FIRST_NAME,EMAIL,Job_description,MANAGER_ID,DEPARTMENT_NAME");
        			                 while (res.next()) {
        			                     String EMP_ID = res.getString("EMPLOYEE_ID");
        			                     String F_NAME = res.getString("FIRST_NAME");
        			                     String Email = res.getString("EMAIL");
        			                     String Job_desc = res.getString("Job_description");
        			                     String MNGR_ID = res.getString("MANAGER_ID");
        			                     String DEPT_NAME = res.getString("Dept_Desc");

        			                     String line = String.format("%s,%s,%s,%s,%s,%s",
        			                    		 EMP_ID, F_NAME, Email, Job_desc, MNGR_ID, DEPT_NAME);

        			                     fileWriter.newLine();
        			                     fileWriter.write(line);
        			                     
        			                     
        			                 }
        			                 lineReader.close();
        			                 con.close();
        			                 stmt.close();
        			                 fileWriter.close();
        			                
        			        
        			        
        			      
        			       
	
        		 }
        		 else
        		 {
        		//if EMAIL id is not valid then it will add @abc.com in the mail id 
        			 //column and put into this in Employee_ID Table	 
          
        			
        			 
        			  String query2 = "INSERT INTO Employee_Failed " + 
        			  "VALUES ('" + EMPLOYEE_ID + "','" 
        			  + F_Name + "','" + L_Name + "','"+address+"',"
        			  		+ "'"+JOB_ID+"','"+MANAGER_ID+"','"+DEPARTMENT_ID+"','"+formatdate+"')";
        			  stmt.executeUpdate(query2);
        			  
        	
        			  System.out.println("Query Executed!! Else Condition"
  			        		+ " Data added Successfully"); 
        			  
        			  Statement stmt2 = con.createStatement();
        			  
        			
		                 
		                 
		                 // write header lines for column names       
		               //  fileWriter.write("EMPLOYEE_ID,FIRST_NAME,EMAIL,Job_description,MANAGER_NAME,DEPARTMENT_NAME");
		              
		                 String failed="\r\n"
		                 		+ "SELECT Employee_Failed.EMPLOYEE_ID, Employee_Failed.FIRST_NAME, Employee_Failed.EMAIL, \r\n"
		                 		+ "Employee_Job.JOB_Description, Department.Dept_Desc, Employee_Failed.MANAGER_ID,Employee_Failed.Created_date\r\n"
		                 		+ "FROM Employee_Failed\r\n"
		                 		+ "INNER JOIN Employee_job ON Employee_Failed.JOB_ID = Employee_Job.JOB_ID\r\n"
		                 		+ "INNER JOIN Department ON Employee_Failed.DEPARTMENT_ID = Department.Dept_ID;";
		                 
		                 
		                 
		                 ResultSet res1= stmt2.executeQuery(failed);
		                 
		                 
		           	  BufferedWriter fileWriter1 = new BufferedWriter(
		                      new FileWriter("../AssignmentCSV/Failed.csv"));
		           	  
		           	 fileWriter1.write("EMPLOYEE_ID,FIRST_NAME,EMAIL,Job_description,MANAGER_ID,DEPARTMENT_NAME");
     
		                 while (res1.next()) 
		                 {
		                     String EMP_ID = res1.getString("EMPLOYEE_ID");
		                     String F_NAME = res1.getString("FIRST_NAME");
		                     String Email = res1.getString("EMAIL");
		                     String Job_desc = res1.getString("Job_description");
		                     String MNGR_ID = res1.getString("MANAGER_ID");
		                     String DEPT_NAME = res1.getString("Dept_Desc");

		                     String line = String.format("%s,%s,%s,%s,%s,%s",
		                    		 EMP_ID, F_NAME, Email, Job_desc, MNGR_ID, DEPT_NAME);

		                     fileWriter1.newLine();
		                     fileWriter1.write(line);
		                     
		                     
		                 }
		        
        	        			         
        		 }
        		 
        	 }
				
        	
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
			}

		
		
		
	
	}
	

}
	 public static boolean isValid(String email)
	    {
	        String emailRegex = "^(.+)@(.+)$";  //[A-Za-z]+@
	  //  String Emailrewrite="[A-Za-z]+@";
	                              
	        Pattern pat = Pattern.compile(emailRegex);
	       
	        if (email == null)
	        	
	            return false;
	        return pat.matcher(email).matches();
	        
	    }
}
