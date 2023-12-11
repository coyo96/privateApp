import { useAuth0 } from "react-native-auth0";
import useRegistrationContext from "../../hooks/useRegistrationContext";
import { useState, useEffect } from "react";
import { View, Text } from "../../components/Themed";
import { TextInput } from "react-native";
import { Address, AppUser } from "../../assets/types/registration";
import CustomButton from "../../components/CustomButton";
import { useToken } from "../../hooks/useToken";
import { registerNewUser } from "../../service/RegistrationService";
import { router } from "expo-router";

export default function RegistrationPage3() {
    const userInfo = useRegistrationContext();
    const { user } = useAuth0();
    const { accessToken } = useToken(); 
    const [addressLine1, setAddressLine1] = useState("");
    const [addressLine2, setAddressLine2] = useState("");
    const [city, setCity] = useState("");
    const [state, setState] = useState("");
    const [postalCode, setPostalCode] = useState<number>();
    const [country, setCountry] = useState("");
    
    useEffect(() => {
        const newAddress: Address = {
                addressLine1,
                addressLine2,
                city,
                stateProvince: state,
                postalCode: postalCode ? postalCode : 0,
                country,
                isDefault: true
            }
        userInfo.setAddress(newAddress)
    },[addressLine1, addressLine2, city, state, postalCode, country] )

    const handlePostalCodeChange = (input: string) => {
        if(input.length > 0) {
            try {
                const code = parseInt(input);
                setPostalCode(code)
            } catch (e) {
                console.log(e)
            }
        }
    }
    async function handleSubmit() {
        console.log(accessToken)
        const newUser : AppUser = {
            username: user?.preferredUsername,
            firstName: userInfo.firstName,
            lastName: userInfo.lastName,
            email: userInfo.email,
            emailVerified: user?.emailVerified ? user.emailVerified : false,
            dateOfBirth: userInfo.dateOfBirth,
            primaryPhone: userInfo.primaryPhone,
            genderCode: userInfo.gender ? userInfo.gender : "d",
            picture: user?.picture ? user.picture : "",
            address: userInfo.address
        }
        if(await registerNewUser(newUser, accessToken) === false) {
            alert("A registration error occurred, please try again later")
        } else {
            alert("Registration Complete")
            router.push("/")
       }
    }
    
    return (
        <View>
            <Text>Where ya from?</Text>
            <TextInput 
                value={addressLine1} 
                onChangeText={(input) => setAddressLine1(input)} 
                placeholder="Street Address" />
            <TextInput 
                value={addressLine2}
                onChangeText={(input) => setAddressLine2(input)}
                placeholder="Address Line 2 (optional)"
            />
            <TextInput
                value={city}
                onChangeText={(input) => setCity(input)}
                placeholder="City"
             /> 
             <TextInput 
                value={state}
                onChangeText={(value) => setState(value)}
                placeholder="State"
             />
            <TextInput
                inputMode="numeric" 
                value={postalCode ? postalCode.toString() : ""}
                onChangeText={handlePostalCodeChange} 
                placeholder="Zip Code"
            />
            <TextInput 
                value={country}
                onChangeText={(input) => setCountry(input)}
                placeholder="Country"
            />

            <CustomButton title="Submit" onPress={handleSubmit}/>
        </View>
    )

}