package pe.edu.unsch.banco.infraestructura;

import pe.edu.unsch.banco.aplicacion.CuentaService;
import pe.edu.unsch.banco.aplicacion.MovimientoRepository;
import pe.edu.unsch.banco.dominio.CuentaBancaria;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MovimientoRepository repositorio = new ArchivoMovimientoRepository();
        CuentaService servicio = new CuentaService(repositorio);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Aviso: El sistema inicia en memoria sin cuenta activa (movimientos.txt es solo historial).");

        int opcion = 0;
        do {
            System.out.println("\n=== SISTEMA BANCARIO ===");
            System.out.println("1. Registrar cuenta");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Consultar saldo");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese titular: ");
                        String titular = scanner.nextLine();
                        System.out.print("Ingrese saldo inicial: ");
                        BigDecimal saldoInicial = new BigDecimal(scanner.nextLine().trim());
                        servicio.registrarCuenta(titular, saldoInicial);
                        System.out.println("Cuenta registrada");
                        break;
                    case 2:
                        System.out.print("Monto a depositar: ");
                        BigDecimal montoDeposito = new BigDecimal(scanner.nextLine().trim());
                        servicio.depositar(montoDeposito);
                        System.out.println("Deposito exitoso");
                        break;
                    case 3:
                        System.out.print("Monto a retirar: ");
                        BigDecimal montoRetiro = new BigDecimal(scanner.nextLine().trim());
                        servicio.retirar(montoRetiro);
                        System.out.println("Retiro exitoso");
                        break;
                    case 4:
                        CuentaBancaria cuenta = servicio.consultarCuenta();
                        System.out.println("Titular: " + cuenta.getTitular());
                        System.out.println("Saldo: S/ " + cuenta.getSaldo());
                        break;
                    case 5:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numérico válido.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Operación rechazada: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Error del sistema: " + e.getMessage());
            }
        } while (opcion != 5);

        scanner.close();
    }
}


