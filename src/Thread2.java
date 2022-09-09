import java.io.File;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.Semaphore;

public class Thread2 extends Thread {
    /* TO DO:
    A. Calcula o valor de previdência obrigatória (INSS) para cada funcionário, 
    atualizando o seu total de descontos e o seu salário líquido. O valor de INSS é 
    calculado como 8% do salário bruto. Executa quatro passos em sequência, um 
    para cada parte da lista, iniciando pela parte 2, depois as partes 3, 4 e 1. 

    B. Espera as 4 threads concluírem a fase A (rendezvous) 

    C.  Imprime os contra-cheques dos funcionários da parte 2 da lista, gerando um 
    arquivo chamado parte2.txt  
    */

    /**
     * Semaforo usado para controlar o acesso a lista de funcionários
     */
    Semaphore mutex, mutex1, mutex2, mutex3, mutex4, barreira;

    public Thread2(Semaphore mutex, Semaphore mutex1, Semaphore mutex2, Semaphore mutex3, Semaphore mutex4, Semaphore barreira) {
        this.mutex = mutex;
        this.mutex1 = mutex1;
        this.mutex2 = mutex2;
        this.mutex3 = mutex3;
        this.mutex4 = mutex4;
        this.barreira = barreira;
    }

    @Override
    public void run() {
        CalculaINSS();
    }

    public void CalculaINSS() {
        var funcionario = Funcionarios.lFuncionarios;
        try {
            // Parte 2
            mutex2.acquire();
                var ultimaPosicaoP2 = Thread0.lParte2.get(Thread0.lParte2.size() - 1);
                for (int i = Thread0.lParte2.get(0); i < ultimaPosicaoP2; i++) {
                    funcionario.get(i).desconto_inss = funcionario.get(i).salario_bruto * 0.08;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_inss;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Inss parte 2");
            mutex2.release();
            
            // Parte 3
            mutex3.acquire();
                var ultimaPosicaoP3 = Thread0.lParte3.get(Thread0.lParte3.size() - 1);
                for (int i = Thread0.lParte3.get(0); i < ultimaPosicaoP3; i++) {
                    funcionario.get(i).desconto_inss = funcionario.get(i).salario_bruto * 0.08;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_inss;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Inss parte 3");
            mutex3.release();
            
            // Parte 4
            mutex4.acquire();
                var ultimaPosicaoP4 = Thread0.lParte4.get(Thread0.lParte4.size() - 1);
                for (int i = Thread0.lParte4.get(0); i < ultimaPosicaoP4; i++) {
                    funcionario.get(i).desconto_inss = funcionario.get(i).salario_bruto * 0.08;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_inss;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Inss parte 4");
            mutex4.release();
            
            // Parte 1
            mutex1.acquire();
                var ultimaPosicaoP1 = Thread0.lParte1.get(Thread0.lParte1.size() - 1);
                for (int i = Thread0.lParte1.get(0); i < ultimaPosicaoP1; i++) {
                    funcionario.get(i).desconto_inss = funcionario.get(i).salario_bruto * 0.08;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_inss;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Inss parte 1");
            mutex1.release();

            mutex.acquire();
                Thread0.contadorThreads++;
                if (Thread0.contadorThreads == Thread0.nThreads) {
                    barreira.release();
                }
            mutex.release();

            System.out.println("[THREAD 2] chegou na barreira");
            barreira.acquire();
            barreira.release();
            System.out.println("[THREAD 2] passou da barreira");
            
            // Ponto Critico, responsavel por imprimir a parte 2
            mutex2.acquire();
                // Imprime contra cheque
                ImprimeContraCheque();
            mutex2.release();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ImprimeContraCheque() {
        try {
            Path path = FileSystems.getDefault().getPath("");
		    String directoryName = path.toAbsolutePath().toString();
            System.out.println(directoryName + "\\src\\arquivos\\parte2.txt");

            // Cria arquivo
            File file = new File(directoryName + "\\src\\arquivos\\parte2.txt");
            file.createNewFile();

            // Escreve no arquivo criado
            FileWriter myWriter = new FileWriter(directoryName + "\\src\\arquivos\\parte2.txt");
            var ultimaPosicaoP2 = Thread0.lParte2.get(Thread0.lParte2.size() - 1);
            for (int i = Thread0.lParte2.get(0); i < ultimaPosicaoP2; i++) {
                myWriter.write(Funcionarios.lFuncionarios.get(i).relatorioDeDados());
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");

        } catch (Exception e) {
            System.out.println("Erro ae escrever o arquivo");
        }
    }

}
