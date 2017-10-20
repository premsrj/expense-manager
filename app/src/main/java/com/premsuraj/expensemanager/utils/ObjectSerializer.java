package com.premsuraj.expensemanager.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerializer {

    public ObjectSerializer() {
    }

    public static String getDocumentPath(Context context, String filename) {
        return context.getFilesDir().getAbsolutePath() + "/" + filename;
    }

    public static String getCachePath(Context context, String filename) {
        return context.getCacheDir().getAbsolutePath() + "/" + filename;
    }

    public void putObject(String dumpFile, Object object) {
        // serialize the object
        try {
            FileOutputStream fout = new FileOutputStream(dumpFile);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(object);
            oos.close();
            fout.close();
        } catch (Exception e) {
            Log.w(ObjectSerializer.class.getSimpleName(), e);
        }
    }

    public Object get(String dumpFile) {

        Object object = null;

        // load the memory
        try {
            FileInputStream fin = new FileInputStream(dumpFile);
            ObjectInputStream ois = new ObjectInputStream(fin);
            object = ois.readObject();
            ois.close();
            fin.close();
        } catch (Exception e) {
            Log.w(ObjectSerializer.class.getSimpleName(), e);
        }

        return object;
    }

    public boolean remove(String dumpFile) {
        File file = new File(dumpFile);
        return file.delete();
    }
}
