import java.util.Stack;

public class ExpressionTree {
	Node constructTree(String Input[]) { 
        Stack<Node> st = new Stack<Node>(); 
        Node t, t1, t2; 
        for (int i = 0; i < Input.length; i++) { 
            if (!Calculator.isOperator(Input[i].charAt(0))) { 
                t = new Node(Input[i]); 
                st.push(t); 
            } else 
            { 
                t = new Node(Input[i]); 
                t1 = st.pop();
                t2 = st.pop(); 
                t.right = t1; 
                t.left = t2; 
                st.push(t); 
            } 
        } 
        t = st.peek(); 
        st.pop(); 
  
        return t;
    }
}
