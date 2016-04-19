package io.egen.rentalflix;

import java.util.List;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import io.egen.rentalflix.Movie;
import io.egen.rentalflix.MovieService;

/**
 * JUnit test cases for MovieService
 */
public class MovieServiceTest {

	private MovieService service1;
	private Movie movieObjectA;
	private Movie movieObjectB;
	private Movie movieObjectC;
	private Movie movieObjectD;
	private Movie movieObjectE;
	private Movie movieObjectF;
	private Movie movieObjectG;
	
	@BeforeClass
	public static void beforeClass () {
		System.out.println("Beginning to run test cases ...");
	}
	
	@Before
	public void before () {
		service1 = new MovieService();
		
		movieObjectA = new Movie();
		movieObjectA.setId(1);
		movieObjectA.setTitle("The Shawshank Redemption");
		movieObjectA.setYear(1994);
		movieObjectA.setLanguage("English");
		
		movieObjectB = new Movie();
		movieObjectB.setId(2);
		movieObjectB.setTitle("Airlift");
		movieObjectB.setYear(2016);
		movieObjectB.setLanguage("Hindi");
		
		movieObjectC = new Movie();
		movieObjectC.setId(1);
		movieObjectC.setTitle("Steve Jobs");
		movieObjectC.setYear(2015);
		movieObjectC.setLanguage("English");
		
		movieObjectD = new Movie();
		movieObjectD.setId(3);
		movieObjectD.setTitle("Steve Jobs");
		movieObjectD.setYear(2015);
		movieObjectD.setLanguage("English");
		
		movieObjectE = new Movie();
		movieObjectE.setId(4);
		movieObjectE.setTitle("The Walk");
		movieObjectE.setYear(2015);
		movieObjectE.setLanguage("English");
		
		movieObjectF = new Movie();
		movieObjectF.setId(5);
		movieObjectF.setTitle("The Lord of the Rings");
		movieObjectF.setYear(2001);
		movieObjectF.setLanguage("English");
		
		movieObjectG = new Movie();
		movieObjectG.setId(5);
		movieObjectG.setTitle("The Lord of the Rings: The Fellowship of the Ring");
		movieObjectG.setYear(2001);
		movieObjectG.setLanguage("English, Sindarin");
	}
	
	@Test
	public void testFindAll1 () {
		//case 1 - comparing empty objects
		List<Movie> actual = service1.findAll();
		List<Movie> expected = new ArrayList<Movie>();
		Assert.assertThat(actual, is(expected));
		
		//case 2 - positive test case (comparing equal lists)
		service1.create(movieObjectA);
		service1.create(movieObjectB);
		actual = service1.findAll();
		expected.add(movieObjectA);
		expected.add(movieObjectB);
		Assert.assertThat(actual, is(expected));
		
		//case 3 - negative test case
		//since the movieId of movieObject3 is 1, it won't be added to the list of available movies
		//as a movie object with id = 1 already exists in the list
		service1.create(movieObjectC);
		actual = service1.findAll();
		expected.add(movieObjectC);
		Assert.assertThat(actual, is(not(expected)));
		
		//case 4 - positive test case (comparing equal lists)
		service1.create(movieObjectD);
		actual = service1.findAll();
		List<Movie> expected2 = new ArrayList<Movie>();
		expected2.add(movieObjectA);
		expected2.add(movieObjectB);
		expected2.add(movieObjectD);
		Assert.assertThat(actual, is(expected2));
	}
	
