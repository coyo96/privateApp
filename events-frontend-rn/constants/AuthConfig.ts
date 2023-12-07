import { Platform } from 'react-native'
import { Buffer } from 'buffer';
import { getRandomBytes, digestStringAsync, CryptoDigestAlgorithm, CryptoEncoding } from 'expo-crypto';
import URLEncode from '../utils/URLEncode';
import { DiscoveryDocument } from 'expo-auth-session';

export const domain = "dev-sethaker.us.auth0.com";
export const clientId = "Ud60sEpQ4lzv5RVj3TZQgTrmv9A5GCID";
export const redirectUri = Platform.OS === 'android' ? "com.eventsapp.android.auth0://dev-sethaker.us.auth0.com/android/com.eventsapp.android/callback" : "com.eventsapp.ios.auth0://dev-sethaker.us.auth0.com/android/com.eventsapp.ios/callback";
export const audience = 'http://localhost:8080';


export const getVerifier = async () => {
    const byteArray = getRandomBytes(32);
    const base64string = Buffer.from(byteArray).toString('base64');
    return URLEncode(base64string)
}

export const getCodeChallenge = async (verifier: string) => {
    return await digestStringAsync(CryptoDigestAlgorithm.SHA256, verifier, { encoding: CryptoEncoding.BASE64})
}
export const discoveryDocument: DiscoveryDocument = {
                                authorizationEndpoint: `https://${domain}/authorize`,
                                tokenEndpoint: `https://${domain}/oauth/token`
                                }



