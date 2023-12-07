import {createContext, ReactNode, useState} from 'react'
type TokenContextProviderProps = {
    children: ReactNode
}

export type TokenContext = {
    accessToken: string | undefined,
    setAccessToken: (token: string | undefined) => void
}

export const TokenContext = createContext({} as TokenContext)

export function TokenContextProvider({ children }: TokenContextProviderProps ) {
    const [accessToken, setAccessToken] = useState<string | undefined>("");
    return <TokenContext.Provider 
                value={{
                    accessToken, 
                    setAccessToken
                }}> 
                { children }
            </TokenContext.Provider>
}