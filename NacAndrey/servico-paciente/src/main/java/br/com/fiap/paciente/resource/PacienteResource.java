package br.com.fiap.paciente.resource;

import br.com.fiap.paciente.domain.Paciente;
import br.com.fiap.paciente.domain.dto.PacienteDTO;
import br.com.fiap.paciente.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("pacientes")
public class PacienteResource {

    @Autowired
    PacienteRepository pacienteRepository;

    // consulta pelo nome/todos
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<PacienteDTO> list (String nome) {
        List<Paciente> pacientes = nome == null ?
                pacienteRepository.findAll() :
                pacienteRepository.findByNomeContaining(nome);
        return PacienteDTO.converter(pacientes);
    }

    //consulta pelo id
    @GetMapping("{id}")
    public ResponseEntity<PacienteDTO> search (@PathVariable("id") Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);

        return paciente.map(p -> ResponseEntity.ok(new PacienteDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    // cadastrar paciente
    @PostMapping
    @Transactional
    public ResponseEntity<PacienteDTO> register (@RequestBody @Valid Paciente paciente,
                                              UriComponentsBuilder uribuilder) {
        pacienteRepository.save(paciente);

        URI uri = uribuilder.path("/pacientes/{id}")
                .buildAndExpand(paciente.getCodigo()).toUri();
        return ResponseEntity.created(uri).body(new PacienteDTO(paciente));
    }

    // atualizar paciente
    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<PacienteDTO> update (@PathVariable Long id, @RequestBody @Valid Paciente pacienteUpdated) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);

        return paciente.map(p -> {
            p.setNome(pacienteUpdated.getNome());
            p.setDataNascimento(pacienteUpdated.getDataNascimento());
            p.setEmail(pacienteUpdated.getEmail());
            p.setCpf(pacienteUpdated.getCpf());
            p.setFumante(pacienteUpdated.getFumante());
            pacienteRepository.save(p);
            return ResponseEntity.ok(new PacienteDTO(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    // deletar paciente
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> remove (@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        return paciente.map(t -> {
            pacienteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
