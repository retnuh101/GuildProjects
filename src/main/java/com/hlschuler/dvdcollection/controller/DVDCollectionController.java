/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.dvdcollection.controller;

import dao.DVDCollectionDao;
import dao.DVDCollectionDaoException;
import dto.DVD;
import java.util.List;
import ui.DVDCollectionView;
import ui.UserIO;
import ui.UserIOConsoleImpl;

/**
 *
 * @author hschuler2992
 */
public class DVDCollectionController {
    
    
    DVDCollectionView view;
    DVDCollectionDao dao;
    private UserIO io = (UserIO) new UserIOConsoleImpl();

    public DVDCollectionController(DVDCollectionDao dao, DVDCollectionView view) {
        this.dao = dao;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {
                   
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        addMovie();
                        break;
                    case 2:
                        removeMovie();
                        break;
                    case 3:
                        editMovie();
                        break;
                    case 4:
                        listMovies();
                        break;
                    case 5:
                        getMovie();
                        break;
                    case 6:
                        keepGoing = false;
                        //Write collection should go here
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();            
        } catch (DVDCollectionDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    

    private void addMovie() throws DVDCollectionDaoException {
        view.displayAddNewMovieBanner();
        DVD newMovie = view.getNewMovieInfo();
        dao.addDVD(newMovie.getTitle(), newMovie);
        view.displayAddSuccessBanner();

    }

    private void listMovies() throws DVDCollectionDaoException {
        view.displayDisplayMoviesList();
        List<DVD> movieList = dao.getAllMovies();
        view.displayMoviesList(movieList);
    }

    private void getMovie() throws DVDCollectionDaoException {
        view.displayDisplayMovieBanner();
        String title = view.getTitleChoice();
        DVD dvd = dao.getMovie(title);
        view.getMovie(dvd);
    }

    private void removeMovie() throws DVDCollectionDaoException {
        view.displayRemoveMovieBanner();
        String title = view.getTitleChoice();
        dao.removeMovie(title);
        view.displayRemoveSuccessBanner();
    }
    
    public void editMovie() throws DVDCollectionDaoException {
        view.displayEditMovieBanner();
        
        String title = view.getTitleChoice();
        int editMenuSelection = view.printEditMenuAndGetSelection();
        
        dao.editMovie(title,editMenuSelection);
        view.displayEditMovieSuccessBanner();
    }
    
    
    
    

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
