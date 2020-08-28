package br.com.fiap.paciente.domain.dto;

import br.com.fiap.paciente.domain.Paciente;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteDTO {

    private Long codigo;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private Boolean fumante;

    public PacienteDTO (Paciente paciente) {
        this.codigo = paciente.getCodigo();
        this.nome = paciente.getNome();
        this.dataNascimento = paciente.getDataNascimento();
        this.cpf = paciente.getCpf();
        this.fumante = paciente.getFumante();
    }

    public static List<PacienteDTO> converter(List<Paciente> pacientes) {
        return pacientes.stream()
                .map(PacienteDTO::new)
                .collect(Collectors.toList());
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Boolean getFumante() {
        return fumante;
    }

    public void setFumante(Boolean fumante) {
        this.fumante = fumante;
    }
}
