package pi;

import java.util.Scanner;

public class Main {
    static String[] pecas = {"Peça 1", "Peça 2", "Peça 3"};
    static String[] horarios = {"Manhã", "Tarde", "Noite"};
    static String[] areas = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
    static double[] precos = {40.00, 60.00, 120.00, 80.00, 250.00};
    static int[][] poltronasPorArea = {{1, 25}, {26, 125}, {126, 155}, {156, 195}, {196, 245}};
    static long[][] p1 = new long[3][255];
    static long[][] p2 = new long[3][255];
    static long[][] p3 = new long[3][255];

    static int totalVendas = 0;

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção: ");
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Imprimir Ingresso");
            System.out.println("3. Estatísticas de Vendas");
            System.out.println("4. Sair");
            int opcao = ler.nextInt();

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

        String peca;
        while (true) {
            System.out.println("Digite para qual peça você quer o ingresso (p1, p2 ou p3):");
            peca = ler.next();

            if (peca.equals("p1") || peca.equals("p2") || peca.equals("p3")) {
                break;
            } else {
                System.out.println("Peça inválida! Por favor, digite p1, p2 ou p3.");
            }
        }

        String horario;
        while (true) {
            System.out.println("Digite horário da peça: m (manhã), t (tarde) ou n (noite):");
            horario = ler.next();

            try {
                verificarHorario(horario);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Horário inválido! Por favor, digite m (manhã), t (tarde) ou n (noite).");
            }
        }

        int areaEscolhida;
        while (true) {
            System.out.println("Escolha a área:");
            for (int i = 0; i < areas.length; i++) {
                System.out.println((i + 1) + ". " + areas[i] + " (R$ " + precos[i] + ") (poltronas " + poltronasPorArea[i][0] + " a " + poltronasPorArea[i][1] + ")");
            }
            areaEscolhida = ler.nextInt();

            if (areaEscolhida >= 1 && areaEscolhida <= 5) {
                break;
            } else {
                System.out.println("Área inválida! Por favor, escolha uma área entre 1 e 5.");
            }
        }

        int poltrona;
        while (true) {
            System.out.print("Número da poltrona: ");
            poltrona = ler.nextInt();

            if (validarPoltrona(areaEscolhida, poltrona)) {
                break;
            } else {
                System.out.println("Número de poltrona inválido para a área escolhida! Por favor, escolha uma poltrona válida.");
            }
        }

        if (peca.equalsIgnoreCase("p1")) {
            if (adicionarVenda(p1, cpf, horario, poltrona)) return;
        } else if (peca.equalsIgnoreCase("p2")) {
            if (adicionarVenda(p2, cpf, horario, poltrona)) return;
        } else {
            if (adicionarVenda(p3, cpf, horario, poltrona)) return;
        }

        totalVendas++;
        System.out.println("Ingresso comprado com sucesso!");
    }

    private static boolean adicionarVenda(long[][] peca, long cpf, String horario, int poltrona) {
        int indiceHorario = verificarHorario(horario);
        if (peca[indiceHorario][poltrona - 1] != 0) {
            System.out.println("Erro: Poltrona já ocupada!");
            return true;
        }
        peca[indiceHorario][poltrona - 1] = cpf;
        return false;
    }

    private static int verificarHorario(String horario) {
        return switch (horario.toLowerCase()) {
            case "m" -> 0;
            case "t" -> 1;
            case "n" -> 2;
            default -> throw new IllegalArgumentException("Horário inválido");
        };
    }

    private static boolean validarPoltrona(int area, int poltrona) {
        return switch (area) {
            case 1 -> poltrona >= 1 && poltrona <= 25; // Plateia A
            case 2 -> poltrona >= 26 && poltrona <= 125; // Plateia B
            case 3 -> poltrona >= 126 && poltrona <= 155; // Frisa (30 poltronas em 6 frisas)
            case 4 -> poltrona >= 156 && poltrona <= 195; // Camarote (40 poltronas em 4 camarotes)
            case 5 -> poltrona >= 196 && poltrona <= 245; // Balcão Nobre
            default -> false;
        };
    }

    public static void imprimirIngresso(Scanner ler) {
        System.out.print("Digite o seu CPF: ");
        long cpf = ler.nextLong();

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
                    System.out.println("Poltrona: " + (j + 1));
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

        int pecaMaisVendida = maisVendido(vendasPorPeca);
        int pecaMenosVendida = menosVendido(vendasPorPeca);
        int sessaoMaisOcupada = maisVendido(vendasPorSessao);
        int sessaoMenosOcupada = menosVendido(vendasPorSessao);

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
                    lucroPorPeca[indicePeca] += getPrecoArea(j + 1);
                }
            }
        }
    }

    private static double getPrecoArea(int poltrona) {
        if (poltrona >= 1 && poltrona <= 25) return precos[0];
        if (poltrona >= 26 && poltrona <= 125) return precos[1];
        if (poltrona >= 126 && poltrona <= 155) return precos[2];
        if (poltrona >= 156 && poltrona <= 195) return precos[3];
        if (poltrona >= 196 && poltrona <= 245) return precos[4];
        return 0;
    }

    private static int maisVendido(int[] array) {
        int max = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[max]) {
                max = i;
            }
        }
        return max;
    }

    private static int menosVendido(int[] array) {
        int min = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[min]) {
                min = i;
            }
        }
        return min;
    }
}
