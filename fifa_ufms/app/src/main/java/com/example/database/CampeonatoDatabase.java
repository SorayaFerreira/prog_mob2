import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Jogador.class, Partida.class}, version = 1)
public abstract class CampeonatoDatabase extends RoomDatabase {

    private static CampeonatoDatabase INSTANCE;

    public abstract JogadorDao jogadorDao();
    public abstract PartidaDao partidaDao();

    public static synchronized CampeonatoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CampeonatoDatabase.class, "Campeonato")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
