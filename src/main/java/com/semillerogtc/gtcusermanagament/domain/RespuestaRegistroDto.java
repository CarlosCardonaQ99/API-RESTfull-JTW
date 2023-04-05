package com.semillerogtc.gtcusermanagament.domain;

import java.time.LocalDateTime;

public class RespuestaRegistroDto {


    private LocalDateTime creado;
    private LocalDateTime modificado;
    private LocalDateTime ultimoAcceso;

    public RespuestaRegistroDto( LocalDateTime creado, LocalDateTime modificado, LocalDateTime ultimoAcceso) {

        this.creado = creado;
        this.modificado = modificado;
        this.ultimoAcceso = ultimoAcceso;
    }

    public LocalDateTime getCreado() {
        return creado;
    }

    public void setCreado(LocalDateTime creado) {
        this.creado = creado;
    }

    public LocalDateTime getModificado() {
        return modificado;
    }

    public void setModificado(LocalDateTime modificado) {
        this.modificado = modificado;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
}

