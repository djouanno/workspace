package system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PhoneArrayListTest {

	protected PhonyArrayList<String> list;

	@Before
	public void init() {
		list = new PhonyArrayList<String>();
	}

	@After
	public void destroy() {
		list = null;
	}

	/**
	 * Test de la methode add, ajout d'un element dans une liste vide.
	 * 
	 * @see system.PhoneArrayList#add(Object)
	 * @type Functional
	 * @input "coucou"
	 * @oracle Must return "true"
	 * @passed yes
	 */
	@Test
	public void addTest() {
		final String s = "coucou";
		list.add(s);
		assertTrue("The list must contains 1 element", list.size() == 1);
		assertEquals("The only element must be " + s, list.get(0), s);
	}

	/**
	 * Test de la méthode get, recuperation d'une valeur.
	 * 
	 * @see system.PhoneArrayList#get(int)
	 * @type Functional
	 * @input 1
	 * @oracle Must return s2 -> "hello"
	 * @passed yes
	 */
	@Test
	public void testGet() {
		final String s2 = "hello";
		list.add("coucou");
		list.add(s2);
		assertTrue("The list must contains 2 elements", list.size() == 2);
		assertEquals("The second element must be " + s2, list.get(1), s2);
	}

	/**
	 * Test de l'exception lever par la methode get pour un index non inclut dans les bornes de la liste. 
	 * 
	 * @see system.PhoneArrayList#get(int)
	 * @type Functional
	 * @input 0
	 * @oracle Must return IndexOutOfBoundsException
	 * @passed yes
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetWithException() {
		list.get(0);
	}

	/**
	 * Test de la methode set, modification d'une valeur à l'index 0.
	 * 
	 * @see system.PhoneArrayList#set(int, Object)
	 * @type Functional
	 * @input 0, s2
	 * @oracle Must return s2
	 * @passed yes
	 */
	@Test
	public void testSet() {
		final String s1 = "coucou", s2 = "hello";
		
		list.add(s1);
		list.set(0, s2);
		assertTrue("", list.get(0).equals(s2));
	}
	
	/**
	 * Test de l'exception lever par la methode set lors d'un depassement des bornes du tableau.
	 * 
	 * @see system.PhoneArrayList#set(int, Object)
	 * @type Functional
	 * @input 2, s2
	 * @oracle Must return IndexOutOfBoundsException
	 * @passed yes
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testSetWithIndexOutOfBoundsException() {
		final String s1 = "coucou", s2 = "hello";
		
		list.add(s1);
		list.set(2, s2);
	}
	
	/**
	 * Test de la méthode add, ajout d'un element s1 à l'index 0.
	 * 
	 * @see system.PhoneArrayList#add(int, Object)
	 * @type Functional
	 * @input 0, s1
	 * @oracle list.size == 2 && list.get(0) == s1
	 * @passed yes
	 */
	@Test
	public void testAddAt() {
		final String s1 = "hello", 
				s2 = "coucou";
		list.add(s2);
		list.add(0, s1);
		assertTrue("The list must contains 2 elements", list.size() == 2);
		assertEquals("The first element must be " + s1, list.get(0), s1);
	}

	/**
	 * Test de l'exception lever par la méthode add lorsque l'index se retrouve hors des bornes du tableau.
	 * 
	 * @see system.PhoneArrayList#add(int, Object)
	 * @type Functional
	 * @input 10, "coucou"
	 * @oracle Must return IndexOutOfBoundsException
	 * @passed yes
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddAtWithException() {
		list.add(10, new String("coucou"));
	}

	/**
	 * Test de la methode addAll, ajout d'une collection d'element.
	 * 
	 * @see system.PhoneArrayList#addAll(int, Collection<?>)
	 * @type Functional
	 * @input 0, c (ArrayList --> {s1, s2, s3})
	 * @oracle Must return list.size == 4 && list.get(0) == s1 && list.get(1) == s2 && list.get(2) == s3 
	 * @passed yes
	 */
	@Test
	public void testAddAllAt() {
		list.add("Toto");
		final List<String> c = new ArrayList<String>();
		final String s1 = "coucou", s2 = "hello", s3 = "hallo";
		c.add(s1);
		c.add(s2);
		c.add(s3);
		list.addAll(0, c);
		assertTrue("The list must contains " + c.size() + 1 + " elements",
				list.size() == c.size() + 1);
		assertEquals("The first element must be " + s1, list.get(0), s1);
		assertEquals("The second element must be " + s2, list.get(1), s2);
		assertEquals("The third element must be " + s3, list.get(2), s3);
	}

	/**
	 * Test de l'exception lever par la méthode addAll lorsque l'index ne se trouve pas dans les bornes du tableau.
	 * 
	 * @see system.PhoneArrayList#addAll(int, Collection)
	 * @type Functional
	 * @input -4, c
	 * @oracle Must return IndexOutOfBoundsException
	 * @passed yes
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void testAddAllAtWithException() {
		list.add("Toto");
		final List<String> c = new ArrayList<String>();
		final String s1 = "coucou", s2 = "hello", s3 = "hallo";
		c.add(s1);
		c.add(s2);
		c.add(s3);
		list.addAll(-4, c);
	}

	/**
	 * Test de la méthode clear, suppression des valueurs contenus la liste.
	 * 
	 * @see system.PhoneArrayList#clear()
	 * @type Functional
	 * @input 
	 * @oracle Must list.size() == 0
	 * @passed yes
	 */
	@Test
	public void testClear() {
		list.add("Coucou");
		list.clear();
		assertTrue("The list must contains 0 element", list.size() == 0);
	}

	/**
	 * Test de la méthode contains, retourne vrai si l'objet donné en parametre est contenu dans la liste.
	 * On ajoute 2 valeurs à la liste s1 et s2 puis on va regarder si elle sont contenues dans la liste avec la méthode contain
	 * 
	 * @see system.PhoneArrayList#contains()
	 * @type Functional
	 * @input s1 s2
	 * @oracle Must return true
	 * @passed yes
	 */
	@Test
	public void testContains() {
		final String s1 = "toto", s2 = "coucou";

		list.add(s2);
		list.add(s1);

		assertTrue("The list must contains " + s2, list.contains(s2));
		assertTrue("The list must contains " + s1, list.contains(s1));
	}

	/**
	 * Test de la méthode contains, on va tester si on detecte la non existance d'un element dans la liste.
	 * 
	 * @see system.PhoneArrayList#contains()
	 * @type Functional
	 * @input s2
	 * @oracle Must return false
	 * @passed yes
	 */
	@Test
	public void testNoContainsElement() {
		final String s1 = "toto", s2 = "coucou";

		list.add(s1);
		assertFalse("The list must not contains " + s2, list.contains(s2));

	}

	/**
	 * Test de la méthode indexOf, on va verifier si cette méthode retourne bien la bonne position des elements.
	 * 
	 * @see system.PhoneArrayList#indexOf()
	 * @type Functional
	 * @input s1 s2 s3
	 * @oracle Must return 0 1 2
	 * @passed yes
	 */
	@Test
	public void testIndexOf() {
		final String s1 = "toto", s2 = "coucou", s3 = null;

		list.add(s1);
		list.add(s2);
		list.add(s3);
		list.add(s1);

		assertTrue("The element " + s1 + " must be in the index 0",
				list.indexOf(s1) == 0);
		assertTrue("The element " + s2 + " must be in the index 1",
				list.indexOf(s2) == 1);
		assertTrue("The element " + s3 + " must be in the index 2",
				list.indexOf(s3) == 2);

	}

	/**
	 * Test de la methode indexOf, on va vérifier que cette méthode nous retourne bien -1 quand l'element n'existe pas
	 * 
	 * @see system.PhoneArrayList#contains()
	 * @type Functional
	 * @input s2
	 * @oracle Must return -1
	 * @passed yes
	 */
	@Test
	public void testNoIndexOfElement() {
		final String s1 = "toto";
		final String s2 = "coucou";

		list.add(s1);
		assertTrue("The index of the element " + s2 + " in this list is -1.",
				list.indexOf(s2) == -1);
	}

	/**
	 * Test de la méthode empty, on va vérifier que la liste n'est pas vite quand on ajoute un element.
	 * 
	 * @see system.PhoneArrayList#isEmpty()
	 * @type Functional
	 * @input s2
	 * @oracle Must return false
	 * @passed yes
	 */
	@Test
	public void testIsNotEmpty() {
		list.add("camion");
		assertFalse("The list must not be empty", list.isEmpty());
	}
	
	/**
	 * Test de la méthode empty, on va vérifier que la liste est vide quand on n'ajoute pas d'element.
	 * 
	 * @see system.PhoneArrayList#isEmpty()
	 * @type Functional
	 * @input 
	 * @oracle Must return true
	 * @passed yes
	 */	
	@Test
	public void testIsEmpty() {
		assertTrue("The list must be empty", list.isEmpty());
	}

	/**
	 * Test de la methode remove, on verifier que la méthode remove arrive à supprimer un element de la liste.
	 * 
	 * @see system.PhoneArrayList#remove(Object)
	 * @type Functional
	 * @input s1
	 * @oracle Must list.contains(s1) == false
	 * @passed yes
	 */
	@Test
	public void testRemove() {
		final String s1 = "toto";

		list.add(s1);
		list.remove(s1);
		assertFalse("The element " + s1 + " must be remove.", list.contains(s1));
		assertTrue("The size of this list must be 0.", list.size() == 0);
	}
	
	/**
	 * Test de la methode remove, on verifier que la méthode remove arrive à supprimer un element null de la liste.
	 * 
	 * @see system.PhoneArrayList#remove(Object)
	 * @type Functional
	 * @input s1
	 * @oracle Must list.contains(s1) == false && list.size() == 0
	 * @passed yes
	 */
	@Test
	public void testRemoveNullElement() {
		final String s1 = null;

		list.add(s1);
		list.remove(s1);
		assertFalse("The element " + s1 + " must be remove.", list.contains(s1));
		assertTrue("The size of this list must be 0.", list.size() == 0);
	}

	/**
	 * Test de la methode remove, on verifier que la méthode remove ne supprime rien lorsque lui passe un objet qui n'est pas dans liste en parametre.
	 * 
	 * @see system.PhoneArrayList#remove(Object)
	 * @type Functional
	 * @input s1
	 * @oracle Must list.size() == 1
	 * @passed yes
	 */
	@Test
	public void testNoRemove() {
		final String s1 = "toto";
		final String s2 = "coucou";

		list.add(s1);
		assertFalse("Must be return false, because there isn't the element "
				+ s1 + " in this list.", list.remove(s2));
		assertTrue("The size of this list must be 1.", list.size() == 1);
	}

	/**
	 * Test de la methode removeAll, on verifier que la methode arrive à supprimer les valeurs dans liste qui lui on été fournit dans la collection passé en parametre.
	 * 
	 * @see system.PhoneArrayList#removeAll(Collection)
	 * @type Functional
	 * @input collection
	 * @oracle Must list.contains(s1) == false && list.contains(s2) == false && list.contains(s3) == false && list.size() == 1
	 * @passed yes
	 */
	@Test
	public void testRemoveAll() {
		final String s1 = "toto";
		final String s2 = "coucou";
		final String s3 = "camion";

		Collection<String> collection = new ArrayList<String>();
		collection.add(s1);
		collection.add(s2);

		list.add(s1);
		list.add(s2);
		list.add(s1);
		list.add(s3);
		list.removeAll(collection);
		assertTrue("The size of the list must be 1.", list.size() == 1);
		assertTrue("The list must contain " + s3, list.contains(s3));
		assertFalse("The list must not contain " + s1, list.contains(s1));
		assertFalse("The list must not contain " + s2, list.contains(s2));
	}

	/**
	 * Test de l'exception lever par la méthode removeAll lorsqu'un objet de la collection n'est pas du meme type que celi de la liste.
	 * 
	 * @see system.PhoneArrayList#removeAll(Collection)
	 * @type Functional
	 * @input collection
	 * @oracle Must return ClassCastException
	 * @passed yes
	 */
	@Test(expected = ClassCastException.class)
	public void testRemoveAllWithClassCastException() {
		Collection<Date> collection = new LinkedList<Date>();
		collection.add(new Date());
		list.add("coucou");
		list.removeAll(collection);
	}

	/**
	 * Test de l'exception lever par la méthode removeAll lorsque on lui passe une collection null en parametre.
	 * 
	 * @see system.PhoneArrayList#removeAll(Collection)
	 * @type Functional
	 * @input null
	 * @oracle Must return NullPointerException
	 * @passed yes
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveAllWithNullPointerException() {
		list.add("element");
		list.removeAll(null);
	}
	
	/**
	 * Test du contructeur, on verifie que lorsqu'on lui passe une colelction d'element il l'ajoute bien à liste.
	 * 
	 * @see system.PhoneArrayList#PhonyArrayList(Collection)
	 * @type Functional
	 * @input null
	 * @oracle Must listCons.size() == 2 && listCons.contains(s1) == true && listCons.contains(s2) == true
	 * @passed yes
	 */
	@Test
	public void testConstructor() {
		final String s1 = "toto", s2 = "coucou";
		
		Collection<String> collection = new LinkedList<>();
		collection.add(s1);
		collection.add(s2);
		
		PhonyArrayList<String> listCons = new PhonyArrayList<>(collection);
		assertTrue("The list must constain 3 elements.", listCons.size() == 2);
		assertTrue("The list must constain " + s1, listCons.contains(s1));
		assertTrue("The list must constain " + s2, listCons.contains(s2));
		
	}

	/**
	 * Test de l'exception lever par le constructeur lorsqu'on lui passe une collection null.
	 * 
	 * @see system.PhoneArrayList#PhonyArrayList(Collection)
	 * @type Functional
	 * @input null
	 * @oracle Must return NullPointerException
	 * @passed yes
	 */
	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullPointerException() {
		new PhonyArrayList<>(null);
	}
}