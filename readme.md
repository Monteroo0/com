# Refactorización LeagueMaster — Principios SOLID (Java)

## Introducción

Este proyecto es sobre un código que se llama **LeagueMaster**.
Tenía una clase llamada `GestorCampeonato` que hacía muchas cosas juntas y eso era difícil de mantener.
Con los principios **SOLID** fui cambiando el código para que sea más ordenado y más fácil de escalar.

---

## Commit 1: SRP (Single Responsibility Principle)

### Antes

```java
public class GestorCampeonato {
    private List<Equipo> equipos = new ArrayList<>();
    private List<Arbitro> arbitros = new ArrayList<>();

    public void registrarParticipantes() {
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
}
```

### Después

```java
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
    private List<Equipo> equipos = new ArrayList<>();
    private List<Arbitro> arbitros = new ArrayList<>();
    private RegistroParticipantes registro = new RegistroParticipantes();

    public void registrarParticipantes() {
        registro.registrarEquiposYArbitros();
        this.equipos = registro.getEquipos();
        this.arbitros = registro.getArbitros();
    }
}
```

---

## Commit 2: OCP (Open/Closed Principle)

### Antes

```java
public void generarReportes(String formato) {
    if (formato.equalsIgnoreCase("TEXTO")) {
        // ...
    } else if (formato.equalsIgnoreCase("HTML")) {
        // ...
    }
}
```

### Después

```java
public interface GeneradorReporte {
    String generar(List<Equipo> equipos, List<Arbitro> arbitros);
}

class ReporteTexto implements GeneradorReporte {
    @Override
    public String generar(List<Equipo> equipos, List<Arbitro> arbitros) {
        StringBuilder contenido = new StringBuilder("--- Reporte del Campeonato (TEXTO) ---\n");
        contenido.append("EQUIPOS:\n");
        for (Equipo equipo : equipos) {
            contenido.append("- ").append(equipo.getNombre()).append("\n");
        }
        contenido.append("ÁRBITROS:\n");
        for (Arbitro arbitro : arbitros) {
            contenido.append("- ").append(arbitro.getNombre()).append("\n");
        }
        return contenido.toString();
    }
}

class ReporteHTML implements GeneradorReporte {
    @Override
    public String generar(List<Equipo> equipos, List<Arbitro> arbitros) {
        StringBuilder html = new StringBuilder("<html><body>\n");
        html.append(" <h1>Reporte del Campeonato</h1>\n");
        html.append(" <h2>Equipos</h2>\n <ul>\n");
        for (Equipo equipo : equipos) {
            html.append(" <li>").append(equipo.getNombre()).append("</li>\n");
        }
        html.append(" </ul>\n <h2>Árbitros</h2>\n <ul>\n");
        for (Arbitro arbitro : arbitros) {
            html.append(" <li>").append(arbitro.getNombre()).append("</li>\n");
        }
        html.append(" </ul>\n</body></html>");
        return html.toString();
    }
}
```

---

## Commit 3: LSP (Liskov Substitution Principle)

### Código

```java
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
```

---

## Commit 4: ISP (Interface Segregation Principle)

### Antes

```java
public void calcularBonificaciones() {
    for (Equipo equipo : equipos) {
        for (Jugador jugador : equipo.getJugadores()) {
            if (jugador.getPosicion().equals("Delantero")) {
                // ...
            }
        }
    }
}
```

### Después

```java
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
                    System.out.println("Bonificación alta: " + jugador.getNombre());
                } else if (jugador.getPosicion().equals("Portero")) {
                    System.out.println("Bonificación estándar: " + jugador.getNombre());
                } else {
                    System.out.println("Bonificación base: " + jugador.getNombre());
                }
            }
        }
    }
}
```

---

## Commit 5: DIP (Dependency Inversion Principle)

### Después

```java
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
```

---

## Conclusión

Aprendí a aplicar los principios **SOLID** en Java.

* **SRP**: separé el registro en otra clase.
* **OCP**: agregué interfaz para reportes.
* **LSP**: verifiqué que Jugador está bien.
* **ISP**: hice interfaz para bonificaciones.
* **DIP**: usé inyección de dependencias.

El código ahora es más fácil de leer y mantener.
