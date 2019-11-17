import org.junit.Test;

import junit.framework.TestCase;

public class TestJUnit extends TestCase {
	@Test
	public void testTrue() {
		assertTrue(true);
	}
	
	@Test
	public void testCheckMenuQuit() {
		ObjectCreator oc = new ObjectCreator();
		MenuSystem ms = new MenuSystem(oc);
		
		assertEquals(true, ms.checkQuit("s"));
		assertEquals(false, ms.checkQuit("yes"));
		assertEquals(false, ms.checkQuit("m"));
		assertEquals(false, ms.checkQuit("1"));
		assertEquals(false, ms.checkQuit("2"));
		assertEquals(false, ms.checkQuit("3"));
		assertEquals(false, ms.checkQuit("4"));
		assertEquals(false, ms.checkQuit("5"));
	}
	
	@Test
	public void testObjectCreationA() {
		ObjectCreator oc = new ObjectCreator();
		
		assertEquals(true, oc.createObjectA("1"));
		assertEquals(true, oc.createObjectA("4241"));
		assertEquals(false, oc.createObjectA("1.0"));
		assertEquals(false, oc.createObjectA("yes"));
		assertEquals(false, oc.createObjectA("no"));
		assertEquals(false, oc.createObjectA("q"));
	}
	
	@Test 
	public void testObjectCreationB() {
		ObjectCreator oc = new ObjectCreator();
		ObjectB b = oc.createObjectB();
		
		assertEquals(true, oc.setObjectBValue(b, 0));
		assertEquals(false, oc.setObjectBValue(b, 1));
		assertEquals(false, oc.setObjectBValue(b, 45));
		
		ObjectB b2 = oc.createObjectB();
		
		assertEquals(true, oc.setObjectBValue(b, 1));
	}
	
	@Test
	public void testObjectCreationC() {
		ObjectCreator oc = new ObjectCreator();
		ObjectC c = oc.createObjectC("2");
		
		assertEquals(true, oc.setObjectCValue(c, 0, "1"));
		assertEquals(false, oc.setObjectCValue(c, 7, "1"));
		assertEquals(false, oc.setObjectCValue(c, 0, "yes"));
	}
	
	@Test
	public void testObjectCreationD(){
		ObjectCreator oc = new ObjectCreator();
		ObjectD d = oc.createObjectD("2");
		
		assertEquals(true, oc.setObjectDValue(d, 0, "1"));
		assertEquals(true, oc.setObjectDValue(d, 1, "32"));
		assertEquals(false, oc.setObjectDValue(d, 1, "yes"));
	}
	
	@Test
	public void testObjectCreationE(){
		ObjectCreator oc = new ObjectCreator();
		ObjectE e = oc.createObjectE();
		
		assertEquals(true, oc.addObjectEValue(e, "1"));
		assertEquals(false, oc.addObjectEValue(e, "yes"));
	}
 
}