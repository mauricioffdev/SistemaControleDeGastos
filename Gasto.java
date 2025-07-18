import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Gasto {
    private LocalDate data;
    private String categoria;
    private String descricao;
    private double valor;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Gasto(LocalDate data, String categoria, String descricao, double valor) {
        this.data = data;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
    }

    // Getters e setters
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | R$ %.2f",
                data.format(formatter),
                categoria,
                descricao,
                valor);
    }
}
