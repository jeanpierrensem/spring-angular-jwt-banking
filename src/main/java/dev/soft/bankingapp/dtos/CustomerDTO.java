package dev.soft.bankingapp.dtos;

import com.fasterxml.jackson.annotation.*;
import dev.soft.bankingapp.entities.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Getter
@Setter
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;


}
