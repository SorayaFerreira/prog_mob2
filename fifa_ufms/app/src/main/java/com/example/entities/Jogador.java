import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"nickname"}, unique = true)})
public class Jogador {
    @PrimaryKey(autoGenerate = true)
    public int idJogador;

    public String nome;
    public String nickname;
    public String email;
    public String dataNascimento;
    public int numeroGols;
    public int numeroAmarelos;
    public int numeroVermelhos;
}
