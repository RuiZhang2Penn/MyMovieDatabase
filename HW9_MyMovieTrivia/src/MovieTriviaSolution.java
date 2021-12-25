import java.util.ArrayList;
import java.util.Scanner;

import file.MovieDB;
import movies.Actor;
import movies.Movie;

/**
 * Movie trivia class providing different methods for querying and updating a movie database.
 */
public class MovieTriviaSolution {
	
	/**
	 * Create instance of movie database
	 */
	MovieDB movieDB = new MovieDB();
	
	public static void main(String[] args) {
		
		//create instance of movie trivia class
		MovieTriviaSolution mt = new MovieTriviaSolution();
		
		//setup movie trivia class
		mt.setUp("moviedata.txt", "movieratings.csv");
		
		//run the interactive program
		//mt.run();
	}
	
	public void setUp(String movieData, String movieRatings) {
		//load movie database files
		movieDB.setUp(movieData, movieRatings);
		
		//print all actors and movies
		this.printAllActors();
		this.printAllMovies();		
	}
		
	/**
	 * Prints all actors, along with the movies each one has acted in.
	 */
	public void printAllActors () {
		System.out.println(movieDB.getActorsInfo());
	}
	
	/**
	 * Prints all movies, along with the ratings for each.
	 */
	public void printAllMovies () {
		System.out.println(movieDB.getMoviesInfo());
	}
	
	/**
	 * Inserts or updates the given actor and actor's movies in the actorsInfo.
	 * @param actor to add or update
	 * @param movies to add or update
	 * @param actorsInfo to update
	 */
	public void insertActor (String actor, String [] movies, ArrayList <Actor> actorsInfo) {
		String actorAfterConvert = actor.trim().toLowerCase();
		for (Actor ac : actorsInfo) {
			if (ac.getName().trim().toLowerCase().equals(actorAfterConvert)) {
				// new movies will be appended to the end of the actor object's movieCasted list
				ArrayList <String> cast = ac.getMoviesCast();
				for (String movie : movies) {
					String movieAfterConvert = movie.trim().toLowerCase();
					if (!cast.contains(movieAfterConvert)) {
						cast.add(movieAfterConvert);
					}
				}
				return;
			}
		}
		Actor newAc = new Actor (actorAfterConvert);
		for (String movie : movies) {
			String movieAfterConvert = movie.trim().toLowerCase();
			// new movies will be appended to the end of the actor object's movieCasted list
			newAc.getMoviesCast().add(movieAfterConvert);
		}
		// new actors will be appended to the end of passed in actorsInfo arraylist
		actorsInfo.add(newAc);
		return;
	}
	
	/**
	 * Inserts or updates the given movie rating in the moviesInfo.
	 * @param movie to add or update
	 * @param ratings to add or update
	 * @param moviesInfo to update
	 */
	public void insertRating (String movie, int [] ratings, ArrayList <Movie> moviesInfo) {
		if (ratings == null || ratings.length != 2) {
			return;
		}
		if (ratings[0] < 0 || ratings[0] > 100) {
			return;
		}
		if (ratings[1] < 0 || ratings[1] > 100) {
			return;
		}
		String movieAfterConvert = movie.trim().toLowerCase();
		for (Movie m : moviesInfo) {
			if (m.getName().trim().toLowerCase().equals(movieAfterConvert)) {
				m.setCriticRating(ratings[0]);
				m.setAudienceRating(ratings[1]);
				return;
			}
		}
		Movie newM = new Movie(movieAfterConvert, ratings[0], ratings[1]);
		moviesInfo.add(newM);
		return;
	}
	
	/**
	 * Returns the list of all movies for a given actor.
	 * @param actor to search for
	 * @param actorsInfo to search
	 * @return list of all movies
	 */
	public ArrayList <String> selectWhereActorIs (String actor, ArrayList <Actor> actorsInfo) {
		String actorAfterConvert = actor.trim().toLowerCase();
		for (Actor ac : actorsInfo) {
			if (actorAfterConvert.equals(ac.getName().trim().toLowerCase())) {
				return ac.getMoviesCast();
			}
		}
		return new ArrayList <> ();
	}
	
	/**
	 * Returns the list of all actors for a given movie.
	 * @param movie to search for
	 * @param actorsInfo to search
	 * @return list of all actors
	 */
	public ArrayList <String> selectWhereMovieIs (String movie, ArrayList <Actor> actorsInfo) {
		String movieAfterConvert = movie.trim().toLowerCase();
		ArrayList <String> actors = new ArrayList <> ();
		for (Actor ac : actorsInfo) {
			if (ac.getMoviesCast().contains(movieAfterConvert)) {
				actors.add(ac.getName());
			}
		}
		return actors;
	}
	
