package pi;

import java.util.Scanner;

public class Main {
    static String[] pecas = {"Peça 1", "Peça 2", "Peça 3"};
    static String[] horarios = {"Manhã", "Tarde", "Noite"};
    static String[] areas = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
    static double[] precos = {40.00, 60.00, 120.00, 80.00, 250.00};

    static int[] poltronas = new int[255];
    static long[][] p1 = new long[3][255];
    static long[][] p2 = new long[3][255];
    static long[][] p3 = new long[3][255];
    
    static int totalVendas = 0;

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);

        while (true) {
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Imprimir Ingresso");
            System.out.println("3. Estatísticas de Vendas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = ler.nextInt();
            ler.nextLine();  // Consumir nova linha

            switch (opcao) {
                case 1:
                    comprarIngresso(ler);
                    break;
                case 2:
                    imprimirIngresso(ler);
                    break;
                case 3:
                    estatisticasVendas();
                    break;
                case 4:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void comprarIngresso(Scanner ler) {
        if (totalVendas >= 255) {
            System.out.println("Não há mais ingressos disponíveis.");
            return;
        }

        System.out.print("Digite o seu CPF: ");
        long cpf = ler.nextLong();
        ler.nextLine();  // Consumir nova linha

        System.out.println("Digite para qual peça você quer o ingresso (p1, p2 ou p3):");
        String peca = ler.nextLine();

        System.out.println("Digite horário da peça (manhã, tarde ou noite):");
        String horario = ler.nextLine();

        System.out.println("Escolha a área:");
        for (int i = 0; i < areas.length; i++) {
            System.out.println((i + 1) + ". " + areas[i] + " (R$ " + precos[i] + ")");
        }
        int areaEscolhida = ler.nextInt();
        ler.nextLine();  // Consumir nova linha

        System.out.print("Número da poltrona: ");
        int poltrona = ler.nextInt();
        ler.nextLine();  // Consumir nova linha

        if (peca.equalsIgnoreCase("p1")) {
            adicionarVenda(p1, cpf, horario, poltrona);
        } else if (peca.equalsIgnoreCase("p2")) {
            adicionarVenda(p2, cpf, horario, poltrona);
        } else if (peca.equalsIgnoreCase("p3")) {
            adicionarVenda(p3, cpf, horario, poltrona);
        }

        totalVendas++;
        System.out.println("Ingresso comprado com sucesso!");
    }

    private static void adicionarVenda(long[][] peca, long cpf, String horario, int poltrona) {
        int indiceHorario = getIndiceHorario(horario);
        peca[indiceHorario][poltrona] = cpf;
    }

    private static int getIndiceHorario(String horario) {
        switch (horario.toLowerCase()) {
            case "manhã":
                return 0;
            case "tarde":
                return 1;
            case "noite":
                return 2;
            default:
                throw new IllegalArgumentException("Horário inválido");
        }
    }

    public static void imprimirIngresso(Scanner ler) {
        System.out.print("Digite o seu CPF: ");
        long cpf = ler.nextLong();
        ler.nextLine();  // Consumir nova linha

        boolean encontrado = false;
        encontrado = imprimirVendaPorCPF(p1, cpf, "Peça 1") || encontrado;
        encontrado = imprimirVendaPorCPF(p2, cpf, "Peça 2") || encontrado;
        encontrado = imprimirVendaPorCPF(p3, cpf, "Peça 3") || encontrado;

        if (!encontrado) {
            System.out.println("Ingresso não encontrado para o CPF informado.");
        }
    }

    private static boolean imprimirVendaPorCPF(long[][] peca, long cpf, String nomePeca) {
        for (int i = 0; i < peca.length; i++) {
            for (int j = 0; j < peca[i].length; j++) {
                if (peca[i][j] == cpf) {
                    System.out.println("Ingresso encontrado:");
                    System.out.println("CPF: " + cpf);
                    System.out.println("Peça: " + nomePeca);
                    System.out.println("Sessão: " + horarios[i]);
                    System.out.println("Poltrona: " + j);
                    return true;
                }
            }
        }
        return false;
    }

    public static void estatisticasVendas() {
        int[] vendasPorPeca = new int[3];
        int[] vendasPorSessao = new int[3];
        double[] lucroPorPeca = new double[3];

        calcularVendasEReceitas(p1, vendasPorPeca, 0, lucroPorPeca);
        calcularVendasEReceitas(p2, vendasPorPeca, 1, lucroPorPeca);
        calcularVendasEReceitas(p3, vendasPorPeca, 2, lucroPorPeca);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 255; j++) {
                if (p1[i][j] != 0) vendasPorSessao[i]++;
                if (p2[i][j] != 0) vendasPorSessao[i]++;
                if (p3[i][j] != 0) vendasPorSessao[i]++;
            }
        }

        int pecaMaisVendida = getIndiceMax(vendasPorPeca);
        int pecaMenosVendida = getIndiceMin(vendasPorPeca);
        int sessaoMaisOcupada = getIndiceMax(vendasPorSessao);
        int sessaoMenosOcupada = getIndiceMin(vendasPorSessao);

        double lucroMedio = (lucroPorPeca[0] + lucroPorPeca[1] + lucroPorPeca[2]) / totalVendas;

        System.out.println("Peça mais vendida: " + pecas[pecaMaisVendida]);
        System.out.println("Peça menos vendida: " + pecas[pecaMenosVendida]);
        System.out.println("Sessão com maior ocupação: " + horarios[sessaoMaisOcupada]);
        System.out.println("Sessão com menor ocupação: " + horarios[sessaoMenosOcupada]);
        System.out.println("Lucro médio por peça: R$ " + lucroMedio);
    }

    private static void calcularVendasEReceitas(long[][] peca, int[] vendasPorPeca, int indicePeca, double[] lucroPorPeca) {
        for (int i = 0; i < peca.length; i++) {
            for (int j = 0; j < peca[i].length; j++) {
                if (peca[i][j] != 0) {
                    vendasPorPeca[indicePeca]++;
                    lucroPorPeca[indicePeca] += getPrecoArea(j);
                }
            }
        }
    }

    private static double getPrecoArea(int poltrona) {
        if (poltrona >= 0 && poltrona <= 24) return precos[0];
        if (poltrona >= 25 && poltrona <= 124) return precos[1];
        if (poltrona >= 125 && poltrona <= 154) return precos[2];
        if (poltrona >= 155 && poltrona <= 194) return precos[3];
        if (poltrona >= 195 && poltrona <= 254) return precos[4];
        return 0;
    }

    private static int getIndiceMax(int[] array) {
        int max = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[max]) {
                max = i;
            }
        }
        return max;
    }

    private static int getIndiceMin(int[] array) {
        int min = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[min]) {
                min = i;
            }
        }
        return min;
    }
}
