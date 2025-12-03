package com.saborgourmet.clienteservice.service;

import com.saborgourmet.clienteservice.dto.ClienteRequest;
import com.saborgourmet.clienteservice.dto.ClienteResponse;
import com.saborgourmet.clienteservice.entity.Cliente;
import com.saborgourmet.clienteservice.exception.DniAlreadyExistsException;
import com.saborgourmet.clienteservice.exception.ResourceNotFoundException;
import com.saborgourmet.clienteservice.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> getAllClientes(Pageable pageable) {
        // Usamos findByActivoTrue para traer solo los no eliminados
        return clienteRepository.findByActivoTrue(pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public ClienteResponse getClienteById(Long id) {
        Cliente cliente = clienteRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
        return mapToResponse(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteResponse getClienteByDni(String dni) {
        Cliente cliente = clienteRepository.findByDniAndActivoTrue(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con DNI: " + dni));
        return mapToResponse(cliente);
    }

    @Transactional
    public ClienteResponse createCliente(ClienteRequest request) {
        if (clienteRepository.existsByDni(request.getDni())) {
            throw new DniAlreadyExistsException("Ya existe un cliente con el DNI: " + request.getDni());
        }

        Cliente cliente = Cliente.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .dni(request.getDni())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .activo(true)
                .build();

        Cliente savedCliente = clienteRepository.save(cliente);
        return mapToResponse(savedCliente);
    }

    @Transactional
    public ClienteResponse updateCliente(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));

        // Validar si cambio el DNI y si el nuevo ya existe
        if (!cliente.getDni().equals(request.getDni()) && clienteRepository.existsByDni(request.getDni())) {
            throw new DniAlreadyExistsException("El DNI " + request.getDni() + " ya está en uso por otro cliente");
        }

        cliente.setNombres(request.getNombres());
        cliente.setApellidos(request.getApellidos());
        cliente.setDni(request.getDni());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());

        return mapToResponse(clienteRepository.save(cliente));
    }

    @Transactional
    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));

        // Soft Delete manual
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    private ClienteResponse mapToResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .dni(cliente.getDni())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .activo(cliente.getActivo())
                .build();
    }
}