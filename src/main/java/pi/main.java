import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    static String[] pecas = {"Peça 1", "Peça 2", "Peça 3"};
    static String[] horarios = {"Manhã", "Tarde", "Noite"};
    static String[] areas = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
    static double[] precos = {40.00, 60.00, 120.00, 80.00, 250.00};
    static int[][] poltronas = {{1, 25}, {26, 125}, {126, 155}, {156, 205}, {206, 255}};
    static long[][] p1 = new long[3][255];
    static long[][] p2 = new long[3][255];
    static long[][] p3 = new long[3][255];
    static int totalVendas = 0;
    static final int retornar = -1;

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção: ");
            System.out.println("1. Comprar Ingresso");
            System.out.println("2. Imprimir Ingresso");
            System.out.println("3. Estatísticas de Vendas");
            System.out.println("4. Sair");
            try {
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
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas um algarismo.");
                ler.nextLine();
            }
        }
    }

    public static void comprarIngresso(Scanner ler) {

        if (totalVendas >= 255) {
            System.out.println("Não há mais ingressos disponíveis.");
            return;
        }

        System.out.print("Digite o seu CPF ou " + retornar + " para voltar ao menu inicial: ");
        long cpf;
        while (true) {

            cpf = ler.nextLong();

            if (cpf == retornar) {
                return;
            }
            if (verificarCPF(cpf)) {
                break;
            } else {
                System.out.println("Cpf inválido! Tente novamente!");
            }

        }

        System.out.println("""
                Digite para qual peça você quer o ingresso
                1 - Peça 1
                2 - Peça 2
                3 - Peça 3
                """);
        int peca;
        while (true) {
            try {
                peca = ler.nextInt();

                if (peca == retornar) {
                    return;
                }

                if (peca == 1 || peca == 2 || peca == 3) {
                    break;
                } else {
                    System.out.println("Peça inválida! Por favor, digite 1, 2 ou 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas um algarismo");
                ler.nextLine();
            }
        }

        System.out.println("Escolha o horário da peça: 1 (manhã), 2 (tarde), 3 (noite) ou " + retornar + " para voltar ao menu inicial: ");
        int horario;
        while (true) {
            try {
                horario = ler.nextInt();

                if (horario == retornar) {
                    return;
                }

                if (horario >= 1 && horario <= 3) {
                    break;
                } else {
                    System.out.println("Horário inválido! Por favor, digite 1 (manhã), 2 (tarde) ou 3 (noite).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas um algarismo");
                ler.nextLine();
            }
        }

        System.out.println("Escolha a área ou " + retornar + " para voltar ao menu inicial: ");
        for (int i = 0; i < areas.length; i++) {
            System.out.println((i + 1) + ". " + areas[i] + " (R$ " + precos[i] + ") (poltronas " + poltronas[i][0] + " a " + poltronas[i][1] + ")");
        }
        int areaEscolhida;
        while (true) {
            try {
                areaEscolhida = ler.nextInt();

                if (areaEscolhida == retornar) {
                    return;
                }

                if (areaEscolhida >= 1 && areaEscolhida <= 5) {
                    break;
                } else {
                    System.out.println("Área inválida! Por favor, escolha uma área entre 1 e 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas um algarismo");
                ler.nextLine();
            }
        }

        System.out.print("Digite o número da poltrona desejada ou " + retornar + " para voltar ao menu inicial: ");
        int poltrona;
        while (true) {
            try {
                poltrona = ler.nextInt();

                if (poltrona == retornar) {
                    return;
                }

                if (validarPoltrona(areaEscolhida, poltrona)) {
                    break;
                } else {
                    System.out.println("Número de poltrona inválido para a área escolhida! Por favor, escolha uma poltrona válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas um algarismo");
                ler.nextLine();
            }
        }

        if (peca == 1) {
            if (adicionarVenda(p1, cpf, horario, poltrona)) return;
        } else if (peca == 2) {
            if (adicionarVenda(p2, cpf, horario, poltrona)) return;
        } else {
            if (adicionarVenda(p3, cpf, horario, poltrona)) return;
        }

        totalVendas++;
        System.out.println("Ingresso comprado com sucesso!");
    }

    private static boolean adicionarVenda(long[][] peca, long cpf, int horario, int poltrona) {
        int indiceHorario = verificarHorario(horario);
        if (peca[indiceHorario][poltrona - 1] != 0) {
            System.out.println("Erro: Poltrona já ocupada!");
            return true;
        }
        peca[indiceHorario][poltrona - 1] = cpf;
        return false;
    }

    public static boolean verificarCPF(long cpf) {
        String cpfString = String.format("%011d", cpf);
        if (cpfString.length() != 11 || cpf == 0) {
            return false;
        }
        int[] cpfArray = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfArray[i] = Integer.parseInt(String.valueOf(cpfString.charAt(i)));
        }

        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += cpfArray[i] * peso;
            peso--;
        }
        int primeiroDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += cpfArray[i] * peso;
            peso--;
        }
        int segundoDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        return cpfArray[9] == primeiroDigito && cpfArray[10] == segundoDigito;
    }

    private static int verificarHorario(int horario) {
        return switch (horario) {
            case 1 -> 0;
            case 2 -> 1;
            case 3 -> 2;
            default -> throw new IllegalArgumentException("Horário inválido");
        };
    }

    private static boolean validarPoltrona(int area, int poltrona) {
        return switch (area) {
            case 1 -> poltrona >= 1 && poltrona <= 25; // Plateia A
            case 2 -> poltrona >= 26 && poltrona <= 125; // Plateia B
            case 3 -> poltrona >= 126 && poltrona <= 155; // Frisa (30 poltronas em 6 frisas)
            case 4 -> poltrona >= 156 && poltrona <= 205; // Camarote (50 poltronas em 5 camarotes)
            case 5 -> poltrona >= 206 && poltrona <= 255; // Balcão Nobre
            default -> false;
        };
    }

    public static void imprimirIngresso(Scanner ler) {
        System.out.print("Digite o seu CPF: ");
        long cpf;
        while (true) {
            try {
                cpf = ler.nextLong();
                if (cpf == retornar) {
                    return;
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite apenas números");
                ler.nextLine();
            }
        }

        boolean encontrado = false;
        for (int i = 0; i < 3; i++) {
            String nomePeca = pecas[i];
            long[][] peca = switch (i) {
                case 0 -> p1;
                case 1 -> p2;
                case 2 -> p3;
                default -> null;
            };

            for (int j = 0; j < peca.length; j++) {
                for (int k = 0; k < peca[j].length; k++) {
                    if (peca[j][k] == cpf) {
                        System.out.println("Ingresso encontrado:");
                        System.out.println("CPF: " + cpf);
                        System.out.println("Peça: " + nomePeca);
                        System.out.println("Sessão: " + horarios[j]);
                        System.out.println("Poltrona: " + (k + 1));
                        encontrado = true;
                    }
                }
            }
        }

        if (!encontrado) {
            System.out.println("Ingresso não encontrado para o CPF informado.");
        }
    }

    public static void estatisticasVendas() {
        int[] vendasPorPeca = new int[3];
        int[] vendasPorSessao = new int[3];
        double[] lucroPorPeca = new double[3];
        double[] lucroMaximoPorPeca = new double[3];
        int[] sessaoMaisLucrativaPorPeca = new int[3];
        double[] lucroMinimoPorPeca = new double[3];
        int[] sessaoMenosLucrativaPorPeca = new int[3];

        for (int i = 0; i < 3; i++) {
            long[][] peca = switch (i) {
                case 0 -> p1;
                case 1 -> p2;
                case 2 -> p3;
                default -> null;
            };

            for (int j = 0; j < peca.length; j++) {
                double lucroSessao = 0;
                for (int k = 0; k < peca[j].length; k++) {
                    if (peca[j][k] != 0) {
                        vendasPorPeca[i]++;
                        lucroPorPeca[i] += precoPorPoltrona(k + 1);
                        vendasPorSessao[j]++;
                        lucroSessao += precoPorPoltrona(k + 1);
                    }
                }
                if (lucroSessao > lucroMaximoPorPeca[i]) {
                    lucroMaximoPorPeca[i] = lucroSessao;
                    sessaoMaisLucrativaPorPeca[i] = j;
                }
                if (lucroSessao < lucroMinimoPorPeca[i] || lucroMinimoPorPeca[i] == 0) {
                    lucroMinimoPorPeca[i] = lucroSessao;
                    sessaoMenosLucrativaPorPeca[i] = j;
                }
            }
        }

        int pecaMaisVendida = maisVendido(vendasPorPeca);
        int pecaMenosVendida = menosVendido(vendasPorPeca);
        int sessaoMaisOcupada = maisVendido(vendasPorSessao);
        int sessaoMenosOcupada = menosVendido(vendasPorSessao);

        double lucroMedio = (lucroPorPeca[0] + lucroPorPeca[1] + lucroPorPeca[2]) / totalVendas;

        System.out.println("Total de vendas: " + totalVendas);
        System.out.println("Peça com mais ingressos vendidos: " + pecas[pecaMaisVendida]);
        System.out.println("Peça com menos ingressos vendidos: " + pecas[pecaMenosVendida]);
        System.out.println("Sessão com maior ocupação: " + horarios[sessaoMaisOcupada]);
        System.out.println("Sessão com menor ocupação: " + horarios[sessaoMenosOcupada]);
        System.out.println("Lucro médio por peça: R$ " + lucroMedio);
        System.out.println("Sessão mais lucrativa da peça 1: " + horarios[sessaoMaisLucrativaPorPeca[0]]);
        System.out.println("Sessão menos lucrativa da peça 1: " + horarios[sessaoMenosLucrativaPorPeca[0]]);
        System.out.println("Sessão mais lucrativa da peça 2: " + horarios[sessaoMaisLucrativaPorPeca[1]]);
        System.out.println("Sessão menos lucrativa da peça 2: " + horarios[sessaoMenosLucrativaPorPeca[1]]);
        System.out.println("Sessão mais lucrativa da peça 3: " + horarios[sessaoMaisLucrativaPorPeca[2]]);
        System.out.println("Sessão menos lucrativa da peça 3: " + horarios[sessaoMenosLucrativaPorPeca[2]]);
    }


    private static double precoPorPoltrona(int poltrona) {
        if (poltrona >= 1 && poltrona <= 25) return precos[0];
        if (poltrona >= 26 && poltrona <= 125) return precos[1];
        if (poltrona >= 126 && poltrona <= 155) return precos[2];
        if (poltrona >= 156 && poltrona <= 205) return precos[3];
        if (poltrona >= 196 && poltrona <= 255) return precos[4];
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
