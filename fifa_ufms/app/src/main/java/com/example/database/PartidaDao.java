import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PartidaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserirPartida(Partida... partida);

    @Update
    void atualizarPartida(Partida... partida);

    @Delete
    void deletarPartida(Partida... partida);

    @Query("SELECT * FROM Partida")
    List<Partida> listarTodasPartidas();

    @Query("SELECT * FROM Partida WHERE idJogador1 = :id OR idJogador2 = :id")
    List<Partida> buscarPorJogador(int id);
}
