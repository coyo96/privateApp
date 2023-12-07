import {discoveryDocument, clientId, redirectUri, audience, getCodeChallenge , getVerifier, domain} from "../constants/AuthConfig"
import URLEncode from "../utils/URLEncode";
import * as AuthSession from 'expo-auth-session'
import toQueryString from "../utils/toQueryString";

export default async function getTokenSilently() {
    const state = Math.random().toString(36).substring(2,15);
    const codeVerifier = await getVerifier();
    const codeChallenge = URLEncode(await getCodeChallenge(codeVerifier))
    console.log(codeChallenge)
    const authRequestConfig: AuthSession.AuthRequestConfig = {
        responseType: 'code',
        codeChallenge,
        codeChallengeMethod: AuthSession.CodeChallengeMethod.S256,
        clientId,
        redirectUri,
        scopes: ['offline_access'],
        state,
        extraParams: {audience: audience}
    };
    const authRequest = new AuthSession.AuthRequest(authRequestConfig);
    
    const result = await authRequest.promptAsync(discoveryDocument);
    console.log(result)

    if(
        result?.type === 'success' &&
        result.params &&
        result.params.code &&
        result.params.state === state
    ) {
        const accessTokenRequestConfig: AuthSession.AccessTokenRequestConfig = {
            code: result.params.code,
            redirectUri,
            clientId,
            scopes: authRequestConfig.scopes
        }
        console.log(1)
        const authCodeResponse = await AuthSession.exchangeCodeAsync(accessTokenRequestConfig, discoveryDocument)
        console.log(2)
            if(authCodeResponse.accessToken) {
                console.log(authCodeResponse.accessToken)
            }
    }
    
}

export const getTokenSilently2 = async () => {
    const state = Math.random().toString(36).substring(2,15);
    const codeVerifier = await getVerifier();
    const codeChallenge = URLEncode(await getCodeChallenge(codeVerifier));
    const authRequestOptions = {
        audience,
        scope:"offline_access",
        response_type: 'code',
        client_id: clientId,
        nonce: state,
        redirect_uri: redirectUri,
        code_challenge_method: "S256",
        code_challenge: codeChallenge,
    }
    const codeRequestUrl = `https://${domain}/authorize`+ (toQueryString(authRequestOptions));
    console.log(codeRequestUrl);
    const result = await fetch(codeRequestUrl);
    console.log(result)
    
    
}


