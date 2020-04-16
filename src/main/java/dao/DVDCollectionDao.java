/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.DVD;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public interface DVDCollectionDao {
    
    public DVD addDVD(String title, DVD dvd)
        throws DVDCollectionDaoException;
    
    public List<DVD> getAllMovies()
        throws DVDCollectionDaoException;
    
    public DVD getMovie(String title)
        throws DVDCollectionDaoException;
    
    public DVD removeMovie(String title)
        throws DVDCollectionDaoException;
    
    public DVD editMovie(String title, int editMenuSelection)
        throws DVDCollectionDaoException;

    
}
