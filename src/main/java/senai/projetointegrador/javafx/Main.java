package pi.projetointegrador;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    static String[] pecas = {"Peça 1", "Peça 2", "Peça 3"};
    static String[] horarios = {"Manhã", "Tarde", "Noite"};
    static String[] areas = {"Plateia A (poltronas 1 a 25)", "Plateia B (poltronas 26 a 125)", "Frisa (poltronas 126 a 155)", "Camarote (poltronas 156 a 205)", "Balcão Nobre (poltronas 206 a 255)"};
    static double[] precos = {40.00, 60.00, 120.00, 80.00, 250.00};
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

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1280, 750);
        anchorPane.setPadding(new Insets(10, 10, 10, 10));
//        anchorPane.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, red 0%, black 100%);");

        // Menu principal
        Label label = new Label("Escolha uma opção: ");
        AnchorPane.setTopAnchor(label, 20.0);
        AnchorPane.setLeftAnchor(label, 20.0);

        Button comprarButton = new Button("Comprar Ingresso");
        comprarButton.setOnAction(e -> {
            try {
                comprarIngresso(primaryStage);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        AnchorPane.setTopAnchor(comprarButton, 50.0);
        AnchorPane.setLeftAnchor(comprarButton, 20.0);

        Button imprimirButton = new Button("Imprimir Ingresso");
        imprimirButton.setOnAction(e -> imprimirIngresso(primaryStage));
        AnchorPane.setTopAnchor(imprimirButton, 80.0);
        AnchorPane.setLeftAnchor(imprimirButton, 20.0);

        Button estatisticasButton = new Button("Estatísticas de Vendas");
        estatisticasButton.setOnAction(e -> estatisticasVendas(primaryStage));
        AnchorPane.setTopAnchor(estatisticasButton, 110.0);
        AnchorPane.setLeftAnchor(estatisticasButton, 20.0);

        Button sairButton = new Button("Sair");
        sairButton.setOnAction(e -> System.exit(0));
        AnchorPane.setTopAnchor(sairButton, 140.0);
        AnchorPane.setLeftAnchor(sairButton, 20.0);

        anchorPane.getChildren().addAll(label, comprarButton, imprimirButton, estatisticasButton, sairButton);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void comprarIngresso(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Comprar Ingresso");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1280, 750);
        anchorPane.setPadding(new Insets(10, 10, 10, 10));

        Image image = new Image(new FileInputStream("C:\\Users\\Aluno\\Documents\\intellij\\projeto integrador fx\\teatro.jpg"));
        ImageView imgItem = new ImageView(image);
        imgItem.setFitWidth(827);
        imgItem.setFitHeight(632);
        AnchorPane.setTopAnchor(imgItem, 10.0);
        AnchorPane.setRightAnchor(imgItem, 10.0);

        Label mensagem = new Label();
        AnchorPane.setTopAnchor(mensagem, 250.0);
        AnchorPane.setLeftAnchor(mensagem, 20.0);
        mensagem.setStyle("-fx-text-fill:red");

        Label cpfLabel = new Label("Digite o seu CPF:");
        AnchorPane.setTopAnchor(cpfLabel, 20.0);
        AnchorPane.setLeftAnchor(cpfLabel, 20.0);

        TextField cpfInput = new TextField();
        AnchorPane.setTopAnchor(cpfInput, 40.0);
        AnchorPane.setLeftAnchor(cpfInput, 20.0);

        Label pecaLabel = new Label("Escolha a peça:");
        AnchorPane.setTopAnchor(pecaLabel, 70.0);
        AnchorPane.setLeftAnchor(pecaLabel, 20.0);

        ChoiceBox<String> pecaChoice = new ChoiceBox<>();
        pecaChoice.getItems().addAll(pecas);
        AnchorPane.setTopAnchor(pecaChoice, 90.0);
        AnchorPane.setLeftAnchor(pecaChoice, 20.0);

        Label horarioLabel = new Label("Escolha o horário:");
        AnchorPane.setTopAnchor(horarioLabel, 115.0);
        AnchorPane.setLeftAnchor(horarioLabel, 20.0);

        ChoiceBox<String> horarioChoice = new ChoiceBox<>();
        horarioChoice.getItems().addAll(horarios);
        AnchorPane.setTopAnchor(horarioChoice, 135.0);
        AnchorPane.setLeftAnchor(horarioChoice, 20.0);

        Label areaLabel = new Label("Escolha a área:");
        AnchorPane.setTopAnchor(areaLabel, 160.0);
        AnchorPane.setLeftAnchor(areaLabel, 20.0);

        ChoiceBox<String> areaChoice = new ChoiceBox<>();
        areaChoice.getItems().addAll(areas);
        AnchorPane.setTopAnchor(areaChoice, 175.0);
        AnchorPane.setLeftAnchor(areaChoice, 20.0);

        Label poltronaLabel = new Label("Escolha a poltrona:");
        AnchorPane.setTopAnchor(poltronaLabel, 205.0);
        AnchorPane.setLeftAnchor(poltronaLabel, 20.0);

        TextField poltronaInput = new TextField();
        AnchorPane.setTopAnchor(poltronaInput, 220.0);
        AnchorPane.setLeftAnchor(poltronaInput, 20.0);

        Button comprarButton = new Button("Comprar");
        comprarButton.setOnAction(e -> {
            if (cpfInput.getText().isEmpty() || pecaChoice.getSelectionModel().isEmpty() || horarioChoice.getSelectionModel().isEmpty() || areaChoice.getSelectionModel().isEmpty() || poltronaInput.getText().isEmpty()) {
                mensagem.setText("Por favor, preencha todos os campos.");
            } else {
                try {
                    long cpf = Long.parseLong(cpfInput.getText());
                    int peca = pecaChoice.getSelectionModel().getSelectedIndex();
                    int horario = horarioChoice.getSelectionModel().getSelectedIndex();
                    int area = areaChoice.getSelectionModel().getSelectedIndex();
                    int poltrona = Integer.parseInt(poltronaInput.getText());

                    boolean success = comprarIngresso(cpf, peca, horario, area, poltrona);
                    if (success) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Sucesso!");
                        alert.setHeaderText(null);
                        alert.setContentText("Ingresso comprado com sucesso!");
                        alert.showAndWait();

                        start(primaryStage);
                    } else {
                        mensagem.setText("Erro ao comprar ingresso. Verifique os dados e tente novamente.");
                    }
                } catch (NumberFormatException ex) {
                    mensagem.setText("Erro: Por favor, insira um CPF válido e um número de poltrona válido.");
                }
            }
        });
        AnchorPane.setTopAnchor(comprarButton, 270.0);
        AnchorPane.setLeftAnchor(comprarButton, 20.0);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        AnchorPane.setTopAnchor(voltar, 270.0);
        AnchorPane.setLeftAnchor(voltar, 120.0);

        anchorPane.getChildren().addAll(cpfLabel, cpfInput, pecaLabel, pecaChoice, horarioLabel, horarioChoice, areaLabel, areaChoice, poltronaLabel, poltronaInput, comprarButton, voltar, mensagem, imgItem);

        Scene scene = new Scene(anchorPane);
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

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1280, 750);
        anchorPane.setPadding(new Insets(10, 10, 10, 10));

        Label mensagem = new Label();
        AnchorPane.setTopAnchor(mensagem, 100.0);
        AnchorPane.setLeftAnchor(mensagem, 20.0);

        Label cpfLabel = new Label("Digite o seu CPF:");
        AnchorPane.setTopAnchor(cpfLabel, 3.5);
        AnchorPane.setLeftAnchor(cpfLabel, 20.0);

        TextField cpfInput = new TextField();
        AnchorPane.setTopAnchor(cpfInput, 20.0);
        AnchorPane.setLeftAnchor(cpfInput, 20.0);

        Button imprimirButton = new Button("Imprimir");
        imprimirButton.setOnAction(e -> {
            String cpfText = cpfInput.getText();
            if (!cpfText.isEmpty()) {
                try {
                    long cpf = Long.parseLong(cpfText);
                    String result = buscarIngresso(cpf);
                    mensagem.setText(result);
                } catch (NumberFormatException ex) {
                    mensagem.setText("CPF inválido");
                }
            } else {
                mensagem.setText("Digite algo válido");
            }
        });
        AnchorPane.setTopAnchor(imprimirButton, 65.0);
        AnchorPane.setLeftAnchor(imprimirButton, 20.0);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        AnchorPane.setTopAnchor(voltar, 65.0);
        AnchorPane.setLeftAnchor(voltar, 122.0);

        anchorPane.getChildren().addAll(cpfLabel, cpfInput, imprimirButton, voltar, mensagem);

        Scene scene = new Scene(anchorPane);
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

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1280, 750);
        anchorPane.setPadding(new Insets(15, 15, 15, 15));

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

        Label totalVendasLabel = new Label("Total de vendas: " + totalVendas);
        AnchorPane.setTopAnchor(totalVendasLabel, 20.0);
        AnchorPane.setLeftAnchor(totalVendasLabel, 20.0);

        Label pecaMaisVendidaLabel = new Label("Peça com mais ingressos vendidos: " + pecas[pecaMaisVendida]);
        AnchorPane.setTopAnchor(pecaMaisVendidaLabel, 50.0);
        AnchorPane.setLeftAnchor(pecaMaisVendidaLabel, 20.0);

        Label pecaMenosVendidaLabel = new Label("Peça com menos ingressos vendidos: " + pecas[pecaMenosVendida]);
        AnchorPane.setTopAnchor(pecaMenosVendidaLabel, 80.0);
        AnchorPane.setLeftAnchor(pecaMenosVendidaLabel, 20.0);

        Label sessaoMaisOcupadaLabel = new Label("Sessão com maior ocupação: " + horarios[sessaoMaisOcupada]);
        AnchorPane.setTopAnchor(sessaoMaisOcupadaLabel, 110.0);
        AnchorPane.setLeftAnchor(sessaoMaisOcupadaLabel, 20.0);

        Label sessaoMenosOcupadaLabel = new Label("Sessão com menor ocupação: " + horarios[sessaoMenosOcupada]);
        AnchorPane.setTopAnchor(sessaoMenosOcupadaLabel, 140.0);
        AnchorPane.setLeftAnchor(sessaoMenosOcupadaLabel, 20.0);

        Label lucroMedioLabel = new Label("Lucro médio por peça: R$ " + lucroMedio);
        AnchorPane.setTopAnchor(lucroMedioLabel, 170.0);
        AnchorPane.setLeftAnchor(lucroMedioLabel, 20.0);

        Label sessaoMais1 = new Label("Sessão mais lucrativa da peça 1:  " + horarios[sessaoMaisLucrativaPorPeca[0]]);
        AnchorPane.setTopAnchor(sessaoMais1, 200.0);
        AnchorPane.setLeftAnchor(sessaoMais1, 20.0);

        Label sessaoMenos1 = new Label("Sessão menos lucrativa da peça 1:  " + horarios[sessaoMenosLucrativaPorPeca[0]]);
        AnchorPane.setTopAnchor(sessaoMenos1, 230.0);
        AnchorPane.setLeftAnchor(sessaoMenos1, 20.0);

        Label sessaoMais2 = new Label("Sessão mais lucrativa da peça 2:  " + horarios[sessaoMaisLucrativaPorPeca[1]]);
        AnchorPane.setTopAnchor(sessaoMais2, 260.0);
        AnchorPane.setLeftAnchor(sessaoMais2, 20.0);

        Label sessaoMenos2 = new Label("Sessão menos lucrativa da peça 2: " + horarios[sessaoMenosLucrativaPorPeca[1]]);
        AnchorPane.setTopAnchor(sessaoMenos2, 290.0);
        AnchorPane.setLeftAnchor(sessaoMenos2, 20.0);

        Label sessaoMais3 = new Label("Sessão mais lucrativa da peça 3: " + horarios[sessaoMaisLucrativaPorPeca[2]]);
        AnchorPane.setTopAnchor(sessaoMais3, 320.0);
        AnchorPane.setLeftAnchor(sessaoMais3, 20.0);

        Label sessaoMenos3 = new Label("Sessão menos lucrativa da peça 3: " + horarios[sessaoMenosLucrativaPorPeca[2]]);
        AnchorPane.setTopAnchor(sessaoMenos3, 350.0);
        AnchorPane.setLeftAnchor(sessaoMenos3, 20.0);


        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        AnchorPane.setTopAnchor(voltar, 380.0);
        AnchorPane.setLeftAnchor(voltar, 20.0);

        anchorPane.getChildren().addAll(totalVendasLabel, pecaMaisVendidaLabel, pecaMenosVendidaLabel, sessaoMaisOcupadaLabel, sessaoMenosOcupadaLabel, lucroMedioLabel, voltar, sessaoMenos1, sessaoMais1, sessaoMais2, sessaoMenos2, sessaoMais3, sessaoMenos3);

        Scene scene = new
                Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double precoPorPoltrona(int poltrona) {
        if (poltrona >= 1 && poltrona <= 25) {
            return precos[0];
        } else if (poltrona >= 26 && poltrona <= 125) {
            return precos[1];
        } else if (poltrona >= 126 && poltrona <= 155) {
            return precos[2];
        } else if (poltrona >= 156 && poltrona <= 205) {
            return precos[3];
        } else if (poltrona >= 206 && poltrona <= 255) {
            return precos[4];
        }
        return 0;
    }

    private int maisVendido(int[] vendas) {
        int max = vendas[0];
        int index = 0;
        for (int i = 1; i < vendas.length; i++) {
            if (vendas[i] > max) {
                max = vendas[i];
                index = i;
            }
        }
        return index;
    }

    private int menosVendido(int[] vendas) {
        int min = vendas[0];
        int index = 0;
        for (int i = 1; i < vendas.length; i++) {
            if (vendas[i] < min) {
                min = vendas[i];
                index = i;
            }
        }
        return index;
    }
}
