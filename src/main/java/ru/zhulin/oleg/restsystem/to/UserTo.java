package ru.zhulin.oleg.restsystem.to;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UserTo extends BaseTo{
    @NotBlank
    private String name;
    @NotBlank
    @Length(min = 5, max = 32)
    private String password;

    public UserTo() {
    }

    public UserTo(Long id, String name, String password) {
        super(id);
        this.name = name;
        this.password = password;
    }
}
