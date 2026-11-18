package migestortareas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Gestor de tareas pendientes (To-Do List) por consola.
 * Equivalente en Java de "mi_gestor_tareas.py".
 */
public class MiGestorTareas {

    /**
     * Representa una tarea con su descripción y su estado.
     * Reemplaza al diccionario {"descripcion": ..., "completada": ...} de Python.
     */
    static class Tarea {
        private String descripcion;
        private boolean completada;

        /**
         * Crea una tarea.
         * @param descripcion texto que describe la tarea
         * @param completada  true si ya fue realizada, false si está pendiente
         */
        Tarea(String descripcion, boolean completada) {
            this.descripcion = descripcion;
            this.completada = completada;
        }

        /** @return la descripción de la tarea */
        String getDescripcion() {
            return descripcion;
        }

        /** @return true si la tarea está completada */
        boolean isCompletada() {
            return completada;
        }

        /** @param completada nuevo estado de la tarea */
        void setCompletada(boolean completada) {
            this.completada = completada;
        }
    }

    /**
     * Inicializa y retorna una lista de ejemplo con 3 tareas.
     * (Función sin parámetros y con retorno).
     *
     * @return lista de tareas de ejemplo
     */
    public static List<Tarea> obtenerTareas() {
        List<Tarea> listaTareas = new ArrayList<>();
        listaTareas.add(new Tarea("Estudiar unidad virtual de programación", false));
        listaTareas.add(new Tarea("Entregar el informe de buenas prácticas", false));
        listaTareas.add(new Tarea("Hacer ejercicio", true));
        return listaTareas;
    }

    /**
     * Muestra las tareas numeradas. Usa [X] si está completada y [ ] si está pendiente.
     * (Función con parámetros y sin retorno).
     *
     * @param listaTareas lista de tareas a imprimir
     */
    public static void mostrarTareas(List<Tarea> listaTareas) {
        if (listaTareas.isEmpty()) {
            System.out.println("No hay tareas registradas.");
            return;
        }
        for (int i = 0; i < listaTareas.size(); i++) {
            Tarea tarea = listaTareas.get(i);
            String marca = tarea.isCompletada() ? "[X]" : "[ ]";
            // Se suma 1 a i para que el usuario vea la numeración desde 1
            System.out.println((i + 1) + ". " + marca + " " + tarea.getDescripcion());
        }
    }

    /**
     * Agrega una nueva tarea (pendiente) a la lista.
     * (Función con parámetros y con retorno).
     *
     * @param listaTareas lista de tareas actual
     * @param descripcion descripción de la nueva tarea
     * @return la lista de tareas modificada
     */
    public static List<Tarea> agregarTarea(List<Tarea> listaTareas, String descripcion) {
        listaTareas.add(new Tarea(descripcion, false));
        return listaTareas;
    }

    /**
     * Marca como completada la tarea de la posición indicada (la posición va de 1 a N).
     * Si la posición no existe, informa al usuario sin detener el programa.
     * (Función con parámetros y sin retorno: modifica la lista original).
     *
     * @param listaTareas lista de tareas actual
     * @param posicion    número de la tarea según se muestra en pantalla (desde 1)
     */
    public static void marcarCompletada(List<Tarea> listaTareas, int posicion) {
        try {
            // get() lanza IndexOutOfBoundsException si el índice no existe
            listaTareas.get(posicion - 1).setCompletada(true);
            System.out.println("Tarea " + posicion + " marcada como completada.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: la posición " + posicion + " no existe. "
                    + "Elige un número entre 1 y " + listaTareas.size() + ".");
        }
    }

    /**
     * Cuenta de forma RECURSIVA cuántas tareas tienen completada == false.
     * (Función recursiva con parámetros y con retorno).
     *
     * @param listaTareas lista de tareas a revisar
     * @param indice      posición actual que se está evaluando (se inicia en 0)
     * @return número total de tareas pendientes
     */
    public static int contarTareasPendientes(List<Tarea> listaTareas, int indice) {
        // CASO BASE: ya se revisó toda la lista, no quedan tareas por contar
        if (indice == listaTareas.size()) {
            return 0;
        }
        // CASO RECURSIVO: si la tarea actual está pendiente suma 1,
        // y en ambos casos se avanza al siguiente índice
        if (!listaTareas.get(indice).isCompletada()) {
            return 1 + contarTareasPendientes(listaTareas, indice + 1);
        }
        return contarTareasPendientes(listaTareas, indice + 1);
    }

    /**
     * Sobrecarga que emula el valor por defecto indice=0 de Python,
     * ya que Java no permite parámetros con valor por defecto.
     *
     * @param listaTareas lista de tareas a revisar
     * @return número total de tareas pendientes
     */
    public static int contarTareasPendientes(List<Tarea> listaTareas) {
        return contarTareasPendientes(listaTareas, 0);
    }

    /**
     * Flujo principal: inicializa la lista y muestra el menú en un bucle while.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        List<Tarea> listaTareas = obtenerTareas();
        int opcion = 0;

        while (opcion != 5) {
            System.out.println("\n===== GESTOR DE TAREAS =====");
            System.out.println("1. Ver tareas");
            System.out.println("2. Agregar tarea");
            System.out.println("3. Marcar tarea como completada");
            System.out.println("4. Mostrar total de tareas pendientes");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Escribe un número del 1 al 5.");
                opcion = 0;
                continue;
            }

            switch (opcion) {
                case 1:
                    mostrarTareas(listaTareas);
                    break;
                case 2:
                    System.out.print("Descripción de la nueva tarea: ");
                    String descripcion = entrada.nextLine().trim();
                    if (descripcion.isEmpty()) {
                        System.out.println("La descripción no puede estar vacía.");
                    } else {
                        listaTareas = agregarTarea(listaTareas, descripcion);
                        System.out.println("Tarea agregada.");
                    }
                    break;
                case 3:
                    mostrarTareas(listaTareas);
                    System.out.print("Número de la tarea a completar: ");
                    try {
                        int posicion = Integer.parseInt(entrada.nextLine().trim());
                        marcarCompletada(listaTareas, posicion);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: debes ingresar un número.");
                    }
                    break;
                case 4:
                    int pendientes = contarTareasPendientes(listaTareas);
                    System.out.println("Tareas pendientes: " + pendientes);
                    break;
                case 5:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Escribe un número del 1 al 5.");
            }
        }
        entrada.close();
    }
}
