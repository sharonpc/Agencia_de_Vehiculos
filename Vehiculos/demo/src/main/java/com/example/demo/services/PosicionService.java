package com.example.demo.services;


import com.example.demo.clients.EmpleadoServiceClient;
import com.example.demo.clients.NotificacionServiceClient;
import com.example.demo.clients.PruebaServiceClient;
import com.example.demo.entities.Posicion;
import com.example.demo.entities.Vehiculo;
import com.example.demo.entities.apiExternaLocalizacion.ConfiguracionApiExterna;
import com.example.demo.entities.apiExternaLocalizacion.ZonaRestringida;
import com.example.demo.entities.dto.EmpleadoDto;
import com.example.demo.entities.dto.PosicionDto;
import com.example.demo.entities.dto.PruebaDTO;
import com.example.demo.repositories.PosicionRepository;
import com.example.demo.services.mappers.PosicionDtoMapper;
import com.example.demo.services.mappers.PosicionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosicionService {

    private final VehiculoService vehiculoService;
    private final ConfiguracionServiceApiExterna configuracionService;
    private final NotificacionServiceClient notificacionServiceClient;
    private final EmpleadoServiceClient empleadoServiceClient;
    private final PosicionMapper posicionMapper;
    private final PosicionRepository posicionRepository;
    private final PosicionDtoMapper posicionDtoMapper;



    @Autowired
    public PosicionService(PosicionRepository posicionRepository, VehiculoService vehiculoService, ConfiguracionServiceApiExterna configuracionService, PruebaServiceClient pruebaServiceClient, NotificacionServiceClient notificacionServiceClient, EmpleadoServiceClient empleadoServiceClient, PosicionMapper posicionMapper, PosicionRepository posicionRepository1, PosicionDtoMapper posicionDtoMapper) {
        this.vehiculoService = vehiculoService;
        this.configuracionService = configuracionService;
        this.notificacionServiceClient = notificacionServiceClient;
        this.empleadoServiceClient = empleadoServiceClient;
        this.posicionMapper = posicionMapper;
        this.posicionRepository = posicionRepository1;
        this.posicionDtoMapper = posicionDtoMapper;
    }



    //para probar lo de abajo, simplementa cambiamos la latitud y longitud, de la fila con id 1 en la tabla Posiciones
    //lat: 42.51 long: 1.535  --> dentro del rango permitido, no genera notificacion
    //lat: -34.603722 long: -58.381592  --> fuera del rango permitido, genera notificacion y la guarda en la bd
    // lat: 42.509 long: 1.537  --> dentro de zona restringida, genera una notificacion y guarda en la bd
    public String localizacion(String patente){

        Vehiculo vehiculo = vehiculoService.consultarPorPatente(patente);

        // Obtener la última posición del vehículo
        Posicion posicion = vehiculo.getPosiciones().stream().reduce((first, second) -> second).orElseThrow();
        Double posicionlat = posicion.getLatitud();
        Double posicionlong = posicion.getLongitud();

        // Verificar si el vehículo está disponible (en prueba o no)

        PruebaDTO pruebaDTODisponible = vehiculoService.isVehiculoDisponibleAhora(vehiculo);

        // Obtener la configuración desde la API externa
        ConfiguracionApiExterna configuracion = configuracionService.obtenerConfiguracion();

        //llamar al endpoint para traer un empleado
        EmpleadoDto empleadoDto = empleadoServiceClient.obtenerEmpleadoPorId(pruebaDTODisponible.getEmpleadoLegajo());

        // Evaluar si está dentro del radio permitido desde la agencia
        double distancia = this.calcularDistanciaHaversine(
                posicionlat, posicionlong,
                configuracion.getCoordenadasAgencia().getLat(),
                configuracion.getCoordenadasAgencia().getLon()
        );

        String mensaje;
        // Verificar si el vehículo excedió el radio
        if (distancia > configuracion.getRadioAdmitidoKm()) {
            System.out.println("Notificacion generada por radio exedido");
            mensaje = "Notificacion generada por radio exedido";
            notificacionServiceClient.generarNotificacion(vehiculo.getId(), mensaje, empleadoDto.getTelefono_contacto(), pruebaDTODisponible.getInteresadoDocumento());
        }

        // Verificar si el vehículo está dentro de alguna zona restringida
        List<ZonaRestringida> zonas = configuracion.getZonasRestringidas();
        for (ZonaRestringida zona : zonas) {
            if (estaEnZonaRestringida(posicionlat, posicionlong, zona)) {
                //generarNotificacion(vehiculo, "Entró en zona restringida");
                System.out.println("Notificacion generada por zona restringida");
                mensaje = "Notificacion generada por zona restringida";
                notificacionServiceClient.generarNotificacion(vehiculo.getId(), mensaje, empleadoDto.getTelefono_contacto(),pruebaDTODisponible.getInteresadoDocumento());
                break; // Salir del ciclo si ya encontró una zona restringida
            }
        }

        return "Evaluación completada. Si el vehículo excedió el radio o entró en una zona restringida, se generó una notificación.";

    }

    // Método para verificar si un vehículo está dentro de una zona restringida
    private boolean estaEnZonaRestringida(Double lat, Double lon, ZonaRestringida zona) {
        // Aseguramos que la latitud del vehículo está dentro de los límites de la zona
        boolean latDentro = (lat >= zona.getSureste().getLat() && lat <= zona.getNoroeste().getLat()) ||
                (lat <= zona.getSureste().getLat() && lat >= zona.getNoroeste().getLat());

        // Aseguramos que la longitud del vehículo está dentro de los límites de la zona
        boolean lonDentro = (lon >= zona.getSureste().getLon() && lon <= zona.getNoroeste().getLon()) ||
                (lon <= zona.getSureste().getLon() && lon >= zona.getNoroeste().getLon());

        return latDentro && lonDentro;
    }

    // Método para generar y guardar una notificación
    /*

    */

    // Método para calcular la distancia entre dos puntos usando la fórmula de Haversine
    public double calcularDistanciaHaversine(Double lat1, Double lon1, Double lat2, Double lon2) {
        // Radio de la Tierra en kilómetros
        final double R = 6371.0; // Radio de la Tierra en km

        // Convertir latitudes y longitudes de grados a radianes
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferencias entre las latitudes y longitudes
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Aplicar la fórmula de Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcular la distancia
        return R * c; // Distancia en kilómetros
    }

    //metodo para manejar POST de posiciones de vehiculo
    public PosicionDto insertarPosicion(PosicionDto posicionDto) {
        Posicion posicion = posicionMapper.apply(posicionDto);
        System.out.println(posicion.getId());
        posicionRepository.save(posicion);
        return posicionDtoMapper.apply(posicion);
    }

}
