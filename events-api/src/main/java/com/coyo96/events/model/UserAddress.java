package com.coyo96.events.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserAddress extends Location {
    @JsonAlias(value = "is_default")
    private Boolean isDefault;
}
