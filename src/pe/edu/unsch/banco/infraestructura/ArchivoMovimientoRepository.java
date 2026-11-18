package pe.edu.unsch.banco.infraestructura;

import pe.edu.unsch.banco.aplicacion.MovimientoRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ArchivoMovimientoRepository implements MovimientoRepository {
    private static final String RUTA_ARCHIVO = "movimientos.txt";

    @Override
    public void guardar(String movimiento) {
        try (FileWriter archivo = new FileWriter(RUTA_ARCHIVO, true)) {
            archivo.write(LocalDateTime.now() + " | " + movimiento + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el movimiento en " + RUTA_ARCHIVO, e);
        }
    }
}

