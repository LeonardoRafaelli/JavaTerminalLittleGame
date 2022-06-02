public class MainTest {
    public static void main(String[] args) {
        String[] vetor = new String[2];

        for(int i = 0; i <= vetor.length; i++){
            vetor[i] = "T";
        }

        for(String s : vetor){
            System.out.println(s);
        }
    }
}
