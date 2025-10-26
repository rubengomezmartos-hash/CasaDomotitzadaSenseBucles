import java.util.Scanner;

public class PE4_wth_for {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        // Estado luces (3 habitaciones ejemplo)
        boolean[] lightOn = {false, false, false};
        int[] lightBrightness = {100, 100, 100}; // 0-100
        String[] lightColor = {"#FFFFFF", "#FFFFFF", "#FFFFFF"};

        // Estado música
        boolean musicPlaying = false;
        String currentSong = "Nada";
        int musicVolume = 50; // 0-100

        // Estado climatización
        String climateMode = "Heating"; // Heating/Cooling
        double targetTemp = 21.0;
        int fanSpeed = 50; // 0-100
        boolean ecoMode = false;

        // Estado Roomba
        String roombaStatus = "Docked"; // Docked/Cleaning/Unloading
        int roombaProgress = 0;
        String roombaNextCycle = "No programado";

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. LLUMS");
            System.out.println("2. MUSICA");
            System.out.println("3. TEMPERATURA");
            System.out.println("4. ROOMBA");
            System.out.println("5. EXIT");
            System.out.print("Selecciona una opción: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    lightsMenu(scanner, lightOn, lightBrightness, lightColor);
                    break;
                case "2":
                    musicMenu(scanner);
                    break;
                case "3":
                    String[] climateState = climateMenu(scanner, climateMode, targetTemp, fanSpeed, ecoMode);
                    climateMode = climateState[0];
                    targetTemp = Double.parseDouble(climateState[1]);
                    fanSpeed = Integer.parseInt(climateState[2]);
                    ecoMode = Boolean.parseBoolean(climateState[3]);
                    break;
                case "4":
                    String[] roombaState = roombaMenu(scanner, roombaStatus, roombaProgress, roombaNextCycle);
                    roombaStatus = roombaState[0];
                    roombaProgress = Integer.parseInt(roombaState[1]);
                    roombaNextCycle = roombaState[2];
                    break;
                case "5":
                    running = false;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }

