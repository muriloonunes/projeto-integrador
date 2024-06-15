package pi.projetointegrador;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Main extends Application {
    static String[] pecas = {"Peça 1", "Peça 2", "Peça 3"};
    static String[] horarios = {"Manhã", "Tarde", "Noite"};
    static String[] areas = {"Plateia A (poltronas 1 a 25)", "Plateia B (poltronas 26 a 125)", "Frisa (poltronas 126 a 155)", "Camarote (poltronas 156 a 205)", "Balcão Nobre (poltronas 206 a 255)"};
    static double[] precos = {40.00, 60.00, 120.00, 80.00, 250.00};
    static long[][] p1 = new long[3][255];
    static long[][] p2 = new long[3][255];
    static long[][] p3 = new long[3][255];
    static int totalVendas = 0;
    private int quantidadeIngressos = 0;

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
            // janela para definir a quantidade de ingressos
            Stage quantidadeStage = new Stage();
            quantidadeStage.setTitle("Quantidade de Ingressos");
            quantidadeStage.initModality(Modality.WINDOW_MODAL);    // bloqueia a janela do menu inicial até que essa seja fechada
            quantidadeStage.initOwner(primaryStage);

            AnchorPane quantidadePane = new AnchorPane();
            quantidadePane.setPrefSize(300, 150);
            quantidadePane.setPadding(new Insets(20));

            Label quantidadeLabel = new Label("Quantidade de ingressos:");
            quantidadeLabel.setLayoutX(35.0);
            quantidadeLabel.setLayoutY(10.0);

            TextField quantidadeIngressosField = new TextField();
            quantidadeIngressosField.setLayoutX(35.0);
            quantidadeIngressosField.setLayoutY(30.0);

            Label mensagem = new Label();
            mensagem.setStyle("-fx-text-fill:red");
            mensagem.setLayoutX(35.0);
            mensagem.setLayoutY(55.0);

            Button okButton = new Button("OK");
            okButton.setLayoutX(35.0);
            okButton.setLayoutY(75.0);

            Button voltarButton = new Button("Voltar");
            voltarButton.setLayoutX(75.0);
            voltarButton.setLayoutY(75.0);

            okButton.setOnAction(event -> {
                mensagem.setText("");
                try {
                    quantidadeIngressos = Integer.parseInt(quantidadeIngressosField.getText());
                    if (quantidadeIngressos <= 0) {
                        mensagem.setText("Quantidade inválida. Por favor, insira um número positivo.");
                    } else {
                        quantidadeStage.close();    // fecha a tela e chama o método de comprarIngresso
                        comprarIngresso(primaryStage);
                    }
                } catch (NumberFormatException ex) {
                    mensagem.setText("Por favor, insira um número válido.");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });

            voltarButton.setOnAction(event -> {
                quantidadeStage.close();
            });

            quantidadePane.getChildren().addAll(quantidadeLabel, quantidadeIngressosField, okButton, voltarButton, mensagem);

            Scene scene = new Scene(quantidadePane);

            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case ESCAPE -> voltarButton.fire();
                    case ENTER -> okButton.fire();
                }
            });

            quantidadeStage.setScene(scene);
            quantidadeStage.showAndWait();

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

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE -> sair.fire();
            }
        });
    }

    private void comprarIngresso(Stage primaryStage) throws FileNotFoundException {
        Stage comprarIngressoStage = new Stage();
        comprarIngressoStage.setTitle("Comprar Ingresso");

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

        // permite que apenas um dos RadioButtons seja selecionado de cada vez
        ToggleGroup umDeCadaVez = new ToggleGroup();
        cpfRadioButton.setToggleGroup(umDeCadaVez);
        cnpjRadioButton.setToggleGroup(umDeCadaVez);

        Label mensagem = new Label();
        AnchorPane.setTopAnchor(mensagem, 360.0);
        AnchorPane.setLeftAnchor(mensagem, 20.0);
        mensagem.setStyle("-fx-text-fill:red");

        Label precosLabel = new Label();
        precosLabel.setText("""
                            Plateia A: R$40,00
                            Plateia B: R$60,00
                            Frisa: R$120,00
                            Camarotes: R$80,00
                            Balcão Nobre: R$250,00
                            """);
        AnchorPane.setTopAnchor(precosLabel, 270.0);
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

            // verifica se nenhum RadioButton foi selecionado
            if (!cpfRadioButton.isSelected() && !cnpjRadioButton.isSelected()) {
                mensagem.setText("Selecione CPF ou CNPJ");
                return; // retorna, pois caso contrário ele ainda exibiria as outras mensagens de erro
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

                    if (isCPF) {
                        long cpf = Long.parseLong(digitado);
                        if (!validarCPF(cpf)) {
                            mensagem.setText("CPF inválido. Por favor, insira um CPF válido.");
                            return;
                        }
                        int resultadoCompra = adicionarIngresso(cpf, peca, horario, area, poltrona);
                        switch (resultadoCompra) {
                            case 0 -> {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Sucesso!");
                                alert.setHeaderText(null);
                                alert.setContentText("Ingresso comprado com sucesso!");
                                alert.showAndWait();
                                // Limpa os campos para o proóximo ingresso ser comprado
                                digitarCPF.setText("");
                                escolherPeca.getSelectionModel().clearSelection();
                                escolherHorario.getSelectionModel().clearSelection();
                                areaChoice.getSelectionModel().clearSelection();
                                escolherPoltrona.setText("");
                                quantidadeIngressos--; // decrementa a quantidade de ingressos que foi digitada anteriormente
                                if (quantidadeIngressos <= 0) {
                                    comprarIngressoStage.close();
                                    start(primaryStage); // todos os ingressos já foram comprados, então ele volta para o menu principal
                                }
                            }
                            case 1 -> mensagem.setText("Não há mais ingressos disponíveis.");
                            case 3 -> mensagem.setText("Número de poltrona inválido para a área escolhida!\nPor favor, escolha uma poltrona válida.");
                            case 4 -> mensagem.setText("A poltrona escolhida já está ocupada. Por favor, escolha outra poltrona.");
                            default -> {
                            }
                        }
                    } else {
                        long cnpj = Long.parseLong(digitado);
                        if (!validarCNPJ(cnpj)) {
                            mensagem.setText("CNPJ inválido. Por favor, insira um CNPJ válido.");
                            return;
                        }
                        int resultadoCompra = adicionarIngresso(cnpj, peca, horario, area, poltrona);
                        switch (resultadoCompra) {
                            case 0 -> {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Sucesso!");
                                alert.setHeaderText(null);
                                alert.setContentText("Ingresso comprado com sucesso!");
                                alert.showAndWait();
                                // Limpa os campos para o proóximo ingresso ser comprado
                                digitarCPF.setText("");
                                escolherPeca.getSelectionModel().clearSelection();
                                escolherHorario.getSelectionModel().clearSelection();
                                areaChoice.getSelectionModel().clearSelection();
                                escolherPoltrona.setText("");
                                quantidadeIngressos--; // decrementa a quantidade de ingressos que foi digitada anteriormente
                                if (quantidadeIngressos <= 0) {
                                    comprarIngressoStage.close();
                                    start(primaryStage); // todos os ingressos já foram comprados, então ele volta para o menu principal
                                }
                            }
                            case 1 -> mensagem.setText("Não há mais ingressos disponíveis.");
                            case 3 -> mensagem.setText("Número de poltrona inválido para a área escolhida!\nPor favor, escolha uma poltrona válida.");
                            case 4 -> mensagem.setText("A poltrona escolhida já está ocupada. Por favor, escolha outra poltrona.");
                            default -> {
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    mensagem.setText("Erro: Por favor, insira um CPF ou CNPJ válido e um número de poltrona válido.");
                }
            }
        });

        AnchorPane.setTopAnchor(comprar, 380.0);
        AnchorPane.setLeftAnchor(comprar, 20.0);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(e -> {
            start(primaryStage);
        });
        AnchorPane.setTopAnchor(voltar, 380.0);
        AnchorPane.setLeftAnchor(voltar, 120.0);

        anchorPane.getChildren().addAll(cpfLabel, digitarCPF, pecaLabel, escolherPeca, horarioLabel, escolherHorario, areaLabel, areaChoice, poltronaLabel, escolherPoltrona, comprar, voltar, mensagem, imgItem, cnpjRadioButton, cpfRadioButton, precosLabel);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> comprar.fire();
                case ESCAPE -> voltar.fire();
                default -> {
                }
            }
        });
        primaryStage.setTitle("Comprar Ingresso");
    }

    public static boolean validarCPF(long cpf) {
        String cpfString = String.format("%011d", cpf);
        if (cpf == 0 || cpf % 11111111111L == 0) {
            return false;
            // retorna false se o cpf digitado foi 000 ou se todos seus dígitos forem iguais (22222222222, etc)
        }

        int[] cpfArray = new int[11];
        for (int i = 0; i < 11; i++) {
            cpfArray[i] = Integer.parseInt(String.valueOf(cpfString.charAt(i)));
            // passa os dígitos da string cpf para cada posiçao do array
        }

        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += cpfArray[i] * peso;
            peso--;
        }
        int primeiroDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        // se o resto da divisão de soma por 11 for menor que 2, primeiroDigito recebe 0. caso contrario, primeiroDigito recebe 11 - soma % 11

        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += cpfArray[i] * peso;
            peso--;
        }
        int segundoDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
        // se o resto da divisão de soma por 11 for menor que 2, segundoDigito recebe 0. caso contrario, segundoDigito recebe 11 - soma % 11

        return cpfArray[9] == primeiroDigito && cpfArray[10] == segundoDigito;
        // verifica se as variaveis primeiroDigito e segundoDigito sao iguais aos digitos verificadores do CPF, que estao na posicao 9 e 10 do array
    }

    public static boolean validarCNPJ(long cnpj) {
        String cnpjString = String.format("%014d", cnpj);
        if (cnpj == 0 || cnpj % 11111111111111L == 0) {
            return false;
            // retorna false se o cnpj digitado foi 000 ou se todos seus dígitos forem iguais (22222222222222, etc)
        }

        int[] cnpjArray = new int[14];
        for (int i = 0; i < 14; i++) {
            cnpjArray[i] = Character.getNumericValue(cnpjString.charAt(i));
            // passa os dígitos da string cpf para cada posiçao do array
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
        // se o resto da divisão de soma por 11 for menor que 2, primeiroDigito recebe 0. caso contrario, primeiroDigito recebe 11 - soma % 11

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
        // se o resto da divisão de soma por 11 for menor que 2, segundoDigito recebe 0. caso contrario, segundoDigito recebe 11 - soma % 11

        return cnpjArray[12] == primeiroDigito && cnpjArray[13] == segundoDigito;
        // verifica se as variaveis primeiroDigito e segundoDigito sao iguais aos digitos verificadores do CPF, que estao na posicao 9 e 10 do array
    }

    private int adicionarIngresso(long cpf, int peca, int horario, int area, int poltrona) {
        if (totalVendas >= 255) {
            return 1; // Todos os ingressos foram vendidos
        }

        // serve apenas para definir se a poltrona que o usuário digitou está dentro da área selecionada por ele:
        int areaMin, areaMax;
       switch (area + 1) {
            case 1 -> {
                areaMin = 1;
                areaMax = 25;
            }
            case 2 -> {
                areaMin = 26;
                areaMax = 125;
            }
            case 3 -> {
                areaMin = 126;
                areaMax = 155;
            }
            case 4 -> {
                areaMin = 156;
                areaMax = 205;
            }
            case 5 -> {
                areaMin = 206;
                areaMax = 255;
            }
            default -> {
                return 2; // Área inválida
            }
        }

        if (poltrona < areaMin || poltrona > areaMax) {
            return 3; // Poltrona fora da área selecionada
        }

        // define em qual das matrizes (p1, p2 ou p3) os dados serão adicionados
        long[][] pecaArray = switch (peca) {
            case 0 -> p1;
            case 1 -> p2;
            case 2 -> p3;
            default -> throw new IllegalArgumentException("Peça inválida");
        };

        // define em qual das linhas da matriz selecionada os dados serão adicionados
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
        cpfLabel.setLayoutX(575.0);
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
                    long numero = Long.parseLong(cpfText);
                    String tipoDoc = validarCPF(numero) ? "CPF" : (validarCNPJ(numero) ? "CNPJ" : "CPF ou CNPJ inválido");
                    // se validarCPF retornar true, tipoDoc recebe "CPF", se validarCNPJ retornar true, tipoDoc recebe "CNPJ", caso contrário, tipoDoc recebe "CPF ou CNPJ inválido"
                    if (tipoDoc.equals("CPF ou CNPJ inválido")) {
                        mensagemErro.setText(tipoDoc);
                        return;
                    }
                    String result = buscarIngresso(numero, tipoDoc);
                    if (result.equals("Ingresso não encontrado para o CPF ou CNPJ informado.")) {
                        mensagemErro.setText(result);
                    } else {
                        mensagem.setText(result);
                    }
                } catch (NumberFormatException ex) {
                    mensagemErro.setText("CPF ou CNPJ inválido");
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

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER -> imprimir.fire();
                case ESCAPE -> voltar.fire();
                default -> {
                }
            }
        });
    }

    private String buscarIngresso(long cpf, String tipoDoc) {
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
                            resultado.append("Ingressos encontrados para o ").append(tipoDoc).append(":\n");
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
                        resultado.append(tipoDoc).append(": ").append(cpfFormatado).append("\n")
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
            return "Ingresso não encontrado para o CPF ou CNPJ informado.";
        } else {
            return resultado.toString();
        }
    }

    private void estatisticasVendas(Stage primaryStage) {
        primaryStage.setTitle("Estatísticas de Vendas");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(1280, 750);
        anchorPane.setPadding(new Insets(15, 15, 15, 15));

        // vetores com 3 linhas, cada linha representa as estatísticas de uma peça
        int[] vendasPorPeca = new int[3];
        int[] vendasPorSessao = new int[3];
        double[] lucroPorPeca = new double[3];
        double[] lucroMaximoPorPeca = new double[3];
        int[] sessaoMaisLucrativaPorPeca = new int[3];
        double[] lucroMinimoPorPeca = new double[3];
        int[] sessaoMenosLucrativaPorPeca = new int[3];

        // seleciona a matriz da peça correspondente à linha do vetor
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
                    if (peca[j][k] != 0) { // peca[j][k] representa a poltrona k na sessão j da peça atual
                        // peca[j][k] != 0 verifica se a poltrona está ocupada
                        vendasPorPeca[i]++; // aumenta o contador de vendas da peça
                        lucroPorPeca[i] += precoPorPoltrona(k + 1); // adiciona o preço da poltrona ao lucro da peça
                        vendasPorSessao[j]++; // aumenta o contador de vendas da peça
                        lucroSessao += precoPorPoltrona(k + 1); // adiciona o preço da poltrona ao lucro da sessão atual
                    }
                }
                if (lucroSessao > lucroMaximoPorPeca[i]) {
                    lucroMaximoPorPeca[i] = lucroSessao;
                    sessaoMaisLucrativaPorPeca[i] = j;
                    // se o lucro da sessao atual for maior que o lucro maximo que ja tava registrado, o lucro máximo da peça é atualizado para armazenar o lucro atual
                    // e tambem armazena o indice da sessao atual como a mais lucrativa
                }
                if (lucroSessao < lucroMinimoPorPeca[i] || lucroMinimoPorPeca[i] == 0) {
                    lucroMinimoPorPeca[i] = lucroSessao;
                    sessaoMenosLucrativaPorPeca[i] = j;
                    // se o lucro da sessão atual é menor que o lucro mínimo já registrado ou se ainda não foi definido (igual a 0)
                    // o lucro minimo da peça é atualizado para armazenar o lucro atual e armazena essa sessao como a menos lucrativa
                }
            }
        }

        int pecaMaisVendida = maisVendido(vendasPorPeca);
        int pecaMenosVendida = menosVendido(vendasPorPeca);
        int sessaoMaisOcupada = maisVendido(vendasPorSessao);
        int sessaoMenosOcupada = menosVendido(vendasPorSessao);

        // verifica se o numero de vendas da peça é diferente de 0, se for, o lucro medio é calculado dividindo o lucro total pelo numero de ingressos vendidos
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

        anchorPane.getChildren().addAll(totalVendasLabel, pecaMaisVendidaLabel, pecaMenosVendidaLabel, sessaoMaisOcupadaLabel, sessaoMenosOcupadaLabel, lucroMedioLabel1, lucroMedioLabel2, lucroMedioLabel3, sessaoMenos1, sessaoMais1, sessaoMais2, sessaoMenos2, sessaoMais3, sessaoMenos3, voltar);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ESCAPE) {
                voltar.fire();
            }
        });
    }

    private double precoPorPoltrona(int poltrona) {
        return switch (poltrona) {
            case 1 -> precos[0];
            case 26 -> precos[1];
            case 126 -> precos[2];
            case 156 -> precos[3];
            case 206 -> precos[4];
            default -> 0;
        };
    }

    private int maisVendido(int[] vendas) {
        // recebe um array e retorna o indice do elemento de maior valor
        int max = 0;
        for (int i = 1; i < vendas.length; i++) {
            if (vendas[i] > vendas[max]) {
                max = i;
            }
        }
        return max;
    }

    private int menosVendido(int[] vendas) {
        // recebe um array e retorna o indice do elemento de menor valor
        int min = 0;
        for (int i = 1; i < vendas.length; i++) {
            if (vendas[i] < vendas[min]) {
                min = i;
            }
        }
        return min;
    }
}
