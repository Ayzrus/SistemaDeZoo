package zoo;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.spec.KeySpec;

public class Zoo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Email para login");
        String Email = scanner.next();
        System.out.println("Password");
        String Password = scanner.next();
        boolean flagLogin = false;
        try {
            flagLogin = Login(Password, Email);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        if (flagLogin) {
            while (true) {
                System.out.println("""
                                1 - Registo de animais
                                2 - Registo de medico veterinario
                                3 - Agendamento de consulta
                                4 - Registo de consulta
                                5 - Historico doenças de animais
                                6 - Quantidade de registo por doença
                                7 - Consulta de ficha animal
                                8 - Remover Animal
                                9 - Remover Veterinário
                                """);
                System.out.print("Escolha uma opcao: ");
                int escolha = scanner.nextInt();
                switch (escolha) {
                    case 0 -> {
                        System.out.println("Saindo...");
                        break;
                    }
                    case 1 -> {
                        registrarAnimal(scanner);
                    }
                    case 2 -> {
                        try {
                            registrarMedico(scanner);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeySpecException e) {
                            e.printStackTrace();
                        }
                    }
                    case 3 -> {
                        registarConsulta(scanner);
                    }case 4 -> {
                        registaConsultaFinalizar(scanner);
                    }case 5 -> {
                        historicoAnimal(scanner);
                    }case 6 -> {
                        historicoDoencas();
                    }case 7 -> {
                        historicoAnimalConsulta(scanner);
                    }
                    case 8 -> {
                        removerAnimal(scanner);
                    }
                    case 9 -> {
                        removerVeterinario(scanner);
                    }
                    default ->
                        System.out.println("Opcao invalida. Tente novamente.");
                }
            }
        }

    }

    private static void registrarAnimal(Scanner scanner) {
        Animal animal = new Animal();

        System.out.println("Digite o numero do chip:");
        animal.setNChip(scanner.nextInt());

        System.out.println("Digite o nome do animal:");
        scanner.nextLine();
        animal.setNome(scanner.nextLine());

        List<Especies> especies = new Especies().getAllEspecies();
        System.out.println("Lista de Especies:");
        for (Especies especie : especies) {
            System.out.println(especie.getId() + " - " + especie.getDescr());
        }

        System.out.println("Digite o ID da especie:");
        animal.setIdEspecie(scanner.nextInt());

        System.out.println("Digite a data de nascimento (formato: yyyy-MM-dd):");
        scanner.nextLine();
        String dataNascimentoStr = scanner.nextLine();
        Date dataNascimento = java.sql.Date.valueOf(dataNascimentoStr);
        animal.setDataNascimento(dataNascimento);

        List<Paises> paises = new Paises().getAllPaises();
        System.out.println("Lista de Paises:");
        for (Paises pais : paises) {
            System.out.println(pais.getId() + " - " + pais.getDescr());
        }

        System.out.println("Digite o ID do pais de origem:");
        animal.setIdPaisOrigem(scanner.nextInt());

        animal.registrarAnimal(animal);
        System.out.println("Animal registrado com sucesso!");
    }

    private static void registrarMedico(Scanner scanner) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Veterinario veterinario = new Veterinario();

        System.out.println("Digite o numero de ordem do médico:");
        int Nordem = scanner.nextInt();
        veterinario.setNOrdem(Nordem);

        System.out.println("Digite o nome do Veterinario:");
        scanner.nextLine();
        veterinario.setNome(scanner.nextLine());

        System.out.println("Digite a morada do médico:");
        veterinario.setMorada(scanner.nextLine());

        System.out.println("Digite o contato do médico:");
        veterinario.setContacto(scanner.nextLine());

        System.out.println("Digite o email do médico:");
        String Email = scanner.nextLine();
        veterinario.setEmail(Email);

        System.out.println("Digite a senha");
        String password = scanner.nextLine();

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        int iterations = 10000;
        int keyLength = 256;
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);

        veterinario.setPassword(hashBase64);

        saveUser(Email, saltBase64);

        veterinario.registrarMedico(veterinario);
        System.out.println("Veterinario registrado com sucesso!");
    }

    public static void registarConsulta(Scanner scanner) {

        List<Animal> Animais = new Animal().getAllAnimais();
        System.out.println("Lista de Animais:");
        for (Animal animail : Animais) {
            System.out.println(animail.getNChip()+ " - " + animail.getNome());
        }

        System.out.println("Digite o ID do animal:");
        
        int idAnimal = scanner.nextInt();

        List<Veterinario> Veterinarios = new Veterinario().getAllVeterinarios();

        System.out.println("Lista de Veterinarios:");

        for (Veterinario veterinario : Veterinarios) {

            System.out.println(veterinario.getNOrdem() + " - " + veterinario.getNome());

        }
        
        System.out.println("Digite o ID do médico:");
        
        int idMedico = scanner.nextInt();

        System.out.println("Digite o valor da consulta:");
        
        double valor = scanner.nextDouble();

        System.out.println("Digite a data da consulta (formato: yyyy-MM-dd):");
        
        scanner.nextLine();
        
        String dataConsultaStr = scanner.nextLine();
        
        Date dataConsulta = java.sql.Date.valueOf(dataConsultaStr);

        Consulta consulta = new Consulta();
        
        consulta.registrarConsulta(idAnimal, idMedico, valor, dataConsulta);
    }

    public static void registaConsultaFinalizar(Scanner scanner){
        
        List<Consulta> Consultas = new Consulta().listarTodasConsultas();
        
        System.out.println("Lista de Consultas:");
        
        for (Consulta consulta : Consultas) {
            
            System.out.println(consulta.getId()+ " - " + consulta.getData());
            
        }
        
        System.out.println("Escolha 1 consulta");
        
        int IdConsulta = scanner.nextInt();
        
        List<Doenca> Doencas = new Doenca().getAllDoencas();
        
        System.out.println("Lista de Doencas:");
        
        for (Doenca doenca : Doencas) {
            
            System.out.println(doenca.getId()+ " - " + doenca.getDescr());
            
        }
        
        System.out.println("Escolha 1 doenca");
        
        int IdDoenca = scanner.nextInt();
        
        ResultadoConsulta ResultadoConsulta = new ResultadoConsulta();
        
        ResultadoConsulta.registrarConsulta(IdConsulta, IdDoenca);
        
    }
    
    private static void historicoAnimal(Scanner sc){
        
        List<Animal> Animais = new Animal().getAllAnimais();
        System.out.println("Lista de Animais:");
        for (Animal animal : Animais) {
            System.out.println(animal.getNChip()+ " - " + animal.getNome());
        }
        
        System.out.println("Digite o ID do animal:");
        
        int idAnimal = sc.nextInt();
        
        List<Animal> Animais1 = new Animal().getAnimal(idAnimal);
        System.out.println("Lista de Doencas do Animal:");
        for (Animal animal : Animais1) {
            System.out.println(animal.getDoenca()+ " - " + animal.getNome());
        }
        
    }
    
    public static void historicoDoencas(){
        
        List<Doenca> Doencas = new Doenca().ContagemDeDoencas();
        System.out.println("Lista de Doencas:");
        for (Doenca doenca : Doencas) {
            System.out.println(doenca.getTotal_registros()+ " - " + doenca.getDescr());
        }
        
    }
    
    private static void historicoAnimalConsulta(Scanner sc){
        
        List<Animal> Animais = new Animal().getAllAnimais();
        System.out.println("Lista de Animais:");
        for (Animal animal : Animais) {
            System.out.println(animal.getNChip()+ " - " + animal.getNome());
        }
        
        System.out.println("Digite o ID do animal:");
        
        int idAnimal = sc.nextInt();
        
        List<Animal> Animais1 = new Animal().getAnimal(idAnimal);
        System.out.println("Lista de Doencas do Animal:");
        for (Animal animal : Animais1) {
            System.out.println("Nome: " + animal.getNome() 
                    + "\n" + "Data Nascimento: " + animal.getDataNascimento() 
                    + "\n" + "Especie: " + animal.getEspecie() + "\n" + "Pais: " 
                    + animal.getPais() + "\n" + "Continente: " 
                    + animal.getContinente());
        }
        
    }
    
    private static void removerAnimal(Scanner scanner) {

        List<Animal> Animais = new Animal().getAllAnimais();

        System.out.println("Lista de Animais:");

        for (Animal animal : Animais) {

            System.out.println(animal.getNChip() + " - " + animal.getNome());

        }

        System.out.println("Insira o Animal a Remover");

        int N_Chip = scanner.nextInt();

        Animal animal = new Animal();

        animal.removeAnimal(N_Chip);

    }

    private static void removerVeterinario(Scanner scanner) {

        List<Veterinario> Veterinarios = new Veterinario().getAllVeterinarios();

        System.out.println("Lista de Veterinarios:");

        for (Veterinario veterinario : Veterinarios) {

            System.out.println(veterinario.getNOrdem() + " - " + veterinario.getNome());

        }

        System.out.println("Insira o Veterinario a Remover");

        int NOrdem = scanner.nextInt();

        Veterinario veterinario = new Veterinario();

        veterinario.removeVeterinario(NOrdem);

    }

    public static void saveUser(String Email, String saltBase64) {
        JSONObject userObject = new JSONObject();
        userObject.put("Email", Email);
        userObject.put("salt", saltBase64);

        try (FileReader file = new FileReader("user_data.json")) {
            StringBuilder content = new StringBuilder();
            int c;
            while ((c = file.read()) != -1) {
                content.append((char) c);
            }

            JSONArray jsonArray = new JSONArray(content.toString());
            jsonArray.put(userObject);

            try (FileWriter fileWriter = new FileWriter("user_data.json")) {
                fileWriter.write(jsonArray.toString());
                fileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String findSaltByEmail(String email) throws IOException {
        try (FileReader file = new FileReader("user_data.json")) {
            StringBuilder content = new StringBuilder();
            int c;
            while ((c = file.read()) != -1) {
                content.append((char) c);
            }

            JSONArray jsonArray = new JSONArray(content.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject userObject = jsonArray.getJSONObject(i);
                if (userObject.getString("Email").equals(email)) {
                    return userObject.getString("salt");
                }
            }
        }
        return null;
    }

    private static boolean Login(String password, String Email) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Veterinario veterinario = new Veterinario();
        boolean flag = false;
        if (veterinario.verificaEmailVet(Email)) {
            String storedSaltBase64 = "";
            try {
                storedSaltBase64 = findSaltByEmail(Email);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String storedHashBase64 = veterinario.getPasswordVet(Email);

            String userEnteredPassword = password;

            byte[] storedSalt = Base64.getDecoder().decode(storedSaltBase64);
            byte[] storedHash = Base64.getDecoder().decode(storedHashBase64);

            KeySpec spec = new PBEKeySpec(userEnteredPassword.toCharArray(), storedSalt, 10000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] enteredHash = factory.generateSecret(spec).getEncoded();

            if (MessageDigest.isEqual(storedHash, enteredHash)) {
                System.out.println("Senha correta. Acesso concedido.");
                flag = true;
            } else {
                System.out.println("Senha incorreta. Acesso negado.");
            }
        }
        return flag;
    }

}
