package io.egen.rentalflix;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import io.egen.rentalflix.IFlix;
import io.egen.rentalflix.Movie;

/**
 * Service implementing IFlix interface
 * You can use any Java collection type to store movies
 */
public class MovieService implements IFlix {

	/** 
	 * Hash map to store movies available for rent
	 * Mapping is from the movie Id to the movie object
	 */
	Map<Integer, Movie> availableMoviesList;
	/** 
	 * Hash map to store the movies that have been rented
	 * Mapping is from the user to the list of movies rented by the user
	 */
	Map<String, List<Movie>> rentedMoviesList;
	
	/**
	 * Constructor to create the collections to store available and rented movies and allocate memory to them.
	 */
	public MovieService () {
		availableMoviesList = new HashMap<Integer, Movie>();
		rentedMoviesList = new HashMap<String, List<Movie>>();
	}
	
	/**
	 * This function returns the list of all available movies for rent.
	 */
	public List<Movie> findAll () {
		List<Movie> resultSet = new ArrayList<Movie>();
		Iterator<Map.Entry<Integer, Movie>> iter = availableMoviesList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry<Integer, Movie> movieObject = (Map.Entry<Integer, Movie>)iter.next();
			resultSet.add(((Movie)movieObject.getValue()));
		}
		return resultSet;
	}
	
	/**
	 * This function iterates through the list of available movies and finds movies with the title = name and returns this result.
	 */
	public List<Movie> findByName (String name) {
		List<Movie> resultSet = new ArrayList<Movie>();
		Iterator<Map.Entry<Integer, Movie>> iter = availableMoviesList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry<Integer, Movie> movieObject = (Map.Entry<Integer, Movie>)iter.next();
			if(((Movie)movieObject.getValue()).getTitle().equals(name)) {
				resultSet.add(((Movie)movieObject.getValue()));
			}
		}
		return resultSet;
	}
	
	/**
	 * This function creates a new object of type Movie and adds it to the list of available movies if it is not already present.
	 */
	public Movie create (Movie movie) {
		int id = movie.getId();
		if(!availableMoviesList.containsKey(id)) {
			availableMoviesList.put(id, movie);
		}	
		return movie;
	}
	
	/**
	 * ASSUMPTION: The function will only search for the movie in the list of movies available to be rented.
	 * If the movie is not present in the list of available movies, the function will throw an IllegalArgumentException,
	 * else, it'll delete the existing movie object and update it with the new value.
	 */
	public Movie update (Movie movie)throws IllegalArgumentException {
		int id = movie.getId();
		if(!availableMoviesList.containsKey(id)) {
			String movieName = movie.getTitle();
			throw new IllegalArgumentException("Trying to update the movie:" + movieName + ".\nMovie is not available to be updated!");
		}
		availableMoviesList.remove(id);
		availableMoviesList.put(id, movie);
		return movie;
	}
	
	/**
	 * ASSUMPTION: The function will only search for the movie in the list of movies available to be rented.
	 * If the movie with requested id is not present in the list of available movies to be rented, the function will throw an IllegalArgumentException,
	 * else, it'll iterate through the available movies list, delete the movie from the list and return it.
	 */
	public Movie delete (int id)throws IllegalArgumentException {
		if(!availableMoviesList.containsKey(id)) {
			throw new IllegalArgumentException("Movie with id = " + id + " is not available to be deleted!");
		}
		Iterator<Map.Entry<Integer, Movie>> iter = availableMoviesList.entrySet().iterator();
		Movie deletedMovie = null;
		while(iter.hasNext()) {
			Map.Entry<Integer, Movie> movieObject = (Map.Entry<Integer, Movie>)iter.next();
			if(((Movie)movieObject.getValue()).getId() == id) {
				deletedMovie = (Movie)movieObject.getValue();
				availableMoviesList.remove(id);
			}
		}
		return deletedMovie;
	}
	
	public boolean rentMovie (int movieId, String user)throws IllegalArgumentException {
		/**
		 * If the movie is not available to be rented, the code snippet will try to find if it is present in the list list of rented movies.
		 * If the movie has already been rented, the function will throw an IllegalArgumentException.
		 * If the movie is not present in either of the lists (i.e., when the control will come out of while loop), the function will return false.
		 */
		if(!availableMoviesList.containsKey(movieId)) {
			Iterator<Map.Entry<String, List<Movie>>> iterUser = rentedMoviesList.entrySet().iterator();
			while(iterUser.hasNext()) {
				Map.Entry<String, List<Movie>> userMovieList = (Map.Entry<String, List<Movie>>)iterUser.next();
				List<Movie> movieList = userMovieList.getValue();
				Iterator<Movie> iterMovie = movieList.iterator();
				while(iterMovie.hasNext()) {
					if(((Movie)iterMovie.next()).getId() == movieId) {
						throw new IllegalArgumentException("Movie has already been rented!");
					}
				}
			}
			return false;
		}	
		/**
		 * If the movie is available to be rented, it is removed from the available list of movies.
		 * If the user is not already present in the rentedMoviesList, we create a new mapping in the list from that user to the movie,
		 * else, we add the movie to the list of movies already rented by user.
		 * It will return true.
		 */
		else {
			Movie movieToBeRented = availableMoviesList.get(movieId);
			availableMoviesList.remove(movieId);
			List<Movie> moviesRentedByUser = null;
			if(rentedMoviesList.containsKey(user)) {
				moviesRentedByUser = rentedMoviesList.get(user);
			}
			else {
				moviesRentedByUser = new ArrayList<Movie>();
				rentedMoviesList.put(user, moviesRentedByUser);
			}
			moviesRentedByUser.add(movieToBeRented);
			return true;
		}	
	}
}
