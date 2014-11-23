import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

class Mailer extends JFrame implements ActionListener

{
	Button b1,b2;

	Mailer()
	{	
		setLayout(null);
		setSize(450,300);
                this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Email Client Project");

		setBackground(Color.lightGray);	
		Label lb=new Label("   Welcome to Java E-mail Client !!!!!!!");
		b1=new Button("Compose Mail");
		b2=new Button("POP3 Mail");
		b1.setForeground(Color.white);
		b1.setBackground(Color.darkGray);
		b2.setForeground(Color.white);
		b2.setBackground(Color.darkGray);
		lb.setForeground(Color.red);
		lb.setBackground(Color.black);
		lb.setBounds(5,30,375,70);
		b1.setBounds(30,150,145,35);
		b2.setBounds(205,150,145,35);

		add(b1);
		add(b2);
		b1.addActionListener(this);
		b2.addActionListener(this);
		setVisible(true);	
		lb.setFont(new Font("Arial",1,20));
		add(lb);		
	}

	public static void main(String args[])
	{
		new Mailer();

	}
	public void actionPerformed(ActionEvent ae)
	{
	String str=ae.getActionCommand();
	if (str.equals("Compose Mail"))
	{
		 setVisible(false);
		 new ComposeMail().show();
	}
	if (str.equals("POP3 Mail"))
	{
		setVisible(false);
		new MailBox();
	}

	} 
}