import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Thread0 extends Thread {
    /* TO DO:
    1. Cria uma lista de n funcionários, onde n é um múltiplo de 4, de forma que, para 
    cada funcionário,  conste: 
        a.  código (inteiro): definido na criação do funcionário (valor único) 
        b.  salário bruto (double): definido na criação do funcionário (valor 
            aleatório entre 1000 e 5000) 
        c. desconto de imposto de renda: calculado pela Thread 1 
        d. desconto de INSS: calculado pela Thread 2 
        e. desconto de previdência privada (double): calculado pela Thread 3 
        f. desconto de plano de saúde (double): calculado pela Thread 4 
        g. total de descontos (double): atualizado quando cada desconto é 
            calculado (pelas threads 1, 2, 3 e 4) 
        h.  salário líquido (double): diferença entre o salário bruto e o total dos 
            descontos; valor atualizado toda vez que o total de descontos é 
            atualizado. 
    2. Divide a lista em quatro partes de igual tamanho, denominadas parte 1, parte 2, 
    parte 3 e parte 4.

    3. Cria e inicia as threads 1 a 4, informando-as sobre o início e o fim de cada parte 
    da lista.
    */

    // Salvando os indeces
    static ArrayList<Integer> lParte1 = new ArrayList<Integer>();
    static ArrayList<Integer> lParte2 = new ArrayList<Integer>();
    static ArrayList<Integer> lParte3 = new ArrayList<Integer>();
    static ArrayList<Integer> lParte4 = new ArrayList<Integer>();
    static int contadorThreads = 0;
    static int nThreads = 4;

    Semaphore mutex, mutex1, mutex2, mutex3, mutex4, barreira;

    public Thread0(Semaphore mutex, Semaphore mutex1, Semaphore mutex2, Semaphore mutex3, Semaphore mutex4, Semaphore barreira) {
        this.mutex = mutex;
        this.mutex1 = mutex1;
        this.mutex2 = mutex2;
        this.mutex3 = mutex3;
        this.mutex4 = mutex4;
        this.barreira = barreira;
    }

    @Override
    public void run() {
        // Cria todos os funcionários
        int n = 100; // Esse n pode ser passado como parametro
        for (int i = 0; i < n; i++) {
            // Adicionando código a todos os funcionários
            Funcionarios.lFuncionarios.add(new Funcionarios(i + 1));
        }

        // Separando a lista de funcionários em 4 partes
        divideLista4partes();

        Thread1 thread1 = new Thread1(mutex, mutex1, mutex2, mutex3, mutex4, barreira);
        Thread2 thread2 = new Thread2(mutex, mutex1, mutex2, mutex3, mutex4, barreira);
        Thread3 thread3 = new Thread3(mutex, mutex1, mutex2, mutex3, mutex4, barreira);
        Thread4 thread4 = new Thread4(mutex, mutex1, mutex2, mutex3, mutex4, barreira);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        
    }

    public void divideLista4partes() {
        int particoes = (Funcionarios.lFuncionarios.size() / 4);
        int contador = 0;
        int i = 0;
        int partes = particoes;

        while( contador < 4){
            for ( ;i < partes; i++) {
                
                // Adiciona o indice das partes
                if ( contador == 0) {
                    lParte1.add(i);
                } else if( contador == 1) {
                    lParte2.add(i);
                } else if( contador == 2) {
                    lParte3.add(i);
                } else if( contador == 3) {
                    lParte4.add(i);
                }
            }

            contador++;
            i = partes;
            partes = particoes + partes;
        }
    }

    public void ImprimeContraCheque() {
        try {
            Path path = FileSystems.getDefault().getPath("");
		    String directoryName = path.toAbsolutePath().toString();
            System.out.println(directoryName + "\\src\\aaaaa.txt");

            File file = new File(directoryName + "\\src\\arquivos\\parte1.txt");
            file.createNewFile();

        } catch (Exception e) {
            System.out.println("To no catch");
        }
    }
}
