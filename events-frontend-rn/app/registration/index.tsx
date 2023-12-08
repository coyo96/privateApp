import { useAuth0 } from "react-native-auth0";
import { Text, View } from "../../components/Themed"
import { TextInput } from "react-native";
import { useEffect } from "react";
import useRegistration from "../../hooks/useRegistrationContext";
import CustomButton from "../../components/CustomButton";
import { router } from "expo-router";

export default function RegisterPage() {
    const {user} = useAuth0();
    const {firstName, setFirstName, lastName, setLastName, email, setEmail } = useRegistration();
    useEffect(() => {
        if(user){
           setEmail(user.email !== undefined ? user.email : "");
           setFirstName(user.givenName !== undefined ? user.givenName : "")
           setLastName(user.familyName !== undefined ? user.familyName : "")
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
                <CustomButton title="Next" onPress={() => router.push("/registration/page_2")} />
            </View>
        </View>
    )
}