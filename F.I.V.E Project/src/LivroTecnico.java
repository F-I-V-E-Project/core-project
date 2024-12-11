package src;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroTecnico implements Livro {

    private int id;
    private int anoLancamento;
    private String area;
    private String nivel;
    private boolean status;
    private String titulo;

    public LivroTecnico(int id, String titulo, int anoLancamento, String area, String nivel, boolean status) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.area = area;
        this.nivel = nivel;
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

    public String getArea() {
        return area;
    }

    public String getNivel() {
        return nivel;
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

    public void setArea(String area) {
        this.area = area;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean adicionarLivro(Connection connection) {
        String sql = "INSERT INTO livros_tecnicos (Titulo, AnoLancamento, Area, Nivel, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.getTitulo());
            pstmt.setInt(2, this.getAnoLancamento());
            pstmt.setString(3, this.getArea());
            pstmt.setString(4, this.getNivel());
            pstmt.setBoolean(5, this.isStatus());
            pstmt.executeUpdate();
            System.out.println("Livro técnico adicionado com sucesso.");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar o livro técnico: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean atualizarLivro(Connection connection) {
        String sql = "UPDATE livros_tecnicos SET Titulo = ?, AnoLancamento = ?, Area = ?, Nivel = ?, Status = ? WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, this.getTitulo());
            pstmt.setInt(2, this.getAnoLancamento());
            pstmt.setString(3, this.getArea());
            pstmt.setString(4, this.getNivel());
            pstmt.setBoolean(5, this.isStatus());
            pstmt.setInt(6, this.getId());
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Livro técnico atualizado com sucesso.");
                return true;
            } else {
                System.out.println("Livro técnico com ID " + this.getId() + " não encontrado.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o livro técnico: " + e.getMessage());
            return false;
        }
    }

    
    public static boolean removerLivro(Connection connection, int id) {
        String sql = "DELETE FROM livros_tecnicos WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Livro Teccnico removido com sucesso.");
                return true;
            } else {
                System.out.println("Livro com ID " + id + " não encontrado.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover o Livro Teccnico " + e.getMessage());
            return false;
        }
    }

    @Override
    public  List<? extends Livro> listarLivros(Connection connection) {
        String sql = "SELECT * FROM livros_tecnicos";
        List<LivroTecnico> livrosTecnicos = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LivroTecnico livro = new LivroTecnico(
                        rs.getInt("Id"),
                        rs.getString("Titulo"),
                        rs.getInt("AnoLancamento"),
                        rs.getString("Area"),
                        rs.getString("Nivel"),
                        rs.getBoolean("Status")
                );
                livrosTecnicos.add(livro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os livros técnicos: " + e.getMessage());
        }
        return livrosTecnicos;
    }

    public boolean pegarEmprestado(Connection connection, int id) {
        String sql = "UPDATE livros_tecnicos SET Status = 1 WHERE Id = ? AND Status = 0";
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
        String sql = "UPDATE livros_tecnicos SET Status = 0 WHERE Id = ? AND Status = 1"; 
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsUpdated = pstmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                System.out.println("Livro devolvido com sucesso!");
                return true;
            } else {
                System.out.println("Livro não encontrado, Já devolvido ou ID inválido.\"");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao devolver o livro: " + e.getMessage());
            return false;
        }
    }
}
