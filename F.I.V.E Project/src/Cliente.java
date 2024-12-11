package src;
import java.sql.*;

public class Cliente {
    private static int nextId = 1;
    private int id;
    private String nomeCompleto;
    private String cpf;  
    private String email;
    private String senha;
    private int multa;

    public Cliente(String nomeCompleto, String cpf, String email, String senha, int multa) { 
        this.id = nextId++;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;  
        this.email = email;
        this.senha = senha;
        this.multa = multa;
    }

    public int getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) { 
        this.cpf = cpf;  
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getMulta() {
        return multa;
    }

    public void setMulta(int multa) {
        this.multa = multa;
    }

    
    public static void listarClientes(Connection connection) {
        String sql = "SELECT * FROM clientes";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d | Nome: %s | CPF: %s | Email: %s | Multa: %d\n",  
                    rs.getInt("Id"), rs.getString("NomeCompleto"), rs.getString("CPF"), rs.getString("Email"), rs.getInt("Multa"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os clientes: " + e.getMessage());
        }
    }

    
    public static void atualizarCliente(Connection connection, int id, String novoNome, String novoCpf, String novoEmail, String novaSenha, int novaMulta) {
        String sql = "UPDATE clientes SET NomeCompleto = ?, CPF = ?, Email = ?, Senha = ?, Multa = ? WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novoCpf); 
            pstmt.setString(3, novoEmail);
            pstmt.setString(4, novaSenha);
            pstmt.setInt(5, novaMulta);
            pstmt.setInt(6, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cliente atualizado com sucesso.");
            } else {
                System.out.println("Cliente com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o cliente: " + e.getMessage());
        }
    }

    
    public static void removerCliente(Connection connection, int id) {
        String sql = "DELETE FROM clientes WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Cliente removido com sucesso.");
            } else {
                System.out.println("Cliente com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover o cliente: " + e.getMessage());
        }
    }

    
    public static void pagarMulta(Connection connection, String cpf) {
        String sql = "UPDATE clientes SET Multa = 0 WHERE cpf = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Multa paga com sucesso para o cliente com cpf " + cpf);
            } else {
                System.out.println("Cliente com cpf " + cpf + " não encontrado ou já não possui multa.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao pagar a multa: " + e.getMessage());
        }
    }
}
