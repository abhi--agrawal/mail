
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

class ComposeMail extends javax.swing.JFrame{

  public ComposeMail() {
    //{{INIT_CONTROLS
    setTitle("Compose Mail");
    getContentPane().setLayout(null);
    setSize(430, 600);
    setVisible(false);
    getContentPane().add(JLabel1);
    JLabel1.setBounds(12, 145, 36, 12);
    JLabel2.setText("To:");
    getContentPane().add(JLabel2);
    JLabel2.setBounds(12, 110, 84, 12);
    JLabel3.setText("Subject:");
    getContentPane().add(JLabel3);
    JLabel3.setBounds(12, 175, 48, 12);
    JLabel4.setText("SMTP Server:");
    getContentPane().add(JLabel4);
    JLabel4.setBounds(12, 75, 80, 30);
    getContentPane().add(_from);
    JLabel5.setText("          Welcome to Java Compose Mail !!!!!");
    getContentPane().add(JLabel5);
	JLabel5.setBounds(8,10,400,30);	
	JLabel5.setForeground(Color.red);
	JLabel5.setFont(new Font("Arial",1,18));
	JLabel5.setBackground(Color.black);		
    JLabel1.setText("From:");
    _from.setBounds(96, 140, 300, 24);
    getContentPane().add(_to);
    _to.setBounds(96, 110, 300, 24);
    getContentPane().add(_subject);
    _subject.setBounds(96, 170, 300, 24);
    getContentPane().add(_smtp);
    _smtp.setBounds(96, 80, 300, 24);
    getContentPane().add(_scrollPane2); 
    _scrollPane2.setBounds(12, 210, 384, 310);
    _scrollPane2.getViewport().add(_body);
    _body.setBounds(0, 0, 381, 105);
    Send.setText("Send");
    Send.setActionCommand("Send");
    Send.setForeground(Color.white);
	Send.setBackground(Color.darkGray);
    getContentPane().add(Send);
    Send.setBounds(15, 550, 132, 20);
    Clear.setText("Clear");
    Clear.setActionCommand("Clear");
    Clear.setForeground(Color.white);
	Clear.setBackground(Color.darkGray);
    getContentPane().add(Clear);
    Clear.setBounds(160, 550, 120, 20);
   	Back.setText("Back");
    Back.setActionCommand("Back");
    Back.setForeground(Color.white);
	Back.setBackground(Color.darkGray);
    getContentPane().add(Back);
    Back.setBounds(300, 550, 120, 20);

    //REGISTER_LISTENERS
    SymAction lSymAction = new SymAction();
    Send.addActionListener(lSymAction);
    Clear.addActionListener(lSymAction);
    Back.addActionListener(lSymAction);    
  }

  public void setVisible(boolean b) {
    if (b)
      setLocation(50, 50);
    super.setVisible(b);
  }

  static public void main(String args[]) 
  {
        new Mailer().show(); 
  }

  public void addNotify() {
    // Record the size of the window prior to
    // calling parents addNotify.
    Dimension size = getSize();
    super.addNotify();

    if (frameSizeAdjusted)
      return;
    frameSizeAdjusted = true;

    // Adjust size of frame according to the
    // insets and menu bar
    Insets insets = getInsets();
    javax.swing.JMenuBar menuBar = getRootPane().getJMenuBar();
    int menuBarHeight = 0;
    if (menuBar != null)
      menuBarHeight = menuBar.getPreferredSize().height;
    setSize(insets.left + insets.right + size.width, insets.top
        + insets.bottom + size.height + menuBarHeight);
  }

  // Used by addNotify
  boolean frameSizeAdjusted = false;

  //{{DECLARE_CONTROLS
  javax.swing.JLabel JLabel1 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel2 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel3 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel4 = new javax.swing.JLabel();
  javax.swing.JLabel JLabel5 = new javax.swing.JLabel();
  javax.swing.JTextField _from = new javax.swing.JTextField();
  javax.swing.JTextField _to = new javax.swing.JTextField();
  javax.swing.JTextField _subject = new javax.swing.JTextField();
  javax.swing.JTextField _smtp = new javax.swing.JTextField();
  javax.swing.JScrollPane _scrollPane2 = new javax.swing.JScrollPane();
  javax.swing.JTextArea _body = new javax.swing.JTextArea();
  javax.swing.JButton Send = new javax.swing.JButton();
  javax.swing.JButton Clear = new javax.swing.JButton();  
  javax.swing.JButton Back = new javax.swing.JButton(); 
  javax.swing.JScrollPane _scrollPane = new javax.swing.JScrollPane();
  javax.swing.JList _output = new javax.swing.JList();
  javax.swing.DefaultListModel _model = new javax.swing.DefaultListModel();
  //Input from the socket.
  java.io.BufferedReader _in;
//Output to the socket.
  java.io.PrintWriter _out;

  class SymAction implements java.awt.event.ActionListener {

    //Make sure the event to the correction method.

    public void actionPerformed(java.awt.event.ActionEvent event) {
      Object object = event.getSource();
      if (object == Send)
        Send_actionPerformed(event);
      else if (object == Clear)
        Clear_actionPerformed(event);
      else if (object == Back)
        Back_actionPerformed(event);        
    }
  }

  protected void send(String s) throws java.io.IOException {
    // Send the SMTP command
    if (s != null) {
      _model.addElement("C:" + s);
      _out.println(s);
      _out.flush();
    }
    // Wait for the response
    String line = _in.readLine();
    if (line != null) {
      _model.addElement("S:" + line);
    }
  }

  void Send_actionPerformed(java.awt.event.ActionEvent event) {
    try {

      java.net.Socket s = new java.net.Socket(_smtp.getText(), 25);
      _out = new java.io.PrintWriter(s.getOutputStream());
      _in = new java.io.BufferedReader(new java.io.InputStreamReader(s
          .getInputStream()));

      send(null);
      send("HELO " + java.net.InetAddress.getLocalHost().getHostName());
      send("MAIL FROM: " + _from.getText());
      send("RCPT TO: " + _to.getText());
      send("DATA");
      _out.println("Subject:" + _subject.getText());
      _out.println(_body.getText());
      send(".");
      s.close();

    } catch (Exception e) {
      _model.addElement("Error: " + e);
    }

  }

  void Clear_actionPerformed(java.awt.event.ActionEvent event) {
  _from.setText("");
  _to.setText("");
  _smtp.setText("");
  _subject.setText("");
  _body.setText("");
  }
  void Back_actionPerformed(java.awt.event.ActionEvent event) {
  setVisible(false);
 (new Mailer()).show();
  }
/*Note
 *
 *Please use the tags <CRLF>.<CRLF> at the end of your message!!!!
 *
 *Definition of CRLF
 *
 *(Carriage Return/Line Feed) The end of line characters used in standard PC text files 
 *(ASCII decimal 13 10, hex 0D 0A). In the Mac, only the CR is used; in Unix, only the LF. 
 *When one considers that the computer world could not standardize the code to use to end 
 *a simple text line, it is truly a miracle that sufficient standards were agreed upon to 
 *support the Internet, which flourishes only because it is a standard.
 **/
  
}