package com.jnu.student.data;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataBank {
    final String Data_FILENAME="book.data";
    public ArrayList<Book> loadShopItems(Context applicationContext) {
        ArrayList<Book> data = new ArrayList<>();
        try {
            FileInputStream fis = applicationContext.openFileInput(Data_FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (ArrayList<Book>) ois.readObject();
            ois.close();
            fis.close();
            Log.d("Serialization", "Data loaded successfully.item count" + data.size() );
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void SavebookItems(Context context, ArrayList<Book> books) {
        try {
            FileOutputStream fos = context.openFileOutput(Data_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(books);
            oos.close();
            fos.close();
            Log.d("Serialization", "Data serialized and saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
