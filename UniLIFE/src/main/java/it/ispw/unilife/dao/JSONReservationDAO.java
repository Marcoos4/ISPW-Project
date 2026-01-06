package it.ispw.unilife.dao;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.ispw.unilife.model.Reservation;
import it.ispw.unilife.model.ReservationStatus;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JSONReservationDAO implements ReservationDAO{
    private final File file;
    private final Gson gson;
    private List<Reservation> reservations;


    public JSONReservationDAO() {
        this.file = new File("data/reservation.json");
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, ctx) -> LocalDate.parse(json.getAsString()))
                .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, type, ctx) -> LocalTime.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();
        this.reservations = new ArrayList<>();

        try {
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }

                file.createNewFile();

                // Inizializza con parentesi quadre per il JSON Array
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore critico: Impossibile creare il file di storage.");
        }
    }

    @Override
    public Reservation getInstance() {

        return null;
    }

    @Override
    public List<Reservation> getAll() {
        try (Reader reader = new FileReader(this.file)) {
            Type listType = new TypeToken<ArrayList<Reservation>>(){}.getType();

            List<Reservation> fromFile = gson.fromJson(reader, listType);

            if (fromFile != null) {
                this.reservations = fromFile;
            } else {
                this.reservations = new ArrayList<>();
            }

        } catch (FileNotFoundException e) {
            this.reservations = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.reservations;
    }

    @Override
    public int insert(Reservation reservation) {

        if (reservation.getStatus() == null || reservation.getStatus() == ReservationStatus.PENDING) {
            System.err.println("ERRORE DAO: Tentativo di salvare prenotazione PENDING scartato.");
            return -1;
        }

        getAll();

        this.reservations.add(reservation);

        saveTo();

        return 0;
    }

    @Override
    public void saveTo() {
        try(Writer writer = new FileWriter(this.file)){
            gson.toJson(this.reservations, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete() {
        return 0;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public List<Reservation> findByStudent(String studentName) {
        return List.of();
    }

    @Override
    public List<Reservation> findByTutorAndDate(String tutor, LocalDate date) {
        return List.of();
    }

}
