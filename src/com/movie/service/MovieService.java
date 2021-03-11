package com.movie.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.movie.bean.Movie;

import com.movie.dao.MovieDao;
import com.movie.enums.Category;
import com.movie.enums.Language;


public class MovieService {
	
	static MovieDao movieDao = new MovieDao();
	List<Movie> movieList = new ArrayList<>();
	
	public void addMovie(Movie movie, List<Movie> movies) {
		movies.add(movie);
		movieList = movies;
	}
	
	public void serializeMovies(List<Movie> movies, String fileName) {
		
		try {
			File f = new File(fileName);
			if(!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream file = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(movies);
			out.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Movie> deserializeMovie(String fileName) {
		List<Movie> movies = new ArrayList<>();
		try {
			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(file);
			
			movies = (List<Movie>) in.readObject();
			in.close();
			file.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return movies;
	}
	
	public List<Movie> getMoviesReleasedInYear(int year) {
		List<Movie> movies = movieDao.getAllMovies();
		List<Movie> filteredMovies = new ArrayList<Movie>();
		DateFormat date = new SimpleDateFormat("yyyy");
		movies.forEach(movie -> {	
			if(Integer.parseInt(date.format(movie.getReleaseDate())) == year) {
				filteredMovies.add(movie);
			}
		});
		return filteredMovies;
	}
	
	public List<Movie> getMoviesByActor(String ...actors) {
		List<Movie> movies = movieDao.getAllMovies();
		List<Movie> filteredMovies = new ArrayList<Movie>();
		for(Movie m : movies) {
			for(String cast : m.getCasting()) {
				if(Arrays.asList(actors).contains(cast)) {
					filteredMovies.add(m);
				}
			}
		}
		return filteredMovies;
	}
	
	public void updateRatings(Movie movie, double rating, List<Movie> movies) {
		for(Movie m : movies) {
			if(m.getMovieId() == movie.getMovieId()) {
				m.setRating(rating);
			}
		}
		movieList = movies;
	}
	
	public void updateBusiness(Movie movie, double amount, List<Movie> movies) {
		for(Movie m : movies) {
			if(m.getMovieId() == movie.getMovieId()) {
				m.setTotalBusinessDone(amount);
			}
		}
		movieList = movies;
	}
	
	Map<Language,Set<Movie>> businessDone(double amount) {
		Set<Movie> set = new HashSet<>();
		Map<Language, Set<Movie>> map = new HashMap<>();
		for(Movie m : movieList) {
			if(m.getTotalBusinessDone() > amount) {
				set.add(m);
				
				if(map.containsKey(m.getLanguage())) {
					map.get(m.getLanguage()).add(m);
				}
				else {
					Set<Movie> tempSet = new HashSet<>();
					tempSet.add(m);
					map.put(m.getLanguage(), tempSet);
				}
			}
		}
		return map;
	}
	
	public static void main(String[] args) {
		MovieReader reader = new MovieReader();
		MovieService service = new MovieService();
		try {
			List<Movie> fileMovies = reader.populateMovies();
			if(service.movieDao.populateDB(fileMovies)) {
				System.out.println("Movies Added to DB from file");	
			} else {
				System.out.println("Error occured while adding movies from file to DB");
			}
			Movie movie = new Movie();
			movie.setMovieId(104);
			movie.setMovieName("Raging Bull");
			movie.setMovieType(Category.DRAMA);
			movie.setLanguage(Language.ENGLISH);
			movie.setRating(8.2);
			movie.setReleaseDate(Date.valueOf("1980-11-14"));
			movie.setTotalBusinessDone(23400000);
			movie.setCasting(Arrays.asList("Robert De Niro", "Joe Pesci"));
			service.addMovie(movie, fileMovies);
			String serializablePath = "E:\\JAVA DEVELOPMENT\\Eclipse workspace\\MovieDetails\\src\\file.ser";
			service.serializeMovies(fileMovies, serializablePath);
			List<Movie> deserializedMovies = service.deserializeMovie(serializablePath);
			List<Movie> filteredByYearMovies = service.getMoviesReleasedInYear(2020);
			List<Movie> filteredByActorMovies = service.getMoviesByActor("Simon Pegg", "Robert Pattinson");
			service.updateRatings(movie, 8.9, fileMovies);
			service.updateBusiness(movie, 20400000, fileMovies);
			Map<Language,Set<Movie>> map = service.businessDone(1200000);
			System.out.println(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
