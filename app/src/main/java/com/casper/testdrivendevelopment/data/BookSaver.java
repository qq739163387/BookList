package com.casper.testdrivendevelopment.data;

import android.content.Context;

import com.casper.testdrivendevelopment.data.model.Book;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BookSaver {
    public Context getContext() {
        return context;
    }

    Context context;

    public ArrayList<Book> getBooks() {
        return books;
    }

    ArrayList<Book>books=new ArrayList<Book>();
    public void save(){
        try{
            ObjectOutputStream outputStream=new ObjectOutputStream(context.openFileOutput("Serialzable.txt",Context.MODE_PRIVATE));
            outputStream.writeObject(books);
            outputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<Book> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Serializable.txt"));
            books=(ArrayList<Book>) inputStream.readObject();
            inputStream.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return books;
    }

    public BookSaver(ArrayList<Book> books) {
        this.books = books;
    }

    public BookSaver(Context context) {
        this.context = context;
    }
}
