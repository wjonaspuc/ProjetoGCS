import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Usuario> listaUsuarios = new ArrayList<>();

    public static void main(String[] args) {
        popularListaExemplo();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o usuário: ");
        String usuario = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        System.out.print("Digite o(s) tipo(s) separado(s) por vírgula (cliente, médico, gestor): ");
        String tipoInput = scanner.nextLine();
        List<String> tipos = Arrays.asList(tipoInput.split("\\s*,\\s*"));

        if (validarUsuario(usuario, senha, tipos)) {
            if (tipos.contains("gestor")) {
                cadastrarUsuario();
            } else if (tipos.contains("cliente")) {
                agendaCliente();
            } else if (tipos.contains("médico")) {
                agendaMedico();
            }
        } else {
            System.out.println("Usuário não encontrado ou senha incorreta.");
        }
    }

    private static void popularListaExemplo() {
        adicionarUsuario(new Usuario("cliente1", "senhacliente1", Arrays.asList("cliente"), "Nome Cliente 1", "cliente1@email.com", "123456789"));
        adicionarUsuario(new Usuario("medico1", "senhamedico1", Arrays.asList("médico"), "Nome Médico 1", "medico1@email.com", "987654321"));
        adicionarUsuario(new Usuario("gestor1", "senhagestor1", Arrays.asList("gestor"), "Nome Gestor 1", "gestor1@email.com", "456789123"));
    }

    private static void adicionarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    private static boolean validarUsuario(String usuario, String senha, List<String> tipos) {
        for (Usuario usuarioArmazenado : listaUsuarios) {
            if (usuario.equals(usuarioArmazenado.getUsuario())
                    && senha.equals(usuarioArmazenado.getSenha())
                    && usuarioArmazenado.getTipos().containsAll(tipos)) {
                return true;
            }
        }
        return false;
    }

    private static void cadastrarUsuario() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o usuário que deseja cadastrar, alterar ou excluir: ");
        String usuarioInput = scanner.nextLine();

        Usuario usuarioExistente = buscarUsuarioPorUsuario(usuarioInput);

        if (usuarioExistente == null) {
            // Usuário não existe, solicitar todos os dados
            System.out.println("Usuário não encontrado. Preencha os dados abaixo:");

            System.out.print("Digite o(s) tipo(s) separado(s) por vírgula (cliente, médico, gestor): ");
            String tipoInput = scanner.nextLine();
            List<String> tipos = Arrays.asList(tipoInput.split("\\s*,\\s*"));

            System.out.print("Digite o novo nome (ignorado se tiver menos de 3 caracteres): ");
            String novoNome = obterDadoValido(scanner.nextLine());

            System.out.print("Digite o novo e-mail (ignorado se tiver menos de 3 caracteres): ");
            String novoEmail = obterDadoValido(scanner.nextLine());

            System.out.print("Digite o novo telefone (ignorado se tiver menos de 3 caracteres): ");
            String novoTelefone = obterDadoValido(scanner.nextLine());

            System.out.print("Digite a nova senha (opcional, ignorada se tiver menos de 3 caracteres): ");
            String novaSenha = obterDadoValido(scanner.nextLine());

            Usuario novoUsuario = new Usuario(usuarioInput, novaSenha, tipos, novoNome, novoEmail, novoTelefone);
            adicionarUsuario(novoUsuario);

            System.out.println("Usuário cadastrado com sucesso.");
        } else {
            // Usuário existe, perguntar se é alteração ou exclusão
            System.out.println("Usuário encontrado. Deseja realizar uma alteração ou exclusão?");
            System.out.print("Digite 'A' para alteração, 'E' para exclusão: ");
            String opcao = scanner.nextLine();

            if (opcao.equalsIgnoreCase("A")) {
                // Alteração - solicitar dados não obrigatórios
                System.out.println("Preencha os dados abaixo para alteração (deixe em branco para manter o valor atual):");

                System.out.print("Digite o(s) tipo(s) separado(s) por vírgula (cliente, médico, gestor): ");
                String tipoInput = scanner.nextLine();
                List<String> tipos = Arrays.asList(tipoInput.split("\\s*,\\s*"));

                System.out.print("Digite o novo nome (ignorado se tiver menos de 3 caracteres): ");
                String novoNome = obterDadoValido(scanner.nextLine());

                System.out.print("Digite o novo e-mail (ignorado se tiver menos de 3 caracteres): ");
                String novoEmail = obterDadoValido(scanner.nextLine());

                System.out.print("Digite o novo telefone (ignorado se tiver menos de 3 caracteres): ");
                String novoTelefone = obterDadoValido(scanner.nextLine());

                System.out.print("Digite a nova senha (opcional, ignorada se tiver menos de 3 caracteres): ");
                String novaSenha = obterDadoValido(scanner.nextLine());

                // Atualizar os dados
                usuarioExistente.atualizarDados(tipos, novoNome, novoEmail, novoTelefone, novaSenha);

                System.out.println("Usuário alterado com sucesso.");
            } else if (opcao.equalsIgnoreCase("E")) {
                // Exclusão
                listaUsuarios.remove(usuarioExistente);
                System.out.println("Usuário excluído com sucesso.");
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

    private static Usuario buscarUsuarioPorUsuario(String usuario) {
        for (Usuario usuarioArmazenado : listaUsuarios) {
            if (usuario.equals(usuarioArmazenado.getUsuario())) {
                return usuarioArmazenado;
            }
        }
        return null;
    }

    private static String obterDadoValido(String dado) {
        // Ignorar dados com menos de 3 caracteres
        return (dado.length() >= 3) ? dado : "";
    }

    private static void agendaCliente() {
        System.out.println("Chamando rotina de agenda de cliente...");
        // Implemente a lógica de agenda de cliente aqui
    }

    private static void agendaMedico() {
        System.out.println("Chamando rotina de agenda de médico...");
        // Implemente a lógica de agenda de médico aqui
    }
}

class Usuario {
    private String usuario;
    private String senha;
    private List<String> tipos;
    private String nome;
    private String email;
    private String telefone;

    public Usuario(String usuario, String senha, List<String> tipos, String nome, String email, String telefone) {
        this.usuario = usuario;
        this.senha = senha;
        this.tipos = tipos;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public List<String> getTipos() {
        return tipos;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void atualizarDados(List<String> novosTipos, String novoNome, String novoEmail, String novoTelefone, String novaSenha) {
        // Atualizar os dados apenas se não estiverem vazios
        this.tipos = (novosTipos.size() > 0) ? novosTipos : this.tipos;
        this.nome = (novoNome.length() >= 3) ? novoNome : this.nome;
        this.email = (novoEmail.length() >= 3) ? novoEmail : this.email;
        this.telefone = (novoTelefone.length() >= 3) ? novoTelefone : this.telefone;
        this.senha = (novaSenha.length() >= 3) ? novaSenha : this.senha;
    }
}
