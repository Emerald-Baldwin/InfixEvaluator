// Small unit test for InfixEvaluator.java

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class InfixEvaluatorTest {

	@Before
	public void setUp() throws Exception {
	}

	/* The five tests below run correctly and do not throw any exceptions */
	@Test
	public void testExpression01() throws SyntaxErrorException {
	    assertEquals( 1, InfixEvaluator.evaluate("7 - 3 * 2") );
	}
	
	@Test
	public void testExpression02() throws SyntaxErrorException {
	    assertEquals( 8, InfixEvaluator.evaluate("( 7 - 3 ) * 2") );
	}
	
	@Test
	public void testExpression03() throws SyntaxErrorException {
	    assertEquals( 9, InfixEvaluator.evaluate("( 18 % 5 ) ^ 2") );
	}
	
	@Test
	public void testExpression04() throws SyntaxErrorException {
	    assertEquals( -288, InfixEvaluator.evaluate("( 1 + 15 ) * ( 3 * ( 4 - ( 5 + 10 / 2 ) ) )") );
	}
	
	@Test
	public void testExpression05() throws SyntaxErrorException {
	    assertEquals( 2, InfixEvaluator.evaluate("627 % ( ( 1 + 4 ) * 5 ^ 2 )") );
	}
	
	/* The three tests below are meant to throw exceptions */
	@Test(expected = SyntaxErrorException.class)
	public void testExpression06() throws SyntaxErrorException {
	    assertEquals( -288, InfixEvaluator.evaluate(")") );
	}
	
	@Test (expected = SyntaxErrorException.class)
	public void testExpression07() throws SyntaxErrorException {
	    assertEquals( -288, InfixEvaluator.evaluate("1 + 2 )") );
	}
	
	@Test (expected = SyntaxErrorException.class)
	public void testExpression08() throws SyntaxErrorException {
	    assertEquals( -288, InfixEvaluator.evaluate("1 * - %") );
	}
}
