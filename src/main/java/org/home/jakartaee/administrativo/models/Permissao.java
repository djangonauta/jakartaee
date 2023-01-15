package org.home.jakartaee.administrativo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(schema = "administrativo", name = "permissao")
public class Permissao implements Serializable {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "codigo_permissao")
    private CodigoPermissao codigo;

    private String descricao;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Permissao other = (Permissao) obj;
        return this.codigo == other.codigo;
    }

    public CodigoPermissao getCodigo() {
        return codigo;
    }

    public void setCodigo(CodigoPermissao codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
