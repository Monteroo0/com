import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author camper
 */
// --- Clases de Entidad (simuladas para el ejercicio) ---
class Equipo {

    private String nombre;
    private List<Jugador> jugadores = new ArrayList<>();

    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarJugador(Jugador j) {
        this.jugadores.add(j);
    }

    public List<Jugador> getJugadores() {
        return this.jugadores;
    }
}

class Jugador {

    private String nombre;
    private String posicion; // Posibles valores: "Portero", "Delantero", "Defensa"

    public Jugador(String nombre, String posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPosicion() {
        return this.posicion;
    }
}

class Arbitro {

    private String nombre;

    public Arbitro(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

/**
 * Clase principal para la gestión de un campeonato de fútbol.
 */
public class GestorCampeonato {

    class RegistroParticipantes {

        private List<Equipo> equipos = new ArrayList<>();
        private List<Arbitro> arbitros = new ArrayList<>();

        public void registrarEquiposYArbitros() {
            Equipo equipoA = new Equipo("Los Ganadores");
            equipoA.agregarJugador(new Jugador("Juan Pérez", "Delantero"));
            equipoA.agregarJugador(new Jugador("Pedro Pan", "Portero"));
            equipos.add(equipoA);
            System.out.println("Equipo 'Los Ganadores' registrado.");
            Equipo equipoB = new Equipo("Los Retadores");
            equipoB.agregarJugador(new Jugador("Alicia Smith", "Defensa"));
            equipos.add(equipoB);
            System.out.println("Equipo 'Los Retadores' registrado.");
            arbitros.add(new Arbitro("Miguel Díaz"));
            System.out.println("Árbitro 'Miguel Díaz' contratado.");
        }

        public List<Equipo> getEquipos() {
            return equipos;
        }

        public List<Arbitro> getArbitros() {
            return arbitros;
        }
    }

// Modificación en GestorCampeonato (líneas 146-152 en el código final, parcialmente)
    public class GestorCampeonato {

        private List<Equipo> equipos = new ArrayList<>();
        private List<Arbitro> arbitros = new ArrayList<>();
        private RegistroParticipantes registro = new RegistroParticipantes();

        public void registrarParticipantes() {
            registro.registrarEquiposYArbitros();
            this.equipos = registro.getEquipos();
            this.arbitros = registro.getArbitros();
        }

        /**
         * Calcula las bonificaciones para los jugadores.
         */
        public interface CalculadorBonificaciones {

            void calcularBonificaciones(List<Equipo> equipos);
        }

        class CalculadorBonificacionesImpl implements CalculadorBonificaciones {

            @Override
            public void calcularBonificaciones(List<Equipo> equipos) {
                System.out.println("\n--- Calculando Bonificaciones de Jugadores ---");
                for (Equipo equipo : equipos) {
                    for (Jugador jugador : equipo.getJugadores()) {
                        if (jugador.getPosicion().equals("Delantero")) {
                            System.out.println("Calculando bonificación alta para Delantero: " + jugador.getNombre());
                        } else if (jugador.getPosicion().equals("Portero")) {
                            System.out.println("Calculando bonificación estándar para Portero: " + jugador.getNombre());
                        } else {
                            System.out.println("Calculando bonificación base para: " + jugador.getNombre());
                        }
                    }
                }
            }
        }

        /**
         * Genera y muestra en consola diferentes tipos de reportes.
         */
// Nueva interfaz y clases (líneas 55-107 en el código final)
        public interface GeneradorReporte {

            String generar(List<Equipo> equipos, List<Arbitro> arbitros);
        }

        class ReporteTexto implements GeneradorReporte {

            @Override
            public String generar(List<Equipo> equipos, List<Arbitro> arbitros) {
                StringBuilder contenidoReporte = new StringBuilder("--- Reporte del Campeonato (TEXTO) ---\n");
                contenidoReporte.append("EQUIPOS:\n");
                for (Equipo equipo : equipos) {
                    contenidoReporte.append("- ").append(equipo.getNombre()).append("\n");
                }
                contenidoReporte.append("ÁRBITROS:\n");
                for (Arbitro arbitro : arbitros) {
                    contenidoReporte.append("- ").append(arbitro.getNombre()).append("\n");
                }
                return contenidoReporte.toString();
            }
        }

        class ReporteHTML implements GeneradorReporte {

            @Override
            public String generar(List<Equipo> equipos, List<Arbitro> arbitros) {
                StringBuilder contenidoHtml = new StringBuilder("<html><body>\n");
                contenidoHtml.append(" <h1>Reporte del Campeonato</h1>\n");
                contenidoHtml.append(" <h2>Equipos</h2>\n <ul>\n");
                for (Equipo equipo : equipos) {
                    contenidoHtml.append(" <li>").append(equipo.getNombre()).append("</li>\n");
                }
                contenidoHtml.append(" </ul>\n <h2>Árbitros</h2>\n <ul>\n");
                for (Arbitro arbitro : arbitros) {
                    contenidoHtml.append(" <li>").append(arbitro.getNombre()).append("</li>\n");
                }
                contenidoHtml.append(" </ul>\n</body></html>");
                return contenidoHtml.toString();
            }
        }

// Modificación en GestorCampeonato (reemplazo parcial, no en el código final aún)
        public void generarReportes(String formato) {
            GeneradorReporte generador;
            if (formato.equalsIgnoreCase("TEXTO")) {
                generador = new ReporteTexto();
            } else {
                generador = new ReporteHTML();
            }
            System.out.println(generador.generar(equipos, arbitros));
        }

        // Método principal para simular la ejecución del módulo
        public static void main(String[] args) {
            GestorCampeonato gestor = new GestorCampeonato();
            gestor.registrarParticipantes();
            gestor.calcularBonificaciones();

            System.out.println("\n--- Generando Reportes ---");
            gestor.generarReportes("TEXTO");

            System.out.println("\n--- Generando más Reportes ---");
            gestor.generarReportes("HTML");
        }
    }
}
