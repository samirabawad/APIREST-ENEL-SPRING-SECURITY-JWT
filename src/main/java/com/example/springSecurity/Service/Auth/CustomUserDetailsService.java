package com.example.springSecurity.Service.Auth;

import com.example.springSecurity.Model.Entitys.User.Admin;
import com.example.springSecurity.Model.Entitys.User.Cliente;
import com.example.springSecurity.Model.Entitys.User.Empresa;
import com.example.springSecurity.Repositories.RepositoryUsers.IAdminRepository;
import com.example.springSecurity.Repositories.RepositoryUsers.IClienteRepository;
import com.example.springSecurity.Repositories.RepositoryUsers.IEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IClienteRepository clienteRepository;
    private final IEmpresaRepository empresaRepository;
    private final IAdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String rut) throws UsernameNotFoundException {
        // Busca en el repositorio de clientes
        Optional<Cliente> cliente = clienteRepository.findByRut(rut);
        if (cliente.isPresent()) {
            return cliente.get();
        }

        // Busca en el repositorio de empresa
        Optional<Empresa> empresa = empresaRepository.findByRut(rut);
        if (empresa.isPresent()) {
            return empresa.get();
        }

        // Busca en el repositorio de admin
        Optional<Admin> admin = adminRepository.findByRut(rut);
        if (admin.isPresent()) {
            return admin.get();
        }

        // Si no se encuentra en ninguno de los repositorios, lanza excepción
        throw new UsernameNotFoundException("User not found with rut: " + rut);
    }
}
