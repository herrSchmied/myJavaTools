package someMath;


import java.util.*;
import java.util.regex.*;

public class MiniMathEngine
{

    // Allowed functions
    private static final Set<String> FUNCTIONS = Set.of(
    "sin","cos","tan","log","ln","sqrt","exp"
    );

    // Predefined constants
    private static final Map<String, Double> CONSTANTS = Map.of(
    "π", Math.PI, "e", Math.E
	);

    // Token pattern: functions, variables, numbers, operators, parentheses
    private static final Pattern TOKEN = Pattern.compile(
    "sin|cos|tan|log|ln|sqrt|exp|[\\p{IsLatin}\\p{IsGreek}]|π|e|[0-9]+(?:\\.[0-9]+)?|[+\\-*/^()]"
	);

	// ---------- TOKENIZER ----------
	public static List<String> tokenize(String expr)
	{
	
		expr = expr.replaceAll("\\s+", "");
	    List<String> tokens = new ArrayList<>();
	    Stack<Character> parens = new Stack<>();
	    Matcher m = TOKEN.matcher(expr);
	    int index = 0;

	    while(m.find())
	    {
	         if(m.start() != index) 
	         {
	                throw new IllegalArgumentException("Invalid token near: " + expr.substring(index));
	         }

	         String t = m.group();

	         // Track parentheses
	         if(t.equals("(")) parens.push('(');
	         if(t.equals(")")) 
	         {
	        	 if (parens.isEmpty()) throw new IllegalArgumentException("Unmatched ')'");
	             parens.pop();
	         }

	         // Unary minus detection
	         if (t.equals("-"))
	         {
	 
	        	 if(tokens.isEmpty() || isOperator(tokens.get(tokens.size() - 1)) || tokens.get(tokens.size() - 1).equals("("))
	        	 {
	        		 tokens.add("u-"); // unary minus
	        	 }
	        	 else 
	        	 {
	        		 tokens.add(t);   // binary minus
	             }
	         
	         }
	         else
	         {
	                tokens.add(t);
	         }
	         
	         index = m.end();
	    }

	        
	    if(!parens.isEmpty())
	    {
	    	throw new IllegalArgumentException("Unmatched '('");
	    }

	    if(index != expr.length())
	    {
	            throw new IllegalArgumentException("Invalid expression near: " + expr.substring(index));
	    }

	    return tokens;    
	}


	// ---------- SHUNTING YARD TO RPN ----------
	public static List<String> toRPN(List<String> tokens)
	{

		List<String> output = new ArrayList<>();
		Stack<String> ops = new Stack<>();

		for (String t : tokens)
		{
	            if (isValue(t)) {
	                output.add(t);
	            }
	            else if (FUNCTIONS.contains(t) || t.equals("u-")) {
	                ops.push(t);
	            }
	            else if (isOperator(t)) {
	                while (!ops.isEmpty() &&
	                        (FUNCTIONS.contains(ops.peek()) || isOperator(ops.peek())) &&
	                        ((precedence(ops.peek()) > precedence(t)) ||
	                        (precedence(ops.peek()) == precedence(t) && !isRightAssociative(t)))) {
	                    output.add(ops.pop());
	                }
	                ops.push(t);
	            }
	            else if (t.equals("(")) {
	                ops.push(t);
	            }
	            else if (t.equals(")")) {
	                while (!ops.peek().equals("(")) output.add(ops.pop());
	                ops.pop(); // remove '('
	                if (!ops.isEmpty() && FUNCTIONS.contains(ops.peek())) output.add(ops.pop());
	            }
	        }

	        while (!ops.isEmpty()) output.add(ops.pop());
	        return output;
	    }

	    // ---------- RPN EVALUATOR ----------
	    public static double evaluateRPN(List<String> rpn, Map<String, Double> vars) {
	        Stack<Double> stack = new Stack<>();

	        for (String token : rpn) {
	            if (token.matches("[0-9]+(?:\\.[0-9]+)?")) {
	                stack.push(Double.parseDouble(token));
	            }
	            else if (token.matches("[\\p{IsLatin}\\p{IsGreek}]|π|e")) {
	                if (CONSTANTS.containsKey(token)) stack.push(CONSTANTS.get(token));
	                else if (vars.containsKey(token)) stack.push(vars.get(token));
	                else throw new IllegalArgumentException("No value provided for variable: " + token);
	            }
	            else if (token.equals("u-")) {
	                double val = stack.pop();
	                stack.push(-val);
	            }
	            else if (FUNCTIONS.contains(token)) {
	                double arg = stack.pop();
	                stack.push(applyFunction(token, arg));
	            }
	            else if ("+-*/^".contains(token)) {
	                double b = stack.pop();
	                double a = stack.pop();
	                stack.push(applyOperator(token, a, b));
	            }
	            else throw new IllegalArgumentException("Unknown token: " + token);
	        }

	        if (stack.size() != 1) throw new IllegalArgumentException("Invalid RPN expression");
	        return stack.pop();
	    }

	    // ---------- HELPER METHODS ----------
	    private static boolean isOperator(String t) { return "+-*/^".contains(t); }
	    private static boolean isValue(String t) { return t.matches("[0-9]+(?:\\.[0-9]+)?|[\\p{IsLatin}\\p{IsGreek}]|π|e"); }
	    private static int precedence(String op) {
	        switch (op) {
	            case "u-": return 4;
	            case "^": return 3;
	            case "*": case "/": return 2;
	            case "+": case "-": return 1;
	        }
	        return 0;
	    }
	    private static boolean isRightAssociative(String op) { return op.equals("^") || op.equals("u-"); }

	    private static double applyFunction(String func, double x) {
	        switch (func) {
	            case "sin": return Math.sin(x);
	            case "cos": return Math.cos(x);
	            case "tan": return Math.tan(x);
	            case "log": return Math.log10(x);
	            case "ln": return Math.log(x);
	            case "sqrt": return Math.sqrt(x);
	            case "exp": return Math.exp(x);
	        }
	        throw new IllegalArgumentException("Unknown function: " + func);
	    }

	    private static double applyOperator(String op, double a, double b) {
	        switch (op) {
	            case "+": return a + b;
	            case "-": return a - b;
	            case "*": return a * b;
	            case "/": return a / b;
	            case "^": return Math.pow(a, b);
	        }
	        throw new IllegalArgumentException("Unknown operator: " + op);
	    }

	    // ---------- SIMPLE USAGE ----------
	    public static void main(String[] args)
	    {
	    
	    	String expr = "-x + sin(π/2) - 0.5*y^2 + ln(e)";

	        // 1. Tokenize
	        List<String> tokens = tokenize(expr);
	        System.out.println("Tokens: " + tokens);

	        // 2. Convert to RPN
	        List<String> rpn = toRPN(tokens);
	        System.out.println("RPN: " + rpn);

	        // 3. Provide variable values
	        Map<String, Double> vars = new HashMap<>();
	        vars.put("x", 3.0);
	        vars.put("y", 2.0);

	        // 4. Evaluate
	        double result = evaluateRPN(rpn, vars);
	        System.out.println("Result: " + result);
	    }
	}