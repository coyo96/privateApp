import { useAuth0 } from "react-native-auth0";
import useRegistrationContext from "../../hooks/useRegistrationContext"
import isDate from "../../utils/isDate";
import DateTimePicker, {DateTimePickerEvent} from "@react-native-community/datetimepicker"
import { Picker } from "@react-native-picker/picker"
import { View, Text } from "../../components/Themed";
import { TextInput } from "react-native";
import { useEffect, useState } from "react";
import CustomButton from "../../components/CustomButton";
import { router } from "expo-router";

export default function RegistrationPage2() {
    const maxDate = new Date();
    const { dateOfBirth, setDateOfBirth, gender, setGender } = useRegistrationContext();
    const { user, isLoading } = useAuth0();
    const [dateOfBirthString, setDateOfBirthString] = useState("")
    const [showCalendar, setShowCalendar] = useState(false);
    
    const handleDateOfBirthChange = (event: DateTimePickerEvent, date?: Date) => {
        if(date && event.type === "set") {
           setDateOfBirth(date)
           setDateOfBirthString(date.toLocaleDateString(undefined, {month: '2-digit', day: "2-digit", year: "numeric"}))
        }
        setShowCalendar(false)
    }

    useEffect(() => {
        if(user && user.birthdate) {
            setDateOfBirth(new Date(user.birthdate))
        }
    }, [user])

    return (
        <View>
            <Text>What's your birthday?</Text>
            <TextInput 
                value={dateOfBirthString} 
                onChangeText={(input) => setDateOfBirthString(input)} 
                onFocus={() => setShowCalendar(true)}
                onBlur={() => setShowCalendar(false)}
                ></TextInput>

            {showCalendar && <DateTimePicker 
                    mode='date' 
                    maximumDate={maxDate}
                    value={dateOfBirth}
                    onChange={handleDateOfBirthChange}  />}

            <Picker 
                selectedValue={gender}
                onValueChange={(value, index) => {
                    setGender(value)
                }}
            >
                <Picker.Item label="Male" value={"m"}/>
                <Picker.Item label="Female" value={"f"}/>
                <Picker.Item label="Non-binary" value={"n"}/>
                <Picker.Item label="I'd rather not say" value={"d"}/>
                <Picker.Item label="Other" value={"o"}/>
            </Picker>

            <CustomButton title="Continue" onPress={() => router.push("/registration/page_3")} />
        </View>
    )


}