import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaCadastro {

    private static List<Usuario> listaUsuarios = new ArrayList<>();

    public static void main(String[] args) {
        popularListaExemplo();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o usuário: ");
        String usuario = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        System.out.print("Digite o tipo (cliente, médico, gestor): ");
        String tipo = scanner.nextLine();

        if (validarUsuario(usuario, senha, tipo)) {
            if (tipo.equals("gestor")) {
                cadastrarUsuario();
            } else if (tipo.equals("cliente")) {
                agendaCliente();
            } else if (tipo.equals("médico")) {
                agendaMedico();
            }
        } else {
            System.out.println("Usuário não encontrado ou senha incorreta.");
        }
    }

    private static void popularListaExemplo() {
        adicionarUsuario(new Usuario("cliente1", "senhacliente1", "cliente"));
        adicionarUsuario(new Usuario("medico1", "senhamedico1", "médico"));
        adicionarUsuario(new Usuario("gestor1", "senhagestor1", "gestor"));
    }

    private static void adicionarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    private static boolean validarUsuario(String usuario, String senha, String tipo) {
        for (Usuario usuarioArmazenado : listaUsuarios) {
            if (usuario.equals(usuarioArmazenado.getUsuario())
                    && senha.equals(usuarioArmazenado.getSenha())
                    && tipo.equals(usuarioArmazenado.getTipo())) {
                return true;
            }
        }
        return false;
    }

    private static void cadastrarUsuario() {
        System.out.println("Chamando rotina de cadastro de usuário para gestor...");
        // Implemente a lógica de cadastro de usuário para gestor aqui
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
    private String tipo;

    public Usuario(String usuario, String senha, String tipo) {
        this.usuario = usuario;
        this.senha = senha;
        this.tipo = tipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }
}