	/**
	 * Returns a list of movies that satisfy an inequality or equality, based on a comparison and a 
	 * targeted rating.
	 * @param comparison is either ‘=’, ‘>’, or ‘< ‘ and is passed in as a char
	 * @param targetRating is an int used for the filter
	 * @param isCritic is a boolean that represents whether we are interested in the critics’ rating or 
	 * the audience rating. true = critic ratings, false = audience ratings.
	 * @param moviesInfo
	 * @return
	 */
	public ArrayList <String> selectWhereRatingIs (char comparison, int targetRating, boolean isCritic, ArrayList <Movie> moviesInfo) {
		ArrayList <String> res = new ArrayList <> ();
		if (comparison != '<' && comparison != '>' && comparison != '=') {
			return res;
		}
		if (targetRating > 100 || targetRating < 0) {
			return res;
		}
		for (Movie m : moviesInfo) {
			if (isCritic) {
				int forComp = m.getCriticRating ();
				if (comparison == '<') {
					if (forComp < targetRating) {
						res.add(m.getName());
					}
				} else if (comparison == '=') {
					if (forComp == targetRating) {
						res.add(m.getName());
					}
				} else {
					if (forComp > targetRating) {
						res.add(m.getName());
					}
				}
			} else {
				int forComp = m.getAudienceRating ();
				if (comparison == '<') {
					if (forComp < targetRating) {
						res.add(m.getName());
					}
				} else if (comparison == '=') {
					if (forComp == targetRating) {
						res.add(m.getName());
					}
				} else {
					if (forComp > targetRating) {
						res.add(m.getName());
					}
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Returns a list of all actors that the given actor has ever worked with in any movie 
	 * except the actor herself/himself.  You may think of how to use the 5 basic utility methods implemented previously.
	 * @param actor to search for
	 * @param actorsInfo to search
	 * @return list of all actors
	 */
	public ArrayList <String> getCoActors (String actor, ArrayList <Actor> actorsInfo) {
		String actorAfterConvert = actor.trim().toLowerCase();
		ArrayList <String> movies = selectWhereActorIs(actor, actorsInfo);
		ArrayList <String> res = new ArrayList <> ();
		
		for (String movie : movies) {
			for (Actor ac : actorsInfo) {
				if (!ac.getName().equals(actorAfterConvert)) {
					if (ac.getMoviesCast().contains(movie)) {
						res.add(ac.getName());
					}
				}
			}
		}
		return res;
	}
	
	/**
	 * Returns a list of movie names where both actors were cast.
	 * @param actor1 to search for
	 * @param actor2 to search for
	 * @param actorsInfo to search
	 * @return list of movie names
	 */
	public ArrayList <String> getCommonMovie (String actor1, String actor2, ArrayList <Actor> actorsInfo) {

		ArrayList <String> moviesActor1 = selectWhereActorIs(actor1, actorsInfo);
		ArrayList <String> moviesActor2 = selectWhereActorIs(actor2, actorsInfo);

		ArrayList <String> res = new ArrayList <> ();
		for (String movie : moviesActor1) {
			if (moviesActor2.contains(movie)) {
				res.add(movie);
			}
		}
		return res;
	}
	
	/**
	 * Returns a list of movie names that both critics and the audience have rated above 85 (>= 85).
	 * @param moviesInfo to search
	 * @return list of movie names
	 */
	public ArrayList <String> goodMovies (ArrayList <Movie> moviesInfo) {
		ArrayList <String> res = new ArrayList <> ();
		for (Movie m : moviesInfo) {
			if (m.getCriticRating() >= 85 && m.getAudienceRating() >= 85) {
				res.add(m.getName());
			}
		}
		return res;
	}
	
	/**
	 * Given a pair of movies, this method returns a list of actors that acted in both movies. 
	 * @param movie1 to search for
	 * @param movie2 to search for
	 * @param actorsInfo to search
	 * @return list of actors
	 */
	public ArrayList <String> getCommonActors (String movie1, String movie2, ArrayList <Actor> actorsInfo) {
		ArrayList <String> actors1 = selectWhereMovieIs(movie1, actorsInfo);
		ArrayList <String> actors2 = selectWhereMovieIs(movie2, actorsInfo);
		ArrayList <String> res = new ArrayList <> ();
		for (String ac : actors1) {
			if (actors2.contains(ac)) {
				res.add(ac);
			}
		}
		return res;
	}
	
	/**
	 * Given the moviesInfo DB, this static method returns the mean value of the critics’ ratings and the audience ratings.
	 * @param moviesInfo to search
	 * @return the mean values as a double array, where the 1st item (index 0) is the mean of all critics’ ratings 
	 * and the 2nd item (index 1) is the mean of all audience ratings
	 */
	public static double [] getMean (ArrayList <Movie> moviesInfo) {
		double [] res = new double [2];
		for (Movie m : moviesInfo) {
			res[0] += m.getCriticRating();
			res[1] += m.getAudienceRating();
		}
		res[0] = res[0] / moviesInfo.size();
		res[1] = res[1] / moviesInfo.size();
		return res;
	}
}
