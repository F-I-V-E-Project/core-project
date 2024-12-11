package src;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroFiccao implements Livro {
    private int id;
    private String titulo;
    private String genero;
    private int anoLancamento;
    private boolean status;

    
    public LivroFiccao(int id, String titulo, String genero, int anoLancamento, boolean status) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public String getGenero() {
        return genero;
    }


    public boolean isStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean adicionarLivro(Connection connection) {
        String sql = "INSERT INTO livros_ficcao (Titulo, Genero, AnoLancamento, Status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.getTitulo());
            pstmt.setString(2, this.getGenero());
            pstmt.setInt(3, this.getAnoLancamento());
            pstmt.setBoolean(4, this.isStatus());
            pstmt.executeUpdate();
            System.out.println("Livro de ficção adicionado com sucesso.");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar o livro de ficção: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean atualizarLivro(Connection connection) {
        String sql = "UPDATE livros_ficcao SET Titulo = ?, Genero = ?, AnoLancamento = ?, Status = ? WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.getTitulo());
            pstmt.setString(2, this.getGenero());
            pstmt.setInt(3, this.getAnoLancamento());
            pstmt.setBoolean(4, this.isStatus());
            pstmt.setInt(5, this.getId());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Livro de ficção atualizado com sucesso.");
                return true;
            } else {
                System.out.println("Livro com ID " + this.getId() + " não encontrado.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o livro de ficção: " + e.getMessage());
            return false;
        }
    }

    public static boolean removerLivro(Connection connection, int id) {
        String sql = "DELETE FROM livros_ficcao WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Livro de Ficção removido com sucesso.");
                return true;
            } else {
                System.out.println("Livro com ID " + id + " não encontrado.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover o Livro de Ficção: " + e.getMessage());
            return false;
        }
    }

    public List<? extends Livro> listarLivros(Connection connection) {
        String sql = "SELECT * FROM livros_ficcao";
        List<LivroFiccao> livrosFiccao = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LivroFiccao livro = new LivroFiccao(
                        rs.getInt("Id"),
                        rs.getString("Titulo"),
                        rs.getString("Genero"),
                        rs.getInt("AnoLancamento"),
                        rs.getBoolean("Status")
                );
                livrosFiccao.add(livro);
            }
            return livrosFiccao;
        } catch (SQLException e) {
            System.err.println("Erro ao listar os livros de ficção: " + e.getMessage());
            return livrosFiccao;
        }
    }

    public boolean pegarEmprestado(Connection connection, int id) {
        String sql = "UPDATE livros_ficcao SET Status = 1 WHERE Id = ? AND Status = 0";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Livro emprestado com sucesso!");
                return true;
            } else {
                System.out.println("Livro não disponível ou ID inválido.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao pegar o livro emprestado: " + e.getMessage());
            return false;
        }
    }

    public boolean devolverLivro(Connection connection, int id) {
        String sql = "UPDATE livros_ficcao SET Status = 0 WHERE Id = ? AND Status = 1"; 
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Livro devolvido com sucesso!");
                return true;
            } else {
                System.out.println("Livro não encontrado, Já devolvido ou ID inválido.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao devolver o livro: " + e.getMessage());
            return false;
        }
    }
}
