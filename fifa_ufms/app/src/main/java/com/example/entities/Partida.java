import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Jogador.class,
                parentColumns = "idJogador",
                childColumns = "idJogador1",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Jogador.class,
                parentColumns = "idJogador",
                childColumns = "idJogador2",
                onDelete = ForeignKey.CASCADE)
})
public class Partida {
    @PrimaryKey(autoGenerate = true)
    public int idPartida;
    public String data;
    public int idJogador1;
    public int idJogador2;
    public int placarJogador1;
    public int placarJogador2;

    public Partida(String data, int idPartida, int idJogador1, int idJogador2, int placarJogador1, int placarJogador2) {
        this.data = data;
        this.idPartida = idPartida;
        this.idJogador1 = idJogador1;
        this.idJogador2 = idJogador2;
        this.placarJogador1 = placarJogador1;
        this.placarJogador2 = placarJogador2;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getIdJogador1() {
        return idJogador1;
    }

    public void setIdJogador1(int idJogador1) {
        this.idJogador1 = idJogador1;
    }

    public int getIdJogador2() {
        return idJogador2;
    }

    public void setIdJogador2(int idJogador2) {
        this.idJogador2 = idJogador2;
    }

    public int getPlacarJogador2() {
        return placarJogador2;
    }

    public void setPlacarJogador2(int placarJogador2) {
        this.placarJogador2 = placarJogador2;
    }

    public int getPlacarJogador1() {
        return placarJogador1;
    }

    public void setPlacarJogador1(int placarJogador1) {
        this.placarJogador1 = placarJogador1;
    }
}
