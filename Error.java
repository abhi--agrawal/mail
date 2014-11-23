import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Error extends Frame implements ActionListener
{	
	public Error()
	{

	setBackground(Color.lightGray);	
	setTitle("Error");
	setLayout(null);
	setResizable(false);

	setSize(400,300);

	Label lber=new Label("Cannot connect to server!");
	Button tryd =new Button("Try Again");

	tryd.setBounds(140,160,70,30);  
	lber.setBounds(100,100,300,30);

	add(lber);
	add(tryd);

	tryd.addActionListener(this);
	setVisible(true);

	}

        @Override
	public void actionPerformed(ActionEvent das)
	{
		String action4 = das.getActionCommand();
		if (action4.equals("Try Again"))
		{
			setVisible(false);	
			new MailBox();
		}	
	}
}