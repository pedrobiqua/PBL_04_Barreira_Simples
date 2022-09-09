import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.Semaphore;

public class Thread4 extends Thread {
    /* TO DO:
    A. Calcula o valor de plano de saúde para cada funcionário e atualizando o seu 
    total de descontos e o seu salário líquido. O valor do plano de saúde é 
    calculado como 2% do salário bruto. Executa quatro passos em sequência, um 
    para cada parte da lista, iniciando pela parte 4, depois as partes 1, 2 e 3.

    B. Espera as 4 threads concluírem a fase A (rendezvous) 

    C. Imprime os contra-cheques dos funcionários da parte 4 da lista, gerando um 
    arquivo chamado parte4.txt
    */

    /**
     * Semaforo usado para controlar o acesso a lista de funcionários
     */
    Semaphore mutex, mutex1, mutex2, mutex3, mutex4, barreira;

    public Thread4(Semaphore mutex, Semaphore mutex1, Semaphore mutex2, Semaphore mutex3, Semaphore mutex4, Semaphore barreira) {
        this.mutex = mutex;
        this.mutex1 = mutex1;
        this.mutex2 = mutex2;
        this.mutex3 = mutex3;
        this.mutex4 = mutex4;
        this.barreira = barreira;
    }

    @Override
    public void run() {
        CalculaPlanoDeSaude();
    }

    public void CalculaPlanoDeSaude() {

        try {
            var funcionario = Funcionarios.lFuncionarios;

            // Parte 4
            mutex4.acquire();
                var ultimaPosicaoP4 = Thread0.lParte4.get(Thread0.lParte4.size() - 1);
                for (int i = Thread0.lParte4.get(0); i < ultimaPosicaoP4; i++) {
                    funcionario.get(i).desconto_plano_sus = funcionario.get(i).salario_bruto * 0.02;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_plano_sus;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Plano de saude parte 4");
            mutex4.release();
            
            // Parte 1
            mutex1.acquire();
                var ultimaPosicaoP1 = Thread0.lParte1.get(Thread0.lParte1.size() - 1);
                for (int i = Thread0.lParte1.get(0); i < ultimaPosicaoP1; i++) {
                    funcionario.get(i).desconto_plano_sus = funcionario.get(i).salario_bruto * 0.02;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_plano_sus;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Plano de saude parte 1");
            mutex1.release();
            
            // Parte 2
            mutex2.acquire();
                var ultimaPosicaoP2 = Thread0.lParte2.get(Thread0.lParte2.size() - 1);
                for (int i = Thread0.lParte2.get(0); i < ultimaPosicaoP2; i++) {
                    funcionario.get(i).desconto_plano_sus = funcionario.get(i).salario_bruto * 0.02;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_plano_sus;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Plano de saude parte 2");
            mutex2.release();

            // Parte 3
            mutex3.acquire();
                var ultimaPosicaoP3 = Thread0.lParte3.get(Thread0.lParte3.size() - 1);
                for (int i = Thread0.lParte3.get(0); i < ultimaPosicaoP3; i++) {
                    funcionario.get(i).desconto_plano_sus = funcionario.get(i).salario_bruto * 0.02;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_plano_sus;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Plano de saude parte 3");
            mutex3.release();

            mutex.acquire();
                Thread0.contadorThreads++;
                if (Thread0.contadorThreads == Thread0.nThreads) {
                    barreira.release();
                }
            mutex.release();

            System.out.println("[THREAD 4] chegou na barreira");
            barreira.acquire();
            barreira.release();
            System.out.println("[THREAD 4] passou da barreira");
            
            // Ponto Critico, responsavel por imprimir a parte 1
            mutex4.acquire();
                // Imprime contra cheque
                ImprimeContraCheque();
            mutex4.release();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ImprimeContraCheque() {
        try {
            Path path = FileSystems.getDefault().getPath("");
		    String directoryName = path.toAbsolutePath().toString();
            System.out.println(directoryName + "\\src\\arquivos\\parte4.txt");

            File file = new File(directoryName + "\\src\\arquivos\\parte4.txt");
            // Cria arquivo
            file.createNewFile();

            // Escreve no arquivo criado
            FileWriter myWriter = new FileWriter(directoryName + "\\src\\arquivos\\parte4.txt");
            var ultimaPosicaoP4 = Thread0.lParte4.get(Thread0.lParte4.size() - 1);
            for (int i = Thread0.lParte4.get(0); i < ultimaPosicaoP4; i++) {
                myWriter.write(Funcionarios.lFuncionarios.get(i).relatorioDeDados());
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");

        } catch (Exception e) {
            System.out.println("Erro ae escrever o arquivo");
        }
    }
}
