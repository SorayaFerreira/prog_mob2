import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JogadorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserirJogador(Jogador... jogador);
    @Update
    void atualizarJogador(Jogador... jogador);
    @Delete
    void deletarJogador(Jogador... jogador);
    @Query("SELECT * FROM Jogador")
    List<Jogador> listarTodosJogadores();
    @Query("SELECT * FROM Jogador WHERE nickname = :nickname LIMIT 1")
    Jogador buscarPorNickname(String nickname);
}
