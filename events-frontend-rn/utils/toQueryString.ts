interface StringMap {
    [key: string]: string;
}
export default function toQueryString(params: StringMap): string {
    return '?' +
        Object.entries(params)
            .map(
                ([key, value]) =>
                    `${encodeURIComponent(key)}=${encodeURIComponent(value)}`
            )
            .join('&');
}