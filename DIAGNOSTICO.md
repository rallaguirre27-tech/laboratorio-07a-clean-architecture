
# Diagnóstico de Arquitectura — Sistema Bancario Inicial

## 1. Tabla de Responsabilidades de `SistemaBancario`

La clase monolítica `SistemaBancario.java` concentra múltiples responsabilidades en un solo archivo, violando el Principio de Responsabilidad Única (SRP):

| Responsabilidad actual | Implementación en el código original | Cambio de requisito que obligaría a modificar la clase |
| :--- | :--- | :--- |
| **Capturar entrada de datos** | Uso directo de `Scanner` (`nextLine()`, `nextDouble()`, `nextInt()`). | Migrar la aplicación de consola a una interfaz gráfica (JavaFX), aplicación web o móvil. |
| **Validar operaciones bancarias** | Condicionales `if (monto > 0)` y `if (monto > 0 && monto <= saldo)`. | Cambiar políticas del banco (ej. exigir un saldo mínimo de apertura o cobrar comisiones por retiro). |
| **Actualizar estado y saldos** | Asignación directa sobre `titular` y operaciones `saldo += monto`, `saldo -= monto`. | Administrar múltiples cuentas simultáneas o soportar distintos tipos de cuenta (ahorros/corriente). |
| **Persistir historial en disco** | Uso directo de `new FileWriter("movimientos.txt", true)`. | Reemplazar el archivo de texto plano por una base de datos relacional (JDBC / H2 / PostgreSQL). |
| **Imprimir resultados por consola** | Llamadas a `System.out.println(...)` mezcladas dentro de los métodos de negocio. | Cambiar el idioma de los mensajes o devolver respuestas estructuradas en formato JSON/XML. |
| **Coordinar el flujo del menú** | Bucle `do-while` y estructura `switch (opcion)` en `main`. | Agregar nuevas operaciones (como transferencias entre cuentas) o cambiar el flujo de navegación. |

---

## 2. Problemas Identificados en el Código Original

1. **Alto acoplamiento (God Class):** La lógica de negocio, la presentación por consola y el acceso a archivos viven en la misma clase, impidiendo reutilizar o probar las reglas bancarias de forma aislada.
2. **Sobrescritura silenciosa de cuentas:** Al ejecutar `registrarCuenta()` por segunda vez, las variables `titular` y `saldo` se reemplazan sin verificar si ya existía una cuenta activa, provocando pérdida de datos.
3. **Validaciones incompletas:** Se permite crear una cuenta con saldo inicial negativo y ejecutar depósitos, retiros o consultas sin haber registrado una cuenta previamente (`titular == null`).
4. **Uso de `double` para dinero:** El tipo `double` utiliza aritmética de punto flotante binario, lo que introduce errores de precisión decimal en operaciones financieras.
5. **Falsa persistencia de cuentas:** El archivo `movimientos.txt` únicamente guarda una bitácora textual de transacciones, pero no reconstruye el estado de la cuenta al reiniciar el programa.

---

## 3. Respuestas a las Preguntas Orientadoras

* **¿Qué sucede si cambiamos el archivo por una base de datos?**
  Al estar `FileWriter` acoplado directamente dentro de `SistemaBancario`, sería obligatorio abrir y modificar la misma clase que gestiona los saldos y el menú, aumentando el riesgo de introducir errores en las reglas del negocio. Con Clean Architecture, bastaría con crear una nueva implementación de repositorio en la capa de infraestructura sin tocar el dominio ni los casos de uso.

* **¿Por qué la entidad bancaria no debería utilizar `Scanner` o `FileWriter`?**
  Porque una entidad de dominio debe modelar exclusivamente los conceptos y reglas puras del negocio bancario. Si depende de detalles tecnológicos de entrada/salida (`Scanner`, `System.out`, `FileWriter`), queda atada a la consola y al sistema de archivos, volviéndose rígida y difícil de probar unitariamente.

* **¿Qué pasaría al registrar una cuenta distinta?**
  En el código inicial, registrar una segunda cuenta sobrescribe directamente los atributos `titular` y `saldo` de la cuenta anterior sin emitir ninguna advertencia, perdiendo el saldo acumulado hasta ese momento.

---

## 4. Justificación de la Refactorización

Separar el sistema en capas (**Dominio**, **Aplicación**, **Infraestructura** y **Presentación**) mediante **Inversión de Dependencias** permite aislar las reglas bancarias críticas en `CuentaBancaria` (usando `BigDecimal` para exactitud monetaria), orquestar los casos de uso en `CuentaService` mediante una interfaz `MovimientoRepository` y desacoplar los detalles técnicos de consola y archivos. De este modo, el sistema se vuelve mantenible, extensible y preparado para evolucionar hacia una base de datos real en futuras iteraciones.