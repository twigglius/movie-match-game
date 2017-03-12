import info.movito.themoviedbapi.*;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.Person;
import info.movito.themoviedbapi.model.people.PersonCredit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by armccullough on 1/8/17.
 */
public class Driver {
    private final static String API_KEY = "d114ed9ee885b88dc76550154aef2142";

    protected static TmdbApi tmdb = new TmdbApi(API_KEY);
    protected static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    protected static TmdbSearch search = tmdb.getSearch();

    public static void main(String[] args) {
        try {
            int numLinks = 5;
            System.out.println("Welcome! The rules to the game are simple. You pick two actors, and the other person has to link" +
                    "them together based on movies and actors they have worked with. " +
                    "for Example: If you were given Tom Hanks and Don Cheadle. You could match Tom Hanks to Halle Barre in Cloud Atlas " +
                    "and Halle Barre to Don Cheadle in Swordfish. This completes the link between the two, and you win!");
            System.out.println();
            System.out.println("First things first, you will need to select a starting actor.");
            int startingActorId = getActor();
            System.out.println("Success with actor id: " + startingActorId);
            System.out.println();
            System.out.println("Now you will have to pick an actor you want to link them to!");
            int endingActorId = getActor();

            String startingActorName = getActorName(startingActorId);
            String endingActorName = getActorName(endingActorId);

            System.out.println("Awesome! You have set it up so " + startingActorName + " and " + endingActorName
                    + " have to be linked!");
            System.out.println();

            System.out.println();


            System.out.println("Please select your first of " + numLinks + " actors to link " + startingActorName
                    + " and " + endingActorName);
            int firstLink = getActor();

            System.out.println();
            System.out.println("Great! What Movie were they in together?");





            if(inMovieTogether(startingActorId, firstLink, 1)){

            }



        } catch (IOException e) {

        }

    }

    public static String getMovie() throws IOException{
        String name = br.readLine().trim();
        int movieId=0;
        MovieResultsPage movie = search.searchMovie(name,null,null, true, null);
        List<MovieDb> movieResults = movie.getResults();

        return "";



    }

    public static String getActorName(int actorId) {
        return tmdb.getPeople().getPersonInfo(actorId).getName();
    }

    public static boolean inMovieTogether(int actor1, int actor2, int movieId) {

        TmdbPeople peopleDb = tmdb.getPeople();

        List<PersonCredit> cast1 = peopleDb.getPersonCredits(actor1).getCast();
        List<PersonCredit> cast2 = peopleDb.getPersonCredits(actor2).getCast();

        for (PersonCredit credit : cast1) {
            if (cast2.contains(credit) && credit.getId() == movieId) {
                return true;
            }
        }

        return false;
    }

    public static int getActor() throws IOException {
        boolean validActor = false;
        Person person;
        int personId = 0;
        while (!validActor) {
            System.out.print("Please Enter an Actors Name: ");
            String name = br.readLine().trim();
            System.out.println();
            try {
                person = acceptOnlyOne(name);
                personId = person.getId();
                System.out.println("DEBUG: Person details: " + person.getName() + " " + person.getId());
                validActor = true;
            } catch (ActorDoesNotExistException e) {
                System.out.println(e.getMessage());
            }
        }
        return personId;
    }

    public static Person acceptOnlyOne(String name) throws IOException, ActorDoesNotExistException {
        int personIndex = 0;
        TmdbPeople.PersonResultsPage people = search.searchPerson(name, false, null);
        List<Person> personSearchResult = people.getResults();
        if (personSearchResult.size() > 1) {
            for (int i = 1; i <= personSearchResult.size(); i++) {
                System.out.println(i + " : " + personSearchResult.get(i - 1).getName());
            }
            boolean validNumber = false;
            while (!validNumber) {
                System.out.println("Which Person did you mean? Enter the ID number next to the name: ");
                String entry = br.readLine().trim();

                try {
                    int actorId = Integer.parseInt(entry);
                    int searchIndex = actorId - 1;
                    if (searchIndex < 0 || searchIndex > personSearchResult.size()) {
                        System.out.println("Invalid Number! Please Enter a Valid Selection");
                    } else {
                        personIndex = searchIndex;
                        validNumber = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Number! Please Enter a Valid Selection");
                }
            }
        } else if (personSearchResult == null || personSearchResult.isEmpty()) {
            throw new ActorDoesNotExistException("Actor Cannot Be Found in Database");
        }
        return personSearchResult.get(personIndex);
    }
}
