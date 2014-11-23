import java.applet.Applet;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Date;

class Time extends Applet implements Runnable
{
	Font f = new Font("TimesRoman",Font.BOLD,24);
	Date d; Thread runner;

    public void init()
    {resize(300,100);}

    public void paint (Graphics g)
    {g.setFont(f);g.drawString(d.toString(),10,50);}

    public void start()
    {if (runner == null) {runner = new Thread(this); runner.start();}}

    public void stop()
    {if (runner != null) {runner.stop(); runner = null;}}

    public void run()
    {
	   while (true)
       {
		   d = new Date(); repaint();
		   try {Thread.sleep(1000);}
		   catch(InterruptedException e){};
	   }
    }
}