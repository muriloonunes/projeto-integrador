package pi.projetointegrador;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    static String[] pecas = {"Peça 1", "Peça 2", "Peça 3"};
    static String[] horarios = {"Manhã", "Tarde", "Noite"};
    static String[] areas = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
    static double[] precos = {40.00, 60.00, 120.00, 80.00, 250.00};
    static int[][] poltronas = {{1, 25}, {26, 125}, {126, 155}, {156, 205}, {206, 255}};
    static long[][] p1 = new long[3][255];
    static long[][] p2 = new long[3][255];
    static long[][] p3 = new long[3][255];
    static int totalVendas = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Venda de Ingressos");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Menu principal
        Label label = new Label("Escolha uma opção: ");
        GridPane.setConstraints(label, 0, 0);

        Button comprarButton = new Button("Comprar Ingresso");
        comprarButton.setOnAction(e -> comprarIngresso(primaryStage));
        GridPane.setConstraints(comprarButton, 0, 1);

        Button imprimirButton = new Button("Imprimir Ingresso");
        imprimirButton.setOnAction(e -> imprimirIngresso(primaryStage));
        GridPane.setConstraints(imprimirButton, 0, 2);

        Button estatisticasButton = new Button("Estatísticas de Vendas");
        estatisticasButton.setOnAction(e -> estatisticasVendas(primaryStage));
        GridPane.setConstraints(estatisticasButton, 0, 3);

        Button sairButton = new Button("Sair");
        sairButton.setOnAction(e -> System.exit(0));
        GridPane.setConstraints(sairButton, 0, 4);

        grid.getChildren().addAll(label, comprarButton, imprimirButton, estatisticasButton, sairButton);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void comprarIngresso(Stage primaryStage) {
        primaryStage.setTitle("Comprar Ingresso");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label cpfLabel = new Label("Digite o seu CPF:");
        GridPane.setConstraints(cpfLabel, 0, 0);

        TextField cpfInput = new TextField();
        GridPane.setConstraints(cpfInput, 1, 0);

        Label pecaLabel = new Label("Escolha a peça:");
        GridPane.setConstraints(pecaLabel, 0, 1);

        ChoiceBox<String> pecaChoice = new ChoiceBox<>();
        pecaChoice.getItems().addAll(pecas);
        GridPane.setConstraints(pecaChoice, 1, 1);

        Label horarioLabel = new Label("Escolha o horário:");
        GridPane.setConstraints(horarioLabel, 0, 2);

        ChoiceBox<String> horarioChoice = new ChoiceBox<>();
        horarioChoice.getItems().addAll(horarios);
        GridPane.setConstraints(horarioChoice, 1, 2);

        Label areaLabel = new Label("Escolha a área:");
        GridPane.setConstraints(areaLabel, 0, 3);

        ChoiceBox<String> areaChoice = new ChoiceBox<>();
        areaChoice.getItems().addAll(areas);
        GridPane.setConstraints(areaChoice, 1, 3);

        Label poltronaLabel = new Label("Escolha a poltrona:");
        GridPane.setConstraints(poltronaLabel, 0, 4);

        TextField poltronaInput = new TextField();
        GridPane.setConstraints(poltronaInput, 1, 4);

        Button comprarButton = new Button("Comprar");
        comprarButton.setOnAction(e -> {
            long cpf = Long.parseLong(cpfInput.getText());
            int peca = pecaChoice.getSelectionModel().getSelectedIndex();
            int horario = horarioChoice.getSelectionModel().getSelectedIndex();
            int area = areaChoice.getSelectionModel().getSelectedIndex();
            int poltrona = Integer.parseInt(poltronaInput.getText());

            boolean success = comprarIngresso(cpf, peca, horario, area, poltrona);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Ingresso comprado com sucesso!");
                alert.showAndWait();
                start(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao comprar ingresso. Verifique os dados e tente novamente.");
                alert.showAndWait();
            }

        });
        GridPane.setConstraints(comprarButton, 1, 5);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        GridPane.setConstraints(voltar, 1, 10);

        grid.getChildren().addAll(cpfLabel, cpfInput, pecaLabel, pecaChoice, horarioLabel, horarioChoice, areaLabel, areaChoice, poltronaLabel, poltronaInput, comprarButton, voltar);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean comprarIngresso(long cpf, int peca, int horario, int area, int poltrona) {
        if (totalVendas >= 255) {
            return false;
        }

        if (!validarPoltrona(area + 1, poltrona)) {
            return false;
        }

        if (peca == 0) {
            if (adicionarVenda(p1, cpf, horario, poltrona)) return false;
        } else if (peca == 1) {
            if (adicionarVenda(p2, cpf, horario, poltrona)) return false;
        } else {
            if (adicionarVenda(p3, cpf, horario, poltrona)) return false;
        }

        totalVendas++;
        return true;
    }

    private boolean adicionarVenda(long[][] peca, long cpf, int horario, int poltrona) {
        int indiceHorario = verificarHorario(horario + 1);
        if (peca[indiceHorario][poltrona - 1] != 0) {
            return true;
        }
        peca[indiceHorario][poltrona - 1] = cpf;
        return false;
    }

    private int verificarHorario(int horario) {
        return switch (horario) {
            case 1 -> 0;
            case 2 -> 1;
            case 3 -> 2;
            default -> throw new IllegalArgumentException("Horário inválido");
        };
    }

    private boolean validarPoltrona(int area, int poltrona) {
        return switch (area) {
            case 1 -> poltrona >= 1 && poltrona <= 25;
            case 2 -> poltrona >= 26 && poltrona <= 125;
            case 3 -> poltrona >= 126 && poltrona <= 155;
            case 4 -> poltrona >= 156 && poltrona <= 205;
            case 5 -> poltrona >= 206 && poltrona <= 255;
            default -> false;
        };
    }

    private void imprimirIngresso(Stage primaryStage) {
        primaryStage.setTitle("Imprimir Ingresso");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label cpfLabel = new Label("Digite o seu CPF:");
        GridPane.setConstraints(cpfLabel, 0, 0);

        TextField cpfInput = new TextField();
        GridPane.setConstraints(cpfInput, 1, 0);

        Button imprimirButton = new Button("Imprimir");
        imprimirButton.setOnAction(e -> {
            long cpf = Long.parseLong(cpfInput.getText());
            String result = buscarIngresso(cpf);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ingresso");
            alert.setHeaderText(null);
            alert.setContentText(result);
            alert.showAndWait();
        });
        GridPane.setConstraints(imprimirButton, 0, 1);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        GridPane.setConstraints(voltar, 1, 1);

        grid.getChildren().addAll(cpfLabel, cpfInput, imprimirButton, voltar);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String buscarIngresso(long cpf) {
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
                        return "Ingresso encontrado:\n" +
                                "CPF: " + cpf + "\n" +
                                "Peça: " + nomePeca + "\n" +
                                "Sessão: " + horarios[j] + "\n" +
                                "Poltrona: " + (k + 1);
                    }
                }
            }
        }
        return "Ingresso não encontrado para o CPF informado.";
    }

    private void estatisticasVendas(Stage primaryStage) {
        primaryStage.setTitle("Estatísticas de Vendas");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        int[] vendasPorPeca = new int[3];
        int[] vendasPorSessao = new int[3];
        double[] lucroPorPeca = new double[3];

        for (int i = 0; i < 3; i++) {
            long[][] peca = switch (i) {
                case 0 -> p1;
                case 1 -> p2;
                case 2 -> p3;
                default -> null;
            };

            for (int j = 0; j < peca.length; j++) {
                for (int k = 0; k < peca[j].length; k++) {
                    if (peca[j][k] != 0) {
                        vendasPorPeca[i]++;
                        lucroPorPeca[i] += precoPorPoltrona(k + 1);
                        vendasPorSessao[j]++;
                    }
                }
            }
        }

        int pecaMaisVendida = maisVendido(vendasPorPeca);
        int pecaMenosVendida = menosVendido(vendasPorPeca);
        int sessaoMaisOcupada = maisVendido(vendasPorSessao);
        int sessaoMenosOcupada = menosVendido(vendasPorSessao);

        double lucroMedio = (lucroPorPeca[0] + lucroPorPeca[1] + lucroPorPeca[2]) / totalVendas;

        Label totalVendasLabel = new Label("Total de vendas: " + totalVendas);
        GridPane.setConstraints(totalVendasLabel, 0, 0);

        Label pecaMaisVendidaLabel = new Label("Peça com mais ingressos vendidos: " + pecas[pecaMaisVendida]);
        GridPane.setConstraints(pecaMaisVendidaLabel, 0, 1);

        Label pecaMenosVendidaLabel = new Label("Peça com menos ingressos vendidos: " + pecas[pecaMenosVendida]);
        GridPane.setConstraints(pecaMenosVendidaLabel, 0, 2);

        Label sessaoMaisOcupadaLabel = new Label("Sessão com maior ocupação: " + horarios[sessaoMaisOcupada]);
        GridPane.setConstraints(sessaoMaisOcupadaLabel, 0, 3);

        Label sessaoMenosOcupadaLabel = new Label("Sessão com menor ocupação: " + horarios[sessaoMenosOcupada]);
        GridPane.setConstraints(sessaoMenosOcupadaLabel, 0, 4);

        Label lucroMedioLabel = new Label("Lucro médio por peça: R$ " + lucroMedio);
        GridPane.setConstraints(lucroMedioLabel, 0, 5);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        GridPane.setConstraints(voltar, 0, 6);

        grid.getChildren().addAll(totalVendasLabel, pecaMaisVendidaLabel, pecaMenosVendidaLabel, sessaoMaisOcupadaLabel, sessaoMenosOcupadaLabel, lucroMedioLabel, voltar);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double precoPorPoltrona(int poltrona) {
        if (poltrona >= 1 && poltrona <= 25) return precos[0];
        if (poltrona >= 26 && poltrona <= 125) return precos[1];
        if (poltrona >= 126 && poltrona <= 155) return precos[2];
        if (poltrona >= 156 && poltrona <= 205) return precos[3];
        if (poltrona >= 206 && poltrona <= 255) return precos[4];
        return 0;
    }

    private int maisVendido(int[] array) {
        int max = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[max]) {
                max = i;
            }
        }
        return max;
    }

    private int menosVendido(int[] array) {
        int min = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[min]) {
                min = i;
            }
        }
        return min;
    }
}
