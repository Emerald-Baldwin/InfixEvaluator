/*Name: Emerald Baldwin
  Assignment: Lab 6
  Title: InfixEvaluator
  Course: CSCE 270
  Lab Section: 2
  Semester: Fall 2013
  Instructor: Kenneth Blaha
  Date: 11/01/13
  Sources consulted: Dr. Blaha, Data Structures: Abstraction and Design Using
  Java, by Koffman and Wolfgang
  Program description: This program reads in a string in infix format and calculates
  the result. This program can evaluate inputs with (, +, -, *, / ^, %, and )
*/

import java.util.EmptyStackException;
import java.util.Stack;

public class InfixEvaluator {
	// The operator stack
	private static Stack<Character> operatorStack;
	// The operand stack
	private static Stack<Integer> operandStack;
	// The operators
	private static final String OPERATORS = "()+-*/%^";
	// the precedence of the operators matches order in OPERATORS
	private static final int[] PRECEDENCE = {-1, -1, 1, 1, 2, 2, 2, 3};
	
	
	/**
	 * This method takes an expression and returns the result of the equation.
	 * If the equation is incorrect (does not have matched parenthesis, does 
	 * not have enough operators or enough operands) evaluate throws a 
	 * SyntaxErrorException
	 * @param expression The expression to be evaluated
	 * @return the integer result of the evaluated expression
	 * @throws SyntaxErrorException if there are unmatched parenthesis, too
	 * many operators, or if the operandStack has more than one element or no elements
	 * at the end
	 */
	public static int evaluate(String expression) throws SyntaxErrorException {
		operandStack = new Stack<Integer>();
		operatorStack = new Stack<Character>();
		String tokens[] = expression.split("\\s+");
		
		for (String j : tokens) {
			char op = j.charAt(0);
			if (isOperator(op)) {
				processOperator(op);
			} else if (Character.isDigit(op)) {
				operandStack.push(Integer.parseInt(j));
			} else {
				throw new SyntaxErrorException("Unexpected Character Encountered: " + op);
			}
		}
		
		while (operatorStack.size() != 0) {
			processBoth(operatorStack.pop());
		}
		
		if (operandStack.size() == 1) {
			return operandStack.pop();
		} else {
			throw new SyntaxErrorException("No operands or too many operands in operator stack");
		}
	}
	
	
	/**
	 * Private method that determines if a character is an operator
	 * @param op the operator to check
	 * @return true if op is an operator, false otherwise
	 */
	private static boolean isOperator(char op) {
		return OPERATORS.indexOf(op) != -1;
	}
	
	
	/**
	 * This method takes an operator op and determines what to do with the operator.
	 * This method throws a SyntaxErrorException if the operator is a ) with no matching 
	 * (
	 * @param op the operator to add to the operatorStack
	 * @throws SyntaxErrorException if there is a closing parenthesis lacking a matching parenthesis
	 */
	private static void processOperator(char op) throws SyntaxErrorException {
		// if the operator stack contains no elements, push op onto the stack
		if (operatorStack.empty() && op != ')') {
			operatorStack.push(op);
		} else {
			// check if op is )
			if (op == ')') {
				if (operatorStack.empty()) {
					throw new SyntaxErrorException("Unmatched parenthesis at beginning of expression");
				}
				while (!operatorStack.empty() && operatorStack.peek() != '(') {
					processBoth(operatorStack.pop());
				}
				try {
					operatorStack.pop();
				} catch(Exception e) {
					throw new SyntaxErrorException("Lacking matching beginning parenthesis");
				}
				
			}
			else if (op == '(') {
				operatorStack.push(op);
			}
			// if the op has higher precedence than anything on the stack, we push it on
			else if (precedence(op) > precedence(operatorStack.peek())) {
				operatorStack.push(op);
			} else {
				// pop all stacked operators with equal or higher precedence than op
				while (!operatorStack.empty() && precedence(op) <= precedence(operatorStack.peek())) {
					// use the popped off operator and process it
					processBoth(operatorStack.pop());
				}
				operatorStack.push(op);
			}
		}
	}
	
	
	/**
	 * private method that determines the precedence of the passed in
	 * operator, op. 
	 * @param op the operator to find the precedence of
	 * @return the value at the index of the operator in PRECEDENCE
	 */
	private static int precedence(char op) {
		return PRECEDENCE[OPERATORS.indexOf(op)];
	}
	
	
	/**
	 * private method that does mathematical operations on the top two operands
	 * in the operandStack and the top operator in the operatorStack
	 * pushes the final result into the operandStack. Throws a SyntaxErrorException
	 * if the operandStack does not contain enough operands
	 * @param op the operator to use in the evaluation
	 * @throws SyntaxErrorException if the operandStack does not contain enough operands
	 */
	private static void processBoth(char op) throws SyntaxErrorException {
		try {
			// pop off the top two operands on the operandStack
			int rhs = operandStack.pop();
			int lhs = operandStack.pop();
			int result = 0;
			
			// process the top two operands
			switch (op) {
			case '+':
				result = lhs + rhs;
				break;
			case '-':
				result = lhs - rhs;
				break;
			case '*':
				result = lhs * rhs;
				break;
			case '/':
				result = lhs / rhs;
				break;
			case '^':
				result = (int) Math.pow(lhs, rhs);
				break;
			case '%':
				result = lhs % rhs;
				break;
			}
			
			// place the result back on the operandStack
			operandStack.push(result);
			
		} catch(EmptyStackException e) {
			throw new SyntaxErrorException("Not enough operands");
		}
	}
}
