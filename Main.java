import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Gasto> gastos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n== Sistema de Controle de Gasto Mensal ==");
            System.out.println("1 - Adicionar gasto");
            System.out.println("2 - Listar gastos");
            System.out.println("3 - Filtrar gastos por categoria");
            System.out.println("4 - Filtrar gastos por mês/ano");
            System.out.println("5 - Editar gasto");
            System.out.println("6 - Mostrar estatísticas");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida, tente novamente.");
                continue;
            }

            switch (opcao) {
                case 1:
                    adicionarGasto();
                    break;
                case 2:
                    listarGastos();
                    break;
                case 3:
                    filtrarPorCategoria();
                    break;
                case 4:
                    filtrarPorMesAno();
                    break;
                case 5:
                    editarGasto();
                    break;
                case 6:
                    mostrarEstatisticas();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }

        }
    }

    private static void adicionarGasto() {
        try {
            System.out.print("Data (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine();
            LocalDate data = LocalDate.parse(dataStr, formatter);

            System.out.print("Categoria: ");
            String categoria = scanner.nextLine();

            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("Valor: R$ ");
            double valor = Double.parseDouble(scanner.nextLine());

            Gasto gasto = new Gasto(data, categoria, descricao, valor);
            gastos.add(gasto);

            System.out.println("Gasto adicionado com sucesso!");
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/yyyy.");
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
        }
    }

    private static void listarGastos() {
        if (gastos.isEmpty()) {
            System.out.println("Nenhum gasto cadastrado.");
            return;
        }

        // Ordena gastos por data crescente antes de mostrar
        gastos.sort((g1, g2) -> g1.getData().compareTo(g2.getData()));

        System.out.println("\n== Lista de Gastos ==");
        for (Gasto g : gastos) {
            System.out.println(g);
        }
    }

    private static void filtrarPorCategoria() {
        System.out.print("Digite a categoria para filtro: ");
        String categoriaFiltro = scanner.nextLine();

        List<Gasto> filtrados = new ArrayList<>();
        for (Gasto g : gastos) {
            if (g.getCategoria().equalsIgnoreCase(categoriaFiltro)) {
                filtrados.add(g);
            }
        }
        if (filtrados.isEmpty()) {
            System.out.println("Nenhum gasto encontrado para a categoria: " + categoriaFiltro);
        } else {
            System.out.println("Gastos na categoria " + categoriaFiltro + ":");
            for (Gasto g : filtrados) {
                System.out.println(g);
            }
        }
    }

    private static void filtrarPorMesAno() {
        try {
            System.out.print("Digite o mês (1-12): ");
            int mes = Integer.parseInt(scanner.nextLine());

            System.out.print("Digite o ano (ex: 2025): ");
            int ano = Integer.parseInt(scanner.nextLine());

            List<Gasto> filtrados = new ArrayList<>();
            for (Gasto g : gastos) {
                if (g.getData().getMonthValue() == mes && g.getData().getYear() == ano) {
                    filtrados.add(g);
                }
            }

            if (filtrados.isEmpty()) {
                System.out.println("Nenhum gasto encontrado para " + String.format("%02d/%d", mes, ano));
            } else {
                System.out.println("Gastos no mês " + String.format("%02d/%d", mes, ano) + ":");
                for (Gasto g : filtrados) {
                    System.out.println(g);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Mês ou ano inválido.");
        }
    }

    private static void listarGastosComIndices() {
        if (gastos.isEmpty()) {
            System.out.println("Nenhum gasto cadastrado.");
            return;
        }

        System.out.println("\n== Lista de Gastos ==");
        for (int i = 0; i < gastos.size(); i++) {
            System.out.println(i + " - " + gastos.get(i));
        }
    }

    private static void editarGasto() {
        listarGastosComIndices();
        System.out.print("Digite o índice do gasto que deseja editar: ");

        try {
            int idx = Integer.parseInt(scanner.nextLine());
            if (idx < 0 || idx >= gastos.size()) {
                System.out.println("Índice inválido.");
                return;
            }

            Gasto g = gastos.get(idx);

            System.out.print("Nova data (dd/MM/yyyy) ou Enter para manter (" + g.getData().format(formatter) + "): ");
            String dataStr = scanner.nextLine();
            if (!dataStr.isBlank()) {
                g.setData(LocalDate.parse(dataStr, formatter));
            }

            System.out.print("Nova categoria ou Enter para manter (" + g.getCategoria() + "): ");
            String categoria = scanner.nextLine();
            if (!categoria.isBlank()) {
                g.setCategoria(categoria);
            }

            System.out.print("Nova descrição ou Enter para manter (" + g.getDescricao() + "): ");
            String descricao = scanner.nextLine();
            if (!descricao.isBlank()) {
                g.setDescricao(descricao);
            }

            System.out.print("Novo valor ou Enter para manter (R$ " + g.getValor() + "): ");
            String valorStr = scanner.nextLine();
            if (!valorStr.isBlank()) {
                g.setValor(Double.parseDouble(valorStr));
            }

            System.out.println("Gasto atualizado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido.");
        }
    }

    private static void mostrarEstatisticas() {
        if (gastos.isEmpty()) {
            System.out.println("Nenhum gasto cadastrado.");
            return;
        }

        double soma = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;

        for (Gasto g : gastos) {
            double val = g.getValor();
            soma += val;
            if (val > max) max = val;
            if (val < min) min = val;
        }

        double media = soma / gastos.size();

        System.out.printf("Estatísticas:\n- Média: R$ %.2f\n- Maior gasto: R$ %.2f\n- Menor gasto: R$ %.2f\n", media, max, min);
    }
}
