/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.hlschuler.dvdcollection.controller.DVDCollectionController;
import dto.DVD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import ui.UserIO;
import ui.UserIOConsoleImpl;

/**
 *
 * @author hschuler2992
 */
public class DVDCollectionDaoFileImpl implements DVDCollectionDao {
    
    public static final String COLLECTION_FILE = "collection.txt";
    public static final String DELIMITER = "::";
    private UserIO io = (UserIO) new UserIOConsoleImpl();
    public Map<String, DVD> movies = new HashMap<>();
    
    DVDCollectionController controller;
    
    @Override
    public DVD addDVD(String title, DVD dvd)
            throws DVDCollectionDaoException {
        loadCollection();
        DVD newMovie = movies.put(title, dvd);
        writeCollection(); //Should not save every time.
        return newMovie;
    }
    
    @Override
    public List<DVD> getAllMovies()
            throws DVDCollectionDaoException {
        loadCollection();
        return new ArrayList(movies.values());
    }
    
    @Override
    public DVD getMovie(String title)
            throws DVDCollectionDaoException {
        loadCollection();
        return movies.get(title);
    }

    //Business logic
    @Override
    public DVD editMovie(String title, int editMenuSelection) throws DVDCollectionDaoException {
        DVD movieToEdit = movies.get(title);
        //prompt for field input
        
        //get user input and store
        
        switch (editMenuSelection) {
            case 1:
                //Edit title
                movies.remove(title);
                title = io.readString("Please enter new Movie Title");
                movieToEdit.setTitle(title);
                break;
            case 2:
                //edit release date
                String releaseDate = io.readString("Please enter new release date: ");
                //set to movieToEdit
                movieToEdit.setReleaseDate(releaseDate);
                break;
            case 3:
                //Edit rating
                String rating = io.readString("Please enter new MPAA Rating");
                movieToEdit.setRating(rating); //
                break;
            case 4:
                //edit director name
                String directorName = io.readString("Please enter new Director Name");
                movieToEdit.setDirectorName(directorName);
                break;
            case 5:
                //Edit studio
                String studio = io.readString("Please enter new Studio Name");
                movieToEdit.setStudio(studio);
                break;
            case 6:
                //edit notes
                String userNotes = io.readString("Please enter any notes about the movie.");
                movieToEdit.setUserNotes(userNotes);
                break;
            default:
            //exit message
            
        }
        movies.put(title, movieToEdit);
        //put dvd back in list
        writeCollection();
        return movieToEdit;
    }
    
    @Override
    public DVD removeMovie(String title)
            throws DVDCollectionDaoException {
        loadCollection();
        DVD removedMovie = movies.remove(title);
        writeCollection();//Should not save every time.
        return removedMovie;
    }
    
    private DVD unmarshallDVD(String dvdAsText) {
        String[] dvdTokens = dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];
        DVD dvdFromFile = new DVD(title);
        dvdFromFile.setReleaseDate(dvdTokens[1]);
        dvdFromFile.setRating(dvdTokens[2]);
        dvdFromFile.setDirectorName(dvdTokens[3]);
        dvdFromFile.setStudio(dvdTokens[4]);
        dvdFromFile.setUserNotes(dvdTokens[5]);
        
        return dvdFromFile;
    }
    
    private void loadCollection() throws DVDCollectionDaoException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(COLLECTION_FILE)));
        } catch (FileNotFoundException e) {
            throw new DVDCollectionDaoException(
                    "Could not load collection data into memory.", e);
        }
        String currentLine;
        DVD currentDVD;
        
        while (scanner.hasNextLine()) {
            
            currentLine = scanner.nextLine();
            
            currentDVD = unmarshallDVD(currentLine);
            
            movies.put(currentDVD.getTitle(), currentDVD);
        }
        scanner.close();
    }
    
    private String marshallDVD(DVD aDVD) {
        String dvdAsText = aDVD.getTitle() + DELIMITER;
        dvdAsText += aDVD.getReleaseDate() + DELIMITER;
        dvdAsText += aDVD.getRating() + DELIMITER;
        dvdAsText += aDVD.getDirectorName() + DELIMITER;
        dvdAsText += aDVD.getStudio() + DELIMITER;
        dvdAsText += aDVD.getUserNotes();
        
        return dvdAsText;
    }
    
    public void writeCollection() throws DVDCollectionDaoException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(COLLECTION_FILE));
        } catch (IOException e) {
            throw new DVDCollectionDaoException(
                    "Could not save student data.", e);
        }
        
        String dvdAsText;
        List<DVD> dvdList = this.getAllMovies();
        for (DVD currentDVD : dvdList) {
            dvdAsText = marshallDVD(currentDVD);
            out.println(dvdAsText);
            out.flush();
        }
        out.close();
    }
    
}
