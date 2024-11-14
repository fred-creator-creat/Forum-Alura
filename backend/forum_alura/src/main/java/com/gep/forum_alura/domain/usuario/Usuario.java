package com.gep.forum_alura.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="usuarios")
@Entity(name="Usuario")
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //"Sempre que uma coluna da tabela do banco de dados tiver um nome diferente do da classe, na classe deve-se adicionar @Column com o nome da coluna definido no banco de dados."
    //@Column(name="username")
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String nome;
    private String sobrenome;
    private String email;
    private Boolean enabled;

    public Usuario(CriarUsuarioDTO criarUsuarioDTO, String hashedPassword) {
        this.username = criarUsuarioDTO.username();
        this.password = hashedPassword;
        this.role = Role.USUARIO;
        this.nome = capitalizado(criarUsuarioDTO.nome());
        this.sobrenome = capitalizado(criarUsuarioDTO.sobrenome());
        this.email = criarUsuarioDTO.email();
        this.enabled = true;
    }

    public void atualizarUsuarioComPassword(AtualizarUsuarioDTO atualizarUsuarioDTO, String hashedPassword) {
        if (atualizarUsuarioDTO.password() != null){
            this.password = hashedPassword;
        }
        if (atualizarUsuarioDTO.role() != null){
            this.role = atualizarUsuarioDTO.role();
        }
        if (atualizarUsuarioDTO.nome() != null){
            this.nome = capitalizado(atualizarUsuarioDTO.nome());
        }
        if (atualizarUsuarioDTO.sobrenome() != null){
            this.sobrenome = capitalizado(atualizarUsuarioDTO.sobrenome());
        }
        if (atualizarUsuarioDTO.email() != null){
            this.email = atualizarUsuarioDTO.email();
        }
        if (atualizarUsuarioDTO.enabled() != null){
            this.enabled = atualizarUsuarioDTO.enabled();
        }
    }


    public void atualizarUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO) {
        if (atualizarUsuarioDTO.role() != null){
            this.role = atualizarUsuarioDTO.role();
        }
        if (atualizarUsuarioDTO.nome() != null){
            this.nome = capitalizado(atualizarUsuarioDTO.nome());
        }
        if (atualizarUsuarioDTO.sobrenome() != null){
            this.sobrenome = capitalizado(atualizarUsuarioDTO.sobrenome());
        }
        if (atualizarUsuarioDTO.email() != null){
            this.email = atualizarUsuarioDTO.email();
        }
        if (atualizarUsuarioDTO.enabled() != null){
            this.enabled = atualizarUsuarioDTO.enabled();
        }
    }

    public void eliminarUsuario(){
        this.enabled = false;
    }

    private String capitalizado(String string) {
        return string.substring(0,1).toUpperCase()+string.substring(1).toLowerCase();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
