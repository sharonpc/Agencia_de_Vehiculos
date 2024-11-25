package com.example.demo.services;

import com.example.demo.clients.PruebaServiceClient;
import com.example.demo.entities.Posicion;
import com.example.demo.entities.Vehiculo;
import com.example.demo.entities.dto.PosicionDto;
import com.example.demo.entities.dto.PruebaDTO;
import com.example.demo.entities.dto.VehiculoDTO;
import com.example.demo.repositories.PosicionRepository;
import com.example.demo.repositories.VehiculoRepository;
import com.example.demo.services.mappers.PosicionDtoMapper;
import com.example.demo.services.mappers.VehiculoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final PruebaServiceClient pruebaServiceClient;
    private  final  VehiculoMapper vehiculoMapper;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository, PruebaServiceClient pruebaServiceClient, VehiculoMapper vehiculoMapper) {
        this.vehiculoRepository = vehiculoRepository;
        this.pruebaServiceClient = pruebaServiceClient;
        this.vehiculoMapper = vehiculoMapper;
    }

    public Vehiculo consultarPorPatente(String patente) {
        return vehiculoRepository.findByPatente(patente);

    }

    //metodo para devolver una lista de pruebas que contenga un vehiculo pasado como parametro (bsuca por la patente)
    public List<PruebaDTO> buscarPruebaPorVehiculo(Vehiculo vehiculo) {
        return pruebaServiceClient.obtenerPruebasPorVehiculoId(vehiculo.getId());
    }

    public boolean isVehiculoDisponible(Vehiculo vehiculo, PruebaDTO pruebaDTO) {
        List<PruebaDTO> listaPruebasPorVehiculoPatente = this.buscarPruebaPorVehiculo(vehiculo); // Trae la lista de pruebas que tengan al vehículo pasado como parámetro
        for (PruebaDTO prueba1 : listaPruebasPorVehiculoPatente) { // Recorre todas las listas para ver si hay una prueba que coincida con el momento actual
            // Verifica si hay superposición de horarios con el vehículo
            if (pruebaDTO.getFechaHoraInicio().isBefore(prueba1.getFechaHoraFin()) && pruebaDTO.getFechaHoraFin().isAfter(prueba1.getFechaHoraInicio())) {
                return false; // Retorna false si hay superposición de horarios, es decir, el vehículo está ocupado
            }
        }
        return true; // Retorna true si no hay superposición de horarios, es decir, el vehículo está disponible
    }

    public PruebaDTO isVehiculoDisponibleAhora(Vehiculo vehiculo) {
        LocalDateTime fechaActual  = LocalDateTime.now();
        List<PruebaDTO> listaPruebasPorVehiculoPatente = this.buscarPruebaPorVehiculo(vehiculo); // Trae la lista de pruebas que tengan al vehículo pasado como parámetro

        for (PruebaDTO prueba1 : listaPruebasPorVehiculoPatente) { // Recorre todas las listas para ver si hay una prueba que coincida con el momento actual
            // Verifica si hay superposición de horarios con el vehículo
            if (prueba1.getFechaHoraInicio().isBefore(fechaActual) && prueba1.getFechaHoraFin().isAfter(fechaActual)) {
                return prueba1; // Retorna la prueba si hay superposición de horarios, es decir, el vehículo está ocupado
            }
        }
        return null;
    }

    public VehiculoDTO buscarPorId(int id){
        Vehiculo vehiculo = vehiculoRepository.findById(id);
        return vehiculoMapper.toDTO(vehiculo);
    }



    public double obtenerKilometrosRecorridos(String patente, Date fechaInicio, Date fechaFin) {
        // Obtener todas las posiciones del vehículo para la prueba especificada
        Vehiculo vehiculo = vehiculoRepository.findByPatente(patente);
        List<Posicion> posiciones = vehiculo.getPosiciones();
        List<Posicion> posicionesFiltradas = posiciones.stream()
                .filter(posicion -> {
                    Date fechaHora = posicion.getFechaHora();
                    return (fechaHora != null) &&
                            !fechaHora.before(fechaInicio) &&
                            !fechaHora.after(fechaFin);
                })
                .toList();



        double distanciaTotal = 0;

        // Recorrer todas las posiciones y calcular la distancia entre posiciones consecutivas
        for (int i = 1; i < posicionesFiltradas.size(); i++) {

            Posicion posicionAnterior = posiciones.get(i - 1);
            Posicion posicionActual = posiciones.get(i);

            // Calcular la distancia entre la posición anterior y la posición actual
            double distancia = this.calcularDistanciaHaversine(
                    posicionAnterior.getLatitud(),
                    posicionAnterior.getLongitud(),
                    posicionActual.getLatitud(),
                    posicionActual.getLongitud()
            );

            // Sumar la distancia recorrida
            distanciaTotal += distancia;
        }

        return distanciaTotal;  // Devuelve la distancia total en kilómetros
    }


    public double calcularDistanciaHaversine(Double lat1, Double lon1, Double lat2, Double lon2) {
        final double R = 6371.0;

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

}
