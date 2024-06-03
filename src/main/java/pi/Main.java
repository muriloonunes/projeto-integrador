package pi;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);

        //0-24 plateia a
        //25-124 plateia b
        //125-129 frisa 1
        //130-134 frisa 2
        //135-139 frisa 3
        //140-144 frisa 4
        //145-149 frisa 5
        //150-154 frisa 6
        //155-164 camarote 1
        //165-174 camarote 2
        //175-184 camarote 3
        //185-194 camarote 4
        //195-204 camarote 5
        //205-254 balcão norte
        //cu
        int[] poltronas = new int[255];

        //linha 0 matutino
        //linha 1 vespertino
        //linha 3 noturno
        long[][] p1 = new long[3][255];
        long[][] p2 = new long[3][255];
        long[][] p3 = new long[3][255];

        
        System.out.println("Digite o seu CPF.");
        long cpf = ler.nextLong();

        System.out.println("Digite para qual peça vc quer o ingresso (p1, p2 ou p3).");
        String peca = ler.next();

        System.out.println("Digite horário da peça.");
        String horario = ler.next();


        
    }
}