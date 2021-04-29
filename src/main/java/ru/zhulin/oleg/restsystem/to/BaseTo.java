package ru.zhulin.oleg.restsystem.to;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseTo{
    private Long id;

    protected BaseTo(){
    }
    protected BaseTo(Long id){
        this.id = id;
    }

    public boolean isNew(){
        return (getId() == null);
    }

}
