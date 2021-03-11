package com.movie.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.movie.bean.Movie;
import com.movie.enums.Category;
import com.movie.enums.Language;

public class MovieReader {
	
	public List<Movie> populateMovies() throws IOException {
		String filePath = "E:\\JAVA DEVELOPMENT\\Eclipse workspace\\MovieDetails\\src\\Movies.txt";
		Path path = Paths.get(filePath);
		List<String> lines = Files.readAllLines(path);
		List<Movie> movies = new ArrayList<Movie>();
		lines.forEach(line -> {
			String[] values = line.split(",");
			Movie m = new Movie();
			m.setMovieId(Integer.parseInt(values[0]));
			m.setMovieName(values[1]);
			m.setMovieType(Category.valueOf(values[2].toUpperCase()));
			m.setLanguage(Language.valueOf(values[3].toUpperCase()));
			m.setReleaseDate(Date.valueOf(values[4]));
			m.setCasting(Arrays.asList(values[5].split("//")));
			m.setRating(Double.parseDouble(values[6]));
			m.setTotalBusinessDone(Double.parseDouble(values[7]));
			movies.add(m);
		});
		return movies;
	}
	
}
