package src;
import java.sql.Connection;

public class FactoryLivroTecnico {
    private static int nextId = 0;
    public LivroTecnico criarLivro(int anoLancamento, String titulo, String area, String nivel) {
        nextId++;  
        return new LivroTecnico(nextId, titulo, anoLancamento, area, nivel, true);
    }

    
    public boolean adicionarLivro(Connection connection, int anoLancamento, String titulo, String area, String nivel) {
        LivroTecnico livro = criarLivro(anoLancamento, titulo, area, nivel);  
        return livro.adicionarLivro(connection);  
    }
}
