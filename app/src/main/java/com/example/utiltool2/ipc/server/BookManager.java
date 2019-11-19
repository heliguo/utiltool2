package com.example.utiltool2.ipc.server;

import android.os.IInterface;
import android.os.RemoteException;

import com.example.utiltool2.ipc.Book;

import java.util.List;

/**
 * author:lgh on 2019-11-19 14:56
 */
public interface BookManager extends IInterface {

    List<Book> getBooks() throws RemoteException;

    void addBook(Book book) throws RemoteException;

}
