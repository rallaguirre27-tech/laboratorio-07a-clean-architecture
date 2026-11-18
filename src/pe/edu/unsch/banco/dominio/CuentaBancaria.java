package pe.edu.unsch.banco.dominio;

import java.math.BigDecimal;

public class CuentaBancaria {
    private final String titular;
    private BigDecimal saldo;

    public CuentaBancaria(String titular, BigDecimal saldoInicial) {
        if (titular == null || titular.trim().isEmpty()) {
            throw new IllegalArgumentException("El titular no puede estar vacío.");
        }
        if (saldoInicial == null || saldoInicial.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo.");
        }
        this.titular = titular.trim();
        this.saldo = saldoInicial;
    }

    public void depositar(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto inválido: debe ser mayor a cero.");
        }
        this.saldo = this.saldo.add(monto);
    }

    public void retirar(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto inválido: debe ser mayor a cero.");
        }
        if (monto.compareTo(this.saldo) > 0) {
            throw new IllegalArgumentException("Fondos insuficientes para realizar el retiro.");
        }
        this.saldo = this.saldo.subtract(monto);
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}

