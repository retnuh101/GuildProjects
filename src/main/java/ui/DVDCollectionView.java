/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dto.DVD;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public class DVDCollectionView {
    private UserIO io;
    
    public DVDCollectionView(UserIO io){
        this.io = io;
    }
    
    public int printMenuAndGetSelection(){
        io.print("Main Menu");
        io.print("1. Add DVD");
        io.print("2. Remove DVD");
        io.print("3. Edit DVD");
        io.print("4. Display Collection");
        io.print("5. Display Movie Info");
        io.print("6. Exit");
        
        return io.readInt("Please select from the above choices.", 1, 6);
    }
    public DVD getNewMovieInfo(){
        String title = io.readString("Please enter Movie Title");
        String releaseDate = io.readString("Please enter Release Date");
        String rating = io.readString("Please enter MPAA Rating");
        String directorName = io.readString("Please enter Director Name");
        String studio = io.readString("Please enter Studio Name");
        String userNotes = io.readString("Please enter any notes about the movie.");
        DVD currentDVD = new DVD(title);
        currentDVD.setTitle(title);
        currentDVD.setReleaseDate(releaseDate);
        currentDVD.setRating(rating);
        currentDVD.setDirectorName(directorName);
        currentDVD.setStudio(studio);
        currentDVD.setUserNotes(userNotes);
        return currentDVD;
    }
    
    public int printEditMenuAndGetSelection(){
        io.print("Edit Menu");
        io.print("1. Edit Title");
        io.print("2. Edit Release Date");
        io.print("3. Edit Rating");
        io.print("4. Edit Director Name");
        io.print("5. Edit Studio Name");
        io.print("6. Edit Notes");
        io.print("7. Exit");
        
        return io.readInt("Please select from the above choices.", 1, 7);
    }
    
    
    
    public void displayEditMovieBanner(){
        io.print("===Edit Movie===");
    }
    public void displayEditMovieSuccessBanner(){
        io.print("Movie Successfully Edited. Please hit enter to continue.");
    }
    
    
    
    public void displayAddNewMovieBanner(){
        io.print("===ADD NEW MOVIE===");
    }
    public void displayAddSuccessBanner(){
        io.readString("Movie successfully added. Please hit enter to continue.");
    }
    
    
    
    
    
    
    public void displayMoviesList(List<DVD> movieList) {
        for (DVD currentMovie : movieList){
            io.print(currentMovie.getTitle() + ": "
                    + currentMovie.getReleaseDate() + " - "
                    + "Rated " + currentMovie.getRating() + " - "
                    + currentMovie.getDirectorName() + " - "
                    + currentMovie.getStudio());
        }
        io.readString("Please hit enter to continue.");
    }
    public void displayDisplayMoviesList(){
        io.print("===Display All Movies===");
    }
    public void displayDisplayMovieBanner(){
        io.print("===Display Movie===");
    }
    public String getTitleChoice(){
        return io.readString("Please enter title of movie.");
    }
    public void getMovie(DVD dvd){
        if (dvd != null){
            io.print(dvd.getTitle());
            io.print("Released: " + dvd.getReleaseDate());
            io.print("Rated: " + dvd.getRating());
            io.print("Directed by: " + dvd.getDirectorName());
            io.print("Studio: " + dvd.getStudio());
            io.print("Notes: " + dvd.getUserNotes());
            io.print("");
        } else {
            io.print("No such movie.");
        }
        io.readString("Please hit enter to continue.");
    }
    public void displayRemoveMovieBanner(){
        io.print("===Remove Movie===");
    }
    public void displayRemoveSuccessBanner(){
        io.readString("Movie successfully removed. Please hit enter to continue.");
    }
   
    
    public void displayExitBanner(){
        io.print("Good Bye!");
    }
    public void displayUnknownCommandBanner(){
        io.print("Unknown Command!!!");
    }
    
    public void displayErrorMessage(String errorMsg) {
    io.print("=== ERROR ===");
    io.print(errorMsg);
}
}
