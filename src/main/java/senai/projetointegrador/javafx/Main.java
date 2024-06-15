package pi.projetointegrador;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
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

        // Menu principal
        Label label = new Label("Escolha uma opção: ");
        label.setLayoutX(591.0);
        label.setLayoutY(17.0);


        Button comprar = new Button("Comprar Ingresso");
        comprar.setOnAction(e -> {
            try {
                comprarIngresso(primaryStage);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        comprar.setLayoutX(590.0);
        comprar.setLayoutY(46.0);

        Button imprimir = new Button("Imprimir Ingresso");
        imprimir.setOnAction(e -> imprimirIngresso(primaryStage));
        imprimir.setLayoutX(590.0);
        imprimir.setLayoutY(76.0);

        Button estatisticas = new Button("Estatísticas de Vendas");
        estatisticas.setOnAction(e -> estatisticasVendas(primaryStage));
        estatisticas.setLayoutX(580.0);
        estatisticas.setLayoutY(106.0);

        Button sair = new Button("Sair");
        sair.setOnAction(e -> System.exit(0));
        sair.setLayoutX(625.0);
        sair.setLayoutY(136.0);

        anchorPane.getChildren().addAll(label, comprar, imprimir, estatisticas, sair);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE:
                    sair.fire();
                    break;
            }
        });
    }

    private void comprarIngresso(Stage primaryStage) throws FileNotFoundException {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1280, 750);
        anchorPane.setPadding(new Insets(10, 10, 10, 10));

        Image image = new Image(new FileInputStream("images/teatro.jpg"));
        ImageView imgItem = new ImageView(image);
        imgItem.setFitWidth(827);
        imgItem.setFitHeight(632);
        AnchorPane.setTopAnchor(imgItem, 10.0);
        AnchorPane.setRightAnchor(imgItem, 10.0);

        RadioButton cpfRadioButton = new RadioButton("CPF");
        AnchorPane.setTopAnchor(cpfRadioButton, 20.0);
        AnchorPane.setLeftAnchor(cpfRadioButton, 20.0);

        RadioButton cnpjRadioButton = new RadioButton("CNPJ");
        AnchorPane.setTopAnchor(cnpjRadioButton, 20.0);
        AnchorPane.setLeftAnchor(cnpjRadioButton, 80.0);

        ToggleGroup umDeCadaVez = new ToggleGroup();
        cpfRadioButton.setToggleGroup(umDeCadaVez);
        cnpjRadioButton.setToggleGroup(umDeCadaVez);

        Label mensagem = new Label();
        AnchorPane.setTopAnchor(mensagem, 350.0);
        AnchorPane.setLeftAnchor(mensagem, 20.0);
        mensagem.setStyle("-fx-text-fill:red");

        Label precosLabel = new Label();
        precosLabel.setText(String.format("Plateia A: %.2f\n", 40.00) +
                String.format("Plateia B: %.2f\n", 60.00) +
                String.format("Frisa: %.2f\n", 120.00) +
                String.format("Camarotes: %.2f\n", 80.00) +
                String.format("Balcão Nobre: %.2f\n", 250.00));
        AnchorPane.setTopAnchor(precosLabel, 265.0);
        AnchorPane.setLeftAnchor(precosLabel, 20.0);

        Label cpfLabel = new Label("Digite o seu CPF ou CNPJ:");
        AnchorPane.setTopAnchor(cpfLabel, 40.0);
        AnchorPane.setLeftAnchor(cpfLabel, 20.0);

        TextField digitarCPF = new TextField();
        AnchorPane.setTopAnchor(digitarCPF, 60.0);
        AnchorPane.setLeftAnchor(digitarCPF, 20.0);

        Label pecaLabel = new Label("Escolha a peça:");
        AnchorPane.setTopAnchor(pecaLabel, 90.0);
        AnchorPane.setLeftAnchor(pecaLabel, 20.0);

        ChoiceBox<String> escolherPeca = new ChoiceBox<>();
        escolherPeca.getItems().addAll(pecas);
        AnchorPane.setTopAnchor(escolherPeca, 110.0);
        AnchorPane.setLeftAnchor(escolherPeca, 20.0);

        Label horarioLabel = new Label("Escolha o horário:");
        AnchorPane.setTopAnchor(horarioLabel, 135.0);
        AnchorPane.setLeftAnchor(horarioLabel, 20.0);

        ChoiceBox<String> escolherHorario = new ChoiceBox<>();
        escolherHorario.getItems().addAll(horarios);
        AnchorPane.setTopAnchor(escolherHorario, 155.0);
        AnchorPane.setLeftAnchor(escolherHorario, 20.0);

        Label areaLabel = new Label("Escolha a área:");
        AnchorPane.setTopAnchor(areaLabel, 180.0);
        AnchorPane.setLeftAnchor(areaLabel, 20.0);

        ChoiceBox<String> areaChoice = new ChoiceBox<>();
        areaChoice.getItems().addAll(areas);
        AnchorPane.setTopAnchor(areaChoice, 195.0);
        AnchorPane.setLeftAnchor(areaChoice, 20.0);

        Label poltronaLabel = new Label("Escolha a poltrona:");
        AnchorPane.setTopAnchor(poltronaLabel, 225.0);
        AnchorPane.setLeftAnchor(poltronaLabel, 20.0);

        TextField escolherPoltrona = new TextField();
        AnchorPane.setTopAnchor(escolherPoltrona, 240.0);
        AnchorPane.setLeftAnchor(escolherPoltrona, 20.0);

        Button comprar = new Button("Comprar");
        comprar.setOnAction(e -> {
            mensagem.setText("");

            // Verifica se nenhum RadioButton está selecionado
            if (!cpfRadioButton.isSelected() && !cnpjRadioButton.isSelected()) {
                mensagem.setText("Selecione CPF ou CNPJ");
                return; // Encerra o método, pois um erro foi encontrado
            }

            if (digitarCPF.getText().isEmpty() || escolherPeca.getSelectionModel().isEmpty() ||
                    escolherHorario.getSelectionModel().isEmpty() || areaChoice.getSelectionModel().isEmpty() ||
                    escolherPoltrona.getText().isEmpty()) {
                mensagem.setText("Por favor, preencha todos os campos.");
            } else {
                try {
                    String digitado = digitarCPF.getText();
                    boolean isCPF = cpfRadioButton.isSelected();


                    int peca = escolherPeca.getSelectionModel().getSelectedIndex();
                    int horario = escolherHorario.getSelectionModel().getSelectedIndex();
                    int area = areaChoice.getSelectionModel().getSelectedIndex();
                    int poltrona = Integer.parseInt(escolherPoltrona.getText());


                    // Validação do CPF ou CNPJ
                    if (isCPF) {
                        long cpf = Long.parseLong(digitado);
                        if (!validarCPF(cpf)) {
                            mensagem.setText("CPF inválido. Por favor, insira um CPF válido.");
                            return;
                        }
                        int resultadoCompra = adicionarIngresso(cpf, peca, horario, area, poltrona);
                        if (resultadoCompra == 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Sucesso!");
                            alert.setHeaderText(null);
                            alert.setContentText("Ingresso comprado com sucesso!");
                            alert.showAndWait();


                            start(primaryStage);
                        } else if (resultadoCompra == 1) {
                            mensagem.setText("Não há mais ingressos disponíveis.");
                        } else if (resultadoCompra == 3) {
                            mensagem.setText("Número de poltrona inválido para a área escolhida!\nPor favor, escolha uma poltrona válida.");
                        } else if (resultadoCompra == 4) {
                            mensagem.setText("A poltrona escolhida já está ocupada. Por favor, escolha outra poltrona.");
                        }
                    } else {
                        long cnpj = Long.parseLong(digitado);
                        if (!validarCNPJ(cnpj)) {
                            mensagem.setText("CNPJ inválido. Por favor, insira um CNPJ válido.");
                            return;
                        }
                        int resultadoCompra = adicionarIngresso(cnpj, peca, horario, area, poltrona);
                        if (resultadoCompra == 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Sucesso!");
                            alert.setHeaderText(null);
                            alert.setContentText("Ingresso comprado com sucesso!");
                            alert.showAndWait();


                            start(primaryStage);
                        } else if (resultadoCompra == 1) {
                            mensagem.setText("Não há mais ingressos disponíveis.");
                        } else if (resultadoCompra == 3) {
                            mensagem.setText("Número de poltrona inválido para a área escolhida!\nPor favor, escolha uma poltrona válida.");
                        } else if (resultadoCompra == 4) {
                            mensagem.setText("A poltrona escolhida já está ocupada. Por favor, escolha outra poltrona.");
                        }
                    }
                } catch (NumberFormatException ex) {
                    mensagem.setText("Erro: Por favor, insira um CPF ou CNPJ válido e um número de poltrona válido.");
                }
            }
        });

        AnchorPane.setTopAnchor(comprar, 370.0);
        AnchorPane.setLeftAnchor(comprar, 20.0);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        AnchorPane.setTopAnchor(voltar, 370.0);
        AnchorPane.setLeftAnchor(voltar, 120.0);

        anchorPane.getChildren().addAll(cpfLabel, digitarCPF, pecaLabel, escolherPeca, horarioLabel, escolherHorario, areaLabel, areaChoice, poltronaLabel, escolherPoltrona, comprar, voltar, mensagem, imgItem, cnpjRadioButton, cpfRadioButton, precosLabel);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    comprar.fire();
                    break;
                case ESCAPE:
                    voltar.fire();
                    break;
                default:
                    break;
            }
        });
        primaryStage.setTitle("Comprar Ingresso");
    }


    private int adicionarIngresso(long cpf, int peca, int horario, int area, int poltrona) {
        if (totalVendas >= 255) {
            return 1; // Todos os ingressos foram vendidos
        }

        int areaMin, areaMax;
        switch (area + 1) {
            case 1:
                areaMin = 1;
                areaMax = 25;
                break;
            case 2:
                areaMin = 26;
                areaMax = 125;
                break;
            case 3:
                areaMin = 126;
                areaMax = 155;
                break;
            case 4:
                areaMin = 156;
                areaMax = 205;
                break;
            case 5:
                areaMin = 206;
                areaMax = 255;
                break;
            default:
                return 2; // Área inválida
        }

        if (poltrona < areaMin || poltrona > areaMax) {
            return 3; // Poltrona fora da área selecionada
        }

        long[][] pecaArray = switch (peca) {
            case 0 -> p1;
            case 1 -> p2;
            case 2 -> p3;
            default -> throw new IllegalArgumentException("Peça inválida");
        };

        int indiceHorario = switch (horario + 1) {
            case 1 -> 0;
            case 2 -> 1;
            case 3 -> 2;
            default -> throw new IllegalArgumentException("Horário inválido");
        };

        if (pecaArray[indiceHorario][poltrona - 1] != 0) {
            return 4; // Poltrona ocupada
        }
        pecaArray[indiceHorario][poltrona - 1] = cpf;

        totalVendas++;
        return 0; // Sucesso
    }


    private void imprimirIngresso(Stage primaryStage) {
        primaryStage.setTitle("Imprimir Ingresso");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1280, 750);
        anchorPane.setPadding(new Insets(10, 10, 10, 10));

        Label mensagem = new Label();
        mensagem.setTextAlignment(TextAlignment.CENTER);
        mensagem.setLayoutX(565.0);
        mensagem.setLayoutY(120.0);

        Label mensagemErro = new Label();
        mensagemErro.setStyle("-fx-text-fill: red");
        mensagemErro.setLayoutX(575.0);
        mensagemErro.setLayoutY(65.0);

        Label cpfLabel = new Label("Digite o seu CPF ou CNPJ:");
        cpfLabel.setLayoutX(605.0);
        cpfLabel.setLayoutY(17.0);

        TextField digitarCPF = new TextField();
        digitarCPF.setLayoutX(575.0);
        digitarCPF.setLayoutY(39.0);

        Button imprimir = new Button("Imprimir");
        imprimir.setOnAction(e -> {
            String cpfText = digitarCPF.getText();
            mensagemErro.setText("");
            mensagem.setText("");

            if (!cpfText.isEmpty()) {
                try {
                    long cpf = Long.parseLong(cpfText);
                    if (cpf == 0) {
                        mensagemErro.setText("CPF ou CNPJ inválido");
                        return;
                    }
                    String result = buscarIngresso(cpf);
                    if (result.equals("Ingresso não encontrado para o CPF ou CNPK+J informado.")) {
                        mensagemErro.setText(result);
                    } else {
                        mensagem.setText(result);
                    }
                } catch (NumberFormatException ex) {
                    mensagemErro.setText("CPF inválido");
                }
            } else {
                mensagemErro.setText("Digite algo válido");
            }
        });
        imprimir.setLayoutX(575.0);
        imprimir.setLayoutY(90.0);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        voltar.setLayoutX(675.0);
        voltar.setLayoutY(90.0);

        anchorPane.getChildren().addAll(cpfLabel, digitarCPF, imprimir, voltar, mensagem, mensagemErro);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    imprimir.fire();
                    break;
                case ESCAPE:
                    voltar.fire();
                    break;
                default:
                    break;
            }
        });
    }

    public static boolean validarCPF(long cpf) {
        String cpfString = String.format("%011d", cpf);
        if (cpf == 0 || cpf % 11111111111L == 0) {
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


    public static boolean validarCNPJ(long cnpj) {
        String cnpjString = String.format("%014d", cnpj);
        if (cnpj == 0 || cnpj % 11111111111111L == 0) {
            return false;
        }

        int[] cnpjArray = new int[14];
        for (int i = 0; i < 14; i++) {
            cnpjArray[i] = Character.getNumericValue(cnpjString.charAt(i));
        }
        int soma = 0;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            soma += cnpjArray[i] * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }
        int primeiroDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);


        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            soma += cnpjArray[i] * peso;
            peso++;
            if (peso == 10) {
                peso = 2;
            }
        }
        int segundoDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);


        return cnpjArray[12] == primeiroDigito && cnpjArray[13] == segundoDigito;
    }


    private String buscarIngresso(long cpf) {
        StringBuilder resultado = new StringBuilder();
        boolean encontrou = false;

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
                        if (!encontrou) {
                            encontrou = true;
                            resultado.append("Ingressos encontrados para o CPF:\n");
                        }
                        String cpfFormatado = String.format("%011d", cpf);
                        String area = "";
                        int poltrona = k + 1;


                        if (poltrona >= 1 && poltrona <= 25) {
                            area = "Plateia A";
                        } else if (poltrona >= 26 && poltrona <= 125) {
                            area = "Plateia B";
                        } else if (poltrona >= 126 && poltrona <= 155) {
                            area = "Frisa";
                        } else if (poltrona >= 156 && poltrona <= 205) {
                            area = "Camarote";
                        } else if (poltrona >= 206 && poltrona <= 255) {
                            area = "Balcão Nobre";
                        }

                        int indiceArea = switch (area) {
                            case "Plateia A" -> 0;
                            case "Plateia B" -> 1;
                            case "Frisa" -> 2;
                            case "Camarote" -> 3;
                            case "Balcão Nobre" -> 4;
                            default -> -1;
                        };
                        resultado.append("CPF: ").append(cpfFormatado).append("\n")
                                .append("Peça: ").append(nomePeca).append("\n")
                                .append("Sessão: ").append(horarios[j]).append("\n")
                                .append("Poltrona: ").append(poltrona).append("\n")
                                .append("Área: ").append(area).append("\n")
                                .append("Preço: R$ ").append(precos[indiceArea]).append("\n\n");

                    }
                }
            }
        }

        if (!encontrou) {
            return "Ingresso não encontrado para o CPF informado.";
        } else {
            return resultado.toString();
        }
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

        double lucroMedioPorPeca1 = vendasPorPeca[0] != 0 ? lucroPorPeca[0] / vendasPorPeca[0] : 0;
        double lucroMedioPorPeca2 = vendasPorPeca[1] != 0 ? lucroPorPeca[1] / vendasPorPeca[1] : 0;
        double lucroMedioPorPeca3 = vendasPorPeca[2] != 0 ? lucroPorPeca[2] / vendasPorPeca[2] : 0;

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

        Label lucroMedioLabel1 = new Label("Lucro médio da peça 1: R$ " + String.format("%.2f", lucroMedioPorPeca1));
        AnchorPane.setTopAnchor(lucroMedioLabel1, 170.0);
        AnchorPane.setLeftAnchor(lucroMedioLabel1, 20.0);

        Label lucroMedioLabel2 = new Label("Lucro médio da peça 2: R$ " + String.format("%.2f", lucroMedioPorPeca2));
        AnchorPane.setTopAnchor(lucroMedioLabel2, 200.0);
        AnchorPane.setLeftAnchor(lucroMedioLabel2, 20.0);

        Label lucroMedioLabel3 = new Label("Lucro médio da peça 3: R$ " + String.format("%.2f", lucroMedioPorPeca3));
        AnchorPane.setTopAnchor(lucroMedioLabel3, 230.0);
        AnchorPane.setLeftAnchor(lucroMedioLabel3, 20.0);

        Label sessaoMais1 = new Label("Sessão mais lucrativa da peça 1: " + horarios[sessaoMaisLucrativaPorPeca[0]]);
        AnchorPane.setTopAnchor(sessaoMais1, 260.0);
        AnchorPane.setLeftAnchor(sessaoMais1, 20.0);

        Label sessaoMenos1 = new Label("Sessão menos lucrativa da peça 1: " + horarios[sessaoMenosLucrativaPorPeca[0]]);
        AnchorPane.setTopAnchor(sessaoMenos1, 290.0);
        AnchorPane.setLeftAnchor(sessaoMenos1, 20.0);

        Label sessaoMais2 = new Label("Sessão mais lucrativa da peça 2: " + horarios[sessaoMaisLucrativaPorPeca[1]]);
        AnchorPane.setTopAnchor(sessaoMais2, 320.0);
        AnchorPane.setLeftAnchor(sessaoMais2, 20.0);

        Label sessaoMenos2 = new Label("Sessão menos lucrativa da peça 2: " + horarios[sessaoMenosLucrativaPorPeca[1]]);
        AnchorPane.setTopAnchor(sessaoMenos2, 350.0);
        AnchorPane.setLeftAnchor(sessaoMenos2, 20.0);

        Label sessaoMais3 = new Label("Sessão mais lucrativa da peça 3: " + horarios[sessaoMaisLucrativaPorPeca[2]]);
        AnchorPane.setTopAnchor(sessaoMais3, 380.0);
        AnchorPane.setLeftAnchor(sessaoMais3, 20.0);

        Label sessaoMenos3 = new Label("Sessão menos lucrativa da peça 3: " + horarios[sessaoMenosLucrativaPorPeca[2]]);
        AnchorPane.setTopAnchor(sessaoMenos3, 410.0);
        AnchorPane.setLeftAnchor(sessaoMenos3, 20.0);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        AnchorPane.setTopAnchor(voltar, 440.0);
        AnchorPane.setLeftAnchor(voltar, 20.0);

        anchorPane.getChildren().addAll(
                totalVendasLabel, pecaMaisVendidaLabel, pecaMenosVendidaLabel,
                sessaoMaisOcupadaLabel, sessaoMenosOcupadaLabel,
                lucroMedioLabel1, lucroMedioLabel2, lucroMedioLabel3,
                sessaoMenos1, sessaoMais1, sessaoMais2, sessaoMenos2,
                sessaoMais3, sessaoMenos3, voltar
        );


        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE:
                    voltar.fire();
                    break;
            }
        });
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
        } else {
            return 0;
        }
    }




    private int maisVendido(int[] vendas) {
        int max = 0;
        for (int i = 1; i < vendas.length; i++) {
            if (vendas[i] > vendas[max]) {
                max = i;
            }
        }
        return max;
    }


    private int menosVendido(int[] vendas) {
        int min = 0;
        for (int i = 1; i < vendas.length; i++) {
            if (vendas[i] < vendas[min]) {
                min = i;
            }
        }
        return min;
    }
}
