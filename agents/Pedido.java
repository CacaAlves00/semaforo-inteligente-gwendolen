package semaforo_inteligente;


public class Pedido {
    public TipoPedido tipo;
    public double tempoPedido;

    public Pedido() {}

    public Pedido(TipoPedido tipo, double tempoPedido) {
        this.tipo = tipo;
        this.tempoPedido = tempoPedido;
    }
}
