import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Pessoa> listaLadoDireito = new ArrayList<>();
    static ArrayList<Pessoa> listaLadoEsquerdo = new ArrayList<>();
    static String[] barcoVetor = new String[2];

    static boolean losing = true;

    public static void main(String[] args) {
        inserePersonagens();
        try{
            jogo();
        } catch (RuntimeException err){
            System.out.println("Erro: " + err.getClass().getSimpleName());
        }
    }

    private static void jogo() throws RuntimeException{
        int counter = 0;
        do{
            mapa();
            for (Pessoa p : listaLadoEsquerdo){
                if(p.isActivated()){
                    counter++;
                }
            }
            if(counter == 6){
                losing = false;
            } else {
                System.out.print("1 - Levar Jogador ao barco" +
                        "\n2 - Retirar Jogador do barco " +
                        "\n-->: ");
                int opcao = sc.nextInt();
                switch (opcao){
                    case 1 -> {
                        try{
                            jogadorAoBarco();
                        }
                        catch (RuntimeException ex){
                            System.out.println("Erro: " + ex.getClass().getSimpleName());
                        }
                    }
                    case 2 -> retiraDoBarco();
                }
                counter = 0;
            }
        }while(losing);

        if(counter == 6){
            System.out.println("Boa viado, ganhastes");
        } else {
            throw new LooserException();
        }
    };

    private static void jogadorAoBarco() throws RuntimeException{
        System.out.print("Digite o jogador que deseja colocar no barco.\n-->: ");
        String decisao = sc.next();
        for (Pessoa jogador : listaLadoDireito) {
            if (jogador.isActivated() && jogador.getSigla().equals(decisao)) {
                if(barcoVetor[0] == null){
                    barcoVetor[0] = jogador.getSigla();
                    jogador.setActivated(false);
                } else if(barcoVetor[1] == null){
                    barcoVetor[1] = jogador.getSigla();
                    jogador.setActivated(false);
                } else if(barcoVetor.length == 2){
                    throw new BoatNumberException();
                }
            }
        }
        for (Pessoa jogador : listaLadoEsquerdo) {
            if (jogador.isActivated() && jogador.getSigla().equals(decisao)) {

                if(barcoVetor[0] == null){
                    barcoVetor[0] = jogador.getSigla();
                } else if(barcoVetor[1] == null){
                    barcoVetor[1] = jogador.getSigla();
                } else if(barcoVetor.length == 2){
                    throw new BoatNumberException();
                }

                jogador.setActivated(false);
            }
        }

    }

    private static void retiraDoBarco(){
        System.out.print("Selecione o lado que deseja soltar o desgraçado: " +
                "\n1 - Esquerdo." +
                "\n2 - Direito;" +
                "\n-->: ");
        if(sc.nextInt() == 2){
            verificaVitoriaDireita();
            if(losing){
                retiraParaDireita();
            }
        } else {
            verificaVitoriaEsquerda();
            if(losing){
                retiraParaEsquerda();
            }
        }
    }

    private static void retiraParaDireita(){
        System.out.print("Digite o jogador que deseja retirar do barco.\n-->: ");
        String decisao = sc.next();
        for(int i = 0 ; i < barcoVetor.length; i++){
            if(barcoVetor[i] != null){
                if(barcoVetor[i].equals(decisao)){
                    barcoVetor[i] = null;
                    i = 2;
                    for(Pessoa p : listaLadoDireito){
                        if(p.getSigla().equals(decisao)){
                            p.setActivated(true);
                        }
                    }
                }
            }
        }
    }
    private static void retiraParaEsquerda(){
        System.out.print("Digite o jogador que deseja retirar do barco.\n-->: ");
        String decisao = sc.next();
        for(int i = 0 ; i < barcoVetor.length; i++){
            if(barcoVetor[i] != null){
                if(barcoVetor[i].equals(decisao)){
                    barcoVetor[i] = null;
                    for(Pessoa p : listaLadoEsquerdo){
                        if(p.getSigla().equals(decisao)){
                            p.setActivated(true);
                        }
                    }
                }
            }
        }
    }



    private static void mapa(){
        String b1 = barcoVetor[0] == null ? "" : barcoVetor[0];
        String b2 = barcoVetor[1] == null ? "" : barcoVetor[1];
        System.out.println(
                isJogadorEsquerdoAtivo("C1") + "    |       |   " + isJogadorDireitoAtivo("C1") + "\n" +
                isJogadorEsquerdoAtivo("C2") + "    |       |   " + isJogadorDireitoAtivo("C2") + "\n" +
                isJogadorEsquerdoAtivo("C3") + "    |       |   " + isJogadorDireitoAtivo("C3") + "\n" +
                isJogadorEsquerdoAtivo("M1") + "    |       |   " + isJogadorDireitoAtivo("M1") + "\n" +
                isJogadorEsquerdoAtivo("M2") + "    |       |   " + isJogadorDireitoAtivo("M2") + "\n" +
                isJogadorEsquerdoAtivo("M3") + "    |       |   " + isJogadorDireitoAtivo("M3") +
                        "\n         " + b1 + " " + b2 +
                      "\n        ______");
    }

    private static void verificaVitoriaDireita(){
        int miss = 0, canibas = 0;
        for(Pessoa p : listaLadoDireito){
            if(p.isActivated()){
                if(p instanceof Canibal){
                    canibas++;
                } else {
                    miss++;
                }
            }
        }
        for (String p : barcoVetor) {
            if(p != null){
                if (p.equals("M1") || p.equals("M2") || p.equals("M3")) {
                    miss++;
                }
            }
        }
        if(canibas > miss && miss != 0){
            losing = false;
        }
    }

    private static void verificaVitoriaEsquerda(){
        int canibas = 0;
        int miss = 0;
        for(Pessoa p : listaLadoEsquerdo){
            if(p.isActivated()){
                if(p instanceof Canibal){
                    canibas++;
                } else {
                    miss++;
                }
            }
        }
        for (String p : barcoVetor) {
            if(p != null){
                if (p.equals("M1") || p.equals("M2") || p.equals("M3")) {
                    miss++;
                }
            }
        }
        if(canibas > miss && miss != 0) {
            losing = false;
        }
    }


    private static String isJogadorDireitoAtivo(String sigla){
        for (Pessoa pessoa : listaLadoDireito) {
            if (pessoa.isActivated() && pessoa.getSigla().equals(sigla)) {
                return pessoa.getSigla();
            }
        }
        return "  ";
    };

    private static String isJogadorEsquerdoAtivo(String sigla){
        for (Pessoa pessoa : listaLadoEsquerdo) {
            if (pessoa.isActivated() && pessoa.getSigla().equals(sigla)) {
                return pessoa.getSigla();
            }
        }
        return "  ";
    };



    private static void inserePersonagens(){
        listaLadoDireito.add(new Canibal(1,"C1", true));
        listaLadoDireito.add(new Canibal(2,"C2", true));
        listaLadoDireito.add(new Canibal(3,"C3", true));
        listaLadoDireito.add(new Missionario(4,"M1", true));
        listaLadoDireito.add(new Missionario(5,"M2", true));
        listaLadoDireito.add(new Missionario(6,"M3", true));

        listaLadoEsquerdo.add(new Canibal(1,"C1", false));
        listaLadoEsquerdo.add(new Canibal(2,"C2", false));
        listaLadoEsquerdo.add(new Canibal(3,"C3", false));
        listaLadoEsquerdo.add(new Missionario(4,"M1", false));
        listaLadoEsquerdo.add(new Missionario(5,"M2", false));
        listaLadoEsquerdo.add(new Missionario(6,"M3", false));

    }
}
