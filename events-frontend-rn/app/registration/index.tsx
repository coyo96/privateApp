import { useAuth0 } from "react-native-auth0";
import { Text, View } from "../../components/Themed"
import { TextInput } from "react-native";
import { useEffect, useState } from "react";
import useRegistration from "../../hooks/useRegistrationContext";
import CustomButton from "../../components/CustomButton";
import { router } from "expo-router";
import { formatPhoneNumber } from "../../utils/formatPhoneNumber";
import { CountryButton, CountryPicker, ListHeaderComponentProps } from "react-native-country-codes-picker";
import validator from "validator";

export default function RegisterPage() {
    const {user} = useAuth0();
    const {firstName, setFirstName, 
            lastName, setLastName, 
            email, setEmail, 
            primaryPhone, setPrimaryPhone } = useRegistration();

    const [showModal, setShowModal] = useState(false);
    const [countryCode, setCountryCode] = useState("+1")
    const [primaryPhoneText, setPrimaryPhoneText] = useState("");


    const handlePhoneChange = (input: string) => {
        const formattedNumber = formatPhoneNumber(input, primaryPhoneText)
        setPrimaryPhoneText(formattedNumber);
    }
    const validateInput = () => {
        if(validator.isMobilePhone(`${countryCode}${primaryPhoneText}`) &&
            firstName !== "" &&
            lastName !== "" &&
            validator.isEmail(email)
        ) {
            const sanitizedNumber = parseInt(validator.whitelist(`${countryCode}${primaryPhoneText}`, '0123456789'))
            setPrimaryPhone(sanitizedNumber);
            router.push("/registration/page_2")
        } else {
            console.log("Invalid information")
        }
            
    }

    useEffect(() => {
        if(user){
           setEmail(user.email !== undefined ? user.email : "");
           setFirstName(user.givenName !== undefined ? user.givenName : "")
           setLastName(user.familyName !== undefined ? user.familyName : "")
           setPrimaryPhoneText(user.phoneNumber !== undefined ? user.phoneNumber : "")
        }
    }, [user])

    
    return (
        <View>
            <Text>Thank you for registering!</Text>
            <Text>We would love to know a little bit more about you</Text>
            <View>
                <Text>What's your name?</Text>
                <TextInput value={firstName} onChangeText={(input) => setFirstName(input)} placeholder="First name"></TextInput>
                <TextInput value={lastName} onChangeText={(input) => setLastName(input)} placeholder="Last name"></TextInput>

                <Text>You got an email?</Text>
                <TextInput value={email} onChangeText={(input) => setEmail(input)} placeholder="something@email.com"></TextInput>
                

                <Text>What's your number?</Text>
                <View style={{display: "flex", flexDirection: "row"}}>
                    <CustomButton title={countryCode} onPress={() => setShowModal(true)}></CustomButton>
                    <TextInput 
                        value={primaryPhoneText} 
                        onChangeText={handlePhoneChange}
                        placeholder="Primary Phone"
                        inputMode="numeric"
                        ></TextInput>
                </View>
                <CountryPicker 
                    lang="en"
                    show={showModal}
                    pickerButtonOnPress={(item) => {
                        setCountryCode(item.dial_code);
                        setShowModal(false);
                    }}
                    ListHeaderComponent={ListHeaderComponent}
                    popularCountries={["us", "ca"]}
                />
                <CustomButton title="Next" onPress={validateInput} />
            </View>
        </View>
    )
}

//Allows for custom header country codes at the top of the country picker modal
function ListHeaderComponent({countries, lang, onPress}: ListHeaderComponentProps) {
    return (
        <View
            style={{
                paddingBottom: 20,
            }}
        >
            <Text>
                Popular countries
            </Text>
            {countries?.map((country, index) => {
                return (
                    <CountryButton key={index} item={country} name={country?.name?.[lang || 'en']} onPress={() => onPress(country)} />
                )
            })}
        </View>
    )
}