import { Stack } from "expo-router";
import { RegistrationContextProvider } from "../../context/RegistrationContext";
export default function RegistrationFlowLayout() {
    return (
        <RegistrationContextProvider>
            <Stack>
                <Stack.Screen name="index"/>
                <Stack.Screen name="page_2" />
                <Stack.Screen name="page_3" />
            </Stack>
        </RegistrationContextProvider>
    )
}