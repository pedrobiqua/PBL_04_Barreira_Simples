import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.Semaphore;

public class Thread1 extends Thread{
    /* TO DO:
    A. Calcula o imposto de renda retido na fonte para cada funcionário, atualizando 
    o seu total de descontos e o seu salário líquido. O valor de imposto de renda é 
    calculado como 20% do salário bruto. Executa quatro passos em sequência, um 
    para cada parte da lista, iniciando pela parte 1 e depois as partes 2, 3 e 4.

    B. Espera as 4 threads concluírem a fase A (rendezvous) 

    C. Imprime os contra-cheques dos funcionários da parte 1 da lista, gerando um 
    arquivo chamado parte1.txt 
    */

    Semaphore mutex, mutex1, mutex2, mutex3, mutex4, barreira;

    public Thread1(Semaphore mutex, Semaphore mutex1, Semaphore mutex2, Semaphore mutex3, Semaphore mutex4, Semaphore barreira) {
        this.mutex = mutex;
        this.mutex1 = mutex1;
        this.mutex2 = mutex2;
        this.mutex3 = mutex3;
        this.mutex4 = mutex4;
        this.barreira = barreira;
    }

    @Override
    public void run() {
        CalculaValorImpostoDeRenda();
    }


    /**
     * Calcula o imposto de renda retido na fonte para cada funcionário, atualizando 
     * o seu total de descontos e o seu salário líquido. O valor de imposto de renda é 
     * calculado como 20% do salário bruto. Executa quatro passos em sequência, um 
     * para cada parte da lista, iniciando pela parte 1 e depois as partes 2, 3 e 4.
     */
    public void CalculaValorImpostoDeRenda() {
        // Precisa arrumar as partes q ainda não estão corretas
        try {
            var funcionario = Funcionarios.lFuncionarios;
            
            // Parte 1
            mutex1.acquire();
                var ultimaPosicaoP1 = Thread0.lParte1.get(Thread0.lParte1.size() - 1);
                for (int i = Thread0.lParte1.get(0); i < ultimaPosicaoP1; i++) {
                    funcionario.get(i).desconto_imp_ren = funcionario.get(i).salario_bruto * 0.20;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_imp_ren;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Imposto de renda calculado da parte 1");
            mutex1.release();
            
            // Parte 2
            mutex2.acquire();
                var ultimaPosicaoP2 = Thread0.lParte2.get(Thread0.lParte2.size() - 1);
                for (int i = Thread0.lParte2.get(0); i < ultimaPosicaoP2; i++) {
                    funcionario.get(i).desconto_imp_ren = funcionario.get(i).salario_bruto * 0.20;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_imp_ren;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Imposto de renda calculado da parte 2");
            mutex2.release();
            
            // Parte 3
            mutex3.acquire();
                var ultimaPosicaoP3 = Thread0.lParte3.get(Thread0.lParte3.size() - 1);
                for (int i = Thread0.lParte3.get(0); i < ultimaPosicaoP3; i++) {
                    funcionario.get(i).desconto_imp_ren = funcionario.get(i).salario_bruto * 0.20;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_imp_ren;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Imposto de renda calculado da parte 3");
            mutex3.release();
            
            // Parte 4
            mutex4.acquire();
                var ultimaPosicaoP4 = Thread0.lParte4.get(Thread0.lParte4.size() - 1);
                for (int i = Thread0.lParte4.get(0); i < ultimaPosicaoP4; i++) {
                    funcionario.get(i).desconto_imp_ren = funcionario.get(i).salario_bruto * 0.20;
                    funcionario.get(i).total_descontos += funcionario.get(i).desconto_imp_ren;
                    funcionario.get(i).salario_liquido = funcionario.get(i).salario_bruto - funcionario.get(i).total_descontos;
                }
                System.out.println("Imposto de renda calculado da parte 4");
            mutex4.release();

            mutex.acquire();
                Thread0.contadorThreads++;
                if (Thread0.contadorThreads == Thread0.nThreads) {
                    barreira.release();
                }
            mutex.release();

            System.out.println("[THREAD 1] chegou na barreira");
            barreira.acquire();
            barreira.release();
            System.out.println("[THREAD 1] passou da barreira");
            
            // Ponto Critico, responsavel por imprimir a parte 1
            mutex1.acquire();
                // Imprime contra cheque
                ImprimeContraCheque();
            mutex1.release();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Imprime os contra-cheques dos funcionários da parte 1 da lista, gerando um 
       arquivo chamado parte1.txt 
     */
    public void ImprimeContraCheque() {
        try {
            Path path = FileSystems.getDefault().getPath("");
		    String directoryName = path.toAbsolutePath().toString();
            System.out.println(directoryName + "\\src\\arquivos\\parte1.txt");

            File file = new File(directoryName + "\\src\\arquivos\\parte1.txt");
            file.createNewFile();

        } catch (Exception e) {
            System.out.println("To no catch");
        }
    }
    
}
