import java.sql.*;
import java.util.List;
import java.util.Scanner;

import src.Cliente;
import src.FactoryLivroFiccao;
import src.FactoryLivroTecnico;
import src.Funcionario;
import src.Livro;
import src.LivroFiccao;
import src.LivroTecnico;

public class BibliotecaCLI {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection;

    public static void main(String[] args) {
        try {
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "nova_senha");

            int opcao;
            do {
                System.out.println("=== MENU PRINCIPAL ===");
                System.out.println("1. Clientes");
                System.out.println("2. Funcionários");
                System.out.println("3. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    
                    case 1:
                        menuClientes();
                        break;
                    case 2:
                        menuFuncionarios();
                        break;
                    case 3:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } while (opcao != 4);

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } finally {
            
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
    public static void menuLivros() {
        int opcao;
        do {
            System.out.println("=== MENU LIVROS ===");
            System.out.println("1. Adicionar Livro");
            System.out.println("2. Listar Livros");
            System.out.println("3. Editar Livro Existente");
            System.out.println("4. Excluir Livro de Ficção");
            System.out.println("5. Excluir Livro Tecnico");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    editarLivro();
                    break;
                case 4:
                    excluirLivroFiccao();
                    break;
                case 5:
                    excluirLivroTecnico();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);
    }

    
    public static void menuClientes() {
        int opcao;
        do {
            System.out.println("=== MENU CLIENTES ===");
            System.out.println("1. Pagar Multa");
            System.out.println("2. Lista de todos os livros");
            System.out.println("3. Selecione o Livro de Ficção que deseja pegar emprestado");
            System.out.println("4. Selecione o Livro Tecnico que deseja pegar emprestado");
            System.out.println("5. Selecione o Livro de Ficção que deseja devolver");
            System.out.println("6. Selecione o Livro Tecnico que deseja devolver");
            System.out.println("7. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    pagarMulta();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    pegarEmprestadoLivroFiccao(connection);
                    break;
                case 4:
                    pegarEmprestadoLivroTecnico(connection);
                    break;
                case 5:
                    devolverLivroLivroFiccao(connection);
                    break;
                case 6:
                    devolverLivroLivroTecnico(connection);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 6);
    }

    
    public static void menuFuncionarios() {
        int opcao;
        do {
            System.out.println("=== MENU FUNCIONÁRIOS ===");
            System.out.println("1. Adicionar Funcionário");
            System.out.println("2. Editar Funcionário");
            System.out.println("3. Excluir Funcionário");
            System.out.println("4. Listar Funcionários");
            System.out.println("5. Livros");
            System.out.println("6. Adicionar Cliente");
            System.out.println("7. Editar dados do Cliente");
            System.out.println("7. Excluir dados do Cliente");
            System.out.println("8. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarFuncionario();
                    break;
                case 2:
                    editarFuncionario();
                    break;
                case 3:
                    excluirFuncionario();
                    break;
                case 4:
                    listarFuncionarios();
                    break;
                case 5:
                    menuLivros();
                    break;
                
                case 6:
                    adicionarCliente();
                    break;

                case 7:
                    editarCliente();
                    break;
                
                case 8:
                    excluirCliente();
                    break;
                
                case 9:
                    return; 
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 5);
    }

    
    public static void adicionarLivro() {
        System.out.println("Escolha o tipo de livro:");
        System.out.println("1. Livro Técnico");
        System.out.println("2. Livro de Ficção");
        int tipoLivro = scanner.nextInt();
        scanner.nextLine();

        if (tipoLivro == 1) {
            
            System.out.print("Digite o título: ");
            String titulo = scanner.nextLine();
            System.out.print("Digite o ano de lançamento: ");
            int anoLancamento = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Digite a área: ");
            String area = scanner.nextLine();
            System.out.print("Digite o nível: ");
            String nivel = scanner.nextLine();

            FactoryLivroTecnico factoryTecnico = new FactoryLivroTecnico();
            factoryTecnico.adicionarLivro(connection, anoLancamento, titulo, area, nivel);
        } else if (tipoLivro == 2) {
            
            System.out.print("Digite o título: ");
            String titulo = scanner.nextLine();
            System.out.print("Digite o ano de lançamento: ");
            int anoLancamento = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Digite o gênero: ");
            String genero = scanner.nextLine();

            FactoryLivroFiccao factoryFiccao = new FactoryLivroFiccao();
            factoryFiccao.adicionarLivro(connection, anoLancamento, genero, titulo);
        } else {
            System.out.println("Opção inválida!");
        }
    }
    public static void editarLivro() {
        System.out.println("Escolha o tipo de livro:");
        System.out.println("1. Livro Técnico");
        System.out.println("2. Livro de Ficção");
        int tipoLivro = scanner.nextInt();
        scanner.nextLine();

        if (tipoLivro == 1) {
            System.out.print("Digite o ID do livro que deseja editar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Digite o novo título: ");
            String titulo = scanner.nextLine();
            System.out.print("Digite o novo ano de lançamento: ");
            int anoLancamento = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Digite a nova área: ");
            String area = scanner.nextLine();
            System.out.print("Digite o novo nível: ");
            String nivel = scanner.nextLine();
            
            LivroTecnico LivroTecnico = new LivroTecnico(id, titulo, anoLancamento,area,nivel, true);
            LivroTecnico.atualizarLivro(connection);
            
        } else if (tipoLivro == 2) {
            System.out.print("Digite o ID do livro que deseja editar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Digite o título: ");
            String titulo = scanner.nextLine();
            System.out.print("Digite o ano de lançamento: ");
            int anoLancamento = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Digite o gênero: ");
            String genero = scanner.nextLine();

            LivroFiccao livroFiccao = new LivroFiccao(id,titulo, genero, anoLancamento, true);            
            livroFiccao.atualizarLivro(connection);
            
        } else {
            System.out.println("Opção inválida!");
        }
    }

    public static void listarLivros() {
        System.out.println("Escolha o tipo de livro:");
        System.out.println("1. Livro Técnico");
        System.out.println("2. Livro de Ficção");
        int tipoLivro = scanner.nextInt();
        scanner.nextLine(); 

      if (tipoLivro == 1) {
        LivroTecnico LivroTecnico = new LivroTecnico(0, "", 0, "", "", true);
        List<? extends Livro> livrosTecnicos = LivroTecnico.listarLivros(connection);
        for (Livro livro : livrosTecnicos) {
            System.out.printf("ID: %d | Título: %s | Ano de Lançamento: %d | Área: %s | Nível: %s | Status: %s\n",
                livro.getId(),
                livro.getTitulo(),
                ((LivroTecnico) livro).getAnoLancamento(),  
                ((LivroTecnico) livro).getArea(),
                ((LivroTecnico) livro).getNivel(),
                ((LivroTecnico) livro).isStatus() ? "Ativo" : "Inativo"
            );
        }
} else if (tipoLivro == 2) {
    
    LivroFiccao livroFiccao = new LivroFiccao(0, "", "", 0, true);
    List<? extends Livro> livrosFiccao = livroFiccao.listarLivros(connection);
    for (Livro livro : livrosFiccao) {
        System.out.printf("ID: %d | Título: %s | Genero: %s | Ano de Lançamento: %d |  Status: %s\n",
            livro.getId(),
            livro.getTitulo(),
            ((LivroFiccao) livro).getGenero(),
            ((LivroFiccao) livro).getAnoLancamento(),  
            ((LivroFiccao) livro).isStatus() ? "Ativo" : "Inativo"
        );
    }
} else {
    System.out.println("Opção inválida!");
}
    }

    public static void excluirLivroFiccao() {
        System.out.print("Digite o ID do livro que deseja excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();
    
        LivroFiccao.removerLivro(connection, id);
    }
    public static void excluirLivroTecnico() {
        System.out.print("Digite o ID do livro que deseja excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine();
    
        LivroTecnico.removerLivro(connection, id);

    }


    public static void pegarEmprestadoLivroFiccao(Connection connection) {
        System.out.print("Digite o ID do livro que deseja pegar emprestado: (caso não saiba o ID do seu livro acesse a lista de livros disponiveis) ");
        int idLivro = scanner.nextInt();
        LivroFiccao livroFiccao = new LivroFiccao(0, "", "", 0, true);
        livroFiccao.pegarEmprestado(connection, idLivro);
    }
    public static void pegarEmprestadoLivroTecnico(Connection connection) {
        System.out.print("Digite o ID do livro que deseja pegar emprestado: (caso não saiba o ID do seu livro acesse a lista de livros disponiveis) ");
        int idLivro = scanner.nextInt();
        LivroTecnico LivroTecnico = new LivroTecnico(0, "", 0, "", "", true);
        LivroTecnico.pegarEmprestado(connection, idLivro);
    }

    public static void devolverLivroLivroFiccao(Connection connection) {
        System.out.print("Digite o ID do livro que deseja devolver: (caso não saiba o ID do seu livro acesse a lista de livros disponiveis) ");
        int idLivro = scanner.nextInt();
        LivroFiccao livroFiccao = new LivroFiccao(0, "", "", 0, true);
        livroFiccao.devolverLivro(connection, idLivro);
    }
    public static void devolverLivroLivroTecnico(Connection connection) {
        System.out.print("Digite o ID do livro que deseja devolver: (caso não saiba o ID do seu livro acesse a lista de livros disponiveis) ");
        int idLivro = scanner.nextInt();
        LivroTecnico LivroTecnico = new LivroTecnico(0, "", 0, "", "", true);
        LivroTecnico.devolverLivro(connection, idLivro);
    }
    
    
    public static void adicionarCliente() {
        System.out.print("Digite o nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();  
        if (cpf.length() != 11 || !cpf.matches("[0-9]+")) {
            System.out.println("CPF inválido! Certifique-se de digitar exatamente 11 números.");
            return;
        }
        System.out.print("Digite o e-mail: ");
        String email = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();
        System.out.print("Digite a multa: ");
        int multa = scanner.nextInt();
        scanner.nextLine(); 
    

        Cliente cliente = new Cliente(nome, cpf, email, senha, multa);
        Funcionario.adicionarCliente(connection, cliente);
    }

    public static void editarCliente() {
        System.out.print("Digite o ID do cliente que deseja editar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Digite o novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o novo CPF: ");
        String cpf = scanner.nextLine();  
        
        if (cpf.length() != 11 || !cpf.matches("[0-9]+")) {
            System.out.println("CPF inválido! Certifique-se de digitar exatamente 11 números.");
            return;
        }
        System.out.print("Digite o novo e-mail: ");
        String email = scanner.nextLine();
        System.out.print("Digite a nova senha: ");
        String senha = scanner.nextLine();
        System.out.print("Digite a nova multa: ");
        int multa = scanner.nextInt();
        scanner.nextLine(); 
    
        Cliente.atualizarCliente(connection, id, nome, cpf, email, senha, multa);  
    }

    public static void excluirCliente() {
        System.out.print("Digite o ID do cliente que deseja excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        Cliente.removerCliente(connection, id);
    }

    public static void pagarMulta() {
        System.out.print("Digite o seu cpf para pagar a multa: ");
        String cpf = scanner.nextLine();
        

        Cliente.pagarMulta(connection, cpf);
    }

    public static void listarClientes() {
        Cliente.listarClientes(connection);
    }

    
    public static void adicionarFuncionario() {
        System.out.print("Digite o nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o e-mail: ");
        String email = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        Funcionario funcionario = new Funcionario(nome, senha);
        funcionario.setEmail(email);
        Funcionario.adicionarFuncionario(connection, funcionario);
    }

    public static void editarFuncionario() {
        System.out.print("Digite o ID do funcionário que deseja editar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Digite o novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o novo e-mail: ");
        String email = scanner.nextLine();
        System.out.print("Digite a nova senha: ");
        String senha = scanner.nextLine();

        Funcionario.atualizarFuncionario(connection, id, nome, email, senha);
    }

    public static void excluirFuncionario() {
        System.out.print("Digite o ID do funcionário que deseja excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 

        Funcionario.removerFuncionario(connection, id);
    }

    public static void listarFuncionarios() {
        Funcionario.listarFuncionarios(connection);
    }
}