import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static List<Usuario> listaUsuarios = new ArrayList<>();
    
    public static String usuarioLogin = "";
    public static String tipoLogin = ""; 
    // Criando instância da Agenda
    public static Agenda agenda = new Agenda();

    public static void main(String[] args) {
        // Lista de usuários
        popularListaExemplo();
       
        // Inclui dados de exemplo na Agenda
        agenda.populaAgenda();
        
        loginCliente();
    }
    

    public static boolean continuarAgendando(boolean usuario) {
        String resposta = "S";
        if (usuario == true) {
            // Pergunta se o usuário deseja fazer mais agendamentos
            Scanner scanner = new Scanner(System.in);
            System.out.print("Deseja fazer mais agendamentos? (S para Sim, N para Não): ");
            resposta = scanner.nextLine().toUpperCase();
        }
        return resposta.equals("S");
    }

    public static int lerOpcao(int opcaoMinima, int opcaoMaxima) {
        // Lê a opção do usuário e verifica se está dentro do intervalo permitido
        Scanner scanner = new Scanner(System.in);

        int opcao;
        do {
            System.out.print("Escolha uma opção: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, escolha uma opção válida: ");
                scanner.next();
            }
            opcao = scanner.nextInt();
        } while (opcao < opcaoMinima || opcao > opcaoMaxima);

        return opcao;
    }    
    

    public static String getUsuario() {
        return usuarioLogin;
    }

    public static void setUsuario(String usuario) {
        usuarioLogin = usuario;
    }

    public static String getTipo() {
        return tipoLogin;
    }

    public static void setTipo(String tipo) {
        tipoLogin = tipo;
    }

    public static void loginCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o usuário: ");
        String usuario = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        //System.out.print("Digite o(s) tipo(s) separado(s) por vírgula (cliente, médico, gestor): ");
        System.out.print("Digite o tipo (cliente, medico, gestor): ");
        String tipoInput = scanner.nextLine();
        List<String> tipos = Arrays.asList(tipoInput.split("\\s*,\\s*"));


        if (validarUsuario(usuario, senha, tipos)) {
            // Perguntando se deseja fazer mais agendamentos
            //while (continuarAgendando(usuarioLogin.length() > 0)) {   
                setUsuario(usuario);
                setTipo(tipoInput);                
                if (tipos.contains("gestor")) {
                    cadastrarUsuario();
                } else if (tipos.contains("cliente")) {
                    agenda.editaAgenda("", usuario);
                } else if (tipos.contains("medico")) {
                    agenda.editaAgenda(usuario, "");
                }
            //}
        } else {
            setUsuario("");
            setTipo("");            
            System.out.println("Usuário não encontrado ou senha incorreta.");
        }

            // Opções após terminar agendamentos
            System.out.println("\nOpções:");
            System.out.println("1. Continuar");
            System.out.println("2. Trocar de usuário");
            System.out.println("3. Sair do Aplicativo");

            int opcao = lerOpcao(1, 2);
         
             if (opcao == 1) {
                 if (validarUsuario(usuario, senha, tipos)) {
                 // Perguntando se deseja fazer mais agendamentos
                 //while (continuarAgendando(usuarioLogin.length() > 0)) {   
                     setUsuario(usuario);
                     setTipo(tipoInput);                
                     if (tipos.contains("gestor")) {
                          cadastrarUsuario();
                     } else if (tipos.contains("cliente")) {
                          agenda.editaAgenda("", usuario);
                     } else if (tipos.contains("medico")) {
                          agenda.editaAgenda(usuario, "");
                     }
            //}
                 } else {
                     System.out.println("Usuário não encontrado ou senha incorreta.");
                     loginCliente();
                 }
             } else if (opcao == 2) {
                // Fazer logout
                // Chama a rotina de login novamente
                loginCliente();
                //agendaCliente(agenda, clienteLogado);
            } else {
                // Sair do aplicativo
                System.out.println("Saindo do aplicativo. Até mais!");
            }


    }

    public static void popularListaExemplo() {
        adicionarUsuario(new Usuario("cliente1", "senhacliente1", Arrays.asList("cliente"), "Nome Cliente 1", "cliente1@email.com", "123456789", ""));
        adicionarUsuario(new Usuario("cliente2", "senhacliente2", Arrays.asList("cliente"), "Nome Cliente 2", "cliente2@email.com", "123456780", ""));
        adicionarUsuario(new Usuario("medico1", "senhamedico1", Arrays.asList("medico"), "Nome Médico 1", "medico1@email.com", "987654321", "123"));
        adicionarUsuario(new Usuario("medico2", "senhamedico2", Arrays.asList("medico"), "Nome Médico 2", "medico2@email.com", "987654320", "120"));
        adicionarUsuario(new Usuario("gestor1", "senhagestor1", Arrays.asList("gestor"), "Nome Gestor 1", "gestor1@email.com", "456789123", ""));
    }

    public static void adicionarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }

    private static boolean validarUsuario(String usuario, String senha, List<String> tipos) {
        for (Usuario usuarioArmazenado : listaUsuarios) {
            if (usuario.equals(usuarioArmazenado.getUsuario())
                    && senha.equals(usuarioArmazenado.getSenha())
                    && contemPeloMenosUmTipo(usuarioArmazenado.getTipos(), tipos)) {
                return true;
            }
        }
        return false;
    }

    private static boolean contemPeloMenosUmTipo(List<String> tiposUsuario, List<String> tiposInformados) {
        for (String tipoInformado : tiposInformados) {
            if (tiposUsuario.contains(tipoInformado)) {
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

            System.out.print("Digite o CRM (opcional, ignorado se tiver menos de 3 caracteres): ");
            String novoCrm = obterDadoValido(scanner.nextLine());

            System.out.print("Digite a nova senha (opcional, ignorada se tiver menos de 3 caracteres): ");
            String novaSenha = obterDadoValido(scanner.nextLine());

            Usuario novoUsuario = new Usuario(usuarioInput, novaSenha, tipos, novoNome, novoEmail, novoTelefone, novoCrm);
            adicionarUsuario(novoUsuario);

            System.out.println("Usuário cadastrado com sucesso.");
        } else {
            // Apresenta os dados do Usuário encontrado
            usuarioExistente.imprimirDados();
            // Usuário existe, perguntar se é alteração ou exclusão
            System.out.println("Usuário encontrado. Deseja realizar uma alteração ou exclusão?");
            System.out.print("Digite 'A' para alteração, 'E' para exclusão: ");
            String opcao = scanner.nextLine();

            if (opcao.equalsIgnoreCase("A")) {
                // Alteração - solicitar dados não obrigatórios
                System.out.println("Preencha os dados abaixo para alteração (deixe em branco para manter o valor atual):");

                System.out.print("Digite o(s) tipo(s) separado(s) por vírgula (cliente, medico, gestor): ");
                String tipoInput = scanner.nextLine();
                List<String> tipos = Arrays.asList(tipoInput.split("\\s*,\\s*"));

                System.out.print("Digite o novo nome (ignorado se tiver menos de 3 caracteres): ");
                String novoNome = obterDadoValido(scanner.nextLine());

                System.out.print("Digite o novo e-mail (ignorado se tiver menos de 3 caracteres): ");
                String novoEmail = obterDadoValido(scanner.nextLine());

                System.out.print("Digite o novo telefone (ignorado se tiver menos de 3 caracteres): ");
                String novoTelefone = obterDadoValido(scanner.nextLine());

                System.out.print("Digite o CRM (opcional, ignorado se tiver menos de 3 caracteres): ");
                String novoCrm = obterDadoValido(scanner.nextLine());

                System.out.print("Digite a nova senha (opcional, ignorada se tiver menos de 3 caracteres): ");
                String novaSenha = obterDadoValido(scanner.nextLine());

                // Atualizar os dados
                usuarioExistente.atualizarDados(tipos, novoNome, novoEmail, novoTelefone, novoCrm, novaSenha);

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

}

class Usuario {
    private String usuario;
    private String senha;
    private List<String> tipos;
    private String nome;
    private String email;
    private String telefone;
    private String crm; // Novo campo opcional

    public Usuario(String usuario, String senha, List<String> tipos, String nome, String email, String telefone, String crm) {
        this.usuario = usuario;
        this.senha = senha;
        this.tipos = tipos;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.crm = crm;
    }

    // Getters e setters para o novo campo 'crm'

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

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public void atualizarDados(List<String> novosTipos, String novoNome, String novoEmail, String novoTelefone, String novaSenha, String novoCrm) {
        // Atualizar os dados apenas se não estiverem vazios
        this.tipos = (novosTipos.size() > 0) ? novosTipos : this.tipos;
        this.nome = (novoNome.length() >= 3) ? novoNome : this.nome;
        this.email = (novoEmail.length() >= 3) ? novoEmail : this.email;
        this.telefone = (novoTelefone.length() >= 3) ? novoTelefone : this.telefone;
        this.crm = (novoCrm.length() >= 3) ? novoCrm : this.crm;
        this.senha = (novaSenha.length() >= 3) ? novaSenha : this.senha;
    }

    public void imprimirDados() {
        System.out.println("Dados do Usuário:");
        System.out.println("Usuário: " + usuario);
        System.out.println("Tipos: " + tipos.toString());
        System.out.println("Nome: " + nome);
        System.out.println("E-mail: " + email);
        System.out.println("Telefone: " + telefone);
        if (crm != null) {
            System.out.println("CRM: " + crm);
        }
        // A senha não é impressa por razões de segurança
    }
}

class Agenda {
    private static int sequencial = 1;
    private int numero;
    private String usuarioMedico;
    private String usuarioCliente; // Campo opcional
    private Date data;
    private Date hora;

    private static List<Agenda> listaAgenda = new ArrayList<>();
    private static List<String> listaUsuarios = Arrays.asList("medico1", "medico2", "cliente1", "cliente2"); // Usuários de exemplo

    public Agenda() {
        this.numero = sequencial++;
    }

    public Agenda(String usuarioMedico, String usuarioCliente, String data, String hora) throws ParseException {
        this(); // Chama o construtor padrão para inicializar o número sequencial
        this.usuarioMedico = usuarioMedico;
        this.usuarioCliente = usuarioCliente;
        this.data = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        this.hora = new SimpleDateFormat("HH:mm").parse(hora);
    }

    public void populaAgenda() {
        try {        
           // Exemplo de dados
           listaAgenda.add(new Agenda("medico1", "cliente1", "15/11/2023", "14:30"));
           listaAgenda.add(new Agenda("medico1", "", "15/11/2023", "15:30"));
           listaAgenda.add(new Agenda("medico1", "cliente2", "16/11/2023", "16:00"));
        } catch (ParseException e) {
            System.out.println("Erro na formatação da data ou hora.");
        }        
    }

    public void editaAgenda(String medico, String cliente) {
        usuarioMedico = medico;
        usuarioCliente = cliente;
        if (usuarioMedico.length() > 0) {
            agendaMedico(usuarioMedico);
        } else {
            agendaCliente(usuarioCliente);
        }
            

        // Exemplo de uso do CRUD simplificado
        System.out.println("\n--- CRUD Simplificado ---");
        System.out.print("Escolha uma opção (I para incluir, E para excluir, A para alterar): ");
        Scanner scanner = new Scanner(System.in);
        char opcao = scanner.nextLine().toUpperCase().charAt(0);

        switch (opcao) {
            case 'I':
                incluirEvento(usuarioMedico, usuarioCliente);
                break;
            case 'E':
                excluirEvento();
                break;
            case 'A':
                alterarEvento();
                break;
            default:
                System.out.println("Opção inválida.");
        }

        // Exibindo a lista após operações CRUD
        exibirListaOrdenada();
        
        
    }

    private static void agendaMedico(String usuario) {
        System.out.println("\n--- Agenda do Médico ---");
        //List<Agenda> agendaMedico = new ArrayList<>();

        //for (Agenda evento : listaAgenda) {
        //    if (evento.getUsuarioMedico().equals(usuario)) {
        //        agendaMedico.add(evento);
        //    }
        //}

        // Ordenar por data e hora
        //Collections.sort(agendaMedico, Comparator.comparing(Agenda::getData).thenComparing(Agenda::getHora));

        // Imprimir em formato de tabela numerada
        System.out.printf("%-5s%-15s%-15s%-15s%-10s\n", "Num", "Usuário Médico", "Usuário Cliente", "Data", "Hora");
        for (Agenda evento : listaAgenda) { //agendaMedico
            if (evento.getUsuarioMedico().equals(usuario)) {
                System.out.printf("%-5d%-15s%-15s%-15s%-10s\n", evento.getNumero(), evento.getUsuarioMedico(),
                    evento.getUsuarioCliente(), new SimpleDateFormat("dd/MM/yyyy").format(evento.getData()),
                    new SimpleDateFormat("HH:mm").format(evento.getHora()));
            }
        }
    }

    private static void agendaCliente(String usuario) {
        System.out.println("\n--- Agenda do Cliente ---");
        //List<Agenda> agendaCliente = new ArrayList<>();

        //for (Agenda evento : listaAgenda) {
        //    if (evento.getUsuarioCliente().equals(usuario)) {
        //        agendaCliente.add(evento);
        //    }
        //}

        // Ordenar por data e hora
        //Collections.sort(agendaCliente, Comparator.comparing(Agenda::getData).thenComparing(Agenda::getHora));

        // Imprimir em formato de tabela numerada
        System.out.printf("%-5s%-15s%-15s%-15s%-10s\n", "Num", "Usuário Médico", "Usuário Cliente", "Data", "Hora");
        for (Agenda evento : listaAgenda) { //agendaCliente
            if (evento.getUsuarioCliente().equals(usuario)) {
                System.out.printf("%-5d%-15s%-15s%-15s%-10s\n", evento.getNumero(), evento.getUsuarioMedico(),
                    evento.getUsuarioCliente(), new SimpleDateFormat("dd/MM/yyyy").format(evento.getData()),
                    new SimpleDateFormat("HH:mm").format(evento.getHora()));
            }
        }
    }

    private static void incluirEvento(String medico, String cliente) {
        String usuarioMedico = medico;
        String usuarioCliente = cliente;
        
        Scanner scanner = new Scanner(System.in);
        if (medico == "") {
           System.out.print("Digite o usuário médico: ");
           usuarioMedico = scanner.nextLine();
           agendaMedico(usuarioMedico);
        } else {
           agendaMedico(usuarioMedico); 
        }
        if (cliente == "") { 
           System.out.print("Digite o usuário cliente (opcional, pressione Enter para deixar em branco): ");
           usuarioCliente = scanner.nextLine();
        } 
        
        System.out.print("Digite a data (DD/MM/YYYY): ");
        String data = scanner.nextLine();

        System.out.print("Digite a hora (HH:MM): ");
        String hora = scanner.nextLine();

        try {
            Agenda novoEvento = new Agenda(usuarioMedico, usuarioCliente, data, hora);
            listaAgenda.add(novoEvento);
            System.out.println("Evento incluído com sucesso.");
        } catch (ParseException e) {
            System.out.println("Erro na formatação da data ou hora.");
        }
    }

    private static void excluirEvento() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o número do evento a ser excluído: ");
        int numeroEvento = Integer.parseInt(scanner.nextLine());

        for (Agenda evento : listaAgenda) {
            if (evento.getNumero() == numeroEvento) {
                listaAgenda.remove(evento);
                System.out.println("Evento excluído com sucesso.");
                return;
            }
        }

        System.out.println("Evento não encontrado.");
    }

    private static void alterarEvento() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o número do evento a ser alterado: ");
        int numeroEvento = Integer.parseInt(scanner.nextLine());

        for (Agenda evento : listaAgenda) {
            if (evento.getNumero() == numeroEvento) {
                System.out.print("Digite a nova data (DD/MM/YYYY): ");
                String novaData = scanner.nextLine();

                System.out.print("Digite a nova hora (HH:MM): ");
                String novaHora = scanner.nextLine();

                try {
                    evento.setData(new SimpleDateFormat("dd/MM/yyyy").parse(novaData));
                    evento.setHora(new SimpleDateFormat("HH:mm").parse(novaHora));

                    System.out.println("Evento alterado com sucesso.");
                } catch (ParseException e) {
                    System.out.println("Erro na formatação da data ou hora.");
                }

                return;
            }
        }

        System.out.println("Evento não encontrado.");
    }

    private static void exibirListaOrdenada() {
        // Exibir a lista ordenada após operações CRUD
        System.out.println("\n--- Lista Atualizada ---");
        // Imprimir em formato de tabela numerada
        System.out.printf("%-5s%-15s%-15s%-15s%-10s\n", "Num", "Usuário Médico", "Usuário Cliente", "Data", "Hora");
        for (Agenda evento : listaAgenda) {
            System.out.printf("%-5d%-15s%-15s%-15s%-10s\n", evento.getNumero(), evento.getUsuarioMedico(),
                    evento.getUsuarioCliente(), new SimpleDateFormat("dd/MM/yyyy").format(evento.getData()),
                    new SimpleDateFormat("HH:mm").format(evento.getHora()));
        }
    }

    // Getters e setters
    public int getNumero() {
        return numero;
    }

    public String getUsuarioMedico() {
        return usuarioMedico;
    }

    public String getUsuarioCliente() {
        return usuarioCliente;
    }

    public Date getData() {
        return data;
    }

    public Date getHora() {
        return hora;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
}

