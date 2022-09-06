import java.util.ArrayList;
import java.util.Random;
public class Funcionarios {

    public static ArrayList<Funcionarios> lFuncionarios = new ArrayList<Funcionarios>();
    
    Random random = new Random();
    int codigo;
    double salario_bruto = random.nextDouble() * 10000;
    // Calculado pela Thread 1
    double desconto_imp_ren;
    // Calculado pela Thread 2
    double desconto_inss;
    // Calculado pela Thread 3
    double desconto_prev_privada;
    // Calculado pela Thread 4
    double desconto_plano_sus;
    // Atualizado quando cada desconto é calculado (pelas threads 1, 2, 3 e 4)
    double total_descontos;

    // Diferença entre o salário bruto e o total dos descontos; 
    // Valor atualizado toda vez que o total de descontos é atualizado. 
    double salario_liquido;

    public Funcionarios(int codigo) {
        this.codigo = codigo;
    }


}
