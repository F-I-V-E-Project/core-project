package src;
import java.sql.*;
public class FactoryLivroFiccao  {
    private static int nextId = 0;

    public LivroFiccao CriarLivro(int anoLancamento, String genero, String titulo) {
        nextId++;
        return new LivroFiccao(nextId, titulo, genero, anoLancamento, true);  
    }

    public boolean adicionarLivro(Connection connection, int anoLancamento, String genero, String titulo) {
        LivroFiccao livro = CriarLivro(anoLancamento, genero, titulo);  
        return livro.adicionarLivro(connection);  
    }
}