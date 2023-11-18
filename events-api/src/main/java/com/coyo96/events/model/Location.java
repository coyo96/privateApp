package com.coyo96.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Location {
    int locationId;
    String locationName;
    String address;
    String addressLine2;
    String city;
    String stateProvince;
    int postalCode;
    String country;
}
