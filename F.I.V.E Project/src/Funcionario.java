package src;
import java.sql.*;
public class Funcionario {

    private static int nextId = 1;
    private int Id;
    private String NomeCompleto;
    private String Senha;
    private String Email;


    
    public int getId() {
        return this.Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNomeCompleto() {
        return NomeCompleto;
    }

    public void setNomeCompleto(String NomeCompleto) {
        this.NomeCompleto = NomeCompleto;
    }
    
    public String getSenha() {
        return Senha;
    }
    
    public void setSenha(String Senha) {
        this.Senha = Senha;
    }
    
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Funcionario(String NomeCompleto, String Senha){
        this.Id = nextId;
        this.NomeCompleto = NomeCompleto;
        this.Senha = Senha;
        nextId++;
    }

    
  
    public static void adicionarCliente(Connection connection, Cliente cliente) {
        String sql = "INSERT INTO clientes (NomeCompleto, CPF, Email, Senha, Multa) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNomeCompleto());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setString(4, cliente.getSenha());
            pstmt.setInt(5, cliente.getMulta());
            pstmt.executeUpdate();
            System.out.println("Cliente adicionado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar o cliente: " + e.getMessage());
        }
    }

    
    public static void adicionarFuncionario(Connection connection, Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (NomeCompleto, Email, Senha) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, funcionario.getNomeCompleto());
            pstmt.setString(2, funcionario.getEmail());
            pstmt.setString(3, funcionario.getSenha());
            pstmt.executeUpdate();
            System.out.println("Funcionário adicionado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar o funcionário: " + e.getMessage());
        }
    }

    public static void listarFuncionarios(Connection connection) {
        String sql = "SELECT * FROM funcionarios";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d | Nome: %s | Email: %s\n",
                    rs.getInt("Id"), rs.getString("NomeCompleto"), rs.getString("Email"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os funcionários: " + e.getMessage());
        }
    }

    public static void atualizarFuncionario(Connection connection, int id, String novoNome, String novoEmail, String novaSenha) {
        String sql = "UPDATE funcionarios SET NomeCompleto = ?, Email = ?, Senha = ? WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novoEmail);
            pstmt.setString(3, novaSenha);
            pstmt.setInt(4, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Funcionário atualizado com sucesso.");
            } else {
                System.out.println("Funcionário com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o funcionário: " + e.getMessage());
        }
    }

    public static void removerFuncionario(Connection connection, int id) {
        String sql = "DELETE FROM funcionarios WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Funcionário removido com sucesso.");
            } else {
                System.out.println("Funcionário com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover o funcionário: " + e.getMessage());
        }
    }
}
