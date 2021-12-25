

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file.MovieDB;

class MovieTriviaSolutionTest {
	
	//instance of movie trivia object to test
	MovieTriviaSolution mt;
	MovieDB movieDB = new MovieDB();
	
	@BeforeEach
	void setUp() throws Exception {
		mt = new MovieTriviaSolution();
		mt.setUp("moviedata.txt", "movieratings.csv");
		movieDB.setUp("moviedata.txt", "movieratings.csv");
	}

	@Test
	void testSetUp() { // provided
		assertEquals(6, movieDB.getActorsInfo().size());
		assertEquals(7, movieDB.getMoviesInfo().size());
		
		assertEquals("meryl streep", movieDB.getActorsInfo().get(0).getName());
		assertEquals(3, movieDB.getActorsInfo().get(0).getMoviesCast().size());
		assertEquals("doubt", movieDB.getActorsInfo().get(0).getMoviesCast().get(0));
		
		assertEquals("doubt", movieDB.getMoviesInfo().get(0).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(0).getCriticRating());
		assertEquals(78, movieDB.getMoviesInfo().get(0).getAudienceRating());
	}

	@Test
	void testInsertActor () {
		mt.insertActor("test1", new String [] {"testmovie1", "testmovie2"}, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size());	
		assertEquals("test1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		assertEquals("testmovie1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0));
		
		// TODO
		
