import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDateTime;

public class SistemaBancario {
    private String titular;
    private double saldo;
    private Scanner scanner = new Scanner(System.in);

    public void registrarCuenta() {
        System.out.print("Ingrese titular: ");
        titular = scanner.nextLine();
        System.out.print("Ingrese saldo inicial: ");
        saldo = scanner.nextDouble();
        scanner.nextLine();
        guardarMovimiento("Cuenta creada | Titular: " + titular + " | Saldo inicial: S/ " + saldo);
        System.out.println("Cuenta registrada");
    }

    public void depositar() {
        System.out.print("Monto a depositar: ");
        double monto = scanner.nextDouble();
        scanner.nextLine();
        if (monto > 0) {
            saldo += monto;
            guardarMovimiento("Titular: " + titular + " | Deposito: S/" + monto + " | Saldo actual: S/" + saldo);
            System.out.println("Deposito exitoso");
        } else {
            System.out.println("Monto invalido");
        }
    }

    public void retirar() {
        System.out.print("Monto a retirar: ");
        double monto = scanner.nextDouble();
        scanner.nextLine();
        if (monto > 0 && monto <= saldo) {
            saldo -= monto;
            guardarMovimiento("Titular: " + titular + " | Retiro: S/ " + monto + " | Saldo actual: S/ " + saldo);
            System.out.println("Retiro exitoso");
        } else {
            System.out.println("Fondos insuficientes o monto invalido");
        }
    }

    public void consultarSaldo() {
        System.out.println("Titular: " + titular);
        System.out.println("Saldo: " + saldo);
    }

    public void guardarMovimiento(String movimiento) {
        try (FileWriter archivo = new FileWriter("movimientos.txt", true)) {
            archivo.write(LocalDateTime.now() + " | " + movimiento + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar");
        }
    }

    public static void main(String[] args) {
        SistemaBancario banco = new SistemaBancario();
        Scanner entrada = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n=== SISTEMA BANCARIO ===");
            System.out.println("1. Registrar cuenta");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Consultar saldo");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = entrada.nextInt();
            switch (opcion) {
                case 1:
                    banco.registrarCuenta();
                    break;
                case 2:
                    banco.depositar();
                    break;
                case 3:
                    banco.retirar();
                    break;
                case 4:
                    banco.consultarSaldo();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } while (opcion != 5);
    }
}