	@Test
	public void testFindByName1 () {
		//case 1 - comparing empty objects
		List<Movie> actual = service1.findByName("The Shawshank Redemption");
		List<Movie> expected = new ArrayList<Movie>();
		Assert.assertThat(actual, is(expected));
		
		//case 2 - positive test case (comparing equal lists)
		service1.create(movieObjectA);
		actual = service1.findByName("The Shawshank Redemption");
		expected = new ArrayList<Movie>();
		expected.add(movieObjectA);
		Assert.assertThat(actual, is(expected));
		
		//case 3 - negative test case (actual should be null while expected contains the movie "The Shawshank Redemption")
		actual.clear();
		actual = service1.findByName("Guardians of the Galaxy");
		Assert.assertThat(actual, is(not(expected)));
		
		//case 4 - positive test case (both actual and expected are empty)
		expected.clear();
		Assert.assertThat(actual, is(expected));
	}
	
	@Test
	public void testCreate1 () {
		//case 1 - positive test case
		Movie actual1 = service1.create(movieObjectA);
		Assert.assertEquals(movieObjectA, actual1);
		
		//case 2 - positive test case
		Movie actual2 = service1.create(movieObjectB);
		Assert.assertEquals(movieObjectB, actual2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdate1 () {
		//case 1 - negative test case (when the movie to be updated is not available)
		service1.update(movieObjectE);
	}
	
	@Test
	public void testUpdate2 () {
		//case 2 - positive test case (when the movie to be updated is available)
		service1.create(movieObjectA);
		service1.create(movieObjectB);
		service1.create(movieObjectD);
		service1.create(movieObjectE);
		service1.create(movieObjectF);
		Movie returnedObject = service1.update(movieObjectG);
		Assert.assertEquals(movieObjectG, returnedObject);
		
		List<Movie> actual = service1.findAll();
		List<Movie> expected = new ArrayList<Movie>();
		expected.add(movieObjectA);
		expected.add(movieObjectB);
		expected.add(movieObjectD);
		expected.add(movieObjectE);
		expected.add(movieObjectG);
		Assert.assertThat(actual, is(expected));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDelete1 () {
		//case 1 - negative test case (when the movie to be deleted is not available)
		service1.delete(11);
	}
	
	@Test
	public void testDelete2 () {
		//case 2 - positive test case (when the movie to be deleted is available)
		service1.create(movieObjectA);
		service1.create(movieObjectB);
		service1.create(movieObjectD);
		service1.create(movieObjectE);
		service1.create(movieObjectG);
		Movie returnedObject = service1.delete(5);
		Assert.assertEquals(movieObjectG, returnedObject);
		List<Movie> actual = service1.findAll();
		List<Movie> expected = new ArrayList<Movie>();
		expected.add(movieObjectA);
		expected.add(movieObjectB);
		expected.add(movieObjectD);
		expected.add(movieObjectE);
		Assert.assertThat(actual, is(expected));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRentMovie1 () {
		//case 1 - negative test case (when the movie has already been rented)
		service1.create(movieObjectA);
		service1.create(movieObjectB);
		service1.create(movieObjectD);
		service1.rentMovie (3, "Mike Minor");
		service1.rentMovie (3, "Yijia Ma");
	}
		
	@Test
	public void testRentMovie2 () {
		//case 2 - positive test case (when the movie to be rented is available)
		service1.create(movieObjectA);
		service1.create(movieObjectB);
		service1.create(movieObjectD);
		service1.create(movieObjectE);
		service1.create(movieObjectG);
		boolean actual = service1.rentMovie (4, "Mike Minor");
		Assert.assertTrue(actual);
		
		//case 2 - positive test case (when the movie to be rented is available)
		boolean actual2 = service1.rentMovie (2, "Yijia Ma");
		Assert.assertTrue(actual2);
		
		//case 3 - negative test case (when the movie to be rented is not available)
		boolean actual3 = service1.rentMovie (12, "Yijia Ma");
		Assert.assertFalse(actual3);
		
		//case 3 - negative test case (when the movie to be rented is not available)
		boolean actual4 = service1.rentMovie (23, "Yang Jihyun");
		Assert.assertFalse(actual4);
	}
	
	@AfterClass
	public static void afterClass () {
		System.out.println("All test cases have executed successfully!");
	}
}
