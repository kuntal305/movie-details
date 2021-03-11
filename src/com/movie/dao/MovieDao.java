package com.movie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.movie.bean.Movie;
import com.movie.enums.Category;
import com.movie.enums.Language;
import com.movie.util.DBUtil;

public class MovieDao {
	private Connection con = DBUtil.getConnection();
	
	public boolean populateDB(List<Movie> movies) {
		String movieQuery = "insert into movies values(?, ?, ?, ?, ?, ?, ?)";
		String castQuery = "insert into casting values(?, ?)";
		try (PreparedStatement moviePst = con.prepareStatement(movieQuery);
				PreparedStatement castPst = con.prepareStatement(castQuery)) {
			for(Movie m : movies) {
				moviePst.setInt(1, m.getMovieId());
				moviePst.setString(2, m.getMovieName());
				moviePst.setString(3, m.getMovieType().toString());
				moviePst.setString(4, m.getLanguage().toString());
				moviePst.setDate(5, m.getReleaseDate());
				moviePst.setDouble(6, m.getRating());
				moviePst.setDouble(7, m.getTotalBusinessDone());
				if(moviePst.execute()) {
					return false;
				}
				for(String c : m.getCasting()) {
					castPst.setString(1, c);
					castPst.setInt(2, m.getMovieId());
					if(castPst.execute()) {
						return false;
					}
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public List<Movie> getAllMovies() {
		String movieQuery = "select * from movies";
		String castQuery = "select * from casting";
		List<Movie> movies = new ArrayList<Movie>();
		try (PreparedStatement moviePst = con.prepareStatement(movieQuery);
				PreparedStatement castPst = con.prepareStatement(castQuery);
				ResultSet movieSet = moviePst.executeQuery();
				ResultSet castSet = castPst.executeQuery()) {
			while(movieSet.next()) {
				Movie m = new Movie();
				m.setMovieId(movieSet.getInt(1));
				m.setMovieName(movieSet.getString(2));
				m.setMovieType(Category.valueOf(movieSet.getString(3)));
				m.setLanguage(Language.valueOf(movieSet.getString(4)));
				m.setReleaseDate(movieSet.getDate(5));
				m.setRating(movieSet.getDouble(6));
				m.setTotalBusinessDone(movieSet.getDouble(7));
				List<String> castList = new ArrayList<>();
				while(castSet.next()) {
					if(castSet.getInt(2) == m.getMovieId()) {
						castList.add(castSet.getString(1));
					}
				}
				m.setCasting(castList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}
}
