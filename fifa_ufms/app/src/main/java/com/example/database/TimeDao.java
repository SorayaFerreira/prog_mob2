import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserirTime(Time... time);
    @Update
    void atualizarTime(Time... time);
    @Delete
    void deletarTime(Time... time);

    @Query("SELECT * FROM Time")
    List<time> listarTodosTimes();
}
