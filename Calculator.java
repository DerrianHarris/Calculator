import java.awt.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;  //notice javax

public class Calculator extends JFrame
{
  JPanel pane = new JPanel(new GridBagLayout());
  JTextField tf = new JTextField(10);
  
  GridBagConstraints c = new GridBagConstraints();
  
  String Input = "";
  
  Calculator() // the frame constructor method
  {
    super("Calculator"); setBounds(100,100,210,200);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container con = this.getContentPane(); // inherit main frame
    
    
    CreateButtons();
    con.add(BorderLayout.NORTH,tf);
    con.add(BorderLayout.CENTER,pane); // add the panel to frame
    				// customize panel here
    
    
    
    setVisible(true); // display this frame
    
  }
  
  String[] ParseInput(String s) {
	  
	  List<String> a = new ArrayList<String>();
	  String Value = "";
	  for(int i = 0; i < s.length();i++) {
		 char val = s.charAt(i);
		  
		 if(Character.isDigit( val ) || val == '.') {
			 Value += val;
			 
			 if(i == s.length()-1 || isOperator(s.charAt(i+1)) || s.charAt(i+1) == ' ') {
				 a.add(Value);
				 Value = "";
			 }
			 
		 }else if(isOperator(val)) {
			 a.add(""+val);
		 }else {
			 a.add(""+val);
		 }
	  }
	  
	  String[] arr = new String[a.size()];
	  arr = a.toArray(arr);
	  return arr;
  }
  
  float SolveTree(Node T) {
	  if(T == null) {
		  return 0;
	  }
	  if(!isOperator(T.Value.charAt(0))) {
		  return Float.parseFloat(T.Value);
	  }
	  float A = SolveTree(T.left);
	  float B = SolveTree(T.right);
	  
	  return Calculate(A,B,T.Value.charAt(0));
  }
  
  float Calculate(float A, float B, char op) {
	  switch(op) {
	  	case '+':
	  		return A + B;
	  	case '-':
	  		return A - B;
	  	case '*':
	  		return A * B;
	  	case '/':
	  		return A / B;
	  	case '^':
	  		return (float) Math.pow(A ,B);
	  }
	  return 0;
  }
  
  String[] InfixToPostfix(String[] s) {
	  Stack<String> st = new Stack<String>(); 
	  List<String> a = new ArrayList<String>();
	  
	  st.push("#");
	  for(String str : s) {
		  if(isNumeric(str) || Character.isAlphabetic(str.charAt(0))) {
			  a.add(str);
		  }else if(str.equals("(")) {
			  st.push(str);
		  }else if(str.equals("^")) {
			  st.push(str);
		  }else if(str.equals(")")) {
			  while(!st.peek().equals("#") && !st.peek().equals("(")) {
				  a.add(st.pop());
			  }
			  st.pop();
		  }else {
			  if(preced(str) > preced(st.peek())) {
				  st.push(str);
			  }else {
				  while(!st.peek().equals("#") && preced(str) <= preced(st.peek())) {
					  a.add(st.pop());
				  }
				  st.push(str);
			  }
		  }
	  }
	  while(!st.peek().equals("#")) {
		  a.add(st.pop());
	  }
	  String[] arr = new String[a.size()];
	  arr = a.toArray(arr);
	  return arr;
  }
  
  int preced(String ch) {
	   if(ch.equals("+") || ch.equals( "-")) {
	      return 1;              //Precedence of + or - is 1
	   }else if(ch.equals("*") || ch.equals("/")) {
	      return 2;            //Precedence of * or / is 2
	   }else if(ch.equals("^")) {
	      return 3;            //Precedence of ^ is 3
	   }else {
	      return 0;
	   }
	}
  
  public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
  
  public void CreateButtons() {
	  ActionListener AL = new ActionListener(){  
		  public void actionPerformed(ActionEvent e){
			  JButton b = (JButton)e.getSource();
			  String text = b.getText();
			  switch(text) {
				  case "CLEAR":
				    Input = "";
				    tf.setText(Input);
				    break;
				  case"ENTER":
					  	String[] Output = InfixToPostfix(ParseInput(Input));
					    ExpressionTree et = new ExpressionTree();
					    Node node = et.constructTree(Output);
					    Input = ""+SolveTree(node);
					    tf.setText(Input);
					  break;
				  default:
				    Input += text;
				    tf.setText(Input);
					  
			} 
		  }  
	};
	  for(int i = 1; i < 10; i++){
		  c.fill = GridBagConstraints.HORIZONTAL;
		  c.weighty = .5;
		  int x = (i-1) % 3;
		  int y = (i-1) / 3;
		  c.gridx = x;
		  c.gridy = y;
		  JButton button = new JButton(""+i);
		  pane.add(button,c);
		  button.addActionListener(AL); 
	  }
	  c.fill = GridBagConstraints.HORIZONTAL;
	  int x = 0;
	  int y = 3;
	  c.gridx = x;
	  c.gridy = y;
	  JButton button = new JButton("(");
	  pane.add(button,c);
	  button.addActionListener(AL);
	  
	  x = 0;
	  y = 4;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton(")");
	  pane.add(button,c);
	  button.addActionListener(AL);  
	  
	  x = 2;
	  y = 3;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton(".");
	  pane.add(button,c);
	  button.addActionListener(AL);
	  
	  x = 1;
	  y = 3;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton("0");
	  pane.add(button,c);
	  button.addActionListener(AL); 
	  
	  x = 3;
	  y = 0;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton("+");
	  pane.add(button,c);
	  button.addActionListener(AL); 
	  
	  x = 3;
	  y = 1;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton("-");
	  pane.add(button,c);
	  button.addActionListener(AL); 
	  
	  x = 3;
	  y = 2;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton("*");
	  pane.add(button,c);
	  button.addActionListener(AL); 
	  
	  x = 3;
	  y = 3;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton("/");
	  pane.add(button,c);
	  button.addActionListener(AL);
	  
	  x = 3;
	  y = 4;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton("^");
	  pane.add(button,c);
	  button.addActionListener(AL); 
	  
	  x = 3;
	  y = 5;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton("ENTER");
	  pane.add(button,c);
	  button.addActionListener(AL); 
	  
	  x = 3;
	  y = 6;
	  c.gridx = x;
	  c.gridy = y;
	  button = new JButton("CLEAR");
	  pane.add(button,c);
	  button.addActionListener(AL);
  }
  public static Boolean isOperator(char c) {
		return (c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '^') || c == '(' || c == ')';
	}
  public static void main(String args[]) {
	  Calculator cal = new Calculator();
  }
}