		mt.insertActor("        TEST2    ", new String [] {}, movieDB.getActorsInfo());
		assertEquals(8, movieDB.getActorsInfo().size());	
		assertEquals("test2", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		assertEquals(0, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		
		mt.insertActor("test3", new String [] {"TESTmovie1", "       testMovie2       "}, movieDB.getActorsInfo());
		assertEquals(9, movieDB.getActorsInfo().size());	
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		assertEquals("testmovie1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0));
		assertEquals("testmovie2", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(1));
		
		mt.insertActor("   Meryl STReep      ", new String [] {"   DOUBT      ", "     Something New     "}, movieDB.getActorsInfo());
		assertEquals(9, movieDB.getActorsInfo().size());	
		assertEquals(4, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size());
		assertTrue(mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).contains("something new"));
		
		mt.insertActor("   Brandon Krakowsky      ", new String [] {"        SPRING CIT590                    "}, movieDB.getActorsInfo());
		assertEquals(9, movieDB.getActorsInfo().size());	
		assertEquals(1, mt.selectWhereActorIs("brandon krakowsky", movieDB.getActorsInfo()).size());
		assertTrue(mt.selectWhereActorIs("brandon krakowsky", movieDB.getActorsInfo()).contains("spring cit590"));
		
	}
	
	@Test
	void testInsertRating () {
		mt.insertRating("testmovie", new int [] {79, 80}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating());
		
		// TODO
		
		mt.insertRating("testmovie1", new int [] {-1, 80}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		
		mt.insertRating("testmovie2", new int [] {101, 80}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		
		mt.insertRating("doubt", new int [] {100, 100}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals(1, mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).size());
		assertTrue(mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).contains("doubt"));
		
		mt.insertRating("       ARRival          ", new int [] {100, 100}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals(2, mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).size());
		assertTrue(mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).contains("arrival"));
		
		mt.insertRating("       TEST MOVIE 3          ", new int [] {0, 0}, movieDB.getMoviesInfo());
		assertEquals(9, movieDB.getMoviesInfo().size());	
		assertEquals("test movie 3", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		assertEquals(0, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(0, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating());
		
	}
	
	@Test
	void testSelectWhereActorIs () {
		assertEquals(3, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size());
		assertEquals("doubt", mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).get(0));
		
		// TODO
		
		assertEquals(3, mt.selectWhereActorIs("          MERyl Streep     ", movieDB.getActorsInfo()).size());
		assertEquals("doubt", mt.selectWhereActorIs("          MERyl Streep     ", movieDB.getActorsInfo()).get(0));
		assertEquals(0, mt.selectWhereActorIs("          Brandon Krakowsky     ", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.selectWhereActorIs("          Not exist     ", movieDB.getActorsInfo()).size());
		
	}
	
	@Test
	void testSelectWhereMovieIs () {
		assertEquals(2, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"));
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("amy adams"));
		
		// TODO
		assertEquals(2, mt.selectWhereMovieIs("     DOUBT     ", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("     DOUBT     ", movieDB.getActorsInfo()).contains("meryl streep"));
		assertEquals(true, mt.selectWhereMovieIs("     DOUBT     ", movieDB.getActorsInfo()).contains("amy adams"));
		assertEquals(0, mt.selectWhereMovieIs("     Not exist     ", movieDB.getActorsInfo()).size());
		
	}
	
	@Test
	void testSelectWhereRatingIs () {
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('=', 65, false, movieDB.getMoviesInfo()).size());
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size());
		
		// TODO
		assertEquals(0, mt.selectWhereRatingIs('!', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('=', -1, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('>', 200, true, movieDB.getMoviesInfo()).size());
		
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(1, mt.selectWhereRatingIs('=', 85, true, movieDB.getMoviesInfo()).size());
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size());
		
		assertEquals(2, mt.selectWhereRatingIs('<', 50, false, movieDB.getMoviesInfo()).size());
		assertEquals(1, mt.selectWhereRatingIs('=', 90, false, movieDB.getMoviesInfo()).size());
		assertEquals(1, mt.selectWhereRatingIs('>', 90, false, movieDB.getMoviesInfo()).size());
		
	}
	
	@Test
	void testGetCoActors () {
		assertEquals(2, mt.getCoActors("meryl streep", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("amy adams"));
		
		// TODO
		
		assertEquals(2, mt.getCoActors("    MERyl STreep            ", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("   MERyl STreep            ", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCoActors("   MERyl STreep            ", movieDB.getActorsInfo()).contains("amy adams"));
		
		assertEquals(0, mt.getCoActors("    Not exist            ", movieDB.getActorsInfo()).size());
		
	}
	
	@Test
	void testGetCommonMovie () {
		assertEquals(1, mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"));
		
		// TODO
		
		assertEquals(1, mt.getCommonMovie("      Meryl STReep", "     TOM hanks      ", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("      Meryl STReep", "     TOM hanks      ", movieDB.getActorsInfo()).contains("the post"));
		
		assertEquals(0, mt.getCommonMovie("      Meryl STReep", "     Brandon Krakowsky      ", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.getCommonMovie("      Not exist", "     Brandon Krakowsky      ", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.getCommonMovie("      Not exist", "   Also not Exist      ", movieDB.getActorsInfo()).size());
		
		assertEquals(3, mt.getCommonMovie("      Meryl STReep", "   Meryl STReep      ", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("      Meryl STReep", "   Meryl STReep      ", movieDB.getActorsInfo()).contains("the post"));
		assertTrue(mt.getCommonMovie("      Meryl STReep", "   Meryl STReep      ", movieDB.getActorsInfo()).contains("doubt"));
		assertTrue(mt.getCommonMovie("      Meryl STReep", "   Meryl STReep      ", movieDB.getActorsInfo()).contains("sophie's choice"));
		
	}
	
	@Test
	void testGoodMovies () {
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"));
		
		// TODO
		
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("et"));
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("rocky ii"));
		
	}
	
	@Test
	void testGetCommonActors () {
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).contains("meryl streep"));
		
		// TODO
		
		assertEquals(1, mt.getCommonActors("     DOUBT    ", "   The Post               ", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("    DOUBT    ", "      The Post               ", movieDB.getActorsInfo()).contains("meryl streep"));
		
		assertEquals(0, mt.getCommonActors("     Popeye    ", "   The Post               ", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.getCommonActors("     Popeye    ", "   Not exist               ", movieDB.getActorsInfo()).size());
		assertEquals(0, mt.getCommonActors("     Not exist 2    ", "   Not exist               ", movieDB.getActorsInfo()).size());
		
		assertEquals(2, mt.getCommonActors("     DOUBT    ", "   DOUBT               ", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("     DOUBT    ", "   DOUBT               ", movieDB.getActorsInfo()).contains("meryl streep"));
		assertTrue(mt.getCommonActors("     DOUBT    ", "   DOUBT               ", movieDB.getActorsInfo()).contains("amy adams"));
		
	}
	
	@Test
	void testGetMean () {
		
		// TODO
		
		//test mean values
		assertEquals(67.857, MovieTriviaSolution.getMean(movieDB.getMoviesInfo())[0], 0.001);
		assertEquals(65.714, MovieTriviaSolution.getMean(movieDB.getMoviesInfo())[1], 0.001);
		
		//update ratings for a movie
		mt.insertRating("testmovie", new int [] {79, 80}, movieDB.getMoviesInfo());
		
		//test mean values again
		assertEquals(69.250, MovieTriviaSolution.getMean(movieDB.getMoviesInfo())[0], 0.001);
		assertEquals(67.500, MovieTriviaSolution.getMean(movieDB.getMoviesInfo())[1], 0.001);
		
	}
}