        scanner.close();
    }

    // -------------------------
    // LUCES
    // -------------------------
    private static void lightsMenu(Scanner scanner, boolean[] lightOn, int[] brightness, String[] color) {
        boolean back = false;
        while (!back) {
            System.out.println("\nLLUMS - Submenú:");
            System.out.println("a. Controlar una habitación");
            System.out.println("b. Controlar todas las habitaciones");
            System.out.println("c. Mostrar estado");
            System.out.println("d. Volver");
            System.out.print("Selecciona una opción: ");
            String op = scanner.nextLine().trim();

            switch (op) {
                case "a":
                    int room = selectRoom(scanner, lightOn.length);
                    singleRoomLights(scanner, room, lightOn, brightness, color);
                    break;
                case "b":
                    allRoomsLights(scanner, lightOn, brightness, color);
                    break;
                case "c":
                    showLightsStatus(lightOn, brightness, color);
                    break;
                case "d":
                    back = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static int selectRoom(Scanner scanner, int maxRooms) {
        while (true) {
            System.out.print("Selecciona habitación (1-" + maxRooms + "): ");
            String s = scanner.nextLine().trim();
            try {
                int r = Integer.parseInt(s);
                if (r >= 1 && r <= maxRooms) return r - 1;
            } catch (NumberFormatException ignored) {}
            System.out.println("Entrada inválida.");
        }
    }

    private static void singleRoomLights(Scanner scanner, int room, boolean[] lightOn, int[] brightness, String[] color) {
        boolean back = false;
        while (!back) {
            System.out.println("\nHabitación " + (room + 1) + " - Opciones:");
            System.out.println("1. Encender/Apagar");
            System.out.println("2. Cambiar brillo");
            System.out.println("3. Cambiar color");
            System.out.println("4. Volver");
            System.out.print("Elige: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    lightOn[room] = !lightOn[room];
                    System.out.println("Estado cambiado: " + (lightOn[room] ? "ON" : "OFF"));
                    break;
                case "2":
                    System.out.print("Introduce brillo (0-100): ");
                    String bStr = scanner.nextLine().trim();
                    try {
                        int b = Integer.parseInt(bStr);
                        if (b < 0) b = 0;
                        if (b > 100) b = 100;
                        brightness[room] = b;
                        System.out.println("Brillo actualizado a " + b + "%");
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;
                case "3":
                    System.out.print("Introduce color hex (ej. #FFA500): ");
                    String c = scanner.nextLine().trim();
                    if (c.matches("#[0-9A-Fa-f]{6}")) {
                        color[room] = c.toUpperCase();
                        System.out.println("Color cambiado a " + color[room]);
                    } else {
                        System.out.println("Formato inválido.");
                    }
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void allRoomsLights(Scanner scanner, boolean[] lightOn, int[] brightness, String[] color) {
        boolean back = false;
        while (!back) {
            System.out.println("\nTodas las habitaciones - Opciones:");
            System.out.println("1. Encender/Apagar todas");
            System.out.println("2. Cambiar brillo todas");
            System.out.println("3. Cambiar color todas");
            System.out.println("4. Volver");
            System.out.print("Elige: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    boolean anyOn = false;
                    if (lightOn[0]) anyOn = true;
                    if (lightOn[1]) anyOn = true;
                    if (lightOn[2]) anyOn = true;

                    boolean newState = !anyOn;

                    lightOn[0] = newState;
                    lightOn[1] = newState;
                    lightOn[2] = newState;

                    System.out.println("Todas las luces " + (newState ? "encendidas" : "apagadas"));
                    break;

                case "2":
                    System.out.print("Introduce brillo (0-100) para todas: ");
                    String bStr = scanner.nextLine().trim();
                    try {
                        int b = Integer.parseInt(bStr);
                        if (b < 0) b = 0;
                        if (b > 100) b = 100;

                        brightness[0] = b;
                        brightness[1] = b;
                        brightness[2] = b;

                        System.out.println("Brillo de todas actualizado a " + b + "%");
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;

                case "3":
                    System.out.print("Introduce color hex (ej. #00FF00) para todas: ");
                    String c = scanner.nextLine().trim();
                    if (c.matches("#[0-9A-Fa-f]{6}")) {
                        color[0] = c.toUpperCase();
                        color[1] = c.toUpperCase();
                        color[2] = c.toUpperCase();
                        System.out.println("Color de todas actualizado a " + c.toUpperCase());
                    } else {
                        System.out.println("Formato inválido.");
                    }
                    break;

                case "4":
                    back = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void showLightsStatus(boolean[] lightOn, int[] brightness, String[] color) {
        System.out.println("\nEstado de luces:");
        System.out.println("Habitación 1: " + (lightOn[0] ? "On" : "Off") + " (" + brightness[0] + "% " + color[0] + ")");
        System.out.println("Habitación 2: " + (lightOn[1] ? "On" : "Off") + " (" + brightness[1] + "% " + color[1] + ")");
        System.out.println("Habitación 3: " + (lightOn[2] ? "On" : "Off") + " (" + brightness[2] + "% " + color[2] + ")");
    }

    // -------------------------
    // MÚSICA
    // -------------------------
    private static void musicMenu(Scanner scanner) {
        boolean back = false;
        boolean musicPlaying = false;
        String currentSong = "Nada";
        int volume = 50;

        while (!back) {
            System.out.println("\nMUSICA - Submenú:");
            System.out.println("a. Play/Pause");
            System.out.println("b. Cambiar canción");
            System.out.println("c. Cambiar volumen");
            System.out.println("d. Mostrar estado");
            System.out.println("e. Volver");
            System.out.print("Selecciona: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "a":
                    musicPlaying = !musicPlaying;
                    System.out.println(musicPlaying ? "Música reproducida" : "Música pausada");
                    break;
                case "b":
                    System.out.print("Introduce nombre de la canción (Formato: Titulo - Artista): ");
                    String song = scanner.nextLine().trim();
                    if (!song.isEmpty()) {
                        currentSong = song;
                        System.out.println("Reproduciendo: " + currentSong);
                        musicPlaying = true;
                    } else {
                        System.out.println("Entrada vacía.");
                    }
                    break;
                case "c":
                    System.out.print("Introduce volumen (0-100): ");
                    String vStr = scanner.nextLine().trim();
                    try {
                        int v = Integer.parseInt(vStr);
                        if (v < 0) v = 0;
                        if (v > 100) v = 100;
                        volume = v;
                        System.out.println("Volumen ajustado a " + volume + "%");
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;
                case "d":
                    System.out.println("Estado: " + (musicPlaying ? "Playing" : "Paused"));
                    System.out.println("Canción: " + currentSong);
                    System.out.println("Volumen: " + volume + "%");
                    break;
                case "e":
                    back = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // -------------------------
    // CLIMATIZACIÓN
    // -------------------------
    private static String[] climateMenu(Scanner scanner, String mode, double temp, int fan, boolean eco) {
        boolean back = false;
        String modeLocal = mode;
        double tempLocal = temp;
        int fanLocal = fan;
        boolean ecoLocal = eco;

        while (!back) {
            System.out.println("\nTEMPERATURA - Submenú:");
            System.out.println("a. Establecer modo (Heating/Cooling)");
            System.out.println("b. Establecer temperatura objetivo");
            System.out.println("c. Establecer velocidad del ventilador");
            System.out.println("d. Activar/Desactivar Eco mode");
            System.out.println("e. Mostrar estado");
            System.out.println("f. Volver");
            System.out.print("Selecciona: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "a":
                    System.out.print("Introduce modo (Heating/Cooling): ");
                    String m = scanner.nextLine().trim();
                    if (m.equalsIgnoreCase("Heating") || m.equalsIgnoreCase("Cooling")) {
                        modeLocal = m.substring(0,1).toUpperCase() + m.substring(1).toLowerCase();
                        System.out.println("Modo establecido a " + modeLocal);
                    } else {
                        System.out.println("Modo inválido.");
                    }
                    break;
                case "b":
                    System.out.print("Introduce temperatura objetivo (°C): ");
                    String tStr = scanner.nextLine().trim();
                    try {
                        double t = Double.parseDouble(tStr);
                        tempLocal = t;
                        System.out.println("Temperatura objetivo: " + tempLocal + " °C");
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;
                case "c":
                    System.out.print("Introduce velocidad ventilador (0-100): ");
                    String fStr = scanner.nextLine().trim();
                    try {
                        int f = Integer.parseInt(fStr);
                        if (f < 0) f = 0;
                        if (f > 100) f = 100;
                        fanLocal = f;
                        System.out.println("Velocidad del ventilador: " + fanLocal + "%");
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                    }
                    break;
                case "d":
                    ecoLocal = !ecoLocal;
                    System.out.println("Eco mode " + (ecoLocal ? "Activado" : "Desactivado"));
                    break;
                case "e":
                    System.out.println("Modo: " + modeLocal);
                    System.out.println("Temperatura: " + tempLocal + " °C");
                    System.out.println("Velocidad ventilador: " + fanLocal + "%");
                    System.out.println("Eco mode: " + (ecoLocal ? "Enabled" : "Disabled"));
                    break;
                case "f":
                    back = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        return new String[] { modeLocal, String.valueOf(tempLocal), String.valueOf(fanLocal), String.valueOf(ecoLocal) };
    }

    // -------------------------
    // ROOMBA
    // -------------------------
    private static String[] roombaMenu(Scanner scanner, String status, int progress, String nextCycle) {
        boolean back = false;
        String statusLocal = status;
        int progressLocal = progress;
        String nextCycleLocal = nextCycle;

        while (!back) {
            System.out.println("\nROOMBA - Submenú:");
            System.out.println("a. Iniciar/Cancelar ciclo de limpieza");
            System.out.println("b. Programar ciclo");
            System.out.println("c. Ir a la base");
            System.out.println("d. Mostrar estado");
            System.out.println("e. Volver");
            System.out.print("Selecciona: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "a":
                    if (!statusLocal.equals("Cleaning")) {
                        statusLocal = "Cleaning";
                        progressLocal = 0;
                        System.out.println("Ciclo de limpieza iniciado.");
                    } else {
                        statusLocal = "Docked";
                        progressLocal = 0;
                        System.out.println("Ciclo cancelado y Roomba anclada.");
                    }
                    break;
                case "b":
                    System.out.print("Introduce fecha y hora próxima (DD/MM/YYYY HH:MM): ");
                    String sched = scanner.nextLine().trim();
                    if (!sched.isEmpty()) {
                        nextCycleLocal = sched;
                        System.out.println("Ciclo programado: " + nextCycleLocal);
                    } else {
                        System.out.println("Entrada vacía.");
                    }
                    break;
                case "c":
                    statusLocal = "Docked";
                    progressLocal = 0;
                    System.out.println("Roomba volviendo a la base.");
                    break;
                case "d":
                    System.out.println("Status: " + statusLocal);
                    System.out.println("Progreso: " + progressLocal + "%");
                    System.out.println("Next cycle: " + nextCycleLocal);
                    break;
                case "e":
                    back = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        return new String[] { statusLocal, String.valueOf(progressLocal), nextCycleLocal };
    }
}
