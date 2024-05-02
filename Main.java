import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Sanduiche {
    private String nome;
    private Map<String, Double> ingredientes;

    public Sanduiche(String nome, Map<String, Double> ingredientes) {
        this.nome = nome;
        this.ingredientes = ingredientes;
    }

    public double calcularCusto(Map<String, Double> precosIngredientes) {
        double custoTotal = 0;
        for (Map.Entry<String, Double> entry : ingredientes.entrySet()) {
            String ingrediente = entry.getKey();
            double quantidade = entry.getValue();
            double preco = precosIngredientes.getOrDefault(ingrediente, 0.0);
            custoTotal += preco * quantidade;
        }
        return custoTotal;
    }

    public double aplicarCorrecao(double custo) {
        return custo * 1.5;
    }

    public String getNome() {
        return nome;
    }
}

public class Main {
    public static void imprimirTabela(Sanduiche[] sanduiches, Map<String, Double> precosIngredientes) {
        System.out.println(String.format("%-20s %-10s %-15s", "Sanduíche", "Custo", "Custo Corrigido"));
        for (Sanduiche sanduiche : sanduiches) {
            double custo = sanduiche.calcularCusto(precosIngredientes);
            double custoCorrigido = sanduiche.aplicarCorrecao(custo);
            System.out.println(String.format("%-20s %-10.2f %-15.2f", sanduiche.getNome(), custo, custoCorrigido));
        }
    }

    public static void main(String[] args) {
        Map<String, Double> ingredientesPrecos = new HashMap<>();
        ingredientesPrecos.put("pao", 1.31);
        ingredientesPrecos.put("carne", 16.90);
        ingredientesPrecos.put("queijo", 29.99);
        ingredientesPrecos.put("bacon", 29.99);

        Sanduiche[] sanduiches = {
            new Sanduiche("Chevron", Map.of("pao", 1.0, "carne", 0.18, "queijo", 0.05, "bacon", 0.05)),
            new Sanduiche("Dinoco", Map.of("pao", 1.0, "carne", 0.22, "queijo", 0.1, "bacon", 0.1))
        };

        Scanner scanner = new Scanner(System.in);
        imprimirTabela(sanduiches, ingredientesPrecos);

        while (true) {
            System.out.print("Qual ingrediente você deseja alterar o preço? (Digite 'sair' para finalizar): ");
            String ingrediente = scanner.nextLine().toLowerCase();
            if (ingrediente.equals("sair")) {
                break;
            }
            if (!ingredientesPrecos.containsKey(ingrediente)) {
                System.out.println("Ingrediente não encontrado.");
                continue;
            }
            System.out.print("Digite o novo preço para " + ingrediente + ": ");
            try {
                double novoPreco = Double.parseDouble(scanner.nextLine());
                ingredientesPrecos.put(ingrediente, novoPreco);
                imprimirTabela(sanduiches, ingredientesPrecos);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um preço válido.");
            }
        }
        scanner.close();
    }
}
