package pi;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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

        long[][] p1 = new long[3][255];
        long[][] p2 = new long[3][255];
        long[][] p3 = new long[3][255];
        Scanner ler = new Scanner(System.in);
        System.out.println("digite o seu cpf");
        long cpf = ler.nextLong();
        System.out.println("digite para qual peça vc quer o ingresso (p1, p2 ou p3)");
        String peca = ler.next();

    }
}