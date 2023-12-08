import { Pressable, PressableProps, Text } from "react-native";

interface ButtonProps extends PressableProps{
    title: string
}
/**
 * Custom Button combines pressables and text to allow custom styling of buttons.
 */
export default function CustomButton({ title, onPress, style, ...props}: ButtonProps) {
    return (
        <Pressable onPress={onPress} style={style} {...props}>
            <Text>{ title }</Text>   
        </Pressable>
    )
}