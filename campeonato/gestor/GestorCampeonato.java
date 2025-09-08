package com.campeonato.gestor;

import java.util.ArrayList;
import java.util.List;

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
    private String posicion;

    public Jugador(String nombre, String posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPosicion() {
        return posicion;
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

public class GestorCampeonato {
    private final RegistroParticipantes registro;
    private final CalculadorBonificaciones calculador;
    private final GeneradorReporte generadorReporte;

    public GestorCampeonato(RegistroParticipantes registro, CalculadorBonificaciones calculador, GeneradorReporte generadorReporte) {
        this.registro = registro;
        this.calculador = calculador;
        this.generadorReporte = generadorReporte;
    }

    public void ejecutar() {
        registro.registrarEquiposYArbitros();
        calculador.calcularBonificaciones(registro.getEquipos());
        System.out.println("\n--- Generando Reportes ---");
        System.out.println(generadorReporte.generar(registro.getEquipos(), registro.getArbitros()));
    }

    public static void main(String[] args) {
        RegistroParticipantes registro = new RegistroParticipantes();
        CalculadorBonificaciones calculador = new CalculadorBonificacionesImpl();
        GestorCampeonato gestor = new GestorCampeonato(registro, calculador, new ReporteTexto());
        gestor.ejecutar();
        System.out.println("\n--- Generando más Reportes ---");
        gestor = new GestorCampeonato(registro, calculador, new ReporteHTML());
        gestor.ejecutar();
    }
}