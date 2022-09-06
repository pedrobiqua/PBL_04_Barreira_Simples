import java.util.ArrayList;

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

    ArrayList<Funcionarios> lParte1 = new ArrayList<Funcionarios>();
    ArrayList<Funcionarios> lParte2 = new ArrayList<Funcionarios>();
    ArrayList<Funcionarios> lParte3 = new ArrayList<Funcionarios>();
    ArrayList<Funcionarios> lParte4 = new ArrayList<Funcionarios>();

    @Override
    public void run() {
        // Cria todos os funcionários
        int n = 100;
        for (int i = 0; i < n; i++) {
            // Adicionando código a todos os funcionários
            Funcionarios.lFuncionarios.add(new Funcionarios(i + 1));
        }

        // Separando a lista de funcionários em 4 partes
        divideLista4partes();

        VisualizandoListaFuncionarios();
        
    }

    public void divideLista4partes() {
        int particoes = (Funcionarios.lFuncionarios.size() / 4);
        int contador = 0;
        int i = 0;
        int partes = particoes;

        while( contador < 4){
            System.out.println("Quantidade de partições: " + particoes);
            for ( ;i < partes; i++) {
                System.out.println("Index: " + i);
                if ( contador == 0) {
                    lParte1.add(Funcionarios.lFuncionarios.get(i));
                } else if( contador == 1) {
                    lParte2.add(Funcionarios.lFuncionarios.get(i));
                } else if( contador == 2) {
                    lParte3.add(Funcionarios.lFuncionarios.get(i));
                } else if( contador == 3) {
                    lParte4.add(Funcionarios.lFuncionarios.get(i));
                }
            }

            contador++;
            i = partes;
            partes = particoes + partes;
        }
    }

    // Apenas para debugar a lista
    public void VisualizandoListaFuncionarios() {
        for (int j = 0; j < lParte4.size(); j++) {
            System.out.println(lParte4.get(j).salario_bruto);
        }
    }
}
