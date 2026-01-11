package it.ispw.unilife.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.ispw.unilife.dao.DAO;
import it.ispw.unilife.exception.DAOException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONDAO<T> implements DAO<T> {

    protected final File file;
    protected final Gson gson;
    private final Type listType;
    protected static final Logger logger = Logger.getLogger(JSONDAO.class.getName());

    protected JSONDAO(String path, Class<T> cls) {
        this.file = new File(path);
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        this.listType = TypeToken.getParameterized(ArrayList.class, cls).getType();

        initFile();
    }

    protected void initFile() {
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                if (file.createNewFile()) {
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write("[]");
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "ERROR: Can't create json File");
        }
    }

    @Override
    public List<T> getAll() throws DAOException{
        try (Reader reader = new FileReader(this.file)) {
            List<T> list = gson.fromJson(reader, listType);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading JSON", e);
            throw new DAOException("ERROR: Can't read from json File");
        }
    }

    @Override
    public void insert(T t) throws DAOException {
        List<T> currentList = getAll();

        if (currentList.contains(t)) {
            throw new DAOException("Element already exists in database");
        }

        currentList.add(t);
        saveToFile(currentList);
    }

    @Override
    public void delete(T t) throws DAOException {
        List<T> currentList = getAll();
        if (currentList.remove(t)) {
            saveToFile(currentList);
        }
    }

    private void saveToFile(List<T> list) {
        try (Writer writer = new FileWriter(this.file)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving JSON", e);
        }
    }
}