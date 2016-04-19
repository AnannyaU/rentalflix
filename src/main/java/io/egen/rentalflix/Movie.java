package io.egen.rentalflix;

/**
 * Entity representing a movie.
 * Fields: id, title, year, language
 */
public class Movie {
	//POJO IMPLEMENTATION GOES HERE
	private int id;
	private String title;
	private int year;
	private String language;
	
	public Movie () {
		id = 0;
		title = "";
		year = 0;
		language = "";
	}
	
	public Movie (Movie movie) {
		id = movie.id;
		title = movie.title;
		year = movie.year;
		language = movie.language;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public int getId () {
		return id;
	}
	
	public void setTitle (String title) {
		this.title = title;
	}
	
	public String getTitle () {
		return title;
	}
	
	public void setYear (int year) {
		this.year = year;
	}
	
	public int getYear () {
		return year;
	}
	
	public void setLanguage (String language) {
		this.language = language;
	}
	
	public String getLanguage () {
		return language;
	}
	
}
