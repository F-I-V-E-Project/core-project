package src;
import java.sql.Connection;
import java.util.List;

public interface Livro {
    int getId();
    String getTitulo();
    boolean adicionarLivro(Connection connection);
    boolean atualizarLivro(Connection connection);
    List<? extends Livro> listarLivros(Connection connection);
    boolean pegarEmprestado(Connection connection, int id);
    boolean devolverLivro(Connection connection, int id);
}
