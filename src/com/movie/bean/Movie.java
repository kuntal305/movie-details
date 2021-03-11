package com.movie.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.movie.enums.Category;
import com.movie.enums.Language;

public class Movie implements Serializable {
	private int movieId;
	private String movieName;
	private Category movieType;
	private Language language;
	private Date releaseDate;
	private List<String> casting;
	private double rating;
	private double totalBusinessDone;
	
	public int getMovieId() {
		return movieId;
	}
	
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	
	
	public String getMovieName() {
		return movieName;
	}
	
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
	public Category getMovieType() {
		return movieType;
	}
	
	public void setMovieType(Category movieType) {
		this.movieType = movieType;
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public List<String> getCasting() {
		return casting;
	}
	
	public void setCasting(List<String> casting) {
		this.casting = casting;
	}
	
	public double getRating() {
		return rating;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public double getTotalBusinessDone() {
		return totalBusinessDone;
	}
	
	public void setTotalBusinessDone(double totalBusinessDone) {
		this.totalBusinessDone = totalBusinessDone;
	}
	
}
