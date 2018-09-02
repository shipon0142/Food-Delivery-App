package com.example.shipon.khaidai;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Shipon on 6/3/2018.
 */

public final class InternalStorage {

    private InternalStorage() {
    }

    public static void writeObject(Context context, String key, ArrayList<String> fileName) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(fileName);
        oos.close();
        fos.close();
    }

    public static ArrayList<String> readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<String> object = (ArrayList<String>) ois.readObject();
        return object;
    }
}
