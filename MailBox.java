 import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import javax.swing.*;

public class MailBox extends JFrame implements ActionListener,ItemListener
{		
	java.awt.Choice chok;
	int marker;

	FileOutputStream fout;
	BufferedReader br;
	FileReader fin;
	JTextField totalMessages;
	JTextArea ta;
	JTextField pop3;
	JTextField user;
	JTextField pass;
	JTextField port;

	JButton con;
	JButton clear;
	JButton err;
	JButton down;
	JButton print;

	String mes;
	String sg;
	String finstr="";
	static String spop3;
	static String sport="";
	static String user_acc="";
	static String pass_acc="";
	String sp;
	int num;

	String message;

	public MailBox()
	{

		setTitle("POP3 Mail Box");
		setLayout(null);
		setBackground(Color.lightGray);
		setSize(450,270);

		Label wel=new Label("                    Welcome to Java POP3 Mail");
		wel.setFont(new Font("Arial",1,18));
		setFont(new Font("Arial",1,12));		
		Label lbpop3=new Label("POP3 Server :");
		Label lbuser=new Label("User Name    :");
		Label lbpass=new Label("Password     :");
		Label lbport=new Label("Port                   :");
		Label lbme=new Label("Total New Mail:");

		pop3=new JTextField(100);
		user=new JTextField(100);
		pass=new JPasswordField(100);
		port=new JTextField(100);


		con=new JButton("Connect");
		clear=new JButton("Clear");
		con.setBackground(Color.darkGray);
		con.setForeground(Color.white);	
		clear.setForeground(Color.white);
		clear.setBackground(Color.darkGray);		
		con.addActionListener(this);
		clear.addActionListener(this);

		wel.setBounds(8,35,435,70);
		wel.setForeground(Color.red);
		wel.setBackground(Color.black);		
		lbpop3.setBounds(25,120,80,20);
		pop3.setBounds(105,120,160,20);
		pop3.setText("");
		lbport.setBounds(270,120,90,20);
		port.setBounds(360,120,30,20);
		port.setText("110");
		port.setForeground(Color.blue);		
		port.setEditable(true);


		lbuser.setBounds(25,150,80,20);
		user.setBounds(105,150,160,20);
		lbpass.setBounds(25,180,80,20);
		pass.setBounds(105,180,160,20);		
		//pass.setEchoChar('*');

		con.setBounds(105,215,100,30);
		clear.setBounds(215,215,100,30);		
		err=new JButton("Back");
	    	down=new JButton("Download Message");
		err.setForeground(Color.white);
		err.setBackground(Color.darkGray);		
		down.setForeground(Color.white);
		down.setBackground(Color.darkGray);		
		lbme.setBounds(270,150,120,20);		
		err.setBounds(320,215,100,30);
		down.setBounds(270,180,150,20);		
		err.addActionListener(this);
		down.addActionListener(this);

		totalMessages=new JTextField(20);		
		totalMessages.setBounds(360,150,60,20);
		totalMessages.setEditable(false);

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		add(wel);
		add(lbpop3);
		add(lbuser);
		add(lbpass);
		add(lbport);
		add(pop3);
		add(user);
		add(pass);
		add(port);
		add(con);
		add(clear);
		add(totalMessages);
		add(err);
		add(lbme);
		add(down);
		setVisible(true);

	}
	public  MailBox(int f)
	{	

		setTitle("Download Message");
		setBackground(Color.lightGray);
		setLayout(null);
		setSize(500,500);
		setResizable(false);

		ta=new JTextArea();
		print=new JButton("Print");
	 	chok=new java.awt.Choice();

		for(int s=0;s<f;s++)
		{
		chok.add("Message"+Integer.toString(s+1));
		}

		Label lbchok=new Label("Messages Inside");
		Label messgg=new Label("Recent Clicked Message Stored in file messages.txt");
		lbchok.setBounds(26,30,97,20); 
		messgg.setBounds(140,30,290,20);		
		chok.setBounds(25,55,90,400);
		print.setBounds(260,470,40,20);
		add(lbchok);
		add(chok);
		add(print);
		ta.setBounds(120,55,350,400);
		add(ta);
		add(messgg);
		print.addActionListener(this);
		chok.addItemListener(this);
                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);

	}


		public void actionPerformed(ActionEvent er)
		{

			String str=er.getActionCommand();

				if (str.equals("Connect"))
				{
					try
					{					

			if (!(pop3.getText()).equals("")&!(port.getText()).equals("")&!(user.getText()).equals("")&!(pass.getText()).equals(""))
			{
			setTitle("Connecting to server....");
			checkMail(pop3.getText(),port.getText(),user.getText(),pass.getText());					

					totalMessages.setText(mes);	
					setTitle("Connected !");
			}
			else
				return;
					}

					catch(UnknownHostException uhe)
				  {
				  	setVisible(false);
					new Error();
				  	System.out.println(uhe);	
				  }
			    catch(ProtocolException pe)
				  {
					 System.out.println(pe);
					 System.exit(0);	
				  }	
			   catch(IOException ioe)
				  {
				 System.out.println(ioe);
				 System.exit(0);
				  }					

				}

				if(str.equals("Clear"))
				{
				pop3.setText("");
				user.setText("");
				pass.setText("");
				}

			if (str.equals("Back"))
		  { 
		  	setVisible(false);
			(new Mailer()).show();
		  } 	


			if (str.equals("Download Message"))
			{
					spop3=pop3.getText();
					sport+=port.getText();
					user_acc+=user.getText();
					pass_acc+=pass.getText();

					setVisible(false);
					if(num>0)
					new MailBox(num);			
					else return;	

			}

				  if (str.equals("Print"))
			{

				Runtime r=Runtime.getRuntime();
				Process p=null;
				try
				{
					p=r.exec("notepad.exe /p messages.txt");
					p.waitFor();
				}
				catch(Exception e)
				{
				}

			}

		}

			public void itemStateChanged(ItemEvent is)
			{	

				int id=chok.getSelectedIndex();		
				try
				{

				ta.setText("");
				getMail(Integer.toString(id+1));
				finstr="--------------------------------Message no. "+(id+1)+"--------------------------------------"+"\n";
				do
				{
					sp=br.readLine();
					if(sp==null)
					break;
					finstr+=sp+"\n";
				}while(sp!=null);
				ta.setText("");
				ta.setText(finstr);


	   			setTitle("Message Downloaded");		

				}
					catch(UnknownHostException uhe)
				  {
				  	setVisible(false);
					new Error();
				  }
			    catch(ProtocolException pe)
				  {
					 System.exit(0);
				  }
			   catch(IOException ioe)
				  {
				System.out.println("Message Received !");
				  }
		}

		public void getMail(String message) 
		throws IOException, ProtocolException, UnknownHostException
		{
		setTitle("Downloading message.......");
		Socket s1;       
	    String reply;          
		String sp;
		int b;	
		s1=new Socket(spop3,110);				


		fout=new  FileOutputStream("messages.txt",false);
		fin=new FileReader("messages.txt");
		br=new BufferedReader(fin);


		BufferedInputStream in=new BufferedInputStream(s1.getInputStream(),2500);
		BufferedReader io=new BufferedReader(new InputStreamReader(s1.getInputStream()),2500);
		PrintStream put=new PrintStream(new BufferedOutputStream(s1.getOutputStream(),2500),true);

		reply=io.readLine();
		System.out.println("Server Response:"+reply);
		put.println("USER"+" "+user_acc);
		reply=io.readLine();
		System.out.println("Server Response:"+reply);
		put.println("PASS"+" "+pass_acc);
		reply=io.readLine();
		System.out.println("Server Response:"+reply);

		put.println("STAT");
		reply=io.readLine();
		System.out.println("Server Response:"+reply);

		put.println("RETR"+" "+message);


		int count=0;

		do			  	 	
		{
		marker=in.read();
		count++;

		}while(marker!=59);	

		in.mark(count);			
		io.mark(count);
		in.reset();
		io.reset();	


		int k=in.available();

		for(int z=0;z<k;z++)
		{	
			b=in.read();
			fout.write(b);			
		}

		fout.close();		

		put.println("QUIT");

		reply=io.readLine();
		System.out.println("Server Response:"+reply);
		in.close();
		io.close();
		s1.close();

		}	

	 public void checkMail(String server_pop3, String server_port, String acc_user, String acc_pass) 
		throws IOException, ProtocolException, UnknownHostException
		{		
		Socket s2;       
    	String rep; 	

		s2=new Socket(server_pop3,110);		

		BufferedReader ios=new BufferedReader(new InputStreamReader(s2.getInputStream()),2500);
		PrintStream puts=new PrintStream(new BufferedOutputStream(s2.getOutputStream(),2500),true);

		int msgTotal=0;
		int tempmsg;
		rep=ios.readLine();
		System.out.println("Server Response:"+rep);
		puts.println("USER"+" "+acc_user);
		rep=ios.readLine();
		System.out.println("Server Response:"+rep);

		puts.println("PASS"+" "+acc_pass);
		rep=ios.readLine();
		System.out.println("Server Response:"+rep);

		puts.println("STAT");
		rep=ios.readLine();


		if(rep.charAt(4)<=79&rep.charAt(5)==32)
		{
		msgTotal+=rep.charAt(4)-48;
		}

		else
		{				
			for(int pl=4;rep.charAt(pl)!=32;pl++)
			{
			tempmsg=rep.charAt(pl)-48;	
			msgTotal=msgTotal*10+tempmsg;

			}		
		}

		mes=Integer.toString(msgTotal);
		num=msgTotal;

	 	System.out.println("Server Response:"+rep);

		puts.println("QUIT");

		rep=ios.readLine();
		System.out.println("Server Response:"+rep);

		s2.close();

		}		

}