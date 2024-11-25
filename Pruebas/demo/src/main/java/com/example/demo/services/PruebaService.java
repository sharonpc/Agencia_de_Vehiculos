package com.example.demo.services;

import com.example.demo.clients.VehiculoServiceClient;
import com.example.demo.entities.Empleado;
import com.example.demo.entities.dto.EmpleadoDto;
import com.example.demo.entities.dto.PruebaDTO;
import com.example.demo.entities.Interesado;
import com.example.demo.entities.Prueba;
import com.example.demo.entities.dto.VehiculoDTO;
import com.example.demo.repositories.PruebaRepository;
import com.example.demo.services.mappers.EmpleadoMapper;
import com.example.demo.services.mappers.PruebaDtoMapper;
import com.example.demo.services.mappers.PruebaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PruebaService {

    private final PruebaRepository pruebaRepository;

    private final InteresadoService interesadoService;

    private final EmpleadoService empleadoService;

    private final VehiculoServiceClient vehiculoServiceClient;

    private final PruebaDtoMapper pruebaDtoMapper;
    private final PruebaMapper pruebaMapper;
    private final EmpleadoMapper empleadoMapper;

    @Autowired
    public PruebaService(PruebaRepository pruebaRepository, InteresadoService interesadoService, EmpleadoService empleadoService, VehiculoServiceClient vehiculoServiceClient, PruebaDtoMapper pruebaDtoMapper, PruebaMapper pruebaMapper, EmpleadoMapper empleadoMapper) {
        this.pruebaRepository = pruebaRepository;
        this.interesadoService = interesadoService;
        this.empleadoService = empleadoService;
        this.vehiculoServiceClient = vehiculoServiceClient;
        this.pruebaDtoMapper = pruebaDtoMapper;
        this.pruebaMapper = pruebaMapper;
        this.empleadoMapper = empleadoMapper;
    }

    //este metodo esta incompleto, aun faltan validar y crear la prueba
    public PruebaDTO agregarPruebaValidandoLicenciaYRestringido(PruebaDTO pruebaDTO) {
        Prueba prueba = pruebaMapper.apply(pruebaDTO); //utilizo el mapper de DTO a Prueba
        //primero traemos el Interesado, Empleado y Vehiculo del objeto prueba pasado en el body...y los buscamos en la base de datos para ver si existen, si es asi los asignamos a variables
        String documentoInteresado = prueba.getInteresado().getDocumento(); //extraigo el documento del interesado pasado en el body de prueba
        Interesado interesado = interesadoService.consultarInteresado(documentoInteresado);//busco el interesado por documento
        int legajoEmpleado = prueba.getEmpleado().getLegajo();
        EmpleadoDto empleadoDto = empleadoService.consultarEmpleadoPorLegajo(legajoEmpleado);
        Empleado empleado = empleadoMapper.apply(empleadoDto);
        int idVehiculo = prueba.getIdVehiculo();

        //en las lineas de abajo creamos un objeto prueba nuevo y le asignamos las entidades vehiculo, interesado y empleado que traemos de la bd (faltaria la logica de crear estas entidades si no existen en la bd)
        Prueba prueba2 = new Prueba(); //creo un objeto prueba
        prueba2.setIdVehiculo(idVehiculo); //le asigno el vehiculo que encontre en la bd
        prueba2.setInteresado(interesado); //le asigno el interesado que encontre en la bd
        prueba2.setEmpleado(empleado); //le asigno el empleado que encontre en la bd
        prueba2.setFechaHoraInicio(prueba.getFechaHoraInicio()); //le asigno la fechaHoraInicio que se paso como parametro
        prueba2.setFechaHoraFin(prueba.getFechaHoraFin()); //le asigno la fechaHoraFin que se paso como parametro
        prueba2.setComentarios(prueba.getComentarios()); //le asigno los comentarios  que se paso como parametro

        boolean vehiculoDisponible = false; //definimos una bandera para ver si el vehiculo esta ocupado o no, la usaremos mas abajo
        boolean empleadoDisponible = false;//definimos una bandera para ver si el empleado esta ocupado o no, la usaremos mas abajo

        //Guardamos en banderas, el vencimiento de licencia y si esta restringido o no
        boolean vencimientoLicencia = interesadoService.validarFechaVencimientoLicencia2(interesado);
        boolean verificarRestringido = interesado.isRestringido(); //cuidado, en vez de ser "getRestringido" parece que Spring le llama IsRestringido al get



        //verificar que el vehiculo no este siendo probado en ese mismo momento, llama al metodo que utilizamos para validar las fechas
        vehiculoDisponible = this.verificarDisponibilidadVehiculo(idVehiculo, pruebaDTO); //retorna falso si esta ocupado, o true si esta disponible
        empleadoDisponible = empleadoService.isEmpleadoDisponible(empleado, prueba);

        // Validaciones combinadas
        if (vencimientoLicencia || verificarRestringido) {
            //"INTERESADO NO CUMPLE CON LAS CONDICIONES DE LICENCIA O RESTRINGIDO"
            System.out.println("INTERESADO NO CUMPLE CON LAS CONDICIONES DE LICENCIA O RESTRINGIDO");
            return null;
        }

        if (!vehiculoDisponible) {
            //vehiculo OCUPADO
            System.out.println("el vehiculo no esta disponible");
            return null;
        }

        if (!empleadoDisponible) {
            //"EMPLEADO OCUPADO"
            System.out.println("el empleado no esta disponible");
            return null;
        }

        // Si todas las validaciones pasan, guardamos la prueba
        pruebaRepository.save(prueba);
        return pruebaDtoMapper.apply(prueba); //retorno el dto al controlador
    }


    public List<PruebaDTO> listarPruebasEnUnMomento(LocalDateTime fecha) {
        // Convertir LocalDateTime a String en el formato adecuado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = fecha.format(formatter);

        // Obtener la lista de pruebas desde el repositorio
        List<Prueba> pruebas = pruebaRepository.findPruebasEnUnMomentoNative(fechaFormateada);

        // Convertir la lista de Prueba a PruebaDTO
        return pruebas.stream().map(pruebaDtoMapper).collect(Collectors.toList());
    }

    //Reporte I
    public List<PruebaDTO> obtenerPruebasIncidentes() {
        List<Prueba> pruebasConIncidentes = pruebaRepository.findByIncidenciaTrue();
        return pruebasConIncidentes.stream()
                .map(pruebaDtoMapper)
                .collect(Collectors.toList());
    }

    //Reporte II
    public List<PruebaDTO> obtenerDetalleIncidentesPorEmpleado(int legajo) {
        List<Prueba> pruebasConIncidentes = pruebaRepository.findByEmpleado_LegajoAndAndIncidenciaTrue(legajo);
        return pruebasConIncidentes.stream()
                .map(pruebaDtoMapper)
                .collect(Collectors.toList());
    }

    //Reporte IV
    public List<PruebaDTO> obtenerPruebas(Integer id){
        return pruebaRepository.findByIdVehiculo(id).stream().map(pruebaDtoMapper).collect(Collectors.toList());
    }


    public PruebaDTO finalizarPrueba(int id, String comentarios){

        //Buscar la prueba por id
        Prueba prueba = pruebaRepository.findById(id);

        //Actualizar campos de prueba
        prueba.setComentarios(comentarios);
        prueba.setFechaHoraFin(LocalDateTime.now());

        //Guardar la prueba actualizada
        pruebaRepository.save(prueba);

        //Convertir la entidad a DTO y devolver
        return pruebaDtoMapper.apply(prueba);
    }


    public VehiculoDTO prueba(int patente) {
        VehiculoDTO vehiculoDTO = vehiculoServiceClient.obtenerVehiculoPorId(patente);
        return vehiculoDTO;
    }


    public boolean verificarDisponibilidadVehiculo(int idVehiculo, PruebaDTO pruebaDTO) {
        List<PruebaDTO> pruebaDTOS = pruebaRepository.findByIdVehiculo(idVehiculo).stream().map(pruebaDtoMapper).toList();
        for (PruebaDTO prueba1 : pruebaDTOS) { // Recorre todas las listas para ver si hay una prueba que coincida con el momento actual
            // Verifica si hay superposición de horarios con el vehículo
            if (pruebaDTO.getFechaHoraInicio().isBefore(prueba1.getFechaHoraFin()) && pruebaDTO.getFechaHoraFin().isAfter(prueba1.getFechaHoraInicio())) {
                return false; // Retorna false si hay superposición de horarios, es decir, el vehículo está ocupado
            }
        }
        return true; // Retorna true si no hay superposición de horarios, es decir, el vehículo está disponible
    }

}
