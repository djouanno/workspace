package system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PhoneArrayListTest
{

    protected PhonyArrayList<String> list;

    @Before
    public void init() {
        list = new PhonyArrayList<String>();
    }

    @After
    public void destroy() {
        list = null;
    }

    @Test
    public void addTest() {
        final String s = "coucou";
        list.add(s);
        assertTrue("The list must contains 1 element", list.size() == 1);
        assertEquals("The only element must be " + s, list.get(0), s);
    }

    @Test
    public void testGet() {
        list.add("coucou");
        final String s2 = "hello";
        list.add(s2);
        assertTrue("The list must contains 2 elements", list.size() == 2);
        assertEquals("The second element must be " + s2, list.get(1), s2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetWithException() {
        list.get(0);
    }

    @Test
    public void testAddAt() {
        final String s = new String("hello");
        list.add("coucou");
        list.add(0, s);
        assertTrue("The list must contains 2 elements", list.size() == 2);
        assertEquals("The first element must be " + s, list.get(0), s);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtWithException() {
        list.add(10, new String("coucou"));
    }

    @Test
    public void testAddAllAt() {
        list.add("Toto");
        final List<String> c = new ArrayList<String>();
        final String s1 = "coucou", s2 = "hello", s3 = "hallo";
        c.add(s1);
        c.add(s2);
        c.add(s3);
        list.addAll(0, c);
        assertTrue("The list must contains " + c.size() + 1 + " elements", list.size() == c.size() + 1);
        assertEquals("The first element must be " + s1, list.get(0), s1);
        assertEquals("The second element must be " + s2, list.get(1), s2);
        assertEquals("The third element must be " + s3, list.get(2), s3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAllAtWithException() {
        list.add("Toto");
        final List<String> c = new ArrayList<String>();
        final String s1 = "coucou", s2 = "hello", s3 = "hallo";
        c.add(s1);
        c.add(s2);
        c.add(s3);
        list.addAll(945454, c);
    }

    @Test
    public void testClear() {
        list.add("Coucou");
        list.clear();
        assertTrue("The list must contains 0 element", list.size() == 0);
    }

    @Test
    public void testContains() {
        final String s1 = "toto";

        list.add("Coucou");
        list.add(s1);
        assertTrue("The list must contains " + s1, list.contains(s1));
        assertFalse("The list must not contains camion", list.contains("camion"));
    }

}